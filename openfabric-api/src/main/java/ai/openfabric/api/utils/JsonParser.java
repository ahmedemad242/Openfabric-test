package ai.openfabric.api.utils;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class JsonParser {
    public static JsonObject parse(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(inputStream));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return new com.google.gson.JsonParser().parse(stringBuilder.toString()).getAsJsonObject();
    }
}
