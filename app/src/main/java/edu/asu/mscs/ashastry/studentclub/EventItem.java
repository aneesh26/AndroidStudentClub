package edu.asu.mscs.ashastry.studentclub;

import java.util.Date;

/**
 * Created by A on 4/29/2015.
 */
public class EventItem {
    private String eventTitle;
    private String eventLocation;
    private String eventTime;
    private Date eventDate;
    private int duration;

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public EventItem(String eventTitle, String eventLocation, String eventTime, Date eventDate, int duration) {
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventTime = eventTime;
        this.eventDate = eventDate;
        this.duration = duration;


    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}
