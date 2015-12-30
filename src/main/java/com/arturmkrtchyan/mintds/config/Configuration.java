package com.arturmkrtchyan.mintds.config;

import lombok.Getter;
import lombok.ToString;
import lombok.Value;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Getter
@ToString
public class Configuration {

    private ServerConfig serverConfig;
    private BloomFilterConfig bloomFilterConfig;

    private Configuration() {
    }

    @SuppressWarnings("unchecked")
    private Configuration(final String path) throws IOException {
        final Map<String, Object> configMap = loadYaml(path);

        final Map<String, Object> serverConfigMap = (Map<String, Object>) configMap.get("server");
        final int port = (Integer)serverConfigMap.get("port");
        final String bindAddress = (String)serverConfigMap.get("bind-address");
        serverConfig = new ServerConfig(bindAddress, port);
    }

    public static Configuration valueOf(final String path) throws IOException {
        return new Configuration(path);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> loadYaml(final String path) throws IOException {
        // yaml parsing
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);
        try( InputStream in = Files.newInputStream(Paths.get(path)) ) {
            return (Map<String, Object>) yaml.load(in);
        }
    }

}
