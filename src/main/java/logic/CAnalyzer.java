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


/**
 *
 * @author osboxes
 */
public class CAnalyzer {    
    

    
    public boolean extractEntitiesFromDocuments(List<DAODocument> documentsList) throws IOException
    {
        
        String FREELINGDIR = "/usr/local";
        String DATA = FREELINGDIR + "/share/freeling/";
        String LANG = "es";     
        System.loadLibrary( "freeling_javaAPI" );
        Util.initLocale( "default" );        
        
        boolean result=false;
        
        Set<Document> freeLingDocuments= new HashSet();
        
            // Create options set for maco analyzer.
            // Default values are Ok, except for data files.
            MacoOptions op = new MacoOptions( LANG );
            op.setDataFiles( "", 
                         DATA + "common/punct.dat",
                         DATA + LANG + "/dicc.src",
                         DATA + LANG + "/afixos.dat",
                         "",
                         DATA + LANG + "/locucions.dat", 
                         DATA + LANG + "/np.dat",
                         DATA + LANG + "/quantities.dat",
                         DATA + LANG + "/probabilitats.dat");    

            Tokenizer tk = new Tokenizer( DATA + LANG + "/tokenizer.dat" );
            Splitter sp = new Splitter( DATA + LANG + "/splitter.dat" );
            SWIGTYPE_p_splitter_status sid = sp.openSession();                      


            /*freelingJNI.Maco_setActiveOptions(swigCPtr, this, umap, num, pun, dat, dic, aff, comp, rtk, mw, ner, qt, prb);*/
            Maco mf = new Maco( op );
            mf.setActiveOptions(false, true, true, true,  // select which among created 
                                true, true, false, true,  // submodules are to be used. 
                                true, true/*ERA TRUE LE PONGO FALSE PARA PROBAR EL BIO*/, true, true);  // default: all created submodules 
                                                          // are used        
            LocalDateTime timePoint = LocalDateTime.now();
            //System.out.println("Inicio carga configuraciones: "+timePoint.toString());
            //System.out.println();  
            
            // Creating a BIO Ner 
            //Ner ner = new Ner(DATA + LANG + "/nerc/ner/ner-ab-poor1.dat");              

            // create a NE classifier
            Nec neclass = new Nec( DATA + LANG + "/nerc/nec/nec-ab-poor1.dat" );        
        
        //With this loop each document that belongs to the corpus is analyzed in
        //order to extract its entities and to classify them
        for (DAODocument c : documentsList) {                        




            timePoint = LocalDateTime.now();
            //System.out.println("Fin carga configuraciones: "+timePoint.toString());
            //System.out.println();

            //FileReader in = new FileReader("/home/osboxes/Desktop/filesToAnalize/fileTest05.txt");    
            String longString=c.getContent();
            
            //“ ” ’ ‘
            //07/12/2016 estos cuatro caracteres producen errores en FreeLing
            
            String tempA=longString.replaceAll("[“]", ""); //Quita “
            String tempB=tempA.replaceAll("[”]", ""); //Quita ”
            String tempC=tempB.replaceAll("[’]", ""); //Quita ’
            String tempD=tempC.replaceAll("[‘]", ""); //Quita ’
            String tempE=tempD.replaceAll("[\"]", ""); //Quita "
            String tempF=tempE.replaceAll("[']", ""); //Quita "
                    
            //String temp0=longString.replaceAll("[(][.][.][.][)]", ""); //Esto quita los "(...)"hechos por redactores 
            String temp0=tempF.replaceAll("[(][.][.][.][)]", ""); //Esto quita los "(...)"hechos por redactores 
            String temp1=temp0.replaceAll("\\s{2}", "\n\n");
            String temp2=temp1.replaceAll("[.]", ". ");
            String temp3=temp2.replaceAll("[.]\\s{2}", ". ");
            String temp4=temp3.replaceAll("[.]\\s{1}\\n", ".\n");
            
            String exito=temp4.replace(' ', ' ');//Quito el caracter rarazo y lo reemplazo por un espacio
            
            //String exito=longString.replaceAll("\\s{2}", "\n\n");
            
            //System.out.println("Este es mi exito:" + exito);

            //String[] loLograre=longString.split("\\s{2}");
            
            /*for(int i=0; i<loLograre.length;i++)
            {
                System.out.println("Lo lograré " + i+ ":"+loLograre[i]);
            }*/
            
            //String in=WordUtils.wrap(longString, 200, "\n", false);
            
            //String in =c.getContent();
            
            //System.out.println("in: " + in);
            
            //BufferedReader input= new BufferedReader(new StringReader(exito)); 
            
            InputStream stream = new ByteArrayInputStream(exito.getBytes());
               
            BufferedReader input= new BufferedReader(new InputStreamReader(stream));             

            Document doc = new Document();        

            timePoint = LocalDateTime.now();
            //System.out.println("Inicio While lectura archivo: "+timePoint.toString());

            //System.out.println();

            String line=null;
            
            while((line=input.readLine())!=null){               
                ListWord listWord = tk.tokenize( line );
                
                //System.out.println("line: " + line);

                // Split the tokens into distinct sentences.
                ListSentence ls = sp.split( sid, listWord, false );

                //System.out.println("List of sentences size: " + ls.size());

                edu.upc.freeling.Paragraph para = new edu.upc.freeling.Paragraph(ls);

                doc.pushBack(para);                                   
            }
            
            timePoint = LocalDateTime.now();
            //System.out.println("Fin  While lectura archivo: "+timePoint.toString());
            //System.out.println();   

            timePoint = LocalDateTime.now();
            //System.out.println("Inicio anlisis: "+timePoint.toString());
            //System.out.println();  

            // Perform morphological analysis with NER and others
            mf.analyze(doc);

            //Testing BIO NER
            //ner.analyze(doc);

            // xPerform named entity (NE) classificiation.
            neclass.analyze(doc);
                    

            //timePoint = LocalDateTime.now();
            //System.out.println("Fin análisis: "+timePoint.toString());
            //System.out.println();        

            //timePoint = LocalDateTime.now();
            //System.out.println("Inicio impresión: "+timePoint.toString());
            //System.out.println();        

            //printEntities(doc);
            
            result=saveEntitiesSentencesParagraphs(doc, c);            
            
            System.out.println("=====================================================");            
            System.out.println("ID of the document: " + c.getDocid());          
            
           //TESTING 07/12/2016
           //doc.delete();
           

            //printEnrichedDoc(doc);

            //printOracionesAndEntities(doc);
            //generateDocCorpusSentEnt(doc);

            //timePoint = LocalDateTime.now();
            //System.out.println("Fin impresión: "+timePoint.toString());
            //System.out.println();            
            
        }  
        
        return result;
    }
    
