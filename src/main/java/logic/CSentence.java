/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import database.DAOSentence;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author osboxes
 */
public class CSentence {
    private String sentenceId;//To set the ID given a sentence
    private String sentenceOrder;//To set the position withing a document for a sentence
    
    public CSentence()
    {

    }

    public String getSentenceID() {
        return sentenceId;
    }

    public String getSentenceOrder() {
        return sentenceOrder;
    }
    
    public void updateSelectedSentences(EntityManager em, String updatedSentence, int sentenceId)
    {
        /*
        It receives a HashSet that contains a set of sentences that should be uppdated after deleting entities.
        */
        
        //DAOSentence dSentence = new DAOSentence(sentenceId);
        
       // EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();      
        
        
        //EntityManager entityManager = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        try
        {
            Query query = em.createNamedQuery("DAOSentence.findBySentenceid");
            query.setParameter("sentenceid", sentenceId);
            DAOSentence daoSentence=(DAOSentence)query.getSingleResult();

            //String queryText="update DAOSentence s set s.sentencecontent=:updatedSentence where s.sentenceid = :sentenceId";

             //The begining of the transaction
            //em.getTransaction().begin();

            daoSentence.setSentencecontent(updatedSentence);

            em.persist(daoSentence);            
        }
        catch(Exception e)
        {
            throw e;
        }

        
    }
    
    
}
