/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import database.DAOCluster;
import database.DAOCorpus;
import database.DAOEntityRaw;
import database.DAOEntityrawcluster;
import database.DAOEntityrawclusterPK;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import smile.clustering.DBScan;
import smile.math.distance.Metric;

/**
 *
 * @author osboxes
 */
public class CCluster {
    private String clusId;
    private int clusSize;
    private double eps;
    private int minPts;
    
    
public String generateClusters(EntityManager em, int minPts,double epsilon,  int corpusId /* DAOCorpus corpusId revisar el solo enviar el id como int*/)
    {
        String answer="";                
                        
        Metric<DAOEntityRaw> distance = new Metric<DAOEntityRaw>() {
            @Override
            public double d(DAOEntityRaw x, DAOEntityRaw y) {
                //return x.equals(y) ? 0.0 : 1.0;
                //double d=calculateJaccardDistance(x.getEntityname(),y.getEntityname());
                double d=calculateJaccardDistance(x.getEntityname(),x.getEntitytype(),y.getEntityname(),y.getEntitytype());
                //System.out.println("Distance from: " + x.getEntityname() + " to: " +y.getEntityname() +" is: " +d );
                return d;
            }
        };
        
        try
        {                       
            //Creo un array para almacenar los objetos que serán organizados en clusters
            CEntityRaw cEntRaw= new CEntityRaw();
            
            List<DAOEntityRaw> entitiesRawList =cEntRaw.getEntitiesByCorpusId(em, corpusId);
            
            DAOEntityRaw[] dataSet=new DAOEntityRaw[entitiesRawList.size()] ;

            //Paso los objetos EntityRaw desde la lista al array
            entitiesRawList.toArray(dataSet);

            //Corro el algortimo DBSCAN provisto por Smile                    
            DBScan<DAOEntityRaw> clusterer = new DBScan<>(dataSet, distance, minPts, epsilon);

            //Obtengo el número de clusters para crear las entradas en las tablas de cluster
            int numClusters=clusterer.getNumClusters();

            Query query;

            if (numClusters!=0)
            {
                 //Obtengo los labels que identifican a cada cluster
                int[] labels = clusterer.getClusterLabel();  
                
                query = em.createNamedQuery("DAOCorpus.findByCorpid");
                query.setParameter("corpid", corpusId);
                DAOCorpus corpus = (DAOCorpus)query.getSingleResult();                  
                
                for (int i=0;i<=numClusters;i++)
                {
                    if(i<numClusters)
                    {
                        DAOCluster daoCluster= new DAOCluster();
                        daoCluster.setEps(epsilon);
                        daoCluster.setMinpts(minPts);
                        daoCluster.setCorpid(corpus);
                        daoCluster.setLabel(i);
                        em.persist(daoCluster);                        
                    }
                    else
                    {
                        /*El último elemento corresponde a los Out Lier o elementos
                        que no pertenecen a ningún cluster
                        */
                        DAOCluster daoCluster= new DAOCluster();
                        daoCluster.setEps(epsilon);
                        daoCluster.setMinpts(minPts);
                        daoCluster.setCorpid(corpus);
                        daoCluster.setLabel(DBScan.OUTLIER);
                        em.persist(daoCluster);                           
                    }             
                }
               for (int i=0; i<labels.length; i++)
               {                                                 
                    DAOCluster clusterId = getClusterByLabelCorpusIdMinPtsAndEpsilon(em, corpus, labels[i], minPts, epsilon);//Ahora debo pasarle los parámetros tambien para identificar solo a un cluster
                    
                    DAOEntityrawcluster DAOEntRawClus=new DAOEntityrawcluster();
                    DAOEntityrawclusterPK DAOEntRawClusPK = new DAOEntityrawclusterPK();
                    
                    DAOEntRawClusPK.setClusterid(clusterId.getClusterid());
                    DAOEntRawClusPK.setEntityid(dataSet[i].getEntityid());
                    
                    DAOEntRawClus.setDAOCluster(clusterId);
                    DAOEntRawClus.setDAOEntityRaw(dataSet[i]);
                    
                    DAOEntRawClus.setDAOEntityrawclusterPK(DAOEntRawClusPK);           
                    
                    em.persist(DAOEntRawClus);        

               }                
                
                /*for (int i=0;i<=numClusters;i++)
                {
                    DAOCluster daoCluster= new DAOCluster();
                    daoCluster.setEps(epsilon);
                    daoCluster.setMinpts(minPts);
                    daoCluster.setCorpid(corpus);
                    daoCluster.setLabel(i);
                    em.persist(daoCluster);
                    //labelClusterID.put(i, daoCluster);                
                }                    

               for (int i=0; i<labels.length; i++)
               {
                   if(labels[i]!= DBScan.OUTLIER)
                   {                            
                       //DAOCluster clusterId = getClusterByLabelAndCorpusId(em, corpus, labels[i], minPts, epsilon);
                       DAOCluster clusterId = getClusterByLabelCorpusIdMinPtsAndEpsilon(em, corpus, labels[i], minPts, epsilon);//Ahora debo pasarle los parámetros tambien para identificar solo a un cluster
                       dataSet[i].setClusterid(clusterId);
                       em.persist(dataSet[i]);
                   }
               }*/                    
            }
            else
            {
                answer="The system could not find any cluster for the given entities";
            }                      
        }
        catch (Exception e)
        {
            answer="CCluster.generateClusters - A problem happened during the cluster generation";
            e.printStackTrace();
        }

       return answer;
    }

private DAOCluster getClusterByLabelAndCorpusId(EntityManager em, DAOCorpus corpus,int label)
{
    DAOCluster answer=new DAOCluster();
    try
    {
        
        String queryText="SELECT d FROM DAOCluster d WHERE d.corpid = :corpid and d.label =:label";
                
        Query query=em.createQuery(queryText);
        
        query.setParameter("corpid", corpus);       
        query.setParameter("label", label);
        
        answer = (DAOCluster)query.getSingleResult();           
    }
    catch(Exception e)
    {
        throw e;
    }    
    
    return answer;
}

private DAOCluster getClusterByLabelCorpusIdMinPtsAndEpsilon(EntityManager em, DAOCorpus corpus,int label, int minPts, double epsilon)
{
    DAOCluster answer=new DAOCluster();
    try
    {
        
        String queryText="SELECT d FROM DAOCluster d WHERE d.corpid = :corpid and d.label =:label "
                + "and d.minpts=:minpts and d.eps=:eps";
                
        Query query=em.createQuery(queryText);
        
        query.setParameter("corpid", corpus);       
        query.setParameter("label", label);
        query.setParameter("minpts", minPts);
        query.setParameter("eps", epsilon);
        
        answer = (DAOCluster)query.getSingleResult();           
    }
    catch(Exception e)
    {
        throw e;
    }    
    
    return answer;
}

public boolean verifyClusterExistance(EntityManager em, int corpusId, int minPts, double epsilon)
{
    boolean answer=false;
    
    try
    {
        em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        Query query = em.createNamedQuery("DAOCorpus.findByCorpid");        
        query.setParameter("corpid",corpusId);
        
        DAOCorpus daoCorpus = (DAOCorpus)query.getSingleResult();                        
                
        String queryText="SELECT d FROM DAOCluster d WHERE d.corpid = :corpid and d.minpts =:minpts and d.eps=:eps";
                
        query=em.createQuery(queryText);
        
        query.setParameter("corpid", daoCorpus);       
        query.setParameter("minpts", minPts);
        query.setParameter("eps", epsilon);        
        
        List<DAOCluster> dClusterList = (List<DAOCluster>)query.getResultList();
        
        if(dClusterList.size()>0)
        {
            answer=true;
        }
    }
    catch(Exception e)
    {
        throw e;
    }        
    return answer;        
}

private float calculateJaccardDistance(String s1, String s2)
    {
        /*Los paso a minúsculas para evitar diferencias causadas por case sensitive*/
        String temp01=s1.toLowerCase();
        String temp02=s2.toLowerCase();
        
        StringTokenizer tokensS1 = new StringTokenizer(temp01);
        StringTokenizer tokensS2 = new StringTokenizer(temp02);
       
        Set<String> setS1=new HashSet<String>();
        Set<String> setS2=new HashSet<String>();
        
        while(tokensS1.hasMoreTokens())
        {
            setS1.add(tokensS1.nextToken());
        }

        while(tokensS2.hasMoreTokens())
        {
            setS2.add(tokensS2.nextToken());
        }
        
        /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
        {
            System.out.println("CONJUNTOS");
            System.out.println("Conjunto S1" + setS1.toString());
            System.out.println("Conjunto S2" + setS2.toString());
            
        }*/
        
        Set<String> tempOriginalSetS1=new HashSet<String>();
        
        //Guardo en un buffer los valores de setS1
        tempOriginalSetS1.addAll(setS1);
        
        //Obtengo la unión de setS1 y setS2 sobre setS1
        setS1.addAll(setS2);
        
        /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
        {
            System.out.println("Resultado de la union" + setS1.toString());                        
        }*/
        
        
        float unionNum=setS1.size();
        
        /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
        {
            System.out.println("INTERSECTANDO");
            System.out.println("tempOriginalSetS1 " + tempOriginalSetS1.toString());                        
            System.out.println("setS2 " + setS2.toString());                                   
        }*/                
        
        //Obtengo la intersección de setS1 (usando el buffer con sus valores originales) y setS2 sobre tempOriginalSetS1
        tempOriginalSetS1.retainAll(setS2);
        
        /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
        {
            System.out.println("Resultado de la interseccion " + tempOriginalSetS1.toString());                        
        }*/        
        
        float intersectionNum=tempOriginalSetS1.size();
        
        float jaccardDistance = 1-(intersectionNum/unionNum);                
        //double distanceDouble=1-(intersectionNum/unionNum);                
        
        /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
        {
            System.out.println("Distancia Jaccard " + jaccardDistance);    
            System.out.println("intersectionNum: " + intersectionNum);
            System.out.println("unionNum: " + unionNum);
            System.out.println("distanceDouble " + distanceDouble);
        }*/        
        
        return jaccardDistance;        

    }  

private float calculateJaccardDistance(String s1, String type1, String s2, String type2)
    {
        float jaccardDistance=1;
        
        //Transformo ambas cadenas a minúscula para evitar detectar diferencias asociadas 
        //al case sensitive
        String typeTemp1=type1.toLowerCase();
        String typeTemp2=type2.toLowerCase();
        
        if(typeTemp1.equals(typeTemp2))
        {
            /*Los paso a minúsculas para evitar diferencias causadas por case sensitive*/
            String temp01=s1.toLowerCase();
            String temp02=s2.toLowerCase();
            
            //Le quito los acentos para evitar detectar diferencias en los casos
            //donde los redactores se han equivocado y no han puesto bien los acentos
            
            temp01=stripAccents(temp01);
            temp02=stripAccents(temp02);

            StringTokenizer tokensS1 = new StringTokenizer(temp01);
            StringTokenizer tokensS2 = new StringTokenizer(temp02);

            Set<String> setS1=new HashSet<String>();
            Set<String> setS2=new HashSet<String>();

            while(tokensS1.hasMoreTokens())
            {
                setS1.add(tokensS1.nextToken());
            }

            while(tokensS2.hasMoreTokens())
            {
                setS2.add(tokensS2.nextToken());
            }

            /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
            {
                System.out.println("CONJUNTOS");
                System.out.println("Conjunto S1" + setS1.toString());
                System.out.println("Conjunto S2" + setS2.toString());

            }*/

            Set<String> tempOriginalSetS1=new HashSet<String>();

            //Guardo en un buffer los valores de setS1
            tempOriginalSetS1.addAll(setS1);

            //Obtengo la unión de setS1 y setS2 sobre setS1
            setS1.addAll(setS2);

            /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
            {
                System.out.println("Resultado de la union" + setS1.toString());                        
            }*/


            float unionNum=setS1.size();

            /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
            {
                System.out.println("INTERSECTANDO");
                System.out.println("tempOriginalSetS1 " + tempOriginalSetS1.toString());                        
                System.out.println("setS2 " + setS2.toString());                                   
            }*/                

            //Obtengo la intersección de setS1 (usando el buffer con sus valores originales) y setS2 sobre tempOriginalSetS1
            tempOriginalSetS1.retainAll(setS2);

            /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
            {
                System.out.println("Resultado de la interseccion " + tempOriginalSetS1.toString());                        
            }*/        

            float intersectionNum=tempOriginalSetS1.size();

            jaccardDistance = 1-(intersectionNum/unionNum);                
            //double distanceDouble=1-(intersectionNum/unionNum);                

            /*if (s1.equals("Ludith Orellana") && s2.equals("Orellana"))
            {
                System.out.println("Distancia Jaccard " + jaccardDistance);    
                System.out.println("intersectionNum: " + intersectionNum);
                System.out.println("unionNum: " + unionNum);
                System.out.println("distanceDouble " + distanceDouble);
            }*/        
            
        }
        
        return jaccardDistance;        

    }

public static String stripAccents(String s) 
{
    s = Normalizer.normalize(s, Normalizer.Form.NFD);
    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    return s;
}