    private boolean saveEntitiesSentencesParagraphs(Document doc, DAODocument daoDoc)
    {
        /*
        This receives two parameters a Document with information about entities, 
        which is know as FreeLing document and a DAODOcument, which is the local
        representation of a document used by this application. The Freeling document 
        is analysed generating entities, sentences and paragraphs on the database        
        
        ChangeName: Including the paragraph
        Date:       26/03/2017
        Detail:     Here I have to add the logic to save the paragraph        
        */
      
        boolean answer=true;
        //This flag is used to avoid any action for empty paragraphs
        //boolean emptyParagraph=true;

        EntityManager entityManager = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();
        //Empiezo la transaccion
        entityManager.getTransaction().begin();           
        
        //To reconstruct paragrpahs using the order of sentences.
        int sentenceNumber=0;
        /*
        This variable is used to control paraghrap's order. In this way we will
        be able to reconstruct the document following the order of paragraphs
        */        
        int paragraphNumber=0; 
        String oracion;
        String bunchOfEntities;
        String entity;
        String paragraph;
        
        ListParagraphIterator pIt = new ListParagraphIterator(doc);        
        
        //The set of paragraphs that belong to the document
        Set<DAOParagraph> daoParagraphSet = new HashSet<DAOParagraph>();
        
        while (pIt.hasNext()) {
            
            /*To iterate through sentences and to detect whether the paragraph is
            empty or not
            */            
            ListSentenceIterator sIt = new ListSentenceIterator(pIt.next());
            
            //Setting the variable that is goint to have the paragraph
            paragraph="";
            
            if (sIt.hasNext())
            {
                System.out.println("NO EMPTY PARAGRAPH");
                //This line is used to create the paragraph used to group sentences
                DAOParagraph daoParagraph = new DAOParagraph();
                //To increase the paragraph order counter
                paragraphNumber++;

                System.out.println("Paragraph number: " + paragraphNumber);

                //The position (order)of this paragraph within the document
                daoParagraph.setParagraphorder(paragraphNumber);

                while (sIt.hasNext()) 
                {  
                    //Change the flag state to process this paragraph
                    //emptyParagraph=false;
                    //Increase the sentence order
                    sentenceNumber++;
                    //Getting the next sentence
                    Sentence s = sIt.next();   
                    //Getting the list of words inside the sentence
                    ListWordIterator wIt = new ListWordIterator(s);              
                    oracion="";
                    entity="";
                    bunchOfEntities="";                    

                    //Creación ede la sentencia que es guardada en la base de datos
                    DAOSentence daoSentence = new DAOSentence();
                    //The sentence is related with the paragraph
                    daoSentence.setParagraphid(daoParagraph);

                    //Aquí debo de generar una colección de entitidades de tipo raw
                    Set <DAOEntityRaw> daoEntityRawSet=new HashSet<DAOEntityRaw>();

                    //Con este while detecto los tipos de entidades
                    while (wIt.hasNext()) {
                        Word w = wIt.next();
                        String type="";
                        switch (w.getTag()) {
                            case "NP00SP0":
                                type="PER";
                                break;
                            case "NP00G00":
                                type="LOC";
                                break;
                            case "NP00O00":
                                type="ORG";
                                break;
                            case "NP00V00":
                                type="OTH";
                                break;
                            default:
                                break;
                        }

                        if(!type.equals(""))
                        {

                            //entity ="<"+type+">"+w.getForm()+"</"+type+">";
                            entity ="<"+type+">"+w.getForm().replace('_',' ')+"</"+type+">";
                            oracion=oracion+entity+" ";                      
                            bunchOfEntities=bunchOfEntities+entity+", ";                      
                            entity="";

                            //Borro el caracter "_" añadido por FreeLing
                            String entityTemp=w.getForm().replace('_',' ');

                            //Creo una nueva entidad con los datos extraídos del texto por FreeLing
                            DAOEntityRaw daoEntityRaw= new DAOEntityRaw();
                            daoEntityRaw.setEntityname(entityTemp);
                            daoEntityRaw.setEntitytype(type);
                            //establezco la relación con la tabla de documentos
                            daoEntityRaw.setSentenceid(daoSentence);

                            //Guardo la entidad en la base de datos
                            entityManager.persist(daoEntityRaw);
                            //entityManager.clear();

                            //Añado la entidad detectada a la colección de entidades que le pertenecen
                            //a una sentencia
                            daoEntityRawSet.add(daoEntityRaw);
                        }
                        else
                        {
                            oracion=oracion+w.getForm()+" ";
                        }
                    }
                    System.out.println();
                    System.out.println("Sentence number: "+ sentenceNumber);
                    System.out.println("Sentence: " + oracion);
                    System.out.println("Entities: " + bunchOfEntities);
                    
                    //Here the paragraph is built
                    paragraph=paragraph+ oracion + "\n";

                    //Tomo el objeto dao de sentencia y le asigno el contenido de la oración detectada                
                    daoSentence.setSentencecontent(oracion);
                    //Tomo el objeto dao de sentencia y le coloco su número de orden en el texto
                    daoSentence.setSentenceorder(sentenceNumber);              
                    //Relaciono la sentencia con su lista de entidades
                    if (!daoEntityRawSet.isEmpty())
                    {
                        daoSentence.setDAOEntityRawSet(daoEntityRawSet);

                    }

                    /*Saving the sentence on the DB. This instruction also saves 
                    the set of entities "daoEntityRawSet" related to the sentence*/
                    entityManager.persist(daoSentence);
                    //entityManager.clear();                            
                }
                //System.out.println("PARAGRAPH: " + paragraph);
                /*Saving the paragraph after cheking the flag to avoid persisting
                an emtpty paragraph that generates a Column 'PARAGRAPHID' cannot be null
                */
                //if(!emptyParagraph)
                //{
                //System.out.println("NO EMPTY PARAGRAPH");
                //Here the paragraph is related to the document
                daoParagraph.setDocid(daoDoc);
                //Here the paragraph content is saved
                daoParagraph.setParagraphcontent(paragraph);
                //The paragraph is saved
                entityManager.persist(daoParagraph);
                //The paragraph is added into the paragraphs set
                daoParagraphSet.add(daoParagraph);
                //The flag is set to tru to avoid processing empty paragraphs
                //emptyParagraph=true;
                //}
                //else
                //{
                  //  System.out.println("EMPTY PARAGRAPH");
                //}                
            }    
            else
            {
                System.out.println("EMPTY PARAGRAPH");
            }
                
    
        }
        if (!daoParagraphSet.isEmpty())
        {            
            daoDoc.setDAOParagraphSet(daoParagraphSet);

        }
        try
        {
            entityManager.getTransaction().commit();
            //entityManager.flush();

        }
        catch(Exception e)
        {
            entityManager.getTransaction().rollback(); 
            answer=false;
        }                           
       
        
        return answer;
    }
    
