/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author osboxes
 */
public class CEntityRawTypeModificationsControl {
    
    /*
    This class is used to hel us to control the modification of entity types
    on raw entities through the GUI called EntitiesRevisionAndFixing
    */
    
    private String oldType;
    private String newType;
    private String entityName;
    private int sentenceId;
    private String sentence;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public int getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
    
    public CEntityRawTypeModificationsControl(String entityName,String oldType, String newType, int sentenceId, String sentence)
    {
        this.entityName=entityName;
        this.oldType=oldType;
        this.newType=newType;
        this.sentenceId=sentenceId;
        this.sentence=sentence;
    }

    public String getOldType() {
        return oldType;
    }
    
    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }
    
}