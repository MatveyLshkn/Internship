package lma.task1.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lma.task1.entity.FileData;
import lma.task1.adapter.LocalDateTypeAdapter;
import lma.task1.entity.Settings;
import lma.task1.exception.JsonFileNotFoundException;
import lma.task1.util.JsonUtil;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.ZipFile;

import static lma.task1.constants.CommonConstants.EMPTY_JSON;
import static lma.task1.constants.CommonConstants.JSON_FILE_NOT_FOUND_MESSAGE;
import static lma.task1.constants.CommonConstants.JSON_DATA_MEMBER_NAME;
import static lma.task1.constants.CommonConstants.JSON_DATA_TEMPLATE;
import static lma.task1.constants.PathConstants.*;
import static lma.task1.util.PatternUtil.matchesDepartmentDataName;

public class DataLoader {

    private static Gson gson;

    @Getter
    private static FileData fileData;

    private static Settings settings;

    static {
        gson = JsonUtil.getGson();
        settings = SettingsLoader.getSettings();

        try {
            loadGeneralData();
            loadDepartments();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static void loadGeneralData() throws IOException {
        JsonObject object = JsonUtil.parseJsonFile(GENERAL_DATA_FILENAME)
                .getAsJsonObject(JSON_DATA_MEMBER_NAME);

        fileData = gson.fromJson(object, FileData.class);
    }

    private static void loadDepartments() throws IOException {
        String[] departments = settings.getUseDepartments();
        if (departments == null) {
            loadAllDepartments();
        } else if (departments.length > 0) {
            loadSpecificDepartments(departments);
        }
    }

    private static void loadAllDepartments() throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        File directory = new File(classloader.getResource(BASE_DATA_DIRECTORY_NAME).getFile());
        File[] files = directory.listFiles(file -> matchesDepartmentDataName(file.getName()));
        if (files != null && files.length != 0) {
            for (File file : files) {
                String departmentFileName = BASE_FILE_PREFIX_WITH_PATH + file.getName() + BASE_FILE_EXTENSION;
                loadDepartmentData(departmentFileName);
            }
        }
    }

    private static void loadSpecificDepartments(String[] departments) throws IOException {
        for (String department : departments) {
            String departmentFileName = BASE_FILE_PREFIX_WITH_PATH + department + BASE_FILE_EXTENSION;
            loadDepartmentData(departmentFileName);
        }
    }

    private static void loadDepartmentData(String departmentFileName) {
        try {
            JsonObject jsonObject = JsonUtil.parseJsonFile(departmentFileName);
            FileData departmentFileData = gson.fromJson(jsonObject, FileData.class);
            copyToFileData(departmentFileData, fileData);
            JsonUtil.writeDataToFile(fileData, GENERAL_DATA_FILENAME);
            JsonUtil.writeDataToFile(null, departmentFileName);
        } catch (JsonFileNotFoundException exception) {
            System.err.println(JSON_FILE_NOT_FOUND_MESSAGE.formatted(departmentFileName));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static void copyToFileData(FileData from, FileData to) {
        copyIfNotNull(from.getCredits(), to.getCredits());
        copyIfNotNull(from.getEvents(), to.getEvents());
        copyIfNotNull(from.getDiscounts(), to.getDiscounts());
        copyIfNotNull(from.getTransactions(), to.getTransactions());
        copyIfNotNull(from.getUsers(), to.getUsers());
    }

    private static <T> void copyIfNotNull(List<T> fromList, List<T> toList) {
        if (fromList != null) {
            toList.addAll(fromList);
        }
    }
}
