/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

import java.io.IOException;


/**
 *
 * @author binow
 */
public class OdtGenerator {

    public static void main(String[] args) throws Exception {
        // Fazendo o parse dos dados do dotproject
        DpProjectData dpProjectData = new DpProjectData("pgds.json");
        ActivitiesData activitiesData = new ActivitiesData(dpProjectData);
        HRData hrData = new HRData(dpProjectData);
        activitiesData.save("dp_" + dpProjectData.getProject() + "_activities");
        hrData.save("dp_" + dpProjectData.getProject() + "_hrs");
    }
    
}