    private static void printEnrichedDoc(Document doc)
    {
      int entId=1;
      String documento="";
      String entity="";
      ListParagraphIterator pIt = new ListParagraphIterator(doc);
      while (pIt.hasNext()) {
          ListSentenceIterator sIt = new ListSentenceIterator(pIt.next());
          while (sIt.hasNext()) {
              Sentence s = sIt.next();              
              ListWordIterator wIt = new ListWordIterator(s);              
              while (wIt.hasNext()) {
                  Word w = wIt.next();

                  String type="";

                  switch (w.getTag()) {
                      case "NP00SP0":
                          type="Person";
                          break;
                      case "NP00G00":
                          type="Location";
                          break;
                      case "NP00O00":
                          type="Organization";
                          break;
                      case "NP00V00":
                          type="Others";
                          break;
                      default:
                          break;
                  }

                  if(!type.equals(""))
                  {
                      entity ="<"+type+">"+w.getForm()+"</"+type+">";
                      documento=documento+entity+" ";
                      entity="";
                  }
                  else
                  {
                      documento=documento+w.getForm()+" ";
                  }
              }

              //System.out.println();
          }
          documento=documento+"\n";
      }

      System.out.println(documento);

    }   
    
    private static void printOracionesAndEntities(Document doc)
    {
      int sentenceNumber=0;
      String oracion;
      String bunchOfEntities;
      String entity;
      ListParagraphIterator pIt = new ListParagraphIterator(doc);
      while (pIt.hasNext()) {
          ListSentenceIterator sIt = new ListSentenceIterator(pIt.next());
          while (sIt.hasNext()) {
              sentenceNumber++;
              Sentence s = sIt.next();                            
              ListWordIterator wIt = new ListWordIterator(s);              
              oracion="";
              entity="";
              bunchOfEntities="";
              while (wIt.hasNext()) {
                  Word w = wIt.next();
                  String type="";
                  switch (w.getTag()) {
                      case "NP00SP0":
                          type="Person";
                          break;
                      case "NP00G00":
                          type="Location";
                          break;
                      case "NP00O00":
                          type="Organization";
                          break;
                      case "NP00V00":
                          type="Others";
                          break;
                      default:
                          break;
                  }

                  if(!type.equals(""))
                  {
                      entity ="<"+type+">"+w.getForm()+"</"+type+">";
                      oracion=oracion+entity;                      
                      bunchOfEntities=bunchOfEntities+entity+", ";
                      entity="";
                  }
                  else
                  {
                      oracion=oracion+w.getForm()+" ";
                  }
              }
              System.out.println();
              System.out.println("Sentence number: "+ sentenceNumber);
              System.out.println("Sentence: " + oracion);
              System.out.println("Entities: " + bunchOfEntities);
              //System.out.println();
          }
          
      }      

    }

 
 public List<CEdge>  getSocialNetworkCoOcurrenceBasedOnSentences(int corpusId, int minPts, double epsilon)
    {
        List<Object[]> rawSocialNetwork=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=   "select " +
                            "senentnorm01.ENTITYNORMID, " +
                            "senentnorm01.ENTITYNORMNAME, " +
                            "senentnorm01.SENTENCEID, " +
                            "senentnorm01.SENTENCECONTENT, "+
                            "senentnorm02.ENTITYNORMID, " +
                            "senentnorm02.ENTITYNORMNAME, " +
                            "senentnorm02.SENTENCEID, " +
                            "senentnorm02.SENTENCECONTENT, " +
                            "senentnorm02.SENTENCECONTENT "+
                            "from " +
                            "(select " +
                            "sen01.ENTITYNORMID, " +
                            "sen01.SENTENCEID, " +
                            "entnorm.ENTITYNORMNAME, " +
                            "sent.SENTENCECONTENT " +
                            "from " +
                            "snadb.sentenceentitynormalized sen01, " +
                            "snadb.entitynormalized entnorm, " +
                            "snadb.sentence sent, " +
                            "snadb.cluster clu, " +
                            "snadb.corpus corp " +
                            "where " +
                            "sen01.ENTITYNORMID=entnorm.ENTITYNORMID and " +
                            "sen01.SENTENCEID=sent.SENTENCEID and " +
                            "entnorm.CLUSTERID=clu.CLUSTERID and " +
                            "corp.CORPID=clu.CORPID and " +
                            "corp.CORPID=? and " +
                            "clu.MINPTS=? and " +
                            "clu.EPS=?) as senentnorm01, " +
                            "(select " +
                            "sen01.ENTITYNORMID, " +
                            "sen01.SENTENCEID, " +
                            "entnorm.ENTITYNORMNAME, " +
                            "sent.SENTENCECONTENT " +
                            "from " +
                            "snadb.sentenceentitynormalized sen01, " +
                            "snadb.entitynormalized entnorm, " +
                            "snadb.sentence sent, " +
                            "snadb.cluster clu, " +
                            "snadb.corpus corp " +
                            "where " +
                            "sen01.ENTITYNORMID=entnorm.ENTITYNORMID and " +
                            "sen01.SENTENCEID=sent.SENTENCEID and " +
                            "entnorm.CLUSTERID=clu.CLUSTERID and " +
                            "corp.CORPID=clu.CORPID and " +
                            "corp.CORPID=? and " +
                            "clu.MINPTS=? and " +
                            "clu.EPS=?) as senentnorm02 " +
                            "where " +
                            "senentnorm01.ENTITYNORMID<>senentnorm02.ENTITYNORMID and " +
                            "senentnorm01.SENTENCEID=senentnorm02.SENTENCEID";                     
                                        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);        
        query.setParameter(4, corpusId);
        query.setParameter(5, minPts);
        query.setParameter(6, epsilon);
        
