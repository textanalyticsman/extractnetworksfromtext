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
@Table(name = "corpus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOCorpus.findAll", query = "SELECT d FROM DAOCorpus d"),
    @NamedQuery(name = "DAOCorpus.findByCorpid", query = "SELECT d FROM DAOCorpus d WHERE d.corpid = :corpid"),
    @NamedQuery(name = "DAOCorpus.findByCorpdesc", query = "SELECT d FROM DAOCorpus d WHERE d.corpdesc = :corpdesc")})
public class DAOCorpus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CORPID")
    private Integer corpid;
    @Column(name = "CORPDESC")
    private String corpdesc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corpid")
    private Set<DAOCluster> dAOClusterSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corpusCorpid")
    private Set<DAOEdgeList> dAOEdgeListSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corpid")
    private Set<DAODocument> dAODocumentSet;

    public DAOCorpus() {
    }

    public DAOCorpus(Integer corpid) {
        this.corpid = corpid;
    }

    public Integer getCorpid() {
        return corpid;
    }

    public void setCorpid(Integer corpid) {
        this.corpid = corpid;
    }

    public String getCorpdesc() {
        return corpdesc;
    }

    public void setCorpdesc(String corpdesc) {
        this.corpdesc = corpdesc;
    }

    @XmlTransient
    public Set<DAOCluster> getDAOClusterSet() {
        return dAOClusterSet;
    }

    public void setDAOClusterSet(Set<DAOCluster> dAOClusterSet) {
        this.dAOClusterSet = dAOClusterSet;
    }

    @XmlTransient
    public Set<DAOEdgeList> getDAOEdgeListSet() {
        return dAOEdgeListSet;
    }

    public void setDAOEdgeListSet(Set<DAOEdgeList> dAOEdgeListSet) {
        this.dAOEdgeListSet = dAOEdgeListSet;
    }

    @XmlTransient
    public Set<DAODocument> getDAODocumentSet() {
        return dAODocumentSet;
    }

    public void setDAODocumentSet(Set<DAODocument> dAODocumentSet) {
        this.dAODocumentSet = dAODocumentSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corpid != null ? corpid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOCorpus)) {
            return false;
        }
        DAOCorpus other = (DAOCorpus) object;
        if ((this.corpid == null && other.corpid != null) || (this.corpid != null && !this.corpid.equals(other.corpid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOCorpus[ corpid=" + corpid + " ]";
    }
    
}
