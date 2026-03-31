package com.agibank.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/**
 * Gerenciador de configurações do projeto
 */
@Slf4j
public class ConfigurationManager {

    private static Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream input = ConfigurationManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            log.error("Erro ao carregar config.properties: {}", e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    public static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }

    public static String getBaseUrl() {
        return getProperty("base.url", "https://blogdoagi.com.br/");
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static long getImplicitWait() {
        return Long.parseLong(getProperty("implicit.wait", "5"));
    }

    public static long getPageLoadTimeout() {
        return Long.parseLong(getProperty("page.load.timeout", "60"));
    }

    public static long getExplicitWait() {
        return Long.parseLong(getProperty("explicit.wait", "20"));
    }
}
