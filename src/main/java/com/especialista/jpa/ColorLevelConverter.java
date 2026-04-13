package com.especialista.jpa;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

// classe pra colorir os logs
public class ColorLevelConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        String color;

        switch (event.getLevel().levelStr) {
            case "ERROR":
                color = "\u001B[31m";
                break;
            case "WARN":
                color = "\u001B[38;5;208m";
                break;
            case "INFO":
                color = "\u001B[32m";
                break;
            case "DEBUG":
                color = "\u001B[34m";
                break;
            default:
                color = "";
        }

        return color + event.getLevel()
            + " [" + event.getThreadName() + "] - "
            + event.getFormattedMessage()
            + "\u001B[0m";
    }
}