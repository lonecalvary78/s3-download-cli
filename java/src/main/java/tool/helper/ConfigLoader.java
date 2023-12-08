package tool.helper;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import tool.model.Configuration;
import tool.model.Environment;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
    private static ConfigLoader instance = new ConfigLoader();
    public static ConfigLoader getInstance() { return instance; }

    public Configuration loadFrom(URI configurationFile) throws IOException {
        var configuration = new Configuration();
        var yaml = new Yaml(newConstructor());
        try(var configurationFileInputStream = Files.newInputStream(Path.of(configurationFile))) {
            configuration = yaml.load(configurationFileInputStream);
        } catch (IOException errorOnFileLoading) {
            throw errorOnFileLoading;
        }
        return configuration;
    }

    private Constructor newConstructor() {
        var createdConstructor = new Constructor(Configuration.class,newLoaderOptions());
        var typeDesc = new TypeDescription(Configuration.class);
        typeDesc.putListPropertyType("environments", Environment.class);
        createdConstructor.addTypeDescription(typeDesc);
        return createdConstructor;
    }

    private LoaderOptions newLoaderOptions() {
        var loaderOptions = new LoaderOptions();
        loaderOptions.setAllowRecursiveKeys(true);
        loaderOptions.setAllowRecursiveKeys(true);
        return loaderOptions;
    }
}
