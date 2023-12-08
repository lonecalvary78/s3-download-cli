package config

import (
	"os"

	"github.com/goccy/go-yaml"
	"github.com/lonecalvary78/s3-download-cli/model"
)

func LoadFrom(configFilePath string) (model.Configuration, error) {
	var configuration model.Configuration
	contentOfConFigFile, error := os.ReadFile(configFilePath)
	if error != nil {
		return model.Configuration{}, error
	}

	errorOnUnmarshalling := yaml.Unmarshal(contentOfConFigFile, &configuration)
	if errorOnUnmarshalling != nil {
		return model.Configuration{}, errorOnUnmarshalling
	}
	return configuration, nil
}
