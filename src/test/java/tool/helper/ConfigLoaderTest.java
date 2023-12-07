package tool.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Path;

public class ConfigLoaderTest {
    @ParameterizedTest
    @ValueSource(strings = {"DEV","QA","PROD"})
    public void withPositiveCase(String specificEnvironmentName) throws IOException {
        var configFile = Path.of("sample/s3-environments.yaml").toUri();
        Assertions.assertTrue(ConfigLoader.getInstance().loadFrom(configFile).getEnvironments().stream().anyMatch(environment -> environment.getName().equals(specificEnvironmentName)));
    }
}
