package com.citi.isg.omc.handover;


import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
    public static void readConfig() throws Exception {
        try {

            String emailProperties = "email.properties";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties props = new Properties();
            InputStream resourceStream = loader.getResourceAsStream(emailProperties);
            props.load(resourceStream);


            Constants.delay = props.getProperty("delay");
            Constants.scheduleHours = props.getProperty("scheduleHours");
            Constants.scheduleMinutes = props.getProperty("scheduleMinutes");
            Constants.setFrom = props.getProperty("setFrom");
            Constants.emailTO = props.getProperty("emailTO");
            Constants.smtpPort = props.getProperty("smtpPort");
            Constants.smtpHost = props.getProperty("smtpHost");
            Constants.emailSubject = props.getProperty("emailSubject");
              Constants.setPassword = props.getProperty("setPassword");
               Constants.smtpAuth= props.getProperty("smtpAuth");
               Constants.smtpTLS= props.getProperty("smtpTLS");

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);

        }

    }

}

