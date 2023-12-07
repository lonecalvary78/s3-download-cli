package tool.exception;

import tool.S3DownloadUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Command {
    public static void main(String... args) throws NoSuchEnvironmentException, URISyntaxException, IOException {
        var configFile = args[0];
        var environmentName = args[1];
        var bucketName = args[2];
        var targetPath = args[3];

        if(configFile == null)
            throw new IllegalArgumentException("the config file is required");

        if(environmentName == null)
            throw new IllegalArgumentException("the environmentName file is required to get detail of the specific environment");

        if(bucketName == null)
            throw new IllegalArgumentException("the target bucket name is required for dowmload the files from storage bucket");

        if(targetPath == null)
            throw new IllegalArgumentException("the target path is required for dowmload the files from storage bucket");

        var downloadUtil = new S3DownloadUtil(URI.create(configFile),environmentName);
        downloadUtil.bulkDownload(bucketName,targetPath);
    }
}
