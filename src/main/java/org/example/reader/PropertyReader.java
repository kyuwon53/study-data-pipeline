package org.example.reader;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final Logger logger = LogManager.getLogger(PropertyReader.class.getName());
    private static final String DEFAULT_PROP_FILE_NAME = "kafka.properties";
    private Properties properties;

    public PropertyReader(String propFileName) {
        String propertyFileName = propFileName == null ? DEFAULT_PROP_FILE_NAME : propFileName;
        this.properties = new Properties();

        try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (inputStream != null) {
                this.properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public PropertyReader() {
        new PropertyReader(DEFAULT_PROP_FILE_NAME);
    }

    public String getStringKey(String key) {
        if (key == null || key.isBlank()) {
            return null;
        }
        return this.properties.getProperty(key);
    }
}
