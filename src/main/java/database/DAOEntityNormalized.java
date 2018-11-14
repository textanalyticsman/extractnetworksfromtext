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
@Table(name = "entitynormalized")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOEntityNormalized.findAll", query = "SELECT d FROM DAOEntityNormalized d"),
    @NamedQuery(name = "DAOEntityNormalized.findByEntitynormid", query = "SELECT d FROM DAOEntityNormalized d WHERE d.entitynormid = :entitynormid"),
    @NamedQuery(name = "DAOEntityNormalized.findByEntitynormname", query = "SELECT d FROM DAOEntityNormalized d WHERE d.entitynormname = :entitynormname"),
    @NamedQuery(name = "DAOEntityNormalized.findByEntitynormtype", query = "SELECT d FROM DAOEntityNormalized d WHERE d.entitynormtype = :entitynormtype"),
    @NamedQuery(name = "DAOEntityNormalized.findByEntitynormrole", query = "SELECT d FROM DAOEntityNormalized d WHERE d.entitynormrole = :entitynormrole")})
public class DAOEntityNormalized implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ENTITYNORMID")
    private Integer entitynormid;
    @Column(name = "ENTITYNORMNAME")
    private String entitynormname;
    @Column(name = "ENTITYNORMTYPE")
    private String entitynormtype;
    @Column(name = "ENTITYNORMROLE")
    private String entitynormrole;
    @JoinColumn(name = "CLUSTERID", referencedColumnName = "CLUSTERID")
    @ManyToOne(optional = false)
    private DAOCluster clusterid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOEntityNormalized")
    private Set<DAOEdgeList> dAOEdgeListSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOEntityNormalized1")
    private Set<DAOEdgeList> dAOEdgeListSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOEntityNormalized")
    private Set<DAOSentenceentitynormalized> dAOSentenceentitynormalizedSet;

    public DAOEntityNormalized() {
    }

    public DAOEntityNormalized(Integer entitynormid) {
        this.entitynormid = entitynormid;
    }

    public Integer getEntitynormid() {
        return entitynormid;
    }

    public void setEntitynormid(Integer entitynormid) {
        this.entitynormid = entitynormid;
    }

    public String getEntitynormname() {
        return entitynormname;
    }

    public void setEntitynormname(String entitynormname) {
        this.entitynormname = entitynormname;
    }

    public String getEntitynormtype() {
        return entitynormtype;
    }

    public void setEntitynormtype(String entitynormtype) {
        this.entitynormtype = entitynormtype;
    }

    public String getEntitynormrole() {
        return entitynormrole;
    }

    public void setEntitynormrole(String entitynormrole) {
        this.entitynormrole = entitynormrole;
    }

    public DAOCluster getClusterid() {
        return clusterid;
    }

    public void setClusterid(DAOCluster clusterid) {
        this.clusterid = clusterid;
    }

    @XmlTransient
    public Set<DAOEdgeList> getDAOEdgeListSet() {
        return dAOEdgeListSet;
    }

    public void setDAOEdgeListSet(Set<DAOEdgeList> dAOEdgeListSet) {
        this.dAOEdgeListSet = dAOEdgeListSet;
    }

    @XmlTransient
    public Set<DAOEdgeList> getDAOEdgeListSet1() {
        return dAOEdgeListSet1;
    }

    public void setDAOEdgeListSet1(Set<DAOEdgeList> dAOEdgeListSet1) {
        this.dAOEdgeListSet1 = dAOEdgeListSet1;
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
        hash += (entitynormid != null ? entitynormid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOEntityNormalized)) {
            return false;
        }
        DAOEntityNormalized other = (DAOEntityNormalized) object;
        if ((this.entitynormid == null && other.entitynormid != null) || (this.entitynormid != null && !this.entitynormid.equals(other.entitynormid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOEntityNormalized[ entitynormid=" + entitynormid + " ]";
    }
    
}
