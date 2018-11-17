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
@Table(name = "deployment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deployment.findAll", query = "SELECT d FROM Deployment d"),
    @NamedQuery(name = "Deployment.findByIddeployment", query = "SELECT d FROM Deployment d WHERE d.iddeployment = :iddeployment"),
    @NamedQuery(name = "Deployment.findByDeploymentUnitID", query = "SELECT d FROM Deployment d WHERE d.deploymentUnitID = :deploymentUnitID"),
    @NamedQuery(name = "Deployment.findByCreatedOn", query = "SELECT d FROM Deployment d WHERE d.createdOn = :createdOn"),
    @NamedQuery(name = "Deployment.findBySeverity", query = "SELECT d FROM Deployment d WHERE d.severity = :severity"),
    @NamedQuery(name = "Deployment.findByAcknowledgement", query = "SELECT d FROM Deployment d WHERE d.acknowledgement = :acknowledgement")})
public class Deployment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddeployment")
    private Integer iddeployment;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "deploymentUnitID")
    private String deploymentUnitID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "createdOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "severity")
    private String severity;
    @Column(name = "acknowledgement")
    private Integer acknowledgement;
    @JoinColumn(name = "createdBy", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User createdBy;
    @JoinColumn(name = "accident", referencedColumnName = "idaccident")
    @ManyToOne(optional = false)
    private Accident accident;
    @JoinColumn(name = "statusID", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status statusID;
    @JoinColumn(name = "actor", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private User actor;

    public Deployment() {
    }

    public Deployment(Integer iddeployment) {
        this.iddeployment = iddeployment;
    }

    public Deployment(Integer iddeployment, String deploymentUnitID, Date createdOn, String severity) {
        this.iddeployment = iddeployment;
        this.deploymentUnitID = deploymentUnitID;
        this.createdOn = createdOn;
        this.severity = severity;
    }

    public Integer getIddeployment() {
        return iddeployment;
    }

    public void setIddeployment(Integer iddeployment) {
        this.iddeployment = iddeployment;
    }

    public String getDeploymentUnitID() {
        return deploymentUnitID;
    }

    public void setDeploymentUnitID(String deploymentUnitID) {
        this.deploymentUnitID = deploymentUnitID;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Integer getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(Integer acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Accident getAccident() {
        return accident;
    }

    public void setAccident(Accident accident) {
        this.accident = accident;
    }

    public Status getStatusID() {
        return statusID;
    }

    public void setStatusID(Status statusID) {
        this.statusID = statusID;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddeployment != null ? iddeployment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deployment)) {
            return false;
        }
        Deployment other = (Deployment) object;
        if ((this.iddeployment == null && other.iddeployment != null) || (this.iddeployment != null && !this.iddeployment.equals(other.iddeployment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Deployment[ iddeployment=" + iddeployment + " ]";
    }
    
}
