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
import javax.persistence.Lob;
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
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAODocument.findAll", query = "SELECT d FROM DAODocument d"),
    @NamedQuery(name = "DAODocument.findByDocid", query = "SELECT d FROM DAODocument d WHERE d.docid = :docid"),
    @NamedQuery(name = "DAODocument.findByDate", query = "SELECT d FROM DAODocument d WHERE d.date = :date")})
public class DAODocument implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DOCID")
    private Integer docid;
    @Lob
    @Column(name = "TITLE")
    private String title;
    @Lob
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "DATE")
    private String date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docid")
    private Set<DAOParagraph> dAOParagraphSet;
    @JoinColumn(name = "CORPID", referencedColumnName = "CORPID")
    @ManyToOne(optional = false)
    private DAOCorpus corpid;

    public DAODocument() {
    }

    public DAODocument(Integer docid) {
        this.docid = docid;
    }

    public Integer getDocid() {
        return docid;
    }

    public void setDocid(Integer docid) {
        this.docid = docid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @XmlTransient
    public Set<DAOParagraph> getDAOParagraphSet() {
        return dAOParagraphSet;
    }

    public void setDAOParagraphSet(Set<DAOParagraph> dAOParagraphSet) {
        this.dAOParagraphSet = dAOParagraphSet;
    }

    public DAOCorpus getCorpid() {
        return corpid;
    }

    public void setCorpid(DAOCorpus corpid) {
        this.corpid = corpid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docid != null ? docid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAODocument)) {
            return false;
        }
        DAODocument other = (DAODocument) object;
        if ((this.docid == null && other.docid != null) || (this.docid != null && !this.docid.equals(other.docid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAODocument[ docid=" + docid + " ]";
    }
    
}
