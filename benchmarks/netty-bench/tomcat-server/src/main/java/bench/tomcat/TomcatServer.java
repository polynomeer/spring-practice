package bench.tomcat;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.nio.charset.StandardCharsets;

public final class TomcatServer {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("port", "8082"));

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        // Base dir required by embedded Tomcat
        File base = new File(System.getProperty("java.io.tmpdir"), "tomcat-bench");
        base.mkdirs();
        tomcat.setBaseDir(base.getAbsolutePath());

        Context ctx = tomcat.addContext("", base.getAbsolutePath());

        HttpServlet servlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
                try {
                    byte[] body = "OK".getBytes(StandardCharsets.UTF_8);
                    resp.setStatus(200);
                    resp.setContentType("text/plain; charset=utf-8");
                    resp.setContentLength(body.length);
                    resp.getOutputStream().write(body);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        String name = "okServlet";
        Tomcat.addServlet(ctx, name, servlet);
        ctx.addServletMappingDecoded("/", name);

        tomcat.start();
        System.out.println("Tomcat listening on http://127.0.0.1:" + port + "/");
        tomcat.getServer().await();
    }
}