    public Set getAvailableClusters(int corpusId)
    {
        //DAOCorpus corpus= new DAOCorpus(corpusId);        
        EntityManager entityManager = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        Query query = entityManager.createNamedQuery("DAOCorpus.findByCorpid");
        query.setParameter("corpid", corpusId);
                
        DAOCorpus corpus  = (DAOCorpus)query.getSingleResult();        
        
        return corpus.getDAOClusterSet();
        
    }
    
    public List<Object[]> getAvailableClustersNative(int corpusId, int minPts, double epsilon)
    {
        List<Object[]> dClusterObjects=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText="SELECT distinct c.CLUSTERID,c.LABEL,e.ENTITYNAME,count(e.ENTITYNAME) \n" +
                            "from " +
                            "snadb.cluster c, " +
                            "snadb.entityraw e, " +
                            "snadb.entityrawcluster ec " +
                            "WHERE " +
                            "ec.CLUSTERID=c.CLUSTERID and " +
                            "ec.ENTITYID=e.ENTITYID and " +
                            "c.CORPID = ? and " +
                            "c.minpts =? and " +
                            "c.eps=? " +
                            "group by 1,2";                           
        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);
        dClusterObjects= query.getResultList();
        
        return dClusterObjects;
    }
    
    public List<DAOCluster> getAvailableClusters(int corpusId, int minPts, double epsilon)
    {
        List<DAOCluster> dClusterList=new ArrayList<DAOCluster>();

        try
        {
            EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
            em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
            Query query = em.createNamedQuery("DAOCorpus.findByCorpid");        
            query.setParameter("corpid",corpusId);

            DAOCorpus daoCorpus = (DAOCorpus)query.getSingleResult();                        

            String queryText="SELECT d FROM DAOCluster d WHERE d.corpid = :corpid and d.minpts =:minpts and d.eps=:eps";

            query=em.createQuery(queryText);

            query.setParameter("corpid", daoCorpus);       
            query.setParameter("minpts", minPts);
            query.setParameter("eps", epsilon);        

            dClusterList = (List<DAOCluster>)query.getResultList();

        }
        catch(Exception e)
        {
            throw e;
        }
        return dClusterList;
    }

    public List<Object[]> getAvailableClusterConfigurationsByCorpusId(int corpusId)
    {
        /*
        This function is used to get the list of entities generated for a given 
        corpus
        */        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText="select distinct MINPTS,eps from snadb.cluster where CORPID=?";                           
        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        List<Object[]> result= query.getResultList();
        
        return result;        
    }
    
    public int generateNewClusterByHand(EntityManager em, int corpusId, int minPts, double epsilon, int label)
    {
        Query query= em.createNamedQuery("DAOCorpus.findByCorpid");
        query.setParameter("corpid", corpusId);
        DAOCorpus corpus= (DAOCorpus)query.getSingleResult();
        
        DAOCluster dCluster = new DAOCluster();
        
        dCluster.setCorpid(corpus);
        dCluster.setMinpts(minPts);
        dCluster.setEps(epsilon);
        dCluster.setLabel(label);
        
        em.getTransaction().begin();
        try
        {   
            em.persist(dCluster);
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            em.getTransaction().rollback();
            throw e;
        }
        
        return dCluster.getClusterid();
    }
    
}
