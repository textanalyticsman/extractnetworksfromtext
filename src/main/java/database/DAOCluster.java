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
@Table(name = "cluster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOCluster.findAll", query = "SELECT d FROM DAOCluster d"),
    @NamedQuery(name = "DAOCluster.findByClusterid", query = "SELECT d FROM DAOCluster d WHERE d.clusterid = :clusterid"),
    @NamedQuery(name = "DAOCluster.findByClustersize", query = "SELECT d FROM DAOCluster d WHERE d.clustersize = :clustersize"),
    @NamedQuery(name = "DAOCluster.findByEps", query = "SELECT d FROM DAOCluster d WHERE d.eps = :eps"),
    @NamedQuery(name = "DAOCluster.findByMinpts", query = "SELECT d FROM DAOCluster d WHERE d.minpts = :minpts"),
    @NamedQuery(name = "DAOCluster.findByLabel", query = "SELECT d FROM DAOCluster d WHERE d.label = :label")})
public class DAOCluster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLUSTERID")
    private Integer clusterid;
    @Column(name = "CLUSTERSIZE")
    private Integer clustersize;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "EPS")
    private Double eps;
    @Column(name = "MINPTS")
    private Integer minpts;
    @Column(name = "LABEL")
    private Integer label;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clusterid")
    private Set<DAOEntityNormalized> dAOEntityNormalizedSet;
    @JoinColumn(name = "CORPID", referencedColumnName = "CORPID")
    @ManyToOne(optional = false)
    private DAOCorpus corpid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOCluster")
    private Set<DAOEntityrawcluster> dAOEntityrawclusterSet;

    public DAOCluster() {
    }

    public DAOCluster(Integer clusterid) {
        this.clusterid = clusterid;
    }

    public Integer getClusterid() {
        return clusterid;
    }

    public void setClusterid(Integer clusterid) {
        this.clusterid = clusterid;
    }

    public Integer getClustersize() {
        return clustersize;
    }

    public void setClustersize(Integer clustersize) {
        this.clustersize = clustersize;
    }

    public Double getEps() {
        return eps;
    }

    public void setEps(Double eps) {
        this.eps = eps;
    }

    public Integer getMinpts() {
        return minpts;
    }

    public void setMinpts(Integer minpts) {
        this.minpts = minpts;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    @XmlTransient
    public Set<DAOEntityNormalized> getDAOEntityNormalizedSet() {
        return dAOEntityNormalizedSet;
    }

    public void setDAOEntityNormalizedSet(Set<DAOEntityNormalized> dAOEntityNormalizedSet) {
        this.dAOEntityNormalizedSet = dAOEntityNormalizedSet;
    }

    public DAOCorpus getCorpid() {
        return corpid;
    }

    public void setCorpid(DAOCorpus corpid) {
        this.corpid = corpid;
    }

    @XmlTransient
    public Set<DAOEntityrawcluster> getDAOEntityrawclusterSet() {
        return dAOEntityrawclusterSet;
    }

    public void setDAOEntityrawclusterSet(Set<DAOEntityrawcluster> dAOEntityrawclusterSet) {
        this.dAOEntityrawclusterSet = dAOEntityrawclusterSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clusterid != null ? clusterid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOCluster)) {
            return false;
        }
        DAOCluster other = (DAOCluster) object;
        if ((this.clusterid == null && other.clusterid != null) || (this.clusterid != null && !this.clusterid.equals(other.clusterid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOCluster[ clusterid=" + clusterid + " ]";
    }
    
}
