package com.citi.isg.omc.handover;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Email Scheduler class schedules the handover to be sent daily
 */
public class EmailScheduler extends TimerTask {
    private final HandoverRepository repository;

    /**
     * +
     * Constructor for the class takes a reference to current repo
     *
     * @param repository
     */
    EmailScheduler(HandoverRepository repository) {
        this.repository = repository;

    }

    @Override
    public void run() {

        HandoverEditor editor = new HandoverEditor(repository);
        editor.sendEmail();
    }

    /**
     * +
     * Sets and schedules the time and date for the scheduler
     *
     * @throws Exception
     */
    public void schedule() throws Exception {
        Timer timer = new Timer();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Constants.scheduleHours));
        cal.set(Calendar.MINUTE, Integer.parseInt(Constants.scheduleMinutes));

        Date date = cal.getTime();

        timer.scheduleAtFixedRate(new EmailScheduler(repository), date, getTimePrecision(Constants.delay));

    }

    /**
     * +
     *
     * @param value String value in days, hours minutes and seconds. Value can end with initial
     * @return long time in milliseconds
     * @throws Exception
     */
    public long getTimePrecision(String value) throws Exception {
        long l;
        String val;
        try {
            if (value.endsWith("d") || value.endsWith("D")) {
                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 24 * 60 * 60 * 1000;
            } else if (value.endsWith("h") || value.endsWith("H")) {

                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 60 * 60 * 1000;

            } else if (value.endsWith("m") || value.endsWith("M")) {
                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 60 * 1000;
            } else if (value.endsWith("s") || value.endsWith("S")) {

                val = value.substring(0, value.length() - 1);
                l = Long.parseLong(val) * 1000;
            } else {

                l = Long.parseLong(value);
            }

        } catch (Exception e) {

            throw new Exception(e);
        }

        return l;
    }

}
