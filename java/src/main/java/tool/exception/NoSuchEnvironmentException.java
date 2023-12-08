package tool.exception;

public class NoSuchEnvironmentException extends Exception {
    public NoSuchEnvironmentException(String environmentName) {
        super("There is no such environment profile for %s from the configuration file".formatted(environmentName));
    }

}
