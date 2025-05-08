package com.mycompany.prj_nota.Pck_Dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private Properties properties = new Properties();

    public Config(String fileName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IOException("Arquivo " + fileName + " não encontrado no classpath.");
            }
            properties.load(input);
        } catch (IOException ex) {
            System.out.println("Erro ao carregar configurações: " + ex.getMessage());
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
