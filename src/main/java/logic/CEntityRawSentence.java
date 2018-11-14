/*
This class is used to implement functions that involves the participation of entities
and sentences
 */
package logic;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author osboxes
 */
public class CEntityRawSentence  {
    
    public void deleteEntityAndUpdateSentece (EntityManager em, int entityId, String entity, String type, int sentenceId, String sentence)
    {
        //oolean answer=true;

        String tagIni="<"+type+">";
        String tagFin="</"+type+">";
        String entityToDelete=tagIni+entity+tagFin;

        //System.out.println("Entity to delete: " + entityToDelete);

        String updatedSentence= sentence.replaceAll(entityToDelete, entity);   
                
        
        //EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();                        
        
        //This class is used to execute the metod to update sententece
        CSentence sent= new CSentence();
        //This class is used to execute the metod to update delete entities raw
        CEntityRaw entRaw= new CEntityRaw();
        
        //em.getTransaction().begin();
        
        try{
            sent.updateSelectedSentences(em, updatedSentence, sentenceId);

            entRaw.deleteEntityById(em, entityId); 
            
            //em.getTransaction().commit();                    
        }
        catch(Exception e)
        {
            throw e;
            //em.getTransaction().rollback();
            //answer=false;
        }
  
        //return answer;        
    }

    public void deleteEntityClusterAndUpdateSentece (EntityManager em, int entityId, String entity, String type, int sentenceId, String sentence)
    {
        //oolean answer=true;

        String tagIni="<"+type+">";
        String tagFin="</"+type+">";
        String entityToDelete=tagIni+entity+tagFin;

        //System.out.println("Entity to delete: " + entityToDelete);

        String updatedSentence= sentence.replaceAll(entityToDelete, entity);   
                
        
        //EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();                        
        
        //This class is used to execute the metod to update sententece
        CSentence sent= new CSentence();
        //This class is used to execute the metod to update delete entities raw
        CEntityRaw entRaw= new CEntityRaw();
        
        //em.getTransaction().begin();
        
        try{
            sent.updateSelectedSentences(em, updatedSentence, sentenceId);

            entRaw.deleteEntityClusterById(em, entityId); 
            
            //em.getTransaction().commit();                    
        }
        catch(Exception e)
        {
            throw e;
            //em.getTransaction().rollback();
            //answer=false;
        }
  
        //return answer;        
    }
    
    public void updateEntityAndUpdateSentence(EntityManager em, int entityId, String entityName, 
                                            String oldType, String newType, int sentenceId, String sentence)
    {
        
        String tagIniOld="<"+oldType+">";
        String tagFinOld="</"+oldType+">";
        String entityOld=tagIniOld+entityName+tagFinOld;
        
        String tagIniNew="<"+newType+">";
        String tagFinNew="</"+newType+">";
        String entityNew=tagIniNew+entityName+tagFinNew;

        //System.out.println("Entity to delete: " + entityToDelete);

        String updatedSentence= sentence.replaceAll(entityOld, entityNew);   
        
        //This class is used to execute the metod to update sententece
        CSentence sent= new CSentence();
        //This class is used to execute the metod to update entities raw
        CEntityRaw entRaw= new CEntityRaw();
        
        //em.getTransaction().begin();
        
        try{
            sent.updateSelectedSentences(em, updatedSentence, sentenceId);

            entRaw.updateEntityType(em, entityId, newType); 
            
            //em.getTransaction().commit();                    
        }
        catch(Exception e)
        {
            throw e;
            //em.getTransaction().rollback();
            //answer=false;
        }
        
        
    }
    
}
