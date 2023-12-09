package client

import (
	"context"
	"fmt"
	"log"

	"github.com/aws/aws-sdk-go-v2/config"
	"github.com/aws/aws-sdk-go-v2/credentials"
	"github.com/aws/aws-sdk-go-v2/service/s3"
)

type StorageClient struct {
	StorageClient *s3.Client
}

func NewStorageClient(host string, port int, secured bool, usePathStyle bool, accessKey string, secretKey string) StorageClient {
	awsConfig, errorOnInitializeAWSConfig := config.LoadConfig(context.TODO(), config.WithCredentialsProvider(credentials.NewStaticCredentialsProvider(accessKey, secretKey)))
	if errorOnInitializeAWSConfig != nil {
		log.Fatal(errorOnInitializeAWSConfig)
	}

	storageEndpoint := constructEndpoint(host, port, secured)

	storageClient := s3.NewFromConfig(awsConfig, func(options *s3.Options) {
		options.UsePathStyle = usePathStyle
		options.BaseEndpoint = &storageEndpoint
	})

	return StorageClient{
		StorageClient: storageClient,
	}
}

func constructEndpoint(host string, port int, secured bool) string {
	if secured == true {
		return fmt.Sprintf("https://%s:%d", host, port)
	}
	return fmt.Sprintf("http://%s:%d", host, port)
}
