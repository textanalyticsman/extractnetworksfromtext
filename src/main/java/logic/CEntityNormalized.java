/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;


import java.util.List;
import database.*;

import java.io.BufferedReader;
import edu.upc.freeling.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import database.DAOCluster;

/**
 *
 * @author osboxes
 */
public class CEntityNormalized {    
    
    public List<Object[]> generateNormalizedEntities(int corpusId, int minPts, double epsilon)
    {
        List<Object[]> listOfNormalizedEntities=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=   "(select  " +
                            "table2.CLUSTERID,table2.LABEL,table2.ENTITYID,table2.ENTITYNAME,table2.entitytype  " +
                            "from  " +
                            "(select  " +
                            "distinct  " +
                            "clus.CLUSTERID as clusterid,  " +
                            "clus.LABEL as label ,  " +
                            "ent.ENTITYNAME as entityname,  " +
                            "ent.ENTITYTYPE as entitytype,  " +
                            "table1.longitud as longitud,  " +
                            "ent.ENTITYID  " +
                            "from  " +
                            "snadb.entityraw ent,  " +
                            "snadb.entityrawcluster entclus,  " +
                            "snadb.cluster clus,  " +
                            "(SELECT  " +
                            "c.CLUSTERID as clusterid,  " +
                            "max(length(e.ENTITYNAME)) as longitud  " +
                            "from  " +
                            "snadb.cluster c,  " +
                            "snadb.entityraw e,  " +
                            "snadb.entityrawcluster ec  " +
                            "WHERE  " +
                            "ec.CLUSTERID=c.CLUSTERID and  " +
                            "ec.ENTITYID=e.ENTITYID and  " +
                            "c.CORPID = ? and  " +
                            "c.minpts =? and  " +
                            "c.eps=? and  " +
                            "c.LABEL<>?  " +
                            "group by 1) as table1  " +
                            "where  " +
                            "ent.ENTITYID=entclus.ENTITYID and  " +
                            "entclus.CLUSTERID=table1.clusterid and  " +
                            "clus.CLUSTERID=entclus.CLUSTERID  " +
                            "having  " +
                            "length(ent.ENTITYNAME)=table1.longitud) as table2  " +
                            "group by 1,2)  " +
                            "  " +
                            "union  " +
                            "  " +
                            "(SELECT c.CLUSTERID,c.LABEL,e.ENTITYID,e.ENTITYNAME,e.ENTITYTYPE  " +
                            "from  " +
                            "snadb.cluster c,  " +
                            "snadb.entityraw e,  " +
                            "snadb.entityrawcluster ec  " +
                            "WHERE  " +
                            "ec.CLUSTERID=c.CLUSTERID and  " +
                            "ec.ENTITYID=e.ENTITYID and  " +
                            "c.CORPID = ? and  " +
                            "c.minpts =? and  " +
                            "c.eps=? and  " +
                            "c.LABEL=?)";                     
                                        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);
        query.setParameter(4, 2147483647);//This represents noise in the definition of DBSCAN as implemented by Smile
        query.setParameter(5, corpusId);
        query.setParameter(6, minPts);
        query.setParameter(7, epsilon);
        query.setParameter(8, 2147483647);//This represents noise in the definition of DBSCAN as implemented by Smile
        
        listOfNormalizedEntities= query.getResultList();
        
