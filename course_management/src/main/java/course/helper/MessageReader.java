package course.helper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class MessageReader {
    private static final String FILE = "messages.properties";

    public static String readMessage(String key) {
        try {
            Properties prop = new Properties();
            InputStream input = MessageReader.class.getClassLoader().getResourceAsStream(FILE);

            if (input != null) {
                prop.load(input);
            } else {
                throw new FileNotFoundException("property file '" + input + "' not found in the classpath");
            }
            return prop.getProperty(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
