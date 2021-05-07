package com.plutotv.common;

import static com.plutotv.common.config.PropertiesHolder.getServiceHost;
import static java.lang.System.getProperty;

public class Constants {

    public static final String SERVICE_HOST = getServiceHost(getProperty("env"));
    public static final String STE_MONGO_HOST = "service-ste-mongo.plutopreprod.tv";
    public static final String SCHEME = "https";
    public static final String REPL_TYPE_UPSERT = "upsert";

    public static class RegEx {
        public static final String DATE_TIME = "\\d{4}-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.*Z";
        public static final String NUMBER = "\\d*";
        public static final String SERVICE_VERSION = "[0-9]+\\.[0-9]+\\.[0-9]+";
    }
}
