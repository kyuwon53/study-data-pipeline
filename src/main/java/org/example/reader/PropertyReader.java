package org.example.reader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public static void main(String[] args) throws IOException {
        getProperty();
    }

    static void getProperty() throws IOException {
        Properties properties = new Properties();
        String propFileName = "kafka.properties";

        try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        }
        System.out.println(properties);
    }
}
