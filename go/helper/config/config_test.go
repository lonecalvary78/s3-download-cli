package config

import (
	"strings"
	"testing"
)

func TestLoadFrom(t *testing.T) {
	environmentName := "QA"
	configuration, errorOnGetConfiguration := LoadFrom("../../../sample/s3-environments.yaml")
	if errorOnGetConfiguration != nil {
		t.Errorf("There is some error ocurred during testing[errorMsg: %s]", errorOnGetConfiguration.Error())
	}
	var matchCount int = 0
	if len(configuration.Environments) > 0 {
		for _, environment := range configuration.Environments {
			if strings.Contains(environment.Name, environmentName) {
				matchCount++
				break
			}
		}
	}

	if matchCount <= 0 {
		t.Errorf("test failed because there is no match environment")
	}
}
