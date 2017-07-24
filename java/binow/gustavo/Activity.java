/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author binow
 */
public class Activity {
    
    private String
            id, 
            description,
            dependencies = "",
            hrs = "";
    
    private Calendar
           plannedStartDate, 
           plannedEndDate, 
           startDate , 
           endDate;
    
    private int plannedDuration, 
        duration, 
        progress;
    
    private Boolean composite = false;
    
    private final ArrayList<Activity> subactivities;

    public Activity() {
        this.subactivities = new ArrayList<Activity>();
    }

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


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public void addSubactity(Activity activity){
        subactivities.add(activity);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
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

    public Double getPlannedDurationAsDouble() {
        return (double) plannedDuration;
    }

    public Double getDurationAsDouble() {
        return (double) duration;
    }

    public Double getProgressAsDouble() {
        return (double) progress;
    }
    
    public Calendar getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(Calendar plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public Calendar getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(Calendar plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    
    
    
    
}
