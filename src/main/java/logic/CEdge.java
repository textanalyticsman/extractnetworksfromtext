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
public class CEdge {
    
    private String source;//It represents the ID of a normalized entity
    private String target;//It represents the ID of a normalized entity
    private String sourceDescription;//It represents a normalied entity. For example: Juan Perez
    private String targetDescription;//It represents a normalied entity. For example: Rosa Perez
    private String sentence;//It represents the sentence where entities co-ocurr
    private String codeDirect;//As the network is symmetric A-B is the same as B-A. Let us say we have A-B
    private String codeReverse;//The reverse code for A-B is B-A
    private boolean visited;//Used during the reduction of redundant edges
    private int weight;//To save the weight of an edge
    private boolean unique; //TO whether the edge is inique or not
    private String sentenceId;
    private String paragraphId; //The paragraph id to manage co-ocurrence based on paragraphs
    private String paragraph; //The paragraph to manage co-ocurrence based on paragraphs
    
  
    
    public CEdge(String source, String target, String sourceDescription, String targetDescription, 
                  String sentenceId, String sentence, String paragraphId, String paragraph)
    {
        this.source=source;
        this.target=target;
        this.sourceDescription=sourceDescription;
        this.targetDescription=targetDescription;
        this.codeDirect=this.source+this.target;
        this.codeReverse=this.target+this.source;
        this.visited=false;
        this.unique=true;
        this.weight=1;
        this.sentenceId=sentenceId;
        this.sentence=sentence;
        this.paragraphId=paragraphId;
        this.paragraph=paragraph;
    }       

    public void setParagraphId(String paragraphId) {
        this.paragraphId = paragraphId;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getSourceDescription() {
        return sourceDescription;
    }

    public void setSourceDescription(String sourceDescription) {
        this.sourceDescription = sourceDescription;
    }

    public String getTargetDescription() {
        return targetDescription;
    }

    public void setTargetDescription(String targetDescription) {
        this.targetDescription = targetDescription;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    } 

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getCodeDirect() {
        return codeDirect;
    }

    public void setCodeDirect(String codeDirect) {
        this.codeDirect = codeDirect;
    }

    public String getSentenceId() {
        return sentenceId;
    }

    public String getParagraphId() {
        return paragraphId;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setSentenceId(String sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getCodeReverse() {
        return codeReverse;
    }

    public void setCodeReverse(String codeReverse) {
        this.codeReverse = codeReverse;
    }  


    
}
