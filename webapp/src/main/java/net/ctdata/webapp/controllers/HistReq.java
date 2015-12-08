package net.ctdata.webapp.controllers;

import net.ctdata.common.Messages.Observation;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import java.util.UUID;

/**
 * Created by dhaval on 29-11-2015.
 */
public class HistReq {

     UUID raspberryNode;
     int sensorID;
     int startYear;
    int startMonth;
    int startDay;
    int startHr;
    int startMin;

    int endYear;
    int endMonth;
    int endDay;
    int endHr;
    int endMin;

    public UUID getRaspberryNode() {
        return raspberryNode;
    }
    public void setRaspberryNode(UUID raspberryNode) {
        this.raspberryNode = raspberryNode;
    }
    public int getSensorID() {
        return sensorID;
    }
    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public int getStartYear() { return startYear;}
    public int getStartMonth() { return startMonth;}
    public int getStartDay() { return startDay;}
    public int getStartHr() { return startHr;}
    public int getStartMin() { return startMin;}

    public int getEndYear() { return endYear;}
    public int getEndMonth() { return endMonth;}
    public int getEndDay() { return endDay;}
    public int getEndHr() { return endHr;}
    public int getEndMin() { return endMin;}

    public void setStartYear(int startYear){ this.startYear = startYear;}
    public void setStartMonth(int startMonth){ this.startMonth = startMonth;}
    public void setStartDay(int startDay){ this.startDay = startDay;}
    public void setStartHr(int startHr){ this.startHr = startHr;}
    public void setStartMin(int startMin){ this.startMin = startMin;}

    public void setEndYear(int endYear){ this.endYear = endYear;}
    public void setEndMonth(int endMonth){ this.endMonth = endMonth;}
    public void setEndDay(int endDay){ this.endDay = endDay;}
    public void setEndHr(int endHr){ this.endHr = endHr;}
    public void setEndMin(int endMin){ this.endMin = endMin;}



}