        return listOfNormalizedEntities;        
    }
    
    public DAOEntityNormalized getNormanlizedEntityForACluster(int corpusId, int minPts, double epsilon, int clusterId)
    {
        /*
        28/08/2018
        After generating normalized entities this software needs a method to cover
        the need of fixing clusters by hand. Thus, this is going to fix the normalization
        process in case clusters are modified after this phase.
        */
        
        //To get the unique register returned by the query
        List<Object[]> resultSet=new ArrayList<Object[]>();
        
        DAOEntityNormalized entityNormalized= new DAOEntityNormalized();
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText="(select " +
                    " table2.CLUSTERID,table2.LABEL,table2.ENTITYID,table2.ENTITYNAME,table2.entitytype " +
                    " from " +
                    " (select " +
                    " distinct " +
                    " clus.CLUSTERID as clusterid, " +
                    " clus.LABEL as label , " +
                    " ent.ENTITYNAME as entityname, " +
                    " ent.ENTITYTYPE as entitytype, " +
                    " table1.longitud as longitud, " +
                    " ent.ENTITYID " +
                    " from " +
                    " snadb.entityraw ent, " +
                    " snadb.entityrawcluster entclus, " +
                    " snadb.cluster clus, " +
                    " (SELECT " +
                    " c.CLUSTERID as clusterid, " +
                    " max(length(e.ENTITYNAME)) as longitud " +
                    " from " +
                    " snadb.cluster c, " +
                    " snadb.entityraw e, " +
                    " snadb.entityrawcluster ec " +
                    " WHERE " +
                    " c.CLUSTERID=? and " +
                    " ec.CLUSTERID=c.CLUSTERID and " +
                    " ec.ENTITYID=e.ENTITYID and " +
                    " c.CORPID = ? and " +
                    " c.minpts =? and " +
                    " c.eps=? and " +
                    " c.LABEL<>? " +
                    " group by 1) as table1 " +
                    " where " +
                    " ent.ENTITYID=entclus.ENTITYID and " +
                    " entclus.CLUSTERID=table1.clusterid and " +
                    " clus.CLUSTERID=entclus.CLUSTERID " +
                    " having " +
                    " length(ent.ENTITYNAME)=table1.longitud) as table2 " +
                    " group by 1,2)";   
        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, clusterId);
        query.setParameter(2, corpusId);
        query.setParameter(3, minPts);
        query.setParameter(4, epsilon);
        /*This represents noise in the definition of DBSCAN as implemented by 
        Smile*/
        query.setParameter(5, 2147483647);
        //Getting the result of the query
        resultSet=query.getResultList();
        //A DAOCluster object is created
        DAOCluster dCluster = new DAOCluster(clusterId);
        entityNormalized.setClusterid(dCluster);
        //Getting the first row because the query only gets one row
        Object[] temp=resultSet.get(0);
        //Column number 4th from the resultSet is used here
        entityNormalized.setEntitynormname((String)temp[3]);
        //Column number 5th from the resultSet is used here
        entityNormalized.setEntitynormtype((String)temp[4]);        
                
        return entityNormalized;
    }        
    
    public List<Object[]>  getNormalizedEntitiesByCorpusEpsMinpts(int corpusId, int minPts, double epsilon)
    {
        List<Object[]> listOfNormalizedEntities=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=   "select  " +
                            "clus.CLUSTERID,clus.LABEL,entnorm.ENTITYNORMID,entnorm.ENTITYNORMNAME,entnorm.ENTITYNORMTYPE,entnorm.ENTITYNORMROLE " +
                            "from " +
                            "snadb.corpus corp, " +
                            "snadb.entitynormalized entnorm, " +
                            "snadb.cluster clus " +
                            "where " +
                            "clus.CORPID=corp.CORPID and " +
                            "entnorm.CLUSTERID=clus.CLUSTERID and " +
                            "clus.CORPID=? and " +
                            "clus.MINPTS=? and " +
                            "clus.EPS=?";                     
                                        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);
        
        listOfNormalizedEntities= query.getResultList();
        
        return listOfNormalizedEntities;        
    }
    
    public void saveNormalizedEntity(EntityManager em, String entityNormName, String entityNormType, int clusterId)
    {
        Query query= em.createNamedQuery("DAOCluster.findByClusterid");
        query.setParameter("clusterid", clusterId);
        DAOCluster cluster= (DAOCluster)query.getSingleResult();
        
        DAOEntityNormalized dEntNorm = new DAOEntityNormalized();
        
        dEntNorm.setClusterid(cluster);
        dEntNorm.setEntitynormname(entityNormName);
        dEntNorm.setEntitynormtype(entityNormType);
        
        //em.getTransaction().begin();
        try
        {   
            em.persist(dEntNorm);
            //em.getTransaction().commit();
        }
        catch (Exception e)
        {
            //em.getTransaction().rollback();
            throw e;
        }
        
       // return dCluster.getClusterid();        
    }
    
    public void saveRelationNormalizedEntitiesSentences(EntityManager em, int entityNormId, int sentenceId)
    {
        Query query= em.createNamedQuery("DAOSentence.findBySentenceid");                
        query.setParameter("sentenceid", sentenceId);
        DAOSentence sentence= (DAOSentence)query.getSingleResult();
        
        query= em.createNamedQuery("DAOEntityNormalized.findByEntitynormid");                
        query.setParameter("entitynormid", entityNormId);
        DAOEntityNormalized entityNormalized= (DAOEntityNormalized)query.getSingleResult();
        
        DAOSentenceentitynormalized DAOSentEntNorm=new DAOSentenceentitynormalized();
        DAOSentenceentitynormalizedPK DAOSentEntNormPK = new DAOSentenceentitynormalizedPK();

        DAOSentEntNormPK.setEntitynormid(entityNormId);
        DAOSentEntNormPK.setSentenceid(sentenceId);
        
        DAOSentEntNorm.setDAOEntityNormalized(entityNormalized);
        DAOSentEntNorm.setDAOSentence(sentence);

        DAOSentEntNorm.setDAOSentenceentitynormalizedPK(DAOSentEntNormPK);                     
        
        //em.getTransaction().begin();
        try
        {   
            em.persist(DAOSentEntNorm);
            //em.getTransaction().commit();
        }
        catch (Exception e)
        {
            //em.getTransaction().rollback();
            throw e;
        }
        
       // return dCluster.getClusterid();        
    }    
    
    public Long validateExistanceOfNormalizedEntities(int corpusId, int minPts, double epsilon)
    {
        long answer=0;
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=   "select count(1) " +
                            "from snadb.entitynormalized en " +
                            "where " +
                            "en.CLUSTERID in  " +
                            "(select c.clusterid " +
                            "from  " +
                            "snadb.cluster c " +
                            "where  " +
                            "c.CORPID =? and " +
                            "c.MINPTS=? and " +
                            "c.EPS=?)";                     
                                        
        Query query = em.createNativeQuery(queryText);
        
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);                
                
        answer= (Long)query.getSingleResult();
        
        return answer;
    }
    
   public Long validateExistanceOfSentenceNormalizedEntities(int corpusId, int minPts, double epsilon)
    {
        long answer=0;
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=   "select count(1)  " +
                            "from snadb.sentenceentitynormalized sen  " +
                            "where  " +
                            "sen.entitynormid in  " +
                            "(select   " +
                            "en.entitynormid  " +
                            "from   " +
                            "snadb.cluster c,  " +
                            "snadb.entitynormalized en  " +
                            "where   " +
                            "c.CORPID =? and  " +
                            "c.MINPTS=? and  " +
                            "c.EPS=? and  " +
                            "en.CLUSTERID=c.CLUSTERID  " +
                            ");   ";                     
                                        
        Query query = em.createNativeQuery(queryText);
        
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);                
                
        answer= (Long)query.getSingleResult();
        
        return answer;
    }    
   
    
    public List<Object[]>  getNormalizedEntitiesAndSentences(int corpusId, int minPts, double epsilon)
    {
        List<Object[]> listOfNormalizedEntitiesAndSentences=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=   "select * from " +
                            "((select " +
                            "entnorm.ENTITYNORMID,entnorm.ENTITYNORMNAME, entnorm.ENTITYNORMTYPE, sent.SENTENCEID, sent.SENTENCECONTENT, entnorm.ENTITYNORMROLE " +
                            "from " +
                            "snadb.corpus corp, " +
                            "snadb.entitynormalized entnorm, " +
                            "snadb.cluster clus, " +
                            "snadb.entityrawcluster entrawclu, " +
                            "snadb.entityraw entraw, " +
                            "snadb.sentence sent " +
                            "where " +
                            "clus.CORPID=corp.CORPID and " +
                            "entnorm.CLUSTERID=clus.CLUSTERID and " +
                            "clus.CLUSTERID=entrawclu.CLUSTERID and " +
                            "entraw.ENTITYID=entrawclu.ENTITYID and " +
                            "entraw.SENTENCEID=sent.SENTENCEID and " +
                            "clus.CORPID=? and " +
                            "clus.MINPTS=? and " +
                            "clus.EPS=? and " +
                            "clus.LABEL<>?) " +                            
                            "union " +
                            "(select " +
                            "entnorm.ENTITYNORMID,entnorm.ENTITYNORMNAME, entnorm.ENTITYNORMTYPE, sent.SENTENCEID, sent.SENTENCECONTENT, entnorm.ENTITYNORMROLE " +
                            "from " +
                            "snadb.corpus corp, " +
                            "snadb.entitynormalized entnorm, " +
                            "snadb.cluster clus, " +
                            "snadb.entityrawcluster entrawclu, " +
                            "snadb.entityraw entraw, " +
                            "snadb.sentence sent " +
                            "where " +
                            "clus.CORPID=corp.CORPID and " +
                            "entnorm.CLUSTERID=clus.CLUSTERID and " +
                            "clus.CLUSTERID=entrawclu.CLUSTERID and " +
                            "entraw.ENTITYID=entrawclu.ENTITYID and " +
                            "entraw.SENTENCEID=sent.SENTENCEID and " +
                            "entraw.ENTITYNAME=entnorm.ENTITYNORMNAME and " +
                            "clus.CORPID=? and " +
                            "clus.MINPTS=? and " +
                            "clus.EPS=? and " +
                            "clus.LABEL=? " +
                            ")) as table1";                     
                                        
        Query query = em.createNativeQuery(queryText);
        
        int noise=2147483647;
        
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);
        query.setParameter(4, noise);
        query.setParameter(5, corpusId);
        query.setParameter(6, minPts);
        query.setParameter(7, epsilon);
        query.setParameter(8, noise);
        
        listOfNormalizedEntitiesAndSentences= query.getResultList();
        
        return listOfNormalizedEntitiesAndSentences;        
    }

    public List<Object[]>  getNormalizedEntitiyAndSentencesForACLuster(int corpusId, int minPts, double epsilon, int clusterId)
    {
        /*
        29/08/2018
        After generating normalized entities this software needs a method to cover
        the need of fixing clusters by hand. Thus, this is going to get the 
        relationships between sentences and a normalized entity 
        in case clusters are modified after this phase.
        04/09/2018
        The query assigned to queryText was modified to include a distinct clause,
        without this clause it returns repeated rows, which generates problems
        for the insert executed on table sentenceentitynormalized
        */
        List<Object[]> listOfNormalizedEntitiesAndSentences=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager(); 
        
        
        String queryText="select distinct " +
                        "entnorm.ENTITYNORMID,sent.SENTENCEID " +
                        "from " +
                        "snadb.corpus corp, " +
                        "snadb.entitynormalized entnorm, " +
                        "snadb.cluster clus, " +
                        "snadb.entityrawcluster entrawclu, " +
                        "snadb.entityraw entraw, " +
                        "snadb.sentence sent " +
                        "where " +
                        "clus.CLUSTERID=? and " +
                        "clus.CORPID=corp.CORPID and " +
                        "entnorm.CLUSTERID=clus.CLUSTERID and " +
                        "clus.CLUSTERID=entrawclu.CLUSTERID and " +
                        "entraw.ENTITYID=entrawclu.ENTITYID and " +
                        "entraw.SENTENCEID=sent.SENTENCEID and " +
                        "clus.CORPID=? and " +
                        "clus.MINPTS=? and " +
                        "clus.EPS=? and " +
                        "clus.LABEL<>?";    
        
        Query query = em.createNativeQuery(queryText);
        
        int noise=2147483647;  
        
        query.setParameter(1, clusterId);
        query.setParameter(2, corpusId);
        query.setParameter(3, minPts);
        query.setParameter(4, epsilon);
        query.setParameter(5, noise);        
        
        listOfNormalizedEntitiesAndSentences= query.getResultList();
        
        return listOfNormalizedEntitiesAndSentences;                
    }        
    
 public String saveRelationNormalizedEntitySentencesForACluster(List<Object[]> resultSet)
 {
        String answer="";
        CEntityNormalized entNorm = new CEntityNormalized();
                        
        int selectCount=resultSet.size();
        
        if(selectCount>0)
        {
            EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager(); 

            em.getTransaction().begin();
            
            for (Object[] result : resultSet)
            {    
                Integer entityNormId=(Integer)result[0];
                Integer sentenceId=(Integer)result[1];
                
                
                CEntityNormalized cEntNorm = new CEntityNormalized();
                cEntNorm.saveRelationNormalizedEntitiesSentences(em, entityNormId, sentenceId);

            }

            try
            {
              em.getTransaction().commit();
            }
            catch (Exception e)
            {
              answer="\nIt was impossible to save the relationship between a normalized entity and sentences";
              em.getTransaction().rollback();
              e.printStackTrace();
              throw e;
            }            
          
        }
        else
        {
            answer="You should generate these relationships before saving them";
        }
        
        return answer;     
 }    

    public void updateEntityNormRole(EntityManager em, int entityId, String newRole)
    {
        try
        {
            Query query = em.createNamedQuery("DAOEntityNormalized.findByEntitynormid");
            query.setParameter("entitynormid", (Integer)entityId);
            DAOEntityNormalized daoEntNorm=(DAOEntityNormalized)query.getSingleResult();

            //String queryText="update DAOSentence s set s.sentencecontent=:updatedSentence where s.sentenceid = :sentenceId";

             //The begining of the transaction
            //em.getTransaction().begin();

            daoEntNorm.setEntitynormrole(newRole);

            em.persist(daoEntNorm);            
        }
        catch(Exception e)
        {
            throw e;
        }           
    }
    
}
