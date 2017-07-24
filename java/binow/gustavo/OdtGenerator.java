/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

import java.io.IOException;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;


/**
 *
 * @author binow
 */
public class OdtGenerator {

    public static void main(String[] args) throws Exception {
        
        // Fazendo o parse dos dados do dotproject
        DpProjectData dpProjectData = new DpProjectData(args[0]);
        ActivitiesData activitiesData = new ActivitiesData(dpProjectData);
        HRData hrData = new HRData(dpProjectData);
        activitiesData.save("../output/dp_" + dpProjectData.getProject() + "_activities");
        hrData.save("../output/dp_" + dpProjectData.getProject() + "_hrs");
    }
    
}
