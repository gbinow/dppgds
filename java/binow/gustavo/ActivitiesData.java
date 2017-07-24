/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

import java.text.SimpleDateFormat;

/**
 *
 * @author binow
 */
public class ActivitiesData {

    private final AnnotatedDocument document;
    private final DpProjectData dpProjectData;
    
    
    public ActivitiesData(DpProjectData dpProjectData) throws Exception {
        
        document = new AnnotatedDocument("planilha.ods");
        
        this.dpProjectData = dpProjectData;
        
        fillDocument();
    }
 
    
    private void fillDocument(){
        
        document.setCell(2 , 2 , dpProjectData.getProject() );
        
        int row = 8;
        for(Activity activity : dpProjectData.getActivities()){
            
            fillRow(row++ , activity);
            
            for(Activity subactivity : activity.getSubactivities()){
                fillRow(row++ , subactivity);
            }
        }
    }

    private void fillRow(int row, Activity activity){
          
        if(activity.isComposite()){
            document.setCell(row, 0, activity.getId(), "COMPOSITEACTIVITY");
            document.setCell(row, 1, activity.getDescription());
            document.setCell(row, 2, "");
        }else{
            document.setCell(row, 0, activity.getId(), "SIMPLEACTIVITY");
            document.setCell(row, 1, "");
            document.setCell(row, 2, activity.getDescription());
        }
        
        
        document.setCell(row, 3, activity.getPlannedStartDate())
            .setCell(row, 4, activity.getPlannedEndDate())
            .setCell(row, 5, activity.getPlannedDurationAsDouble())
            .setCell(row, 6, activity.getHrs())
            .setCell(row, 7, "")
            .setCell(row, 8, activity.getDependencies())
            .setCell(row, 9, "")
            .setCell(row, 10, activity.getStartDate())
            .setCell(row, 11, activity.getEndDate())
            .setCell(row, 12, activity.getDurationAsDouble())
            .setCell(row, 13, activity.getHrs())
            .setCell(row, 14, activity.getProgressAsDouble());
    }
    
    public void save(String name) {
        document.save(name);
    }
    
    
    
}
