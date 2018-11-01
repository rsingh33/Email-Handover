package com.citi.isg.omc.handover;

import java.util.ArrayList;

public class Environment {
    private final static String prod = "PROD";
    private final static String uat = "UAT";
    private final static String sit = "SIT";

    static ArrayList<String> environment = new ArrayList<>();


    static ArrayList<String> getEnvironment(){

        environment.add(prod);
        environment.add(uat);
        environment.add(sit);
       
        return environment;
    }
}
