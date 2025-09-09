package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static Properties properties;
    
    static {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream("C:\\Users\\chenn\\eclipse-workspace\\NOP_COMMERCE\\resources\\config.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

