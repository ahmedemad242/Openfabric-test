package ai.openfabric.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class JsonParser {
    public static Object parse(InputStream inputStream, Class t) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(inputStream));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        if(stringBuilder.toString() == null || stringBuilder.toString().equals(""))
            return null;

        return objectMapper.readValue(stringBuilder.toString(), t);
    }
}
