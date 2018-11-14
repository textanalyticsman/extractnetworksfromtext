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
@Table(name = "jaccarddistance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOJaccardDistance.findAll", query = "SELECT d FROM DAOJaccardDistance d"),
    @NamedQuery(name = "DAOJaccardDistance.findByEntityid01", query = "SELECT d FROM DAOJaccardDistance d WHERE d.dAOJaccardDistancePK.entityid01 = :entityid01"),
    @NamedQuery(name = "DAOJaccardDistance.findByEntityid02", query = "SELECT d FROM DAOJaccardDistance d WHERE d.dAOJaccardDistancePK.entityid02 = :entityid02"),
    @NamedQuery(name = "DAOJaccardDistance.findByEntityname01", query = "SELECT d FROM DAOJaccardDistance d WHERE d.entityname01 = :entityname01"),
    @NamedQuery(name = "DAOJaccardDistance.findByEntityname02", query = "SELECT d FROM DAOJaccardDistance d WHERE d.entityname02 = :entityname02"),
    @NamedQuery(name = "DAOJaccardDistance.findByJaccarddistance", query = "SELECT d FROM DAOJaccardDistance d WHERE d.jaccarddistance = :jaccarddistance")})
public class DAOJaccardDistance implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DAOJaccardDistancePK dAOJaccardDistancePK;
    @Column(name = "ENTITYNAME01")
    private String entityname01;
    @Column(name = "ENTITYNAME02")
    private String entityname02;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "JACCARDDISTANCE")
    private Float jaccarddistance;
    @JoinColumn(name = "ENTITYID01", referencedColumnName = "ENTITYID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOEntityRaw dAOEntityRaw;
    @JoinColumn(name = "ENTITYID02", referencedColumnName = "ENTITYID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOEntityRaw dAOEntityRaw1;

    public DAOJaccardDistance() {
    }

    public DAOJaccardDistance(DAOJaccardDistancePK dAOJaccardDistancePK) {
        this.dAOJaccardDistancePK = dAOJaccardDistancePK;
    }

    public DAOJaccardDistance(int entityid01, int entityid02) {
        this.dAOJaccardDistancePK = new DAOJaccardDistancePK(entityid01, entityid02);
    }

    public DAOJaccardDistancePK getDAOJaccardDistancePK() {
        return dAOJaccardDistancePK;
    }

    public void setDAOJaccardDistancePK(DAOJaccardDistancePK dAOJaccardDistancePK) {
        this.dAOJaccardDistancePK = dAOJaccardDistancePK;
    }

    public String getEntityname01() {
        return entityname01;
    }

    public void setEntityname01(String entityname01) {
        this.entityname01 = entityname01;
    }

    public String getEntityname02() {
        return entityname02;
    }

    public void setEntityname02(String entityname02) {
        this.entityname02 = entityname02;
    }

    public Float getJaccarddistance() {
        return jaccarddistance;
    }

    public void setJaccarddistance(Float jaccarddistance) {
        this.jaccarddistance = jaccarddistance;
    }

    public DAOEntityRaw getDAOEntityRaw() {
        return dAOEntityRaw;
    }

    public void setDAOEntityRaw(DAOEntityRaw dAOEntityRaw) {
        this.dAOEntityRaw = dAOEntityRaw;
    }

    public DAOEntityRaw getDAOEntityRaw1() {
        return dAOEntityRaw1;
    }

    public void setDAOEntityRaw1(DAOEntityRaw dAOEntityRaw1) {
        this.dAOEntityRaw1 = dAOEntityRaw1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dAOJaccardDistancePK != null ? dAOJaccardDistancePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOJaccardDistance)) {
            return false;
        }
        DAOJaccardDistance other = (DAOJaccardDistance) object;
        if ((this.dAOJaccardDistancePK == null && other.dAOJaccardDistancePK != null) || (this.dAOJaccardDistancePK != null && !this.dAOJaccardDistancePK.equals(other.dAOJaccardDistancePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOJaccardDistance[ dAOJaccardDistancePK=" + dAOJaccardDistancePK + " ]";
    }
    
}
