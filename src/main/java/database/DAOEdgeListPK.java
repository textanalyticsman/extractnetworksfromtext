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
public class DAOEdgeListPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ENTITYNORMID01")
    private int entitynormid01;
    @Basic(optional = false)
    @Column(name = "ENTITYNORMID02")
    private int entitynormid02;

    public DAOEdgeListPK() {
    }

    public DAOEdgeListPK(int entitynormid01, int entitynormid02) {
        this.entitynormid01 = entitynormid01;
        this.entitynormid02 = entitynormid02;
    }

    public int getEntitynormid01() {
        return entitynormid01;
    }

    public void setEntitynormid01(int entitynormid01) {
        this.entitynormid01 = entitynormid01;
    }

    public int getEntitynormid02() {
        return entitynormid02;
    }

    public void setEntitynormid02(int entitynormid02) {
        this.entitynormid02 = entitynormid02;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) entitynormid01;
        hash += (int) entitynormid02;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOEdgeListPK)) {
            return false;
        }
        DAOEdgeListPK other = (DAOEdgeListPK) object;
        if (this.entitynormid01 != other.entitynormid01) {
            return false;
        }
        if (this.entitynormid02 != other.entitynormid02) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOEdgeListPK[ entitynormid01=" + entitynormid01 + ", entitynormid02=" + entitynormid02 + " ]";
    }
    
}