        rawSocialNetwork= query.getResultList();
        
        List<CEdge> redundantSocialNetwork=new ArrayList<CEdge>();
        
        for (Object[] result : rawSocialNetwork)
        {
            /*
               "senentnorm01.ENTITYNORMID, " +                  0
                            "senentnorm01.ENTITYNORMNAME, " +   1
                            "senentnorm01.SENTENCEID, " +       2
                            "senentnorm01.SENTENCECONTENT, "+   3
                            "senentnorm02.ENTITYNORMID, " +     4
                            "senentnorm02.ENTITYNORMNAME, " +   5
                            "senentnorm02.SENTENCEID, " +       6
                            "senentnorm02.SENTENCECONTENT, " +  7
                            "senentnorm02.SENTENCECONTENT "+    8    
            */
            String source=String.valueOf((Integer)result[0]);            
            String sourceDescription=(String)result[1];
            String sentenceId=String.valueOf((Integer)result[2]);
            String sentence=(String)result[3];
            String target=String.valueOf((Integer)result[4]);
            String targetDescription=(String)result[5];
            //String sentenceIdSource=String.valueOf((Integer)result[2]);
            //String sentence=(String)result[6];
            CEdge edge= new CEdge(source, target, sourceDescription, targetDescription, sentenceId, sentence,"","");
            
            redundantSocialNetwork.add(edge);                                                 
        }

