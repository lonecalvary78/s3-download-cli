package tool.model;

import lombok.Data;

import java.util.List;

@Data
public class Configuration {
    private List<Environment> environments;
}
