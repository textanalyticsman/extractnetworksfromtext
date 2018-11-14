/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.HashSet;
import database.*;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author osboxes
 */
public class CCorpus {
    
    private String corpusId; //To save the ID given each corpus
    private String corpusDesc; /*To save a description of the Corpus. 
                               For example, this corpus encloses a group of documents
                               related to the case...*/
    

    public String getCorpusId() {
        return corpusId;
    }

    public String getCorpusDesc() {
        return corpusDesc;
    }
    
    
    public int createCorpus(String description)
    {
      /*
      This function receives the description of the corpus, creates a corpus and 
      returns the ID of the corpus.
      */
        
      EntityManager entityManager = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
      //The begining of the transaction
      entityManager.getTransaction().begin();
      
      //Creation of the DAO object that represents a Corpus
      DAOCorpus corpus=new DAOCorpus();
      //Setting the corpus description
      corpus.setCorpdesc(description);             
      //Saving the new corpus
      entityManager.persist(corpus);      
      try
      {
          //Commiting the transaction
          entityManager.getTransaction().commit();                    
      }
      catch(Exception e)
      {
          //Executing a rollback in case of error
          entityManager.getTransaction().rollback();          
      }

      int corpusId=corpus.getCorpid();
      //Returning the corpus id that can be used by other routines
      return corpusId;
        
    }
    
    public List getAvailableCorpora()
    {
        EntityManager entityManager = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        Query query = entityManager.createNamedQuery("DAOCorpus.findAll");
        List<DAOOctoparseImport> resultList = query.getResultList();

        return resultList;        
    }
    
    public DAOCorpus getCorpusById(int corpusId)
    {
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        
        Query query = em.createNamedQuery("DAOCorpus.findByCorpid");
        //Setting the parameter used to get the corpus
        query.setParameter("corpid", corpusId);
        //Getting the corpus that will be used to set the relationship between documents and corpus
        DAOCorpus corpus = (DAOCorpus)query.getSingleResult();
        
        return corpus;
        
    }
    
    public boolean validateEntitiesExistanceForCorpus(int corpusId)
    {
        /*
        This function is used to to validate whether there are entities for a given corpus or not.
        In this way, it is possible to avoid unnecessary processing
        
        18/08/2018
        Query modified to include the paragraph
        */
        
        boolean result=false;
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText="select " +
                        "count(e.entityid) " +
                        "from " +
                        "Corpus c, " +  
                        "Document d, " +  
                        "Sentence s, " +
                        "EntityRaw e, " +
                        "paragraph p " + 
                        "where " + 
                        "c.corpid= ? and " +
                        "c.corpid=d.corpid and " +
                        "p.DOCID=d.DOCID and " +
                        "s.paragraphid=p.paragraphid and " +
                        "s.sentenceid=e.sentenceid ";
        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        Long numbEntities = (Long)query.getSingleResult();
                
        
        if(numbEntities>0)
        {
            result=true;
        }        
        
        return result;
    }
    
    public List<Object[]> getEntitiesBelongToCorpus(int corpusId)
    {
        /*
        This function is used to get the list of entities generated for a given 
        corpus
        */        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText= "select " +
                        "e.ENTITYID,e.ENTITYNAME,e.ENTITYTYPE, " +
                        "d.DOCID, d.TITLE,s.SENTENCEID,s.SENTENCECONTENT " +
                        "from " +
                        "CORPUS c, " +
                        "document d, " +
                        "sentence s, " +
                        "entityraw e, " +
                        "paragraph p " +
                        "where " +
                        "c.CORPID=? and " +
                        "c.CORPID=d.CORPID and " +
                        "s.SENTENCEID=e.SENTENCEID and " +
                        "s.PARAGRAPHID=p.PARAGRAPHID and " +
                        "p.DOCID=d.DOCID";                        
        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        List<Object[]> result= query.getResultList();
        
        return result;
  
    }

    public boolean corpusHasCluster(int corpusId)
    {
        boolean answer=false;
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText="select count(c.CLUSTERID) " +
                        "from " +
                        "snadb.cluster c " +
                        "where " +
                        "c.CORPID=?";                        
        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        Object result= query.getSingleResult();
        
        if((Long)result>0)
        {
            answer=true;
        }

        return answer;
    }
    
}
