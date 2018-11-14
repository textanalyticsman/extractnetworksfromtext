/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osboxes
 */
public class CFileManager {
    
    public void writeCSVfile(JTable table, String filePrefix) throws IOException, ClassNotFoundException, SQLException{
        Writer writer = null;
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int nRow = dtm.getRowCount();
        int nCol = dtm.getColumnCount();
        try {
            LocalDateTime timePoint = LocalDateTime.now();
            String theDate = timePoint.toLocalDate().toString();
            LocalTime now = LocalTime.now();
            String theHour=now.toString();
            String fileName=filePrefix+theDate+theHour;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/osboxes/NetBeansProjects/networkfromtextinspanish/GephiFiles/"+fileName), "utf-8"));

            //write the header information
            StringBuffer bufferHeader = new StringBuffer();
            for (int j = 0; j < nCol; j++) {
                bufferHeader.append(dtm.getColumnName(j));
                if (j!=nCol) bufferHeader.append(", ");
            }
            writer.write(bufferHeader.toString() + "\r\n");

           //write row information
            for (int i = 0 ; i < nRow ; i++){
                 StringBuffer buffer = new StringBuffer();
                for (int j = 0 ; j < nCol ; j++){
                    buffer.append(dtm.getValueAt(i,j));
                    if (j!=nCol) buffer.append(", ");
                }
                writer.write(buffer.toString() + "\r\n");
            }
        } finally {
              writer.close();
        }
    }
    
}
