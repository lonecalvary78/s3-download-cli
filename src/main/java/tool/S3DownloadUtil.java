package tool;

import software.amazon.awssdk.services.s3.S3Client;
import tool.exception.NoSuchEnvironmentException;
import tool.helper.ConfigLoader;
import tool.helper.s3.StorageUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class S3DownloadUtil {
    private S3Client storageClient;
    public S3DownloadUtil(URI configFile, String environmentName, String bucketName, String keyPath) throws URISyntaxException, IOException, NoSuchEnvironmentException {
        if(environmentName != null && !environmentName.isBlank()) {
            var configuration = ConfigLoader.getInstance().loadFrom(configFile).getEnvironments().stream().filter(environment -> environment.getName().equals(environmentName)).findFirst().orElseThrow(()-> new NoSuchEnvironmentException(environmentName));
            storageClient = StorageUtil.getInstance().newS3Client(configuration.getName(), configuration.getPort(), configuration.isSecured(),configuration.isPathStyle(), configuration.getAccessKey(), configuration.getSecretKey());
        }
    }

    public void bulkDownload(String bucketName, String keyPath) throws IOException {
        StorageUtil.getInstance().bulkDownload(storageClient,bucketName,keyPath);
    }
}