        int numberOfEdges=redundantSocialNetwork.size();
        
        /*
        Primero haciendo uso  del flag visitado voy a marcar todas las aristas repetidas que el query 
        trae 
                
        */
       for (int i=0;i<numberOfEdges-1;i++)
        {
            if(!redundantSocialNetwork.get(i).isVisited())
            {
                String codeDirectSource=redundantSocialNetwork.get(i).getCodeDirect();
                String sentenceIdCurrent= redundantSocialNetwork.get(i).getSentenceId();
                
                //String sentences=temporalSocialNetwork.get(i).getSentence();//Para colectar la lista de sentencias de las aristas iguales
                for(int j=i+1;j<numberOfEdges;j++)
                {
                    //String sentenceSource=temporalSocialNetwork.get(i).getSentence();                    
                    if(!redundantSocialNetwork.get(j).isVisited())
                    {                    
                        String codeDirectTarget=redundantSocialNetwork.get(j).getCodeDirect();
                        String codeReverseTarget=redundantSocialNetwork.get(j).getCodeReverse();

                        if(codeDirectSource.compareTo(codeDirectTarget)==0||codeDirectSource.compareTo(codeReverseTarget)==0)
                        {
                            String sentencenIdNext=redundantSocialNetwork.get(j).getSentenceId();
                            
                            if(sentenceIdCurrent.compareTo(sentencenIdNext)==0)//Las sentencias tienen que ser diferentes para evitar redundancia
                            {
                                //int newWeight=temporalSocialNetwork.get(i).getWeight()+1;
                                //temporalSocialNetwork.get(i).setWeight(newWeight);
                                //temporalSocialNetwork.get(j).setVisited(true);
                                redundantSocialNetwork.get(j).setVisited(true);

                                //sentences=sentences+"\\n"+temporalSocialNetwork.get(j).getSentence();                                
                            }

                        }
                    }
                    //temporalSocialNetwork.get(i).setVisited(true);                                    
                }
               //temporalSocialNetwork.get(i).setSentence(sentences);
            } //
        }
                
        List<CEdge> temporalSocialNetwork=new ArrayList<CEdge>();
        
        for (int i=0;i<numberOfEdges;i++)
        {
            CEdge edge=redundantSocialNetwork.get(i);
            if(!edge.isVisited())
            {
                temporalSocialNetwork.add(edge);
            }                
        }          
        
        int numberOfEdgesTemp=temporalSocialNetwork.size();
        
        for (int i=0;i<numberOfEdgesTemp-1;i++)
        {
            if(temporalSocialNetwork.get(i).isUnique())
            {
                String codeDirectSource=temporalSocialNetwork.get(i).getCodeDirect();
                String sentenceIdCurrent= temporalSocialNetwork.get(i).getSentenceId();
                
                String sentences=temporalSocialNetwork.get(i).getSentence();//Para colectar la lista de sentencias de las aristas iguales
                for(int j=i+1;j<numberOfEdgesTemp;j++)
                {
                    //String sentenceSource=temporalSocialNetwork.get(i).getSentence();                    
                    if(temporalSocialNetwork.get(j).isUnique())
                    {                    
                        String codeDirectTarget=temporalSocialNetwork.get(j).getCodeDirect();
                        String codeReverseTarget=temporalSocialNetwork.get(j).getCodeReverse();

                        if(codeDirectSource.compareTo(codeDirectTarget)==0||codeDirectSource.compareTo(codeReverseTarget)==0)
                        {
                            String sentencenIdNext=temporalSocialNetwork.get(j).getSentenceId();
                            
                            if(sentenceIdCurrent.compareTo(sentencenIdNext)!=0)//Las sentencias tienen que ser diferentes para evitar redundancia
                            {
                                int newWeight=temporalSocialNetwork.get(i).getWeight()+1;
                                temporalSocialNetwork.get(i).setWeight(newWeight);
                                //temporalSocialNetwork.get(j).setVisited(true);
                                temporalSocialNetwork.get(j).setUnique(false);

                                sentences=sentences+'\n'+temporalSocialNetwork.get(j).getSentence();                                
                            }

                        }
                    }
                    //temporalSocialNetwork.get(i).setVisited(true);                                    
                }
                temporalSocialNetwork.get(i).setSentence(sentences);
            } //
        }
        
        List<CEdge> finalSocialNetwork=new ArrayList<CEdge>();
        
