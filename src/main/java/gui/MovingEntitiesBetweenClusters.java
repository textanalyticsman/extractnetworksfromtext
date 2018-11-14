/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import database.DAOCluster;
import database.DAOCorpus;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import logic.CCluster;
import logic.CCorpus;
import logic.CEntityRaw;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author osboxes
 */
public class MovingEntitiesBetweenClusters extends javax.swing.JInternalFrame {

    /**
     * Creates new form EntitiesExtraction
     */
    public MovingEntitiesBetweenClusters() {
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

        jButtonLoadAvailableCorpora = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableAvailableCorpora = new javax.swing.JTable();
        jButtonMoveSelectedEntities = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableClusters = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCorpusEntitiesBelongCluster = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableConfigurationClusterAvailable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableClusters1 = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableCorpusEntitiesBelongCluster1 = new javax.swing.JTable();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jButtonLoadAvailableCorpora.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonLoadAvailableCorpora.setText("Load available corpora");
        jButtonLoadAvailableCorpora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadAvailableCorporaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setText("Available corpora within the database");

        jTableAvailableCorpora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identifier", "Corpus description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAvailableCorpora.setToolTipText("");
        jTableAvailableCorpora.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableAvailableCorpora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAvailableCorporaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableAvailableCorpora);

