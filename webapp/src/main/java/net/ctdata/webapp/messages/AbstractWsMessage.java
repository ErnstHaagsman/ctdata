package net.ctdata.webapp.messages;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "messageType")
public class AbstractWsMessage {
}
