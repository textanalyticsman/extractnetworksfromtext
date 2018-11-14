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
import javax.persistence.Lob;
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
@Table(name = "edgelist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOEdgeList.findAll", query = "SELECT d FROM DAOEdgeList d"),
    @NamedQuery(name = "DAOEdgeList.findByEntitynormid01", query = "SELECT d FROM DAOEdgeList d WHERE d.dAOEdgeListPK.entitynormid01 = :entitynormid01"),
    @NamedQuery(name = "DAOEdgeList.findByEntitynormname01", query = "SELECT d FROM DAOEdgeList d WHERE d.entitynormname01 = :entitynormname01"),
    @NamedQuery(name = "DAOEdgeList.findByEntitynormname02", query = "SELECT d FROM DAOEdgeList d WHERE d.entitynormname02 = :entitynormname02"),
    @NamedQuery(name = "DAOEdgeList.findByEntitynormid02", query = "SELECT d FROM DAOEdgeList d WHERE d.dAOEdgeListPK.entitynormid02 = :entitynormid02"),
    @NamedQuery(name = "DAOEdgeList.findByWeight", query = "SELECT d FROM DAOEdgeList d WHERE d.weight = :weight")})
public class DAOEdgeList implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DAOEdgeListPK dAOEdgeListPK;
    @Column(name = "ENTITYNORMNAME01")
    private String entitynormname01;
    @Column(name = "ENTITYNORMNAME02")
    private String entitynormname02;
    @Column(name = "WEIGHT")
    private Integer weight;
    @Lob
    @Column(name = "SENTENCES")
    private String sentences;
    @JoinColumn(name = "CORPUS_CORPID", referencedColumnName = "CORPID")
    @ManyToOne(optional = false)
    private DAOCorpus corpusCorpid;
    @JoinColumn(name = "ENTITYNORMID01", referencedColumnName = "ENTITYNORMID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOEntityNormalized dAOEntityNormalized;
    @JoinColumn(name = "ENTITYNORMID02", referencedColumnName = "ENTITYNORMID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DAOEntityNormalized dAOEntityNormalized1;

    public DAOEdgeList() {
    }

    public DAOEdgeList(DAOEdgeListPK dAOEdgeListPK) {
        this.dAOEdgeListPK = dAOEdgeListPK;
    }

    public DAOEdgeList(int entitynormid01, int entitynormid02) {
        this.dAOEdgeListPK = new DAOEdgeListPK(entitynormid01, entitynormid02);
    }

    public DAOEdgeListPK getDAOEdgeListPK() {
        return dAOEdgeListPK;
    }

    public void setDAOEdgeListPK(DAOEdgeListPK dAOEdgeListPK) {
        this.dAOEdgeListPK = dAOEdgeListPK;
    }

    public String getEntitynormname01() {
        return entitynormname01;
    }

    public void setEntitynormname01(String entitynormname01) {
        this.entitynormname01 = entitynormname01;
    }

    public String getEntitynormname02() {
        return entitynormname02;
    }

    public void setEntitynormname02(String entitynormname02) {
        this.entitynormname02 = entitynormname02;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    public DAOCorpus getCorpusCorpid() {
        return corpusCorpid;
    }

    public void setCorpusCorpid(DAOCorpus corpusCorpid) {
        this.corpusCorpid = corpusCorpid;
    }

    public DAOEntityNormalized getDAOEntityNormalized() {
        return dAOEntityNormalized;
    }

    public void setDAOEntityNormalized(DAOEntityNormalized dAOEntityNormalized) {
        this.dAOEntityNormalized = dAOEntityNormalized;
    }

    public DAOEntityNormalized getDAOEntityNormalized1() {
        return dAOEntityNormalized1;
    }

    public void setDAOEntityNormalized1(DAOEntityNormalized dAOEntityNormalized1) {
        this.dAOEntityNormalized1 = dAOEntityNormalized1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dAOEdgeListPK != null ? dAOEdgeListPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOEdgeList)) {
            return false;
        }
        DAOEdgeList other = (DAOEdgeList) object;
        if ((this.dAOEdgeListPK == null && other.dAOEdgeListPK != null) || (this.dAOEdgeListPK != null && !this.dAOEdgeListPK.equals(other.dAOEdgeListPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOEdgeList[ dAOEdgeListPK=" + dAOEdgeListPK + " ]";
    }
    
}
