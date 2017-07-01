/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author binow
 */
public class DpProjectData {

    private String project;
    
    private ArrayList<Activity> activities;
    private ArrayList<HR> hrs;
    
    
    public DpProjectData(String filename) throws IOException {
        
        JSONObject dpProjectData = new JSONObject(readFile(filename , StandardCharsets.UTF_8));
        
        project = dpProjectData.getString("project");
        activities = parseActivities(dpProjectData);
        hrs = parseHRs(dpProjectData);
    }
    
    private ArrayList<Activity> parseActivities(JSONObject dpProjectData){
        
        JSONArray activitiesJson = dpProjectData.getJSONArray("activities");
        
        ArrayList<Activity> activities = new ArrayList<Activity>();
                   
        for(Object item : activitiesJson){
            
            JSONObject activityJson = (JSONObject) item;
            
            Activity activity = parseActivity(activityJson);
            activity.setComposite(true);
            
            if(activityJson.has("subactivities")){
                JSONArray subctivitiesJson = activityJson.getJSONArray("subactivities");
                
                    for(Object subitem : subctivitiesJson){
                        activity.addSubactity(parseActivity((JSONObject) subitem));
                    }
            }
            
            activities.add(activity);
        }
        
        return activities; 
    }
    
    private Activity parseActivity(JSONObject activityJson){
        Activity activity = new Activity();
        activity.setDescription(this.getString(activityJson,"description"));
        
        activity.setPlannedStartDate(this.getString(activityJson,"plannedStartDate"));
        activity.setPlannedEndDate(this.getString(activityJson,"plannedEndDate"));
        activity.setPlannedDuration(this.getInt(activityJson,"plannedDuration"));
        
        activity.setHrs(this.getString(activityJson,"hrs"));
        
        activity.setStartDate(this.getString(activityJson,"startDate"));
        activity.setEndDate(this.getString(activityJson,"endDate"));
        activity.setDuration(this.getInt(activityJson,"duration"));
        
        
        
        return activity;
    }
    
    private ArrayList<HR> parseHRs(JSONObject dpProjectData){
        
        JSONArray hrsJson = dpProjectData.getJSONArray("costs");
        
        ArrayList<HR> hrs = new ArrayList<HR>();
        
        for(Object item : hrsJson){
            
            JSONObject hrJson = (JSONObject) item;
            
            HR hr = new HR();
            
            hr.setName(getString(hrJson,"name"));
            hr.setCost(getDouble(hrJson,"cost"));
            
            hrs.add(hr);
        }
        
        return hrs;
    }
    
    static String readFile(String path, Charset encoding) throws IOException 
    {
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    }

    private String getString(JSONObject json , String pos){
        
        if( json.has(pos) )
            return json.getString(pos);
        
        return "";
    }
    
    private int getInt(JSONObject json , String pos){
        
        if( json.has(pos) )
            return json.getInt(pos);
        
        return -1;
    }
    
    private Double getDouble(JSONObject json , String pos){
        
        if( json.has(pos) )
            return json.getDouble(pos);
        
        return new Double(-1);
    }
    
    public String getProject() {
        return project;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public ArrayList<HR> getHrs() {
        return hrs;
    }
    
}
