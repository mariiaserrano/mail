/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Setter
public class ConfigurationSingleton {
    private static ConfigurationSingleton config;

    private ConfigurationSingleton() {

    }
    public static ConfigurationSingleton getInstance() {

        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                config = yaml.loadAs(new FileInputStream("config/config.yaml"),
                        ConfigurationSingleton .class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ConfigurationSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }

    private String ruta;
}