        jButtonMoveSelectedEntities.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButtonMoveSelectedEntities.setText("Move selected entities");
        jButtonMoveSelectedEntities.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMoveSelectedEntitiesActionPerformed(evt);
            }
        });

        jTableClusters.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identifier", "Label", "Sample of entities", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableClusters.setToolTipText("");
        jTableClusters.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableClusters.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableClustersMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableClusters);
        TableRowSorter<TableModel> sorterCluster = new TableRowSorter<TableModel>(jTableClusters.getModel());
        jTableClusters.setRowSorter(sorterCluster);
        //jTableCorpusEntities.convertRowIndexToView(0);

        List<RowSorter.SortKey> sortClusterKeys = new ArrayList<>(25);
        sortClusterKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        //sortClusterKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorterCluster.setSortKeys(sortClusterKeys);

        jLabel3.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel3.setText("List of clusters");

        jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel4.setText("Entities that belong to the selected cluster");

        jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel8.setText("Available configured clusters");

        jTableCorpusEntitiesBelongCluster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entity Identifier", "Entity name", "Entity type", "Entity length", "Sentence Identifier", "Sentence", "To move"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCorpusEntitiesBelongCluster.setToolTipText("");
        jTableCorpusEntitiesBelongCluster.setMinimumSize(new java.awt.Dimension(60, 0));
        jTableCorpusEntitiesBelongCluster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCorpusEntitiesBelongClusterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCorpusEntitiesBelongCluster);
        TableRowSorter<TableModel> sorter01 = new TableRowSorter<TableModel>(jTableCorpusEntitiesBelongCluster.getModel());
        jTableCorpusEntitiesBelongCluster.setRowSorter(sorter01);
        //jTableCorpusEntities.convertRowIndexToView(0);

        List<RowSorter.SortKey> sortKeys01 = new ArrayList<>(25);
        sortKeys01.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys01.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sorter01.setSortKeys(sortKeys01);

        /*TableColumn typeColumn = jTableCorpusEntitiesBelongCluster.getColumnModel().getColumn(2);
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("LOC");
        comboBox.addItem("ORG");
        comboBox.addItem("OTH");
        comboBox.addItem("PER");
        typeColumn.setCellEditor(new DefaultCellEditor(comboBox));*/

        ////////////////////////////////////////////////////////////////////
        //Source: https://tips4java.wordpress.com/2009/06/07/table-cell-listener/
        Action action = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                TableCellListener tcl = (TableCellListener)e.getSource();
                if(tcl.getColumn()==6)
                {
                    System.out.println("Row   : " + tcl.getRow());
                    System.out.println("Column: " + tcl.getColumn());
                    System.out.println("Old   : " + tcl.getOldValue());
                    System.out.println("New   : " + tcl.getNewValue());

                    boolean newValue=(Boolean)tcl.getNewValue();
                    int currentRow=tcl.getRow();
                    int entityId=(Integer)jTableCorpusEntitiesBelongCluster.getModel().getValueAt(currentRow, 0);
                    // int trueIndex=jTableCorpusEntitiesBelongCluster.convertRowIndexToModel(tcl.getRow()-2);
                    //System.out.println("True index: " + trueIndex);
                    /*int currentRow=tcl.getRow();
                    int entityId=(Integer)jTableCorpusEntitiesBelongCluster.getModel().getValueAt(currentRow, 0);
                    String entityName=(String)jTableCorpusEntitiesBelongCluster.getModel().getValueAt(currentRow, 1);
                    String oldType=(String)tcl.getOldValue();
                    String newType=(String)tcl.getNewValue();
                    int sentenceId=(Integer)jTableCorpusEntitiesBelongCluster.getModel().getValueAt(currentRow, 4);
                    String sentence=(String)jTableCorpusEntitiesBelongCluster.getModel().getValueAt(currentRow, 5);
                    System.out.println("Entity id: " + entityId);

                    addEntityIntoHashMap(entityId, entityName, oldType, newType, sentenceId, sentence);*/

                    addEntityIntoHashMap(newValue,entityId);
                }

            }
        };

        TableCellListener tcl = new TableCellListener(jTableCorpusEntitiesBelongCluster, action);

        jTableConfigurationClusterAvailable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MinPts", "Epsilon"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableConfigurationClusterAvailable.setToolTipText("");
        jTableConfigurationClusterAvailable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableConfigurationClusterAvailable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableConfigurationClusterAvailableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableConfigurationClusterAvailable);

        jTableClusters1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identifier", "Label", "Sample of entities", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableClusters1.setToolTipText("");
        jTableClusters1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableClusters1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableClusters1MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTableClusters1);
        TableRowSorter<TableModel> sorter02 = new TableRowSorter<TableModel>(jTableClusters1.getModel());
        jTableClusters1.setRowSorter(sorter02);
        //jTableCorpusEntities.convertRowIndexToView(0);

        List<RowSorter.SortKey> sortKeys02 = new ArrayList<>(25);
        sortKeys02.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys02.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sorter02.setSortKeys(sortKeys02);

        jTableCorpusEntitiesBelongCluster1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Entity Identifier", "Entity name", "Entity type", "Entity length", "Sentence Identifier", "Sentence"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableCorpusEntitiesBelongCluster1.setToolTipText("");
        jTableCorpusEntitiesBelongCluster1.setMinimumSize(new java.awt.Dimension(60, 0));
        jTableCorpusEntitiesBelongCluster1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCorpusEntitiesBelongCluster1MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTableCorpusEntitiesBelongCluster1);
        TableRowSorter<TableModel> sorter03 = new TableRowSorter<TableModel>(jTableCorpusEntitiesBelongCluster1.getModel());
        jTableCorpusEntitiesBelongCluster1.setRowSorter(sorter03);
        //jTableCorpusEntities.convertRowIndexToView(0);

        List<RowSorter.SortKey> sortKeys03 = new ArrayList<>(25);
        sortKeys03.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sortKeys03.add(new RowSorter.SortKey(3, SortOrder.ASCENDING));
        sorter03.setSortKeys(sortKeys03);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonLoadAvailableCorpora)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(534, 534, 534))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(463, 463, 463))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane3)
                                        .addGap(31, 31, 31)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane1)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jScrollPane6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonMoveSelectedEntities, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonLoadAvailableCorpora)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButtonMoveSelectedEntities, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLoadAvailableCorporaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadAvailableCorporaActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model1 = (DefaultTableModel) jTableAvailableCorpora.getModel();
        model1.setRowCount(0);
        
        CCorpus corpus = new CCorpus();

        List<DAOCorpus> resultList = corpus.getAvailableCorpora();

        for (DAOCorpus c : resultList) {
            //jTextArea1.append(c.getName() + " (" + c.getCity() + ")" + "\n");
            //System.out.println(c.getName() + " (" + c.getCity() + ")" + "\n");

            DefaultTableModel model = (DefaultTableModel)jTableAvailableCorpora.getModel();
            Integer identifier=(Integer)c.getCorpid();
            String description=c.getCorpdesc();

            model.insertRow(jTableAvailableCorpora.getRowCount(), new Object []{identifier,description});

        }

    }//GEN-LAST:event_jButtonLoadAvailableCorporaActionPerformed

    private void jTableAvailableCorporaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAvailableCorporaMouseClicked
        // TODO add your handling code here:
        showConfiguredClusterByCorpus();
    }//GEN-LAST:event_jTableAvailableCorporaMouseClicked

    private void showConfiguredClusterByCorpus()
    {
        DefaultTableModel model = (DefaultTableModel) jTableConfigurationClusterAvailable.getModel();
        model.setRowCount(0);
        
        model = (DefaultTableModel) jTableClusters.getModel();
        model.setRowCount(0);
        
        int row=jTableAvailableCorpora.getSelectedRow();                
        int corpusId=(Integer)jTableAvailableCorpora.getValueAt(row, 0);   
        
        CCluster cluster = new CCluster();

        List <Object[]>results=cluster.getAvailableClusterConfigurationsByCorpusId(corpusId);

        for (Object[] result : results) {
            // Iterating through array object
            model = (DefaultTableModel)jTableConfigurationClusterAvailable.getModel();
            int minPts=(Integer)result[0];
            double epsilon=(Double)result[1];
            Object [] newRow = new Object []{minPts, epsilon};
            model.insertRow(jTableConfigurationClusterAvailable.getRowCount(), newRow);
        }         
        
    }
    private void jButtonMoveSelectedEntitiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMoveSelectedEntitiesActionPerformed

        String answer=moveEntitiesIntoOtherCluster();
        
        if(answer.compareTo("")==0)
        {
            refreshScreenAfterGeneratingNewCluster();

            String message="Selected entities were moved into another cluster";
            String title="Moving entities between clusters";                
            JOptionPane.showMessageDialog(null,message,title, JOptionPane.INFORMATION_MESSAGE);                               
        }
        else
        {
            String title="Moving entities between clusters";                
            JOptionPane.showMessageDialog(null,answer,title, JOptionPane.ERROR_MESSAGE);                                           
        }                
    }//GEN-LAST:event_jButtonMoveSelectedEntitiesActionPerformed

    private void refreshScreenAfterGeneratingNewCluster()
    { /*       
        int row=jTableAvailableCorpora.getSelectedRow();                
        int corpusId=(Integer)jTableAvailableCorpora.getValueAt(row, 0); 
        
        row=jTableConfigurationClusterAvailable.getSelectedRow();
        
        int minPts=(Integer)jTableConfigurationClusterAvailable.getValueAt(row, 0); 
        double epsilon=(Double)jTableConfigurationClusterAvailable.getValueAt(row, 1); 
        
        showClusters(corpusId, minPts,  epsilon);*/

        showEntitiesAndSentencesByCluster(jTableCorpusEntitiesBelongCluster,jTableClusters);
        showEntitiesAndSentencesByCluster(jTableCorpusEntitiesBelongCluster1,jTableClusters1);
    }
    
    private String moveEntitiesIntoOtherCluster()
    {
        String answer="";
                
        if(!entitiesToMove.isEmpty())
        {
            EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager(); 
                   
            //Reviso si hay un cluster de destino que haya sido seleccionado
            int row1=jTableClusters1.getSelectedRow();
            if(row1!=-1)
            {   //Obtengo el ID del cluster de destino             
                int newClusterId=(Integer)jTableClusters1.getValueAt(row1, 0);
                //Obtengo el ID del cluster de origen
                int row=jTableClusters.getSelectedRow();
                int oldClusterId=(Integer)jTableClusters.getValueAt(row, 0);    
                //Verifico que el origen sea diferente del destino para evitar
                //actualizaciones sin sentido
                if(newClusterId!=oldClusterId)
                {
                    CEntityRaw cEntRaw = new CEntityRaw();

                    //em.getTransaction().begin();
                    try
                    {   
                        //Enlazo a las entidades seleccionadas con su nuevo cluster
                        cEntRaw.updateEntitiesClusterByHand(em, entitiesToMove, oldClusterId, newClusterId);                
                    }                                    
                    catch(Exception e)
                    {
                        answer="A problem happened during the movement of entities";
                        e.printStackTrace();
                    }
                    //Limpió el HashSet que contiene las entidades seleccionadas para mover
                    //entre clusters. Si no lo hago se generarían errores.
                   entitiesToMove.clear();                                    
                }
                else
                {
                   answer="The destination cluster must be different from the source cluster"; 
                }
            }
            else
            {
                answer="In order to move an entity into other cluster you must select a destination cluster";
            }                        
        }
        else
        {
            answer="In order to move an entity into other cluster you must select at least one entity";
        }
        
        return answer;
    }
    
    private void showClusters(int corpusId,int minPts, double epsilon)
    {
        DefaultTableModel model1 = (DefaultTableModel) jTableClusters.getModel();
        model1.setRowCount(0);
        
        model1 = (DefaultTableModel) jTableClusters1.getModel();
        model1.setRowCount(0);
        
        model1 = (DefaultTableModel) jTableCorpusEntitiesBelongCluster.getModel();
        model1.setRowCount(0);
        
        // TODO add your handling code here:
        CCluster cluster = new CCluster();

        /*List<DAOCluster> resultSet = cluster.getAvailableClusters(corpusId, minPts, epsilon);

        for (DAOCluster c : resultSet) {

            DefaultTableModel model = (DefaultTableModel)jTableClusters.getModel();
            Integer clusterid=c.getClusterid();
            Integer label=c.getLabel();

            model.insertRow(jTableClusters.getRowCount(), new Object []{clusterid,label});

        }*/

        List<Object[]> resultSet = cluster.getAvailableClustersNative(corpusId, minPts, epsilon);

        for (Object[] result : resultSet) {

            DefaultTableModel model = (DefaultTableModel)jTableClusters.getModel();
            DefaultTableModel modelc1 = (DefaultTableModel)jTableClusters1.getModel();
            Integer clusterid=(Integer)result[0];
            Integer label=(Integer)result[1];
            String clusterSample="";
            if(label==2147483647)
            {
                clusterSample="Noise";                
            }
            else
            {
                clusterSample=(String)result[2];                
            }
            Long quantity=(Long)result[3];
            model.insertRow(jTableClusters.getRowCount(), new Object []{clusterid,label,clusterSample,quantity});
            modelc1.insertRow(jTableClusters1.getRowCount(), new Object []{clusterid,label,clusterSample,quantity});
        }
                        
    }
    
    
    private void jTableClustersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClustersMouseClicked
        // TODO add your handling code here:
        showEntitiesAndSentencesByCluster(jTableCorpusEntitiesBelongCluster,jTableClusters);
    }//GEN-LAST:event_jTableClustersMouseClicked

    private void showEntitiesAndSentencesByCluster(javax.swing.JTable entityTable, javax.swing.JTable clusterTable)
    {
        DefaultTableModel model = (DefaultTableModel) entityTable.getModel();
        model.setRowCount(0);
        
        int row=clusterTable.getSelectedRow();                
        int clusterId=(Integer)clusterTable.getValueAt(row, 0);   
        
        CEntityRaw centityRaw = new CEntityRaw();
        
        List <Object[]>results=centityRaw.getEntitiesAndSentencesByCluster(clusterId);
        
        for (Object[] result : results) {
            // Iterating through array object
            model = (DefaultTableModel)entityTable.getModel();
            String temp=(String)result[1];
            int length=temp.length();
            Object [] newRow = new Object []{result[0],result[1],result[2],length,result[3],result[4]};
            model.insertRow(entityTable.getRowCount(), newRow);
        }         
        
    }
    
    private void jTableCorpusEntitiesBelongClusterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCorpusEntitiesBelongClusterMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2)
        {
            int row=jTableCorpusEntitiesBelongCluster.getSelectedRow();

            System.out.println("Row: " + row);

            int trueIndex=jTableCorpusEntitiesBelongCluster.convertRowIndexToModel(row);

            System.out.println("Fila: " + trueIndex);

            String sentence= (String)jTableCorpusEntitiesBelongCluster.getModel().getValueAt(trueIndex, 5);

            String message=WordUtils.wrap(sentence, 200, "\n", false);

            String title="Sentence detail";

            JOptionPane.showMessageDialog(null,message,title, JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jTableCorpusEntitiesBelongClusterMouseClicked

    private void jTableConfigurationClusterAvailableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableConfigurationClusterAvailableMouseClicked
        // TODO add your handling code here:                        
        int row=jTableAvailableCorpora.getSelectedRow();                
        int corpusId=(Integer)jTableAvailableCorpora.getValueAt(row, 0); 
        
        row=jTableConfigurationClusterAvailable.getSelectedRow();
        
        int minPts=(Integer)jTableConfigurationClusterAvailable.getValueAt(row, 0); 
        double epsilon=(Double)jTableConfigurationClusterAvailable.getValueAt(row, 1); 
        
        showClusters(corpusId, minPts,  epsilon);
    }//GEN-LAST:event_jTableConfigurationClusterAvailableMouseClicked

    private void jTableClusters1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClusters1MouseClicked
        // TODO add your handling code here:
        showEntitiesAndSentencesByCluster(jTableCorpusEntitiesBelongCluster1,jTableClusters1);
    }//GEN-LAST:event_jTableClusters1MouseClicked

    private void jTableCorpusEntitiesBelongCluster1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCorpusEntitiesBelongCluster1MouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2)
        {
            int row=jTableCorpusEntitiesBelongCluster1.getSelectedRow();

            System.out.println("Row: " + row);

            int trueIndex=jTableCorpusEntitiesBelongCluster1.convertRowIndexToModel(row);

            //System.out.println("Fila: " + trueIndex);

            String sentence= (String)jTableCorpusEntitiesBelongCluster1.getModel().getValueAt(trueIndex, 5);

            String message=WordUtils.wrap(sentence, 200, "\n", false);

            String title="Sentence detail";

            JOptionPane.showMessageDialog(null,message,title, JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jTableCorpusEntitiesBelongCluster1MouseClicked
    
 private void addEntityIntoHashMap(boolean newValue, int entityId) 
    {
        if(newValue)//reviso si la entidad ya existe en el Hash Map
        {
            /*Si la entidad ya existe en el Hash Map simplemente modifico su NewType*/
            entitiesToMove.add(entityId);
        }
        else
        {
            /*Si la entidad no existe en el Hash Map, la añado*/
            entitiesToMove.remove(entityId);
        }
        
        
    }    
    
    private HashSet<Integer> entitiesToMove = new HashSet<Integer>() ;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLoadAvailableCorpora;
    private javax.swing.JButton jButtonMoveSelectedEntities;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTableAvailableCorpora;
    private javax.swing.JTable jTableClusters;
    private javax.swing.JTable jTableClusters1;
    private javax.swing.JTable jTableConfigurationClusterAvailable;
    private javax.swing.JTable jTableCorpusEntitiesBelongCluster;
    private javax.swing.JTable jTableCorpusEntitiesBelongCluster1;
    // End of variables declaration//GEN-END:variables
}
