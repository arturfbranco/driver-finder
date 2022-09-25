package br.com.driver.finder.server.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesReader {
    private static PropertiesReader propertiesReader;
    private Properties properties;

    public static PropertiesReader instance() throws IOException {
        if(PropertiesReader.propertiesReader == null){
            PropertiesReader propertiesReader = new PropertiesReader();
            propertiesReader.setProperties();
            PropertiesReader.propertiesReader = propertiesReader;
        }
        return PropertiesReader.propertiesReader;
    }

    private void setProperties() throws IOException {
        Properties properties = new Properties();
        InputStream input = this.getClass().getResourceAsStream("/config.properties");
        properties.load(input);
        this.properties = properties;
    }
    public Properties getProperties(){
        return this.properties;
    }
    public Integer getReceiverPort(){
        return Integer.parseInt(this.properties.getProperty("server-port"));
    }
    public String getGoogleApiKey(){
        return this.properties.getProperty("google-api-key");
    }
}
