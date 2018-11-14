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
public class DAODocumentPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "DOCID")
    private int docid;
    @Basic(optional = false)
    @Column(name = "CORPID")
    private int corpid;

    public DAODocumentPK() {
    }

    public DAODocumentPK(int docid, int corpid) {
        this.docid = docid;
        this.corpid = corpid;
    }

    public int getDocid() {
        return docid;
    }

    public void setDocid(int docid) {
        this.docid = docid;
    }

    public int getCorpid() {
        return corpid;
    }

    public void setCorpid(int corpid) {
        this.corpid = corpid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) docid;
        hash += (int) corpid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAODocumentPK)) {
            return false;
        }
        DAODocumentPK other = (DAODocumentPK) object;
        if (this.docid != other.docid) {
            return false;
        }
        if (this.corpid != other.corpid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAODocumentPK[ docid=" + docid + ", corpid=" + corpid + " ]";
    }
    
}
