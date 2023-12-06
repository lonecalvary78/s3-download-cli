package tool.helper.s3;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class StorageUtil {
    private static StorageUtil instance = new StorageUtil();
    public static StorageUtil getInstance() { return instance; }


    public S3Client newS3Client(String host, int port, boolean secured, boolean pathStyle, String accessKey, String secretKey) {
        return S3Client.builder().credentialsProvider(newCredentialProvider(accessKey,secretKey)).endpointOverride(URI.create(constructEndpoint(host,port,secured))).forcePathStyle(pathStyle).build();
    }

    private String constructEndpoint(String host, int port, boolean isSecured) {
        var appender = new StringBuilder();
        if(isSecured)
            appender.append("https://");
        else
            appender.append("http://");

        appender.append(host);
        if(port>0) {
            appender.append(":%d".formatted(port));
        }

        return appender.toString();
    }

    private AwsCredentialsProvider newCredentialProvider(String accessKey, String secretKey) {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey,secretKey));
    }

    public void bulkDownload(S3Client client,String bucketName, String keyPath) throws IOException {
        var listObjectRequest = ListObjectsRequest.builder().bucket(bucketName).build();
        var listBucketObjects = client.listObjects(listObjectRequest).contents();
        for(S3Object individualObject: listBucketObjects) {
            if(individualObject.key() != null && individualObject.key().contains(keyPath)) {
                var getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(individualObject.key()).build();
                var objectContent = client.getObject(getObjectRequest).readAllBytes();
                if(objectContent.length>0) {
                    Files.write(Path.of(individualObject.key()),objectContent, StandardOpenOption.CREATE_NEW);
                }
            }
        }
    }


}
