package com.southsystem.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gerenciador de configurações do projeto
 */
public class ConfigurationManager {
    
    private static Properties properties = new Properties();
    
    static {
        loadProperties();
    }
    
    /**
     * Carrega as propriedades dos arquivos de configuração
     */
    private static void loadProperties() {
        try (InputStream input = ConfigurationManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar config.properties: " + e.getMessage());
        }
    }
    
    /**
     * Retorna o valor de uma propriedade
     * @param key Chave da propriedade
     * @return Valor da propriedade
     */
    public static String getProperty(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }
    
    /**
     * Retorna o valor de uma propriedade com valor padrão
     * @param key Chave da propriedade
     * @param defaultValue Valor padrão
     * @return Valor da propriedade ou valor padrão
     */
    public static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue));
    }
    
    /**
     * Retorna a URL base da aplicação
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://blogdoagi.com.br/");
    }
    
    /**
     * Retorna o navegador configurado
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    /**
     * Verifica se deve executar em modo headless
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    /**
     * Retorna o timeout implícito em segundos
     */
    public static long getImplicitWait() {
        return Long.parseLong(getProperty("implicit.wait", "10"));
    }
    
    /**
     * Retorna o timeout de carregamento de página em segundos
     */
    public static long getPageLoadTimeout() {
        return Long.parseLong(getProperty("page.load.timeout", "30"));
    }
    
    /**
     * Retorna o timeout explícito em segundos
     */
    public static long getExplicitWait() {
        return Long.parseLong(getProperty("explicit.wait", "15"));
    }
}
