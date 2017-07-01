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
public class HRData {
     
    private final AnnotatedDocument document;
    private final DpProjectData dpProjectData;
    
    private static final String[] STYLES = {"hr001","hr002","Project"};
    
    public HRData(DpProjectData dpProjectData) throws Exception {
        
        document = new AnnotatedDocument(STYLES);
        
        this.dpProjectData = dpProjectData;
        
        setDocumentProperties();
        
        fillDocument();
    }
 
    private void fillDocument(){
        
        document.setCell(0 , 0 , dpProjectData.getProject() , "PROJETO");
        
        int row = 1;
        for(HR hr : dpProjectData.getHrs()){
            fillRow(row++ , hr);
        }
    }
    
    private void fillRow(int row, HR hr){
          
        document
            .setCell(row, 0, hr.getName(), "hr001")
            .setCell(row, 1, hr.getCostAsString(), "hr002");
    }
 
    private void setDocumentProperties(){
        
        document.setProperty("hr001", "[[completeText]];instance({content},http://localhost/ontologies/SE/gep.owl#HumanResource,$hr);");
        document.setProperty("hr002", "[[completeText]];property($hr,http://localhost/ontologies/SE/gep.owl#CostHiring,{content});");
    }
    
    public void save(String name) {
        document.save(name);
    }
}
