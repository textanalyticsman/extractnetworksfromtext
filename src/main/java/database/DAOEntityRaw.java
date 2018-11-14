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
@Table(name = "entityraw")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOEntityRaw.findAll", query = "SELECT d FROM DAOEntityRaw d"),
    @NamedQuery(name = "DAOEntityRaw.findByEntityid", query = "SELECT d FROM DAOEntityRaw d WHERE d.entityid = :entityid"),
    @NamedQuery(name = "DAOEntityRaw.findByEntityname", query = "SELECT d FROM DAOEntityRaw d WHERE d.entityname = :entityname"),
    @NamedQuery(name = "DAOEntityRaw.findByEntitytype", query = "SELECT d FROM DAOEntityRaw d WHERE d.entitytype = :entitytype")})
public class DAOEntityRaw implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ENTITYID")
    private Integer entityid;
    @Column(name = "ENTITYNAME")
    private String entityname;
    @Column(name = "ENTITYTYPE")
    private String entitytype;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOEntityRaw")
    private Set<DAOEntityrawcluster> dAOEntityrawclusterSet;
    @JoinColumn(name = "SENTENCEID", referencedColumnName = "SENTENCEID")
    @ManyToOne
    private DAOSentence sentenceid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOEntityRaw")
    private Set<DAOJaccardDistance> dAOJaccardDistanceSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dAOEntityRaw1")
    private Set<DAOJaccardDistance> dAOJaccardDistanceSet1;

    public DAOEntityRaw() {
    }

    public DAOEntityRaw(Integer entityid) {
        this.entityid = entityid;
    }

    public Integer getEntityid() {
        return entityid;
    }

    public void setEntityid(Integer entityid) {
        this.entityid = entityid;
    }

    public String getEntityname() {
        return entityname;
    }

    public void setEntityname(String entityname) {
        this.entityname = entityname;
    }

    public String getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(String entitytype) {
        this.entitytype = entitytype;
    }

    @XmlTransient
    public Set<DAOEntityrawcluster> getDAOEntityrawclusterSet() {
        return dAOEntityrawclusterSet;
    }

    public void setDAOEntityrawclusterSet(Set<DAOEntityrawcluster> dAOEntityrawclusterSet) {
        this.dAOEntityrawclusterSet = dAOEntityrawclusterSet;
    }

    public DAOSentence getSentenceid() {
        return sentenceid;
    }

    public void setSentenceid(DAOSentence sentenceid) {
        this.sentenceid = sentenceid;
    }

    @XmlTransient
    public Set<DAOJaccardDistance> getDAOJaccardDistanceSet() {
        return dAOJaccardDistanceSet;
    }

    public void setDAOJaccardDistanceSet(Set<DAOJaccardDistance> dAOJaccardDistanceSet) {
        this.dAOJaccardDistanceSet = dAOJaccardDistanceSet;
    }

    @XmlTransient
    public Set<DAOJaccardDistance> getDAOJaccardDistanceSet1() {
        return dAOJaccardDistanceSet1;
    }

    public void setDAOJaccardDistanceSet1(Set<DAOJaccardDistance> dAOJaccardDistanceSet1) {
        this.dAOJaccardDistanceSet1 = dAOJaccardDistanceSet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entityid != null ? entityid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOEntityRaw)) {
            return false;
        }
        DAOEntityRaw other = (DAOEntityRaw) object;
        if ((this.entityid == null && other.entityid != null) || (this.entityid != null && !this.entityid.equals(other.entityid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOEntityRaw[ entityid=" + entityid + " ]";
    }
    
}
