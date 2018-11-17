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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FAZOUL
 */
@Entity
@Table(name = "deploymentunit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deploymentunit.findAll", query = "SELECT d FROM Deploymentunit d"),
    @NamedQuery(name = "Deploymentunit.findByIddeploymentUnit", query = "SELECT d FROM Deploymentunit d WHERE d.iddeploymentUnit = :iddeploymentUnit"),
    @NamedQuery(name = "Deploymentunit.findByOrgname", query = "SELECT d FROM Deploymentunit d WHERE d.orgname = :orgname"),
    @NamedQuery(name = "Deploymentunit.findByDateCreated", query = "SELECT d FROM Deploymentunit d WHERE d.dateCreated = :dateCreated"),
    @NamedQuery(name = "Deploymentunit.findByPhoneNumber", query = "SELECT d FROM Deploymentunit d WHERE d.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Deploymentunit.findByLatitude", query = "SELECT d FROM Deploymentunit d WHERE d.latitude = :latitude"),
    @NamedQuery(name = "Deploymentunit.findByLongitude", query = "SELECT d FROM Deploymentunit d WHERE d.longitude = :longitude")})
public class Deploymentunit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddeploymentUnit")
    private Integer iddeploymentUnit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "orgname")
    private String orgname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "latitude")
    private String latitude;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "longitude")
    private String longitude;
    @JoinColumn(name = "actorGroupName", referencedColumnName = "idactors")
    @ManyToOne(optional = false)
    private Actorgroup actorGroupName;
    @JoinColumn(name = "actors", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User actors;
    @JoinColumn(name = "createdBy", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;

    public Deploymentunit() {
    }

    public Deploymentunit(Integer iddeploymentUnit) {
        this.iddeploymentUnit = iddeploymentUnit;
    }

    public Deploymentunit(Integer iddeploymentUnit, String orgname, Date dateCreated, String phoneNumber, String latitude, String longitude) {
        this.iddeploymentUnit = iddeploymentUnit;
        this.orgname = orgname;
        this.dateCreated = dateCreated;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIddeploymentUnit() {
        return iddeploymentUnit;
    }

    public void setIddeploymentUnit(Integer iddeploymentUnit) {
        this.iddeploymentUnit = iddeploymentUnit;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Actorgroup getActorGroupName() {
        return actorGroupName;
    }

    public void setActorGroupName(Actorgroup actorGroupName) {
        this.actorGroupName = actorGroupName;
    }

    public User getActors() {
        return actors;
    }

    public void setActors(User actors) {
        this.actors = actors;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddeploymentUnit != null ? iddeploymentUnit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deploymentunit)) {
            return false;
        }
        Deploymentunit other = (Deploymentunit) object;
        if ((this.iddeploymentUnit == null && other.iddeploymentUnit != null) || (this.iddeploymentUnit != null && !this.iddeploymentUnit.equals(other.iddeploymentUnit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Deploymentunit[ iddeploymentUnit=" + iddeploymentUnit + " ]";
    }
    
}
