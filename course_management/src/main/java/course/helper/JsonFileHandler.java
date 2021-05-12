package course.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class JsonFileHandler<T> {

    public static <T> T[] convertJsonToPojo(String fileName, Class<T[]> cls) {
        try {
            URL resource = JsonFileHandler.class.getClassLoader().getResource(fileName);
            File file = Paths.get(resource.toURI()).toFile();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(file);
            return mapper.convertValue(jsonNode, cls);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
