package net.ctdata.webapp.messages;

import net.ctdata.common.Messages.Observation;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.TimeZone;

public class ObservationMessage {
    private String time;
    private String observation;
    private final DateTimeFormatter formatter;

    public ObservationMessage(Observation observation){
        formatter = DateTimeFormat.mediumDateTime();
        this.time = observation.getTime().toString(formatter.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))));
        this.observation = String.format("%.2f mm", observation.getObservation());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
