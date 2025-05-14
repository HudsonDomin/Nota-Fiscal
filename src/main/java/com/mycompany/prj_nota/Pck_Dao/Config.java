package com.mycompany.prj_nota.Pck_Dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties objProperties = new Properties();

    public Config(String sFileName) {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(sFileName)) {
            if (input == null) {
                throw new IOException("Arquivo " + sFileName + " não encontrado no classpath.");
            }
            objProperties.load(input);
        } catch (IOException ex) {
            System.out.println("Erro ao carregar configurações: " + ex.getMessage());
        }
    }

    public String get(String key) {
        return objProperties.getProperty(key);
    }
}
