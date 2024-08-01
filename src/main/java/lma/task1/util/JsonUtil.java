package lma.task1.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import lma.task1.adapter.LocalDateTypeAdapter;
import lma.task1.entity.FileData;
import lma.task1.exception.JsonFileNotFoundException;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

import static lma.task1.constants.CommonConstants.EMPTY_JSON;
import static lma.task1.constants.CommonConstants.JSON_DATA_TEMPLATE;

@UtilityClass
public class JsonUtil {

    @Getter
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
    }

    private String getPathOfParentOfJarFile() {
        return new File(JsonUtil.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getFile())
                .getParentFile()
                .getPath();
    }

    public JsonObject parseJsonFile(String path) throws IOException {
        File file = new File(getPathOfParentOfJarFile() + File.separator + path);
        if (!file.exists()) {
            throw new JsonFileNotFoundException();
        }
        InputStream inputStream = new FileInputStream(file.getPath());
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(reader)
                .getAsJsonObject();
        reader.close();
        inputStream.close();

        return object;
    }

    public void writeDataToFile(FileData fileData, String filePath) throws IOException {
        String json;
        if (fileData == null) {
            json = EMPTY_JSON;
        } else {
            json = JSON_DATA_TEMPLATE.formatted(gson.toJson(fileData));
        }
        Files.write(Paths.get(getPathOfParentOfJarFile() + File.separator + filePath), json.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
    }
}