package com.dataeye.crawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ProfileManager {

    private static final Logger logger = LoggerFactory.getLogger(ProfileManager.class);

    private static Properties properties;

    public static void init(String[] args) {
        properties = new Properties();
        try {
            String filePath;
            if (args.length == 0) {
                properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
            } else {
                filePath = args[0];
                properties.load(new FileInputStream(new File(filePath)));
            }

        } catch (Exception ex) {
            logger.error(" read config.properties in classpath error! ", ex);
        }
    }

    public static Properties get() {
        return properties;
    }

    public static int getValueInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get().getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getValue(String key, String defaultValue) {
        String value = get().getProperty(key);
        if (value == null || "".equals(value)) {
            value = defaultValue;
        }
        return value;
    }

    public void runConf(String[] args) {
        try {
            String filePath;
            if (args.length == 0) {
                properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
            } else {
                filePath = args[0];
                properties.load(new FileInputStream(new File(filePath)));
            }
        } catch (Exception ex) {
            logger.error(" runConf error! ", ex);
        }

        System.out.println(ProfileManager.get().getProperty("url"));
    }

    public static void main(String[] args) {
        ProfileManager pm = new ProfileManager();
        pm.runConf(args);
    }

}
