package net.ctdata.webapp.messages;

import net.ctdata.common.Messages.Observation;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ObservationMessage {
    private String time;
    private double observation;
    private final DateTimeFormatter formatter;

    public ObservationMessage(Observation observation){
        formatter = DateTimeFormat.mediumDateTime();
        this.time = observation.getTime().toString(formatter.withZone(DateTimeZone.getDefault()));
        this.observation = observation.getObservation();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getObservation() {
        return observation;
    }

    public void setObservation(double observation) {
        this.observation = observation;
    }
}
