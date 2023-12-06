package tool;

import software.amazon.awssdk.services.s3.S3Client;
import tool.helper.ConfigLoader;
import tool.helper.s3.StorageUtil;

import java.io.IOException;
import java.net.URISyntaxException;

public class S3DownloadUtil {
    private S3Client storageClient;
    public S3DownloadUtil(String environmentName, String bucketName, String keyPath) throws URISyntaxException, IOException {
        if(environmentName != null && !environmentName.isBlank()) {
            var configuration = ConfigLoader.getInstance().loadFrom(this.getClass().getClassLoader().getResource("s3-environments.yaml").toURI()).getEnvironments().stream().filter(environment -> environment.getName().equals(environmentName)).findFirst().get();
            storageClient = StorageUtil.getInstance().newS3Client(configuration.getName(), configuration.getPort(), configuration.isSecured(),configuration.isPathStyle(), configuration.getAccessKey(), configuration.getSecretKey());
        }
    }

    public void bulkDownload(String bucketName, String keyPath) throws IOException {
        StorageUtil.getInstance().bulkDownload(storageClient,bucketName,keyPath);
    }
}
