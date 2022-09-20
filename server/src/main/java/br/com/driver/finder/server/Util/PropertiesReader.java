package br.com.driver.finder.server.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesReader {
    private Properties properties;

    public Properties getProperties() throws IOException {
        if(this.properties == null){
            String configPropertiesPath = Objects.requireNonNull(Thread
                            .currentThread()
                            .getContextClassLoader()
                            .getResource("config.properties"))
                    .getPath();
            Properties properties = new Properties();
            InputStream input = new FileInputStream(configPropertiesPath);
            properties.load(input);
            this.properties = properties;
        }
        return this.properties;
    }
}
