/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author FAZOUL
 */
@Entity
@Table(name = "accident")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accident.findAll", query = "SELECT a FROM Accident a"),
    @NamedQuery(name = "Accident.findByIdaccident", query = "SELECT a FROM Accident a WHERE a.idaccident = :idaccident"),
    @NamedQuery(name = "Accident.findByPlaceOccured", query = "SELECT a FROM Accident a WHERE a.placeOccured = :placeOccured"),
    @NamedQuery(name = "Accident.findByRoad", query = "SELECT a FROM Accident a WHERE a.road = :road"),
    @NamedQuery(name = "Accident.findByTimeOccured", query = "SELECT a FROM Accident a WHERE a.timeOccured = :timeOccured"),
    @NamedQuery(name = "Accident.findByDeadVictims", query = "SELECT a FROM Accident a WHERE a.deadVictims = :deadVictims"),
    @NamedQuery(name = "Accident.findByInjuredVictims", query = "SELECT a FROM Accident a WHERE a.injuredVictims = :injuredVictims"),
    @NamedQuery(name = "Accident.findByMsisdn", query = "SELECT a FROM Accident a WHERE a.msisdn = :msisdn"),
    @NamedQuery(name = "Accident.findByLatitude", query = "SELECT a FROM Accident a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "Accident.findByLongitude", query = "SELECT a FROM Accident a WHERE a.longitude = :longitude")})
public class Accident implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaccident")
    private Integer idaccident;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "placeOccured")
    private String placeOccured;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "road")
    private String road;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timeOccured")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOccured;
    @Basic(optional = false)
    @NotNull
    @Column(name = "deadVictims")
    private int deadVictims;
    @Basic(optional = false)
    @NotNull
    @Column(name = "injuredVictims")
    private int injuredVictims;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "msisdn")
    private String msisdn;
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
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accident")
    private Collection<Deployment> deploymentCollection;

    public Accident() {
    }

    public Accident(Integer idaccident) {
        this.idaccident = idaccident;
    }

    public Accident(Integer idaccident, String placeOccured, String road, Date timeOccured, int deadVictims, int injuredVictims, String msisdn, String latitude, String longitude) {
        this.idaccident = idaccident;
        this.placeOccured = placeOccured;
        this.road = road;
        this.timeOccured = timeOccured;
        this.deadVictims = deadVictims;
        this.injuredVictims = injuredVictims;
        this.msisdn = msisdn;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdaccident() {
        return idaccident;
    }

    public void setIdaccident(Integer idaccident) {
        this.idaccident = idaccident;
    }

    public String getPlaceOccured() {
        return placeOccured;
    }

    public void setPlaceOccured(String placeOccured) {
        this.placeOccured = placeOccured;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public Date getTimeOccured() {
        return timeOccured;
    }

    public void setTimeOccured(Date timeOccured) {
        this.timeOccured = timeOccured;
    }

    public int getDeadVictims() {
        return deadVictims;
    }

    public void setDeadVictims(int deadVictims) {
        this.deadVictims = deadVictims;
    }

    public int getInjuredVictims() {
        return injuredVictims;
    }

    public void setInjuredVictims(int injuredVictims) {
        this.injuredVictims = injuredVictims;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    @XmlTransient
    public Collection<Deployment> getDeploymentCollection() {
        return deploymentCollection;
    }

    public void setDeploymentCollection(Collection<Deployment> deploymentCollection) {
        this.deploymentCollection = deploymentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaccident != null ? idaccident.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accident)) {
            return false;
        }
        Accident other = (Accident) object;
        if ((this.idaccident == null && other.idaccident != null) || (this.idaccident != null && !this.idaccident.equals(other.idaccident))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Accident[ idaccident=" + idaccident + " ]";
    }
    
}
