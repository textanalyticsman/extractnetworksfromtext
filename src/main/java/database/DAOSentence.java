/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author osboxes
 */
@Entity
@Table(name = "sentence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOSentence.findAll", query = "SELECT d FROM DAOSentence d"),
    @NamedQuery(name = "DAOSentence.findBySentenceid", query = "SELECT d FROM DAOSentence d WHERE d.sentenceid = :sentenceid"),
    @NamedQuery(name = "DAOSentence.findBySentenceorder", query = "SELECT d FROM DAOSentence d WHERE d.sentenceorder = :sentenceorder")})
public class DAOSentence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SENTENCEID")
    private Integer sentenceid;
    @Column(name = "SENTENCEORDER")
    private Integer sentenceorder;
    @Lob
    @Column(name = "SENTENCECONTENT")
    private String sentencecontent;
    @JoinColumn(name = "PARAGRAPHID", referencedColumnName = "PARAGRAPHID")
    @ManyToOne(optional = false)
    private DAOParagraph paragraphid;
    @OneToMany(mappedBy = "sentenceid")
    private Set<DAOEntityRaw> dAOEntityRawSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOSentence")
    private Set<DAOSentenceentitynormalized> dAOSentenceentitynormalizedSet;

    public DAOSentence() {
    }

    public DAOSentence(Integer sentenceid) {
        this.sentenceid = sentenceid;
    }

    public Integer getSentenceid() {
        return sentenceid;
    }

    public void setSentenceid(Integer sentenceid) {
        this.sentenceid = sentenceid;
    }

    public Integer getSentenceorder() {
        return sentenceorder;
    }

    public void setSentenceorder(Integer sentenceorder) {
        this.sentenceorder = sentenceorder;
    }

    public String getSentencecontent() {
        return sentencecontent;
    }

    public void setSentencecontent(String sentencecontent) {
        this.sentencecontent = sentencecontent;
    }

    public DAOParagraph getParagraphid() {
        return paragraphid;
    }

    public void setParagraphid(DAOParagraph paragraphid) {
        this.paragraphid = paragraphid;
    }

    @XmlTransient
    public Set<DAOEntityRaw> getDAOEntityRawSet() {
        return dAOEntityRawSet;
    }

    public void setDAOEntityRawSet(Set<DAOEntityRaw> dAOEntityRawSet) {
        this.dAOEntityRawSet = dAOEntityRawSet;
    }

    @XmlTransient
    public Set<DAOSentenceentitynormalized> getDAOSentenceentitynormalizedSet() {
        return dAOSentenceentitynormalizedSet;
    }

    public void setDAOSentenceentitynormalizedSet(Set<DAOSentenceentitynormalized> dAOSentenceentitynormalizedSet) {
        this.dAOSentenceentitynormalizedSet = dAOSentenceentitynormalizedSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sentenceid != null ? sentenceid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOSentence)) {
            return false;
        }
        DAOSentence other = (DAOSentence) object;
        if ((this.sentenceid == null && other.sentenceid != null) || (this.sentenceid != null && !this.sentenceid.equals(other.sentenceid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOSentence[ sentenceid=" + sentenceid + " ]";
    }
    
}
