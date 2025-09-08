package com.polynomeer.export.config;

import com.polynomeer.export.app.ExportCoordinator;
import com.polynomeer.export.app.ExportDecisionService;
import com.polynomeer.export.app.PreGeneratedService;
import com.polynomeer.export.infra.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class WiringConfig {
    @Bean
    CacheService cacheService() {
        return new InMemoryCache();
    }

    @Bean
    ExcelGenerator excelGenerator() {
        return new PoiExcelGenerator();
    }

    @Bean
    S3Storage s3Storage() {
        return new DummyS3Storage();
    }

    @Bean
    PreGeneratedService preGeneratedService(ExcelGenerator g, S3Storage s) {
        return new PreGeneratedService(g, s);
    }

    @Bean
    ExportDecisionService decisionService(CacheService c) {
        return new ExportDecisionService(c);
    }

    @Bean
    ExportWorker worker(ExcelGenerator g, S3Storage s, CacheService c, PreGeneratedService p) {
        return new ExportWorker(g, s, c, p);
    }

    @Bean
    ExportQueue queue(ExportWorker w) {
        return new AsyncExportQueue(w);
    }

    @Bean
    ExportCoordinator coordinator(ExportDecisionService d, PreGeneratedService p, ExportQueue q) {
        return new ExportCoordinator(d, p, q);
    }
}