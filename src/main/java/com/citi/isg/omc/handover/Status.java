package com.citi.isg.omc.handover;

import java.util.ArrayList;

public class Status {

    private final static String inProgress = "IN PROGRESS";
    private final static String completed = "COMPLETED";
    private final static String pending = "PENDING";
    private final static String newItem = "NEW";
    static  ArrayList<String> status = new ArrayList<>();


    static ArrayList<String> getStatus(){

         status.add(inProgress);
         status.add(completed);
         status.add(pending);
         status.add(newItem);
         return status;
     }


}
