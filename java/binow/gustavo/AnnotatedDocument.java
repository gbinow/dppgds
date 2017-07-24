/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeStyles;
import org.odftoolkit.odfdom.incubator.meta.OdfOfficeMeta;

/**
 *
 * @author binow
 */
public class AnnotatedDocument {

    private final OdfSpreadsheetDocument document;
    
    public AnnotatedDocument(String templatePath) throws Exception {
        
        document = OdfSpreadsheetDocument.loadDocument(templatePath);
        
    }
    
    public AnnotatedDocument setCell(int row, int col, String value){
        
       return setCell(row, col, value, null); 
    }
    
    public AnnotatedDocument setCell(int row, int col, String value, String style){
        
        OdfTable table = document.getTableList().get(0);
        
        OdfTableCell cell = table.getCellByPosition(col, row);
        cell.setStringValue(value);
        
        if(style != null){
            cell.getOdfElement().setStyleName("SemanticAnnotation-ref-" + style);        
        }
        
        return this;   
    }
    
    public AnnotatedDocument setCell(int row, int col, Double value){
        
        OdfTable table = document.getTableList().get(0);
        
        OdfTableCell cell = table.getCellByPosition(col, row);
        cell.setDoubleValue(value);
        
        
        return this;   
    }
    
    
    public AnnotatedDocument setCell(int row, int col, Calendar value){
        OdfTable table = document.getTableList().get(0);
        OdfTableCell cell = table.getCellByPosition(col, row);
        cell.setDateValue(value);
        
        return this;   
    }
    
    public void save(String name){
        
        try {
            document.save( name + ".ods");
        } catch (Exception ex) {
            Logger.getLogger(AnnotatedDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
