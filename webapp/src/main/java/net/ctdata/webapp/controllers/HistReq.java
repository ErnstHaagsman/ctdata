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
     Interval interval;

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

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval timePeriod) {
        this.interval = new Interval(timePeriod.getStart().toDateTime(DateTimeZone.UTC),
                timePeriod.getEnd().toDateTime(DateTimeZone.UTC));
    }

}