        for (int i=0;i<numberOfEdgesTemp;i++)
        {
            CEdge edge=temporalSocialNetwork.get(i);
            if(edge.isUnique())
            {
                finalSocialNetwork.add(edge);
            }                
        }        
        
        return finalSocialNetwork;        
    }

  public List<CEdge>  getSocialNetworkCoOcurrenceBasedOnParagraphs(int corpusId, int minPts, double epsilon)
    {
        List<Object[]> rawSocialNetwork=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=    "select "+
                            "senentnorm01.ENTITYNORMID, "+
                            "senentnorm01.ENTITYNORMNAME, "+
                            "senentnorm01.PARAGRAPHID, "+
                            "senentnorm02.PARAGRAPHCONTENT, "+
                            "senentnorm02.ENTITYNORMID, "+
                            "senentnorm02.ENTITYNORMNAME "+
                            "from "+
                            "(select "+
                            "sen01.ENTITYNORMID, "+
                            "sen01.SENTENCEID, "+
                            "entnorm.ENTITYNORMNAME, "+
                            "sent.SENTENCECONTENT, "+
                            "para.PARAGRAPHID, "+
                            "para.PARAGRAPHCONTENT "+
                            "from "+
                            "snadb.sentenceentitynormalized sen01, "+
                            "snadb.entitynormalized entnorm, "+
                            "snadb.sentence sent, "+
                            "snadb.paragraph para, "+
                            "snadb.cluster clu, "+
                            "snadb.corpus corp "+
                            "where "+
                            "sen01.ENTITYNORMID=entnorm.ENTITYNORMID and "+
                            "sen01.SENTENCEID=sent.SENTENCEID and "+
                            "sent.PARAGRAPHID=para.PARAGRAPHID and "+
                            "entnorm.CLUSTERID=clu.CLUSTERID and "+
                            "corp.CORPID=clu.CORPID and "+
                            "corp.CORPID=? and "+
                            "clu.MINPTS=? and "+
                            "clu.EPS=?) as senentnorm01, "+
                            "(select "+
                            "sen01.ENTITYNORMID, "+
                            "sen01.SENTENCEID, "+
                            "entnorm.ENTITYNORMNAME, "+
                            "sent.SENTENCECONTENT, "+
                            "para.PARAGRAPHID, "+
                            "para.PARAGRAPHCONTENT "+
                            "from "+
                            "snadb.sentenceentitynormalized sen01, "+
                            "snadb.entitynormalized entnorm, "+
                            "snadb.sentence sent, "+
                            "snadb.paragraph para, "+
                            "snadb.cluster clu, "+
                            "snadb.corpus corp "+
                            "where "+
                            "sen01.ENTITYNORMID=entnorm.ENTITYNORMID and "+
                            "sen01.SENTENCEID=sent.SENTENCEID and "+
                            "sent.PARAGRAPHID=para.PARAGRAPHID and "+
                            "entnorm.CLUSTERID=clu.CLUSTERID and "+
                            "corp.CORPID=clu.CORPID and "+
                            "corp.CORPID=? and "+
                            "clu.MINPTS=? and "+
                            "clu.EPS=?) as senentnorm02 "+
                            "where "+
                            "senentnorm01.ENTITYNORMID<>senentnorm02.ENTITYNORMID and "+
                            "senentnorm01.PARAGRAPHID=senentnorm02.PARAGRAPHID ";                     
                                        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);        
        query.setParameter(4, corpusId);
        query.setParameter(5, minPts);
        query.setParameter(6, epsilon);
        
        rawSocialNetwork= query.getResultList();
        
        List<CEdge> redundantSocialNetwork=new ArrayList<CEdge>();
        
        for (Object[] result : rawSocialNetwork)
        {
            /*
               "senentnorm01.ENTITYNORMID, " +                  0
                            "senentnorm01.ENTITYNORMNAME, " +   1
                            "senentnorm01.SENTENCEID, " +       2
                            "senentnorm01.SENTENCECONTENT, "+   3
                            "senentnorm02.ENTITYNORMID, " +     4
                            "senentnorm02.ENTITYNORMNAME, " +   5
                            "senentnorm02.SENTENCEID, " +       6
                            "senentnorm02.SENTENCECONTENT, " +  7
                            "senentnorm02.SENTENCECONTENT "+    8    
            */
            String source=String.valueOf((Integer)result[0]);            
            String sourceDescription=(String)result[1];
            String paragraphId=String.valueOf((Integer)result[2]);
            String paragrapContent=(String)result[3];
            String target=String.valueOf((Integer)result[4]);
            String targetDescription=(String)result[5];
           
            CEdge edge= new CEdge(source, target, sourceDescription, targetDescription, "", "",paragraphId,paragrapContent);
            
            redundantSocialNetwork.add(edge);                                                 
        }

        int numberOfEdges=redundantSocialNetwork.size();
        
        /*
        Primero haciendo uso  del flag visitado voy a marcar todas las aristas repetidas que el query 
        trae 
                
        */
       for (int i=0;i<numberOfEdges-1;i++)
        {
            if(!redundantSocialNetwork.get(i).isVisited())
            {
                String codeDirectSource=redundantSocialNetwork.get(i).getCodeDirect();
                String paragraphIdCurrent= redundantSocialNetwork.get(i).getParagraphId();
                
                //String sentences=temporalSocialNetwork.get(i).getSentence();//Para colectar la lista de sentencias de las aristas iguales
                for(int j=i+1;j<numberOfEdges;j++)
                {
                    //String sentenceSource=temporalSocialNetwork.get(i).getSentence();                    
                    if(!redundantSocialNetwork.get(j).isVisited())
                    {                    
                        String codeDirectTarget=redundantSocialNetwork.get(j).getCodeDirect();
                        String codeReverseTarget=redundantSocialNetwork.get(j).getCodeReverse();

                        if(codeDirectSource.compareTo(codeDirectTarget)==0||codeDirectSource.compareTo(codeReverseTarget)==0)
                        {
                            String paragraphIdNext=redundantSocialNetwork.get(j).getParagraphId();
                            
                            if(paragraphIdCurrent.compareTo(paragraphIdNext)==0)//Los parrafos tienen que ser diferentes para evitar redundancia
                            {
                                //int newWeight=temporalSocialNetwork.get(i).getWeight()+1;
                                //temporalSocialNetwork.get(i).setWeight(newWeight);
                                //temporalSocialNetwork.get(j).setVisited(true);
                                redundantSocialNetwork.get(j).setVisited(true);

                                //sentences=sentences+"\\n"+temporalSocialNetwork.get(j).getSentence();                                
                            }

                        }
                    }
                    //temporalSocialNetwork.get(i).setVisited(true);                                    
                }
               //temporalSocialNetwork.get(i).setSentence(sentences);
            } //
        }
                
        List<CEdge> temporalSocialNetwork=new ArrayList<CEdge>();
        
        for (int i=0;i<numberOfEdges;i++)
        {
            CEdge edge=redundantSocialNetwork.get(i);
            if(!edge.isVisited())
            {
                temporalSocialNetwork.add(edge);
            }                
        }          
        
        int numberOfEdgesTemp=temporalSocialNetwork.size();
        
        for (int i=0;i<numberOfEdgesTemp-1;i++)
        {
            if(temporalSocialNetwork.get(i).isUnique())
            {
                String codeDirectSource=temporalSocialNetwork.get(i).getCodeDirect();
                String paragraphIdCurrent= temporalSocialNetwork.get(i).getParagraphId();
                
                String paragraph=temporalSocialNetwork.get(i).getParagraph();//Para colectar la lista de parrafos de las aristas iguales
                for(int j=i+1;j<numberOfEdgesTemp;j++)
                {
                    //String sentenceSource=temporalSocialNetwork.get(i).getSentence();                    
                    if(temporalSocialNetwork.get(j).isUnique())
                    {                    
                        String codeDirectTarget=temporalSocialNetwork.get(j).getCodeDirect();
                        String codeReverseTarget=temporalSocialNetwork.get(j).getCodeReverse();

                        if(codeDirectSource.compareTo(codeDirectTarget)==0||codeDirectSource.compareTo(codeReverseTarget)==0)
                        {
                            String paragraphIdNext=temporalSocialNetwork.get(j).getParagraphId();
                            
                            if(paragraphIdCurrent.compareTo(paragraphIdNext)!=0)//Los parrafos tienen que ser diferentes para evitar redundancia
                            {
                                int newWeight=temporalSocialNetwork.get(i).getWeight()+1;
                                temporalSocialNetwork.get(i).setWeight(newWeight);
                                //temporalSocialNetwork.get(j).setVisited(true);
                                temporalSocialNetwork.get(j).setUnique(false);

                                paragraph=paragraph+'\n'+temporalSocialNetwork.get(j).getParagraph();                                
                            }

                        }
                    }
                    //temporalSocialNetwork.get(i).setVisited(true);                                    
                }
                temporalSocialNetwork.get(i).setParagraph(paragraph);
            } //
        }
        
        List<CEdge> finalSocialNetwork=new ArrayList<CEdge>();
        
        for (int i=0;i<numberOfEdgesTemp;i++)
        {
            CEdge edge=temporalSocialNetwork.get(i);
            if(edge.isUnique())
            {
                finalSocialNetwork.add(edge);
            }                
        }        
        
        return finalSocialNetwork;        
    }
 
    public void saveSocialNetworkCoOccurrenceBasedOnSentences(EntityManager em, int sourceId, String sourceDescription, int targetId, 
                                 String targetDescription, int corpusId, int weight, String sentences)
    {
        Query query= em.createNamedQuery("DAOEntityNormalized.findByEntitynormid");                
        query.setParameter("entitynormid", sourceId);
        DAOEntityNormalized entity01= (DAOEntityNormalized)query.getSingleResult();
        
        query= em.createNamedQuery("DAOEntityNormalized.findByEntitynormid");                
        query.setParameter("entitynormid", targetId);
        DAOEntityNormalized entity02= (DAOEntityNormalized)query.getSingleResult();
        
        query= em.createNamedQuery("DAOCorpus.findByCorpid");                
        query.setParameter("corpid", corpusId);
        DAOCorpus corpus= (DAOCorpus)query.getSingleResult();
        
        DAOEdgeList socialNetwork=new DAOEdgeList();
        DAOEdgeListPK socialNetworkPK = new DAOEdgeListPK();

        socialNetworkPK.setEntitynormid01(sourceId);
        socialNetworkPK.setEntitynormid02(targetId);
        
        socialNetwork.setDAOEdgeListPK(socialNetworkPK);
        
        socialNetwork.setDAOEntityNormalized(entity01);
        socialNetwork.setDAOEntityNormalized1(entity02);
        
        socialNetwork.setCorpusCorpid(corpus);
        
        socialNetwork.setEntitynormname01(sourceDescription);
        socialNetwork.setEntitynormname02(targetDescription);
        
        socialNetwork.setWeight(weight);
        socialNetwork.setSentences(sentences);
                
        //em.getTransaction().begin();
        try
        {   
            em.persist(socialNetwork);
            //em.getTransaction().commit();
        }
        catch (Exception e)
        {
            //em.getTransaction().rollback();
            throw e;
        }
        
       // return dCluster.getClusterid();        
    }  
    
