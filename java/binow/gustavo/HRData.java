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
    private int hrCount = 0;
    
    public HRData(DpProjectData dpProjectData) throws Exception {
        
        document = new AnnotatedDocument("rechumanos.ods");
        
        this.dpProjectData = dpProjectData;
        
        fillDocument();
    }
 
    private void fillDocument(){
        
        document.setCell(3 , 1 , dpProjectData.getProject());
        
        int row = 9;
        for(HR hr : dpProjectData.getHrs()){
            fillRow(row++ , hr);
        }
    }
    
    private void fillRow(int row, HR hr){
          
        document
            .setCell(row, 0, (double) ++hrCount)
            .setCell(row, 0, hr.getName())
            .setCell(row, 1, hr.getCost());
    }
 
    public void save(String name) {
        document.save(name);
    }
}
