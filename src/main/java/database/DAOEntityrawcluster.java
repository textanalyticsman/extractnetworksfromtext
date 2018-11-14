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
@Table(name = "entityrawcluster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOEntityrawcluster.findAll", query = "SELECT d FROM DAOEntityrawcluster d"),
    @NamedQuery(name = "DAOEntityrawcluster.findByClusterid", query = "SELECT d FROM DAOEntityrawcluster d WHERE d.dAOEntityrawclusterPK.clusterid = :clusterid"),
    @NamedQuery(name = "DAOEntityrawcluster.findByEntityid", query = "SELECT d FROM DAOEntityrawcluster d WHERE d.dAOEntityrawclusterPK.entityid = :entityid"),
    @NamedQuery(name = "DAOEntityrawcluster.findBySpare", query = "SELECT d FROM DAOEntityrawcluster d WHERE d.spare = :spare")})
public class DAOEntityrawcluster implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DAOEntityrawclusterPK dAOEntityrawclusterPK;
    @Column(name = "SPARE")
    private String spare;
    @JoinColumn(name = "CLUSTERID", referencedColumnName = "CLUSTERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOCluster dAOCluster;
    @JoinColumn(name = "ENTITYID", referencedColumnName = "ENTITYID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOEntityRaw dAOEntityRaw;

    public DAOEntityrawcluster() {
    }

    public DAOEntityrawcluster(DAOEntityrawclusterPK dAOEntityrawclusterPK) {
        this.dAOEntityrawclusterPK = dAOEntityrawclusterPK;
    }

    public DAOEntityrawcluster(int clusterid, int entityid) {
        this.dAOEntityrawclusterPK = new DAOEntityrawclusterPK(clusterid, entityid);
    }

    public DAOEntityrawclusterPK getDAOEntityrawclusterPK() {
        return dAOEntityrawclusterPK;
    }

    public void setDAOEntityrawclusterPK(DAOEntityrawclusterPK dAOEntityrawclusterPK) {
        this.dAOEntityrawclusterPK = dAOEntityrawclusterPK;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public DAOCluster getDAOCluster() {
        return dAOCluster;
    }

    public void setDAOCluster(DAOCluster dAOCluster) {
        this.dAOCluster = dAOCluster;
    }

    public DAOEntityRaw getDAOEntityRaw() {
        return dAOEntityRaw;
    }

    public void setDAOEntityRaw(DAOEntityRaw dAOEntityRaw) {
        this.dAOEntityRaw = dAOEntityRaw;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dAOEntityrawclusterPK != null ? dAOEntityrawclusterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOEntityrawcluster)) {
            return false;
        }
        DAOEntityrawcluster other = (DAOEntityrawcluster) object;
        if ((this.dAOEntityrawclusterPK == null && other.dAOEntityrawclusterPK != null) || (this.dAOEntityrawclusterPK != null && !this.dAOEntityrawclusterPK.equals(other.dAOEntityrawclusterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOEntityrawcluster[ dAOEntityrawclusterPK=" + dAOEntityrawclusterPK + " ]";
    }
    
}