public void saveSocialNetworkCoOccurrenceBasedOnParagraph(EntityManager em, int sourceId, String sourceDescription, int targetId, 
                                 String targetDescription, int corpusId, int weight, String paragraph)
    {
        Query query= em.createNamedQuery("DAOEntityNormalized.findByEntitynormid");                
        query.setParameter("entitynormid", sourceId);
        DAOEntityNormalized entity01= (DAOEntityNormalized)query.getSingleResult();
        
        query= em.createNamedQuery("DAOEntityNormalized.findByEntitynormid");                
        query.setParameter("entitynormid", targetId);
        DAOEntityNormalized entity02= (DAOEntityNormalized)query.getSingleResult();
        
        query= em.createNamedQuery("DAOCorpus.findByCorpid");                
        query.setParameter("corpid", corpusId);
        DAOCorpus corpus= (DAOCorpus)query.getSingleResult();
        
        DAOEdgeList socialNetwork=new DAOEdgeList();
        DAOEdgeListPK socialNetworkPK = new DAOEdgeListPK();

        socialNetworkPK.setEntitynormid01(sourceId);
        socialNetworkPK.setEntitynormid02(targetId);
        
        socialNetwork.setDAOEdgeListPK(socialNetworkPK);
        
        socialNetwork.setDAOEntityNormalized(entity01);
        socialNetwork.setDAOEntityNormalized1(entity02);
        
        socialNetwork.setCorpusCorpid(corpus);
        
        socialNetwork.setEntitynormname01(sourceDescription);
        socialNetwork.setEntitynormname02(targetDescription);
        
        socialNetwork.setWeight(weight);
        /*The table EDGELIST on the DB has a field called sentences
        that in this case is used to save the paragraph*/
        socialNetwork.setSentences(paragraph);
                
        //em.getTransaction().begin();
        try
        {   
            em.persist(socialNetwork);
            //em.getTransaction().commit();
        }
        catch (Exception e)
        {
            //em.getTransaction().rollback();
            throw e;
        }
        
       // return dCluster.getClusterid();        
    }    
    
