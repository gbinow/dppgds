/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

/**
 *
 * @author binow
 */
public class ActivitiesData {

    private final AnnotatedDocument document;
    private final DpProjectData dpProjectData;
    
    private static final String[] STYLES = {
        "ACTIVITYDESCRIPTION",
        "COMPOSITEACTIVITY",
        "CONCLUSION",
        "DATAINI",
        "DEPENDENCIES",
        "DURATION",
        "EAP",
        "HR",
        "PAPEL",
        "PROJETO",
        "REALDATEEND",
        "REALDATESTART",
        "REALDURATION",
        "REALHR",
        "SIMPLEACTIVITY"
    };
    
    public ActivitiesData(DpProjectData dpProjectData) throws Exception {
        
        document = new AnnotatedDocument(STYLES);
        
        this.dpProjectData = dpProjectData;
        
        setDocumentProperties();
        
        fillDocument();
    }
 
    private void setDocumentProperties(){
        
        document.setProperty("ACTIVITYDESCRIPTION", "[[completeText]];property($activity,http://localhost/ontologies/SE/gep.owl#IdentityActivityProject,{content});");
        document.setProperty("COMPOSITEACTIVITY", "[[completeText]];instance({content},http://localhost/ontologies/SE/gep.owl#CompositeProjectActivity,$activity);");
        document.setProperty("CONCLUSION", "[[completeText]];property($activity,http://localhost/ontologies/SE/gep.owl#ActivityProgressIndex,{content});");
        document.setProperty("DATAINI", "[[completeText]];property($activity,http://localhost/ontologies/SE/gep.owl#PlannedEndDateActivity,{content});");
        document.setProperty("DEPENDENCIES", "[[break with ',' into 'var']];instance({slice},http://localhost/ontologies/SE/gep.owl#ProjectActivity,$activitylinha);property($activity,http://localhost/ontologies/SE/gep.owl#DependsOn,$activitylinha);");
        document.setProperty("DURATION", "[[completeText]];property($activity,http://localhost/ontologies/SE/gep.owl#PlannedDurationActivity,{content});");
        document.setProperty("EAP", "[[break with ',' into 'var']];instance({slice},http://localhost/ontologies/SE/gep.owl#WorkPackage,$eapslinha);property($activity,http://localhost/ontologies/SE/gep.owl#isToProduceDeliverable,$eapslinha);");
        document.setProperty("HR", "[[break with ',' into 'var']];instance({slice},http://localhost/ontologies/SE/gep.owl#HumanResource,$hrlinha);property($activity,http://localhost/ontologies/SE/gep.owl#Allocates,$hrlinha);");
        document.setProperty("PAPEL", "[[completeText]];instance({content},http://localhost/ontologies/SE/gep.owl#HumanResourceAllocation,$papel);property($activity,http://localhost/ontologies/SE/gep.owl#IsToBePerformed,$papel);");
        document.setProperty("PROJETO", "[[completeText]];instance({content},http://localhost/ontologies/SE/gep.owl#Project,$project);property($project,http://localhost/ontologies/SE/gep.owl#DescriptionProject,{content});");
        document.setProperty("REALDATEEND", "[[completeText]];property($activity,http://localhost/ontologies/SE/gep.owl#EndDateActivity,{content});");
        document.setProperty("REALDATESTART", "[[completeText]];property($activity,http://localhost/ontologies/SE/gep.owl#StartDateActivity,{content});");
        document.setProperty("REALDURATION", "[[completeText]];property($activity,http://localhost/ontologies/SE/gep.owl#ActualDurationActivity,{content});");
        document.setProperty("REALHR", "[[break with ',' into 'var']];instance({slice},http://localhost/ontologies/SE/gep.owl#HumanResourceParticipation,$rhrlinha);property($activity,http://localhost/ontologies/SE/gep.owl#ParticipationOfHR,$rhrlinha);");
        document.setProperty("SIMPLEACTIVITY", "[[completeText]];instance({content},http://localhost/ontologies/SE/gep.owl#SimpleProjectActivity,$activity);");
        document.setProperty("SemanticDocument", "True");
    }
    
    private void fillDocument(){
        
        document.setCell(0 , 0 , dpProjectData.getProject() , "PROJETO");
        
        int row = 1;
        for(Activity activity : dpProjectData.getActivities()){
            
            fillRow(row++ , activity);
            
            for(Activity subactivity : activity.getSubactivities()){
                fillRow(row++ , subactivity);
            }
        }
    }

    private void fillRow(int row, Activity activity){
          
        document
            .setCell(row, 0, activity.getDescription(), "ACTIVITYDESCRIPTION")
            .setCell(row, 1, activity.getPlannedStartDate(), "DATAINI")
            .setCell(row, 2, activity.getPlannedEndDate())
            .setCell(row, 3, activity.getPlannedDurationAsString(),"DURATION")
            .setCell(row, 4, activity.getHrs(),"HR")
            .setCell(row, 5, activity.getDescription(), "ACTIVITYDESCRIPTION")
            .setCell(row, 6, activity.getStartDate(), "REALDATESTART")
            .setCell(row, 7, activity.getEndDate(), "REALDATEEND")
            .setCell(row, 8, activity.getDurationAsString(),"REALDURATION")
            .setCell(row, 9, activity.getHrs(),"HR");
    }
    
    public void save(String name) {
        document.save(name);
    }
    
    
    
}
