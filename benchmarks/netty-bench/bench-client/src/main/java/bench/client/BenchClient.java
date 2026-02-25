package bench.client;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public final class BenchClient {

    public static void main(String[] args) throws Exception {
        String url = System.getProperty("url", "http://127.0.0.1:8081/");
        int concurrency = Integer.parseInt(System.getProperty("concurrency", "256"));
        int seconds = Integer.parseInt(System.getProperty("seconds", "15"));
        int warmupSeconds = Integer.parseInt(System.getProperty("warmupSeconds", "5"));
        int timeoutMillis = Integer.parseInt(System.getProperty("timeoutMillis", "2000"));

        System.out.println("Target: " + url);
        System.out.println("Concurrency: " + concurrency);
        System.out.println("Warmup: " + warmupSeconds + "s, Run: " + seconds + "s");

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(timeoutMillis))
                .version(HttpClient.Version.HTTP_1_1)
                .executor(Executors.newFixedThreadPool(Math.min(concurrency, 512)))
                .build();

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofMillis(timeoutMillis))
                .GET()
                .build();

        runPhase("warmup", client, req, concurrency, warmupSeconds);
        runPhase("run", client, req, concurrency, seconds);
    }

    private static void runPhase(String phase, HttpClient client, HttpRequest req, int concurrency, int seconds) throws Exception {
        int maxSamples = Math.max(100_000, concurrency * seconds * 2);
        long[] latMicros = new long[maxSamples];
        AtomicLong sampleIdx = new AtomicLong(0);

        AtomicLong ok = new AtomicLong(0);
        AtomicLong err = new AtomicLong(0);

        long endAt = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            pump(client, req, endAt, ok, err, latMicros, sampleIdx, done);
        }

        done.await();

        long total = ok.get() + err.get();
        double rps = total / (double) seconds;

        long usedSamples = Math.min(sampleIdx.get(), latMicros.length);
        long[] samples = Arrays.copyOf(latMicros, (int) usedSamples);
        Arrays.sort(samples);

        System.out.println("\n== " + phase.toUpperCase() + " RESULTS ==");
        System.out.printf("Requests: %d (ok=%d, err=%d)%n", total, ok.get(), err.get());
        System.out.printf("RPS: %.1f%n", rps);

        if (usedSamples > 0) {
            LongSummaryStatistics stats = Arrays.stream(samples).summaryStatistics();
            System.out.printf("Latency (microseconds): min=%d avg=%.1f max=%d%n",
                    stats.getMin(), stats.getAverage(), stats.getMax());
            System.out.printf("p50=%d p90=%d p99=%d%n",
                    percentile(samples, 50), percentile(samples, 90), percentile(samples, 99));
        }
        System.out.println();
    }

    private static void pump(HttpClient client, HttpRequest req,
                             long endAtNanos,
                             AtomicLong ok, AtomicLong err,
                             long[] latMicros, AtomicLong sampleIdx,
                             CountDownLatch done) {

        sendOnce(client, req, endAtNanos, ok, err, latMicros, sampleIdx, done);
    }

    private static void sendOnce(HttpClient client, HttpRequest req,
                                 long endAtNanos,
                                 AtomicLong ok, AtomicLong err,
                                 long[] latMicros, AtomicLong sampleIdx,
                                 CountDownLatch done) {

        if (System.nanoTime() >= endAtNanos) {
            done.countDown();
            return;
        }

        long start = System.nanoTime();
        client.sendAsync(req, HttpResponse.BodyHandlers.discarding())
                .whenComplete((resp, ex) -> {
                    long durMicros = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - start);

                    long idx = sampleIdx.getAndIncrement();
                    if (idx < latMicros.length) latMicros[(int) idx] = durMicros;

                    if (ex != null) {
                        err.incrementAndGet();
                    } else if (resp.statusCode() == 200) {
                        ok.incrementAndGet();
                    } else {
                        err.incrementAndGet();
                    }

                    sendOnce(client, req, endAtNanos, ok, err, latMicros, sampleIdx, done);
                });
    }

    private static long percentile(long[] sorted, int p) {
        if (sorted.length == 0) return 0;
        double rank = (p / 100.0) * (sorted.length - 1);
        int lo = (int) Math.floor(rank);
        int hi = (int) Math.ceil(rank);
        if (lo == hi) return sorted[lo];
        double frac = rank - lo;
        return (long) (sorted[lo] * (1 - frac) + sorted[hi] * frac);
    }
}