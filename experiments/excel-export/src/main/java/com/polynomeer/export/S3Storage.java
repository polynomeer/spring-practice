package com.polynomeer.export;

interface S3Storage {
    String upload(String bucket, String key, byte[] bytes, String contentType) throws Exception;
}