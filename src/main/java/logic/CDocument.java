/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import database.DAOCorpus;
import database.DAODocument;
import database.DAOOctoparseImport;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author osboxes
 */
public class CDocument {
    
    private String docId; //To save the ID given each document
    private String docName; //To save the name of each document
    private String docPath; //To save the path where the document resides
    private String docDesc; /*To save a description of the document. 
                            For example, this is a courd judgement related to the case...*/
   

    public String getDocId() {
        return docId;
    }

    public String getDocName() {
        return docName;
    }

    public String getDocPath() {
        return docPath;
    }

    public String getDocDesc() {
        return docDesc;
    }
    
    public boolean createDocuments(int corpusId, HashSet documentsSet)
    {
        boolean answer=true;
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        
        //A query to get information about the corpus
        Query query2 = em.createNamedQuery("DAOCorpus.findByCorpid");
        //Setting the parameter used to get the corpus
        query2.setParameter("corpid", corpusId);
        //Getting the corpus that will be used to set the relationship between documents and corpus
        DAOCorpus corpus = (DAOCorpus)query2.getSingleResult();
        
        Iterator iter= documentsSet.iterator();  
        
        //The begining of the transaction
        em.getTransaction().begin();
        
        while (iter.hasNext())
        {            
            //A query to get the document that was imported using octoparse
            Query query1 = em.createNamedQuery("DAOOctoparseImport.findById");    
            //Getting the ID from the HashSet
            int octoParseId=(Integer)iter.next();
            //Setting the parameter used to get a document
            query1.setParameter("id", octoParseId);
            //Getting the corpus that will be used to set the relationship between documents and corpus
            DAOOctoparseImport octoParseImport = (DAOOctoparseImport)query1.getSingleResult();
            //Creating a document
            DAODocument doc=new DAODocument();
            //Setting document content
            doc.setContent(octoParseImport.getContent());
            //Setting document date
            doc.setDate(octoParseImport.getDate());
            //Setting document title
            doc.setTitle(octoParseImport.getTitle());
            //Setting the relationship with corpus
            doc.setCorpid(corpus); 
            //Saving the document
            em.persist(doc);
        }        
        try
        {
            //Commiting the transaction
            em.getTransaction().commit();                    
        }
        catch(Exception e)
        {
            //Executing a rollback in case of error
            em.getTransaction().rollback();  
            answer=false;
        }        
        return answer;        
    }
    
    public List getDocumentsBelongCorpus(DAOCorpus corpusId)
    {
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        Query query = em.createQuery("select d from DAODocument d where d.corpid=:corpusId");
        query.setParameter("corpusId", corpusId);
        List<DAOOctoparseImport> resultList = query.getResultList();
        return resultList;   
    }
    
}
