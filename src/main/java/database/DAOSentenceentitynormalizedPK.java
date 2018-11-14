/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author osboxes
 */
@Embeddable
public class DAOSentenceentitynormalizedPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ENTITYNORMID")
    private int entitynormid;
    @Basic(optional = false)
    @Column(name = "SENTENCEID")
    private int sentenceid;

    public DAOSentenceentitynormalizedPK() {
    }

    public DAOSentenceentitynormalizedPK(int entitynormid, int sentenceid) {
        this.entitynormid = entitynormid;
        this.sentenceid = sentenceid;
    }

    public int getEntitynormid() {
        return entitynormid;
    }

    public void setEntitynormid(int entitynormid) {
        this.entitynormid = entitynormid;
    }

    public int getSentenceid() {
        return sentenceid;
    }

    public void setSentenceid(int sentenceid) {
        this.sentenceid = sentenceid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) entitynormid;
        hash += (int) sentenceid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOSentenceentitynormalizedPK)) {
            return false;
        }
        DAOSentenceentitynormalizedPK other = (DAOSentenceentitynormalizedPK) object;
        if (this.entitynormid != other.entitynormid) {
            return false;
        }
        if (this.sentenceid != other.sentenceid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOSentenceentitynormalizedPK[ entitynormid=" + entitynormid + ", sentenceid=" + sentenceid + " ]";
    }
    
}
