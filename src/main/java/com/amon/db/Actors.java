/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FAZOUL
 */
@Entity
@Table(name = "actors")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actors.findAll", query = "SELECT a FROM Actors a"),
    @NamedQuery(name = "Actors.findByIdactors", query = "SELECT a FROM Actors a WHERE a.idactors = :idactors"),
    @NamedQuery(name = "Actors.findByNames", query = "SELECT a FROM Actors a WHERE a.names = :names"),
    @NamedQuery(name = "Actors.findByCreatedBy", query = "SELECT a FROM Actors a WHERE a.createdBy = :createdBy"),
    @NamedQuery(name = "Actors.findByDateCreated", query = "SELECT a FROM Actors a WHERE a.dateCreated = :dateCreated"),
    @NamedQuery(name = "Actors.findByStatusID", query = "SELECT a FROM Actors a WHERE a.statusID = :statusID")})
public class Actors implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idactors")
    private Integer idactors;
    @Size(max = 45)
    @Column(name = "names")
    private String names;
    @Column(name = "createdBy")
    private Integer createdBy;
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "statusID")
    private Integer statusID;

    public Actors() {
    }

    public Actors(Integer idactors) {
        this.idactors = idactors;
    }

    public Integer getIdactors() {
        return idactors;
    }

    public void setIdactors(Integer idactors) {
        this.idactors = idactors;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idactors != null ? idactors.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actors)) {
            return false;
        }
        Actors other = (Actors) object;
        if ((this.idactors == null && other.idactors != null) || (this.idactors != null && !this.idactors.equals(other.idactors))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Actors[ idactors=" + idactors + " ]";
    }
    
}
