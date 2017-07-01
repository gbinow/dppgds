/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

import java.util.ArrayList;

/**
 *
 * @author binow
 */
public class Activity {
    
    private String description, 
           plannedStartDate, 
           plannedEndDate, 
           startDate , 
           endDate,
           hrs;
    
    private int plannedDuration, 
        duration;
    
    private Boolean composite = false;
    
    private ArrayList<Activity> subactivities = new ArrayList<Activity>();

    public ArrayList<Activity> getSubactivities() {
        return subactivities;
    }

    public Boolean isComposite() {
        return composite;
    }

    public void setComposite(Boolean composite) {
        this.composite = composite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public void addSubactity(Activity activity){
        subactivities.add(activity);
    }

    public String getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(String plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public String getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(String plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public int getPlannedDuration() {
        return plannedDuration;
    }

    public void setPlannedDuration(int plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    public String getHrs() {
        return hrs;
    }

    public void setHrs(String hrs) {
        this.hrs = hrs;
    }

    public String getPlannedDurationAsString() {
        return plannedDuration != -1 ? String.valueOf(plannedDuration) : "";
    }

    String getDurationAsString() {
        return duration != -1 ? String.valueOf(duration) : "";
    }
    
    
    
}
