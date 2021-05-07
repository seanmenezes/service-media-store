/*
 * (c) 2020 Pluto Inc.
 */

package com.plutotv.common.config;

import com.plutotv.test.lib.LogUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

import static org.apache.commons.lang.StringUtils.EMPTY;

public class PropertiesHolder {
    private Properties properties;
    private static PropertiesHolder instance;
    private static String resourceName = "config.properties";

    private PropertiesHolder() {
    }

    public static synchronized PropertiesHolder getInstance() {
        if (instance == null) {
            instance = new PropertiesHolder();

            try {
                instance.properties = PropertiesLoaderUtils.loadAllProperties(resourceName);
            } catch (IOException e) {
                LogUtils.logSevereMessage("Cannot load property file.");
            }
        }
        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

    public static String getProperty(String key){
        return getInstance().getProperties().getProperty(key);
    }

    public static String getServiceHost(String environment) {
        if (EMPTY.equals(environment)) {
            LogUtils.logSevereMessageThenFail("Environment is not set. Ending Test.");
        }
        return getProperty(environment);
    }
}
