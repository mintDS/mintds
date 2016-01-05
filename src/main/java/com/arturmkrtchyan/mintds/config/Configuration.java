package com.arturmkrtchyan.mintds.config;

import lombok.Getter;
import lombok.ToString;
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

    public static final int DEFAULT_NUMBER_OF_ELEMENTS = 16_777_216; // 2^24
    public static final double DEFAULT_FALSE_POSITIVE_PROBABILITY = 0.1;

    private ServerConfig serverConfig;
    private BloomFilterConfig bloomFilterConfig;

    @SuppressWarnings("unchecked")
    private Configuration(final String path) throws IOException {
        final Map<String, Object> configMap = loadYaml(path);

        serverConfig = readServerConfig(configMap);
        bloomFilterConfig = readBloomFilterConfig(configMap);
    }

    private Configuration() {
        this("0.0.0.0", 7657);
    }

    private Configuration(final String bindAddress, final int port) {
        serverConfig = new ServerConfig(bindAddress, port);
        bloomFilterConfig = new BloomFilterConfig(DEFAULT_NUMBER_OF_ELEMENTS, DEFAULT_FALSE_POSITIVE_PROBABILITY);
    }

    public static Configuration valueOf(final String path) throws IOException {
        return new Configuration(path);
    }

    public static Configuration valueOf(final String bindAddress, final int port) {
        return new Configuration(bindAddress, port);
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

    @SuppressWarnings("unchecked")
    private ServerConfig readServerConfig(Map<String, Object> configMap) {
        final Map<String, Object> serverConfigMap = (Map<String, Object>) configMap.get("server");
        final int port = (Integer)serverConfigMap.get("port");
        final String bindAddress = (String)serverConfigMap.get("bind-address");
        return new ServerConfig(bindAddress, port);
    }

    @SuppressWarnings("unchecked")
    private BloomFilterConfig readBloomFilterConfig(Map<String, Object> configMap) {
        final Map<String, Object> bloomFilterConfigMap = (Map<String, Object>) configMap.get("bloomfilter");
        final int elements = (Integer) bloomFilterConfigMap.get("elements");
        final double probability = (Double)bloomFilterConfigMap.get("probability");
        return new BloomFilterConfig(elements, probability);
    }

}
