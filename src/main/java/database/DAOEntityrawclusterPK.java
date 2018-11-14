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
public class DAOEntityrawclusterPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CLUSTERID")
    private int clusterid;
    @Basic(optional = false)
    @Column(name = "ENTITYID")
    private int entityid;

    public DAOEntityrawclusterPK() {
    }

    public DAOEntityrawclusterPK(int clusterid, int entityid) {
        this.clusterid = clusterid;
        this.entityid = entityid;
    }

    public int getClusterid() {
        return clusterid;
    }

    public void setClusterid(int clusterid) {
        this.clusterid = clusterid;
    }

    public int getEntityid() {
        return entityid;
    }

    public void setEntityid(int entityid) {
        this.entityid = entityid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) clusterid;
        hash += (int) entityid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOEntityrawclusterPK)) {
            return false;
        }
        DAOEntityrawclusterPK other = (DAOEntityrawclusterPK) object;
        if (this.clusterid != other.clusterid) {
            return false;
        }
        if (this.entityid != other.entityid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOEntityrawclusterPK[ clusterid=" + clusterid + ", entityid=" + entityid + " ]";
    }
    
}
