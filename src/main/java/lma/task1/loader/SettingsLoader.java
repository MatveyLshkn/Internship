package lma.task1.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lma.task1.adapter.LocalDateTypeAdapter;
import lma.task1.constants.CommonConstants;
import lma.task1.entity.Settings;
import lma.task1.exception.SettingMissingException;
import lma.task1.util.JsonUtil;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDate;

import static lma.task1.constants.CommonConstants.ERROR_STATUS_CODE;
import static lma.task1.constants.CommonConstants.JSON_DATA_MEMBER_NAME;
import static lma.task1.constants.CommonConstants.JSON_SETTINGS_MEMBER_NAME;
import static lma.task1.constants.CommonConstants.MISSING_EUR_COST_SETTING_MESSAGE;
import static lma.task1.constants.CommonConstants.MISSING_SETTINGS_FILE_MESSAGE;
import static lma.task1.constants.CommonConstants.MISSING_SORT_SETTING_MESSAGE;
import static lma.task1.constants.CommonConstants.MISSING_USD_COST_SETTING_MESSAGE;
import static lma.task1.constants.PathConstants.SETTINGS_FILENAME;

public class SettingsLoader {

    private static Gson gson;

    @Getter
    private static Settings settings;

    static {
        gson = JsonUtil.getGson();
        try {
            load();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static void load() throws IOException {
        JsonObject object = null;
        object = JsonUtil.parseJsonFile(SETTINGS_FILENAME)
                .getAsJsonObject(JSON_SETTINGS_MEMBER_NAME);

        settings = gson.fromJson(object, Settings.class);
        if (settings == null) {
            throw new SettingMissingException(MISSING_SETTINGS_FILE_MESSAGE);
        }
        validateSettings();
    }

    private static void validateSettings() throws SettingMissingException {
        String missingSettings = "";

        if (settings.getSortBy() == null) {
            missingSettings += MISSING_SORT_SETTING_MESSAGE + "\n";
        }
        if (settings.getStartCostUSD() == null) {
            missingSettings += MISSING_USD_COST_SETTING_MESSAGE + "\n";
        }
        if (settings.getStartCostEUR() == null) {
            missingSettings += MISSING_EUR_COST_SETTING_MESSAGE + "\n";
        }

        if (missingSettings.length() > 0) {
            throw new SettingMissingException(missingSettings);
        }
    }
}
