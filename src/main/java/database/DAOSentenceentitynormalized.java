/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osboxes
 */
@Entity
@Table(name = "sentenceentitynormalized")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOSentenceentitynormalized.findAll", query = "SELECT d FROM DAOSentenceentitynormalized d"),
    @NamedQuery(name = "DAOSentenceentitynormalized.findBySpare", query = "SELECT d FROM DAOSentenceentitynormalized d WHERE d.spare = :spare"),
    @NamedQuery(name = "DAOSentenceentitynormalized.findByEntitynormid", query = "SELECT d FROM DAOSentenceentitynormalized d WHERE d.dAOSentenceentitynormalizedPK.entitynormid = :entitynormid"),
    @NamedQuery(name = "DAOSentenceentitynormalized.findBySentenceid", query = "SELECT d FROM DAOSentenceentitynormalized d WHERE d.dAOSentenceentitynormalizedPK.sentenceid = :sentenceid")})
public class DAOSentenceentitynormalized implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DAOSentenceentitynormalizedPK dAOSentenceentitynormalizedPK;
    @Column(name = "SPARE")
    private String spare;
    @JoinColumn(name = "ENTITYNORMID", referencedColumnName = "ENTITYNORMID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOEntityNormalized dAOEntityNormalized;
    @JoinColumn(name = "SENTENCEID", referencedColumnName = "SENTENCEID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOSentence dAOSentence;

    public DAOSentenceentitynormalized() {
    }

    public DAOSentenceentitynormalized(DAOSentenceentitynormalizedPK dAOSentenceentitynormalizedPK) {
        this.dAOSentenceentitynormalizedPK = dAOSentenceentitynormalizedPK;
    }

    public DAOSentenceentitynormalized(int entitynormid, int sentenceid) {
        this.dAOSentenceentitynormalizedPK = new DAOSentenceentitynormalizedPK(entitynormid, sentenceid);
    }

    public DAOSentenceentitynormalizedPK getDAOSentenceentitynormalizedPK() {
        return dAOSentenceentitynormalizedPK;
    }

    public void setDAOSentenceentitynormalizedPK(DAOSentenceentitynormalizedPK dAOSentenceentitynormalizedPK) {
        this.dAOSentenceentitynormalizedPK = dAOSentenceentitynormalizedPK;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public DAOEntityNormalized getDAOEntityNormalized() {
        return dAOEntityNormalized;
    }

    public void setDAOEntityNormalized(DAOEntityNormalized dAOEntityNormalized) {
        this.dAOEntityNormalized = dAOEntityNormalized;
    }

    public DAOSentence getDAOSentence() {
        return dAOSentence;
    }

    public void setDAOSentence(DAOSentence dAOSentence) {
        this.dAOSentence = dAOSentence;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dAOSentenceentitynormalizedPK != null ? dAOSentenceentitynormalizedPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOSentenceentitynormalized)) {
            return false;
        }
        DAOSentenceentitynormalized other = (DAOSentenceentitynormalized) object;
        if ((this.dAOSentenceentitynormalizedPK == null && other.dAOSentenceentitynormalizedPK != null) || (this.dAOSentenceentitynormalizedPK != null && !this.dAOSentenceentitynormalizedPK.equals(other.dAOSentenceentitynormalizedPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOSentenceentitynormalized[ dAOSentenceentitynormalizedPK=" + dAOSentenceentitynormalizedPK + " ]";
    }
    
}