public List<Object[]>  getSocialNetworkFomDatabase(int corpusId, int minPts, double epsilon)
    {
        List<Object[]> socialNetworkFromDatabase=new ArrayList<Object[]>();
        
        
        EntityManager em = Persistence.createEntityManagerFactory("SNAFromSpanishTextPU").createEntityManager();        
        
        String queryText=   "select  " +
                            "distinct " +
                            "el.ENTITYNORMID01, " +
                            "el.ENTITYNORMNAME01, " +
                            "el.ENTITYNORMID02, " +
                            "el.ENTITYNORMNAME02, " +
                            "el.SENTENCES, " +
                            "el.WEIGHT " +
                            "from  " +
                            "snadb.edgelist el, " +
                            "snadb.entitynormalized ent, " +
                            "snadb.corpus corp, " +
                            "snadb.cluster clu " +
                            "where " +
                            "corp.CORPID=? and " +
                            "clu.MINPTS=? and " +
                            "clu.EPS=? and  " +
                            "el.CORPUS_CORPID=corp.CORPID and " +
                            "clu.CORPID=corp.CORPID and " +
                            "ent.CLUSTERID=clu.CLUSTERID and " +
                            "(ent.ENTITYNORMID=el.ENTITYNORMID01 or " +
                            "ent.ENTITYNORMID=el.ENTITYNORMID02)";                     
                                        
        Query query = em.createNativeQuery(queryText);
        query.setParameter(1, corpusId);
        query.setParameter(2, minPts);
        query.setParameter(3, epsilon);        
  
        socialNetworkFromDatabase= query.getResultList();
        
        return socialNetworkFromDatabase;        
    }    
 
}
