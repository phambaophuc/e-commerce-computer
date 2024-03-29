package DoAnJava.LinhKienDienTu.utils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class S3Util {
    private static final String ACCESS_KEY = "AKIARKGNIVJVEJCZ2D4C";
    private static final String SECRET_KEY = "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";
    private static final String BUCKET = "phuc-public-image";

    public static void uploadFile(String fileName, InputStream inputStream)
            throws AwsServiceException, SdkClientException, IOException {

        S3Client client = createS3Client();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .acl("public-read")
                .contentType("image/jpeg")
                .build();

        client.putObject(request, RequestBody.fromInputStream(inputStream, inputStream.available()));
    }

    public static void deleteFile(String fileName) {
        S3Client client = createS3Client();

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();

        client.deleteObject(request);
    }

    private static S3Client createS3Client() {
        S3Client client = S3Client.builder()
                .region(Region.AP_SOUTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
                .build();

        return client;
    }
}
