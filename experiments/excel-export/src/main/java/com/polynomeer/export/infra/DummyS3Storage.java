package com.polynomeer.export.infra;

public class DummyS3Storage implements S3Storage {
    // Replace with AWS SDK: S3Client.putObject + generatePresignedUrl
    @Override
    public String upload(String bucket, String key, byte[] bytes, String contentType) {
        // Pretend we uploaded and return a mock pre-signed URL
        return "https://s3.example/" + bucket + "/" + key + "?token=mock";
    }
}