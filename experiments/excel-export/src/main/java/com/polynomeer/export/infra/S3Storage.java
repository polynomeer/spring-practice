package com.polynomeer.export.infra;

public interface S3Storage {
    String upload(String bucket, String key, byte[] bytes, String contentType) throws Exception;
}