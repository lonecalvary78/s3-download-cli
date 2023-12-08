package model

type Configuration struct {
	Environments []Environment `yaml:"environments"`
}

type Environment struct {
	Name      string `yaml:"name"`
	Host      string `yaml:"host"`
	Port      int    `yaml:"port"`
	Secured   bool   `yaml:"secured"`
	PathStyle bool   `yaml:"pathStyle"`
	AccessKey string `yaml:"accessKey"`
	SecretKey string `yaml:"secretKey"`
}
