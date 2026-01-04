package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertyUtil_1 {

    private static final Logger LOGGER = java.util.logging.Logger.getLogger(PropertyUtil_1.class.getName());
    private static Properties properties;

    public PropertyUtil_1(String filePath) {
        File file = new File(filePath); //specify the path to your file
        try {
            FileInputStream fis = new FileInputStream(file);  // to read file
            properties = new Properties(); //load properties file into memory
            try {
                properties.load(fis); //
            } catch (Exception e) {
                LOGGER.severe("Error loading properties file: " + e.getMessage());
            }
        }
        catch(FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
