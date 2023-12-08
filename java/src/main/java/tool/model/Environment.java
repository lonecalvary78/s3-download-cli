package tool.model;


import lombok.Data;

@Data
public class Environment {
    private String name;
    private String host;
    private int port;
    private boolean secured;
    private boolean pathStyle;
    private String accessKey;
    private String secretKey;
}
