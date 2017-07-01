/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binow.gustavo;

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

    private OdfSpreadsheetDocument document;
    
    public AnnotatedDocument(String[] styles) throws Exception {
        
        document = OdfSpreadsheetDocument.newSpreadsheetDocument();
        
        for(String style : styles){
            addStyle(style);
        }
        
    }
 
    
    private void addStyle(String style){
        
        OdfOfficeStyles styles = document.getOrCreateDocumentStyles();
        
        styles.newStyle ( "SemanticAnnotation-ref-" + style, OdfStyleFamily.TableCell );
    }
    
    public AnnotatedDocument setProperty(String name, String value){
        
        OdfOfficeMeta meta = document.getOfficeMetadata();
        
        meta.setUserDefinedData(name, "String", value);
        
        return this;
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
    
    public void save(String name){
        
        try {
            document.save( name + ".ods");
        } catch (Exception ex) {
            Logger.getLogger(AnnotatedDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
