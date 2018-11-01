package com.citi.isg.omc.handover;

import java.util.ArrayList;

public class CurrentlyWith {

    private final static String prodSupport = "PS";
    private final static String l3 = "L3";
    private final static String services = "SERVICES";
    private final static String core = "CORE";
    private final static String config = "CONFIG";
    private final static String reporting = "REPORTING";
    private final static String tools = "TOOLS";
    private final static String MAMS = "MAMS";
    private final static String AMC = "AMC";
    private final static String KYC = "KYC";
    private final static String other = "OTHER";

    static ArrayList<String> currentlyWith = new ArrayList<>();


    static ArrayList<String> getCurrentlyWith(){
        currentlyWith.add(prodSupport);
        currentlyWith.add(l3);
        currentlyWith.add(services);
        currentlyWith.add(core);
        currentlyWith.add(config);
        currentlyWith.add(reporting);
        currentlyWith.add(tools);
        currentlyWith.add(MAMS);
        currentlyWith.add(AMC);
        currentlyWith.add(KYC);
        currentlyWith.add(other);



        return currentlyWith;
    }

}
