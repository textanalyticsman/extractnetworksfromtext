/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author osboxes
 */
@Entity
@Table(name = "octoparseimport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DAOOctoparseImport.findAll", query = "SELECT d FROM DAOOctoparseImport d"),
    @NamedQuery(name = "DAOOctoparseImport.findByDate", query = "SELECT d FROM DAOOctoparseImport d WHERE d.date = :date"),
    @NamedQuery(name = "DAOOctoparseImport.findById", query = "SELECT d FROM DAOOctoparseImport d WHERE d.id = :id")})
public class DAOOctoparseImport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "DATE")
    private String date;
    @Lob
    @Column(name = "TITLE")
    private String title;
    @Lob
    @Column(name = "SUMMARY")
    private String summary;
    @Lob
    @Column(name = "CONTENT")
    private String content;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;

    public DAOOctoparseImport() {
    }

    public DAOOctoparseImport(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DAOOctoparseImport)) {
            return false;
        }
        DAOOctoparseImport other = (DAOOctoparseImport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.DAOOctoparseImport[ id=" + id + " ]";
    }
    
}
