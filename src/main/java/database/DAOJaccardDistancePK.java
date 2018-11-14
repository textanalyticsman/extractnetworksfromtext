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
public class DAOJaccardDistancePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ENTITYID01")
    private int entityid01;
    @Basic(optional = false)
    @Column(name = "ENTITYID02")
    private int entityid02;

    public DAOJaccardDistancePK() {
    }

    public DAOJaccardDistancePK(int entityid01, int entityid02) {
        this.entityid01 = entityid01;
        this.entityid02 = entityid02;
    }

    public int getEntityid01() {
        return entityid01;
    }

    public void setEntityid01(int entityid01) {
        this.entityid01 = entityid01;
    }

    public int getEntityid02() {
        return entityid02;
    }

    public void setEntityid02(int entityid02) {
        this.entityid02 = entityid02;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) entityid01;
        hash += (int) entityid02;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOJaccardDistancePK)) {
            return false;
        }
        DAOJaccardDistancePK other = (DAOJaccardDistancePK) object;
        if (this.entityid01 != other.entityid01) {
            return false;
        }
        if (this.entityid02 != other.entityid02) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOJaccardDistancePK[ entityid01=" + entityid01 + ", entityid02=" + entityid02 + " ]";
    }
    
}
