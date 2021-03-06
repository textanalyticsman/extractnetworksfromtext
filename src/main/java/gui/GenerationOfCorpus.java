/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.List;
import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import database.*;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logic.*;

/**
 *
 * @author osboxes
 */
public class GenerationOfCorpus extends javax.swing.JInternalFrame {

    /**
     * Creates new form GenerationOfCorpus
     */
    public GenerationOfCorpus() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAvailableDocuments = new javax.swing.JTable();
        jButtonGenerateCorpus = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldCorpusDescription = new javax.swing.JTextField();
        jButtonLoadAvailableDocuments = new javax.swing.JButton();
        jButtonSelectAll = new javax.swing.JButton();
        jButtonSelectAll1 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jTableAvailableDocuments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identifier", "Date", "Title", "Content sample", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableAvailableDocuments.setToolTipText("");
        jScrollPane1.setViewportView(jTableAvailableDocuments);

        jButtonGenerateCorpus.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonGenerateCorpus.setText("Generate corpus");
        jButtonGenerateCorpus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateCorpusActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setText("Corpus description:");

        jButtonLoadAvailableDocuments.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonLoadAvailableDocuments.setText("Load available documents");
        jButtonLoadAvailableDocuments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadAvailableDocumentsActionPerformed(evt);
            }
        });

        jButtonSelectAll.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonSelectAll.setText("Select all");
        jButtonSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectAllActionPerformed(evt);
            }
        });

        jButtonSelectAll1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonSelectAll1.setText("Deselect all");
        jButtonSelectAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSelectAll1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSelectAll1)
                            .addComponent(jButtonSelectAll))
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonGenerateCorpus)
                            .addComponent(jButtonLoadAvailableDocuments)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldCorpusDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(68, 68, 68))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jButtonLoadAvailableDocuments)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSelectAll)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSelectAll1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldCorpusDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jButtonGenerateCorpus)
                .addGap(50, 50, 50))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGenerateCorpusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerateCorpusActionPerformed
        // TODO add your handling code here:
        
        if(validateInput())
        {
            //Creating the class that interface with the DAO class
            CCorpus c= new CCorpus();
            //Getting the corpus description given by the userf
            String corpusDescription=jTextFieldCorpusDescription.getText();
            //Calling the function that create the corpus and returns its ID
            int corpusID=c.createCorpus(corpusDescription);
            
            if(corpusID>0)
            {
                HashSet documentsSet=new java.util.HashSet();

               //Getting the number of documents that belong to the JTable
               int selectCount=jTableAvailableDocuments.getRowCount();

               //Going over the table to get selected rows
               for (int i=0;i<selectCount;i++)
               {
                   //Boolean isSelected=(Boolean)jTableAvailableDocuments.getValueAt(i, 4);
                   //System.out.println(isSelected.booleanValue());
                   if(jTableAvailableDocuments.getValueAt(i, 4)!=null)
                   {
                       if((Boolean)jTableAvailableDocuments.getValueAt(i, 4))
                       {
                           documentsSet.add(jTableAvailableDocuments.getValueAt(i, 0));
                       }
                       
                   }
               }

               /*Iterator iter= documentsSet.iterator();
               while (iter.hasNext())
               {
                   System.out.println(iter.next());
               }*/

               //Creating documents and the relationship with corpus
               CDocument doc =new CDocument();
               boolean answer=doc.createDocuments(corpusID, documentsSet);               
               if(answer)
               {
                    String message="The corpus was created";
                    String title="Corpus generation";                
                    JOptionPane.showMessageDialog(null,message,title, JOptionPane.INFORMATION_MESSAGE);                                   
               }
               else
               {
                    String message="It seems to be a corpus was created without related documents, please review the database";
                    String title="Corpus generation";                
                    JOptionPane.showMessageDialog(null,message,title, JOptionPane.ERROR_MESSAGE);                                                      
               }
            }
            else
            {
                String message="There was a problem during the creation of the corpus";
                String title="Corpus generation";                
                JOptionPane.showMessageDialog(null,message,title, JOptionPane.ERROR_MESSAGE);                
            }


        }
        else
        {
            String message="You should select at least one document and fill the corpus description before creating a corpus";
            String title="Corpus generation";
            JOptionPane.showMessageDialog(null,message,title, JOptionPane.ERROR_MESSAGE);
        }


        //model.getValueAt(ERROR, NORMAL)

    }//GEN-LAST:event_jButtonGenerateCorpusActionPerformed

    
    private boolean validateInput()
    {
        int numRows=jTableAvailableDocuments.getRowCount();
        String desc=jTextFieldCorpusDescription.getText();
        
        boolean answer=false;
        
        if (desc.length()>5 && numRows>0)
        {
            answer=true;
        }
        
        return answer;
    }
    
    private void jButtonLoadAvailableDocumentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadAvailableDocumentsActionPerformed
        // TODO add your handling code here:
        /*
        Here I have to load the information from the table called OCTOPARSESIMPORT
        This information is composed by documentos extracted from a Peruvian neswpapers
        thanks to the tool called OctoParse. The idea is the user could select some of these documents
        or even all of them
        */
        EntityManager entityManager = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        Query query = entityManager.createNamedQuery("DAOOctoparseImport.findAll");
        List<DAOOctoparseImport> resultList = query.getResultList();

        for (DAOOctoparseImport c : resultList) {
            //jTextArea1.append(c.getName() + " (" + c.getCity() + ")" + "\n");
            //System.out.println(c.getName() + " (" + c.getCity() + ")" + "\n");

            DefaultTableModel model = (DefaultTableModel)jTableAvailableDocuments.getModel();
            String date=c.getDate();
            String title=c.getTitle();
            String content=c.getContent();
            String contentShrink;

            Integer identifier=(Integer)c.getId();

            if(content.length()>1000)
            {
                contentShrink=content.substring(0,999);
            }
            else
            {
                contentShrink=content;
            }

            model.insertRow(jTableAvailableDocuments.getRowCount(), new Object []{identifier,date,title,contentShrink});

        }

    }//GEN-LAST:event_jButtonLoadAvailableDocumentsActionPerformed

    private void jButtonSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectAllActionPerformed
        // TODO add your handling code here:
        //jTableAvailableDocuments.selectAll();
        
        int selectCount=jTableAvailableDocuments.getRowCount();

        //Going over the table to get selected rows
        for (int i=0;i<selectCount;i++)
        {
            //Boolean isSelected=(Boolean)jTableAvailableDocuments.getValueAt(i, 4);
            //System.out.println(isSelected.booleanValue());
            jTableAvailableDocuments.setValueAt(Boolean.TRUE, i, 4);            
        }        

    }//GEN-LAST:event_jButtonSelectAllActionPerformed

    private void jButtonSelectAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSelectAll1ActionPerformed
        // TODO add your handling code here:        
        
        int selectCount=jTableAvailableDocuments.getRowCount();

        //Going over the table to get selected rows
        for (int i=0;i<selectCount;i++)
        {
            //Boolean isSelected=(Boolean)jTableAvailableDocuments.getValueAt(i, 4);
            //System.out.println(isSelected.booleanValue());
            jTableAvailableDocuments.setValueAt(Boolean.FALSE, i, 4);            
        }        

    }//GEN-LAST:event_jButtonSelectAll1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonGenerateCorpus;
    private javax.swing.JButton jButtonLoadAvailableDocuments;
    private javax.swing.JButton jButtonSelectAll;
    private javax.swing.JButton jButtonSelectAll1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAvailableDocuments;
    private javax.swing.JTextField jTextFieldCorpusDescription;
    // End of variables declaration//GEN-END:variables
    

}
