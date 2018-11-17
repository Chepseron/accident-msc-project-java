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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByIdusers", query = "SELECT u FROM User u WHERE u.idusers = :idusers"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPword", query = "SELECT u FROM User u WHERE u.pword = :pword"),
    @NamedQuery(name = "User.findByCreatedAt", query = "SELECT u FROM User u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "User.findByLastLogin", query = "SELECT u FROM User u WHERE u.lastLogin = :lastLogin"),
    @NamedQuery(name = "User.findByDepartment", query = "SELECT u FROM User u WHERE u.department = :department"),
    @NamedQuery(name = "User.findByGroupID", query = "SELECT u FROM User u WHERE u.groupID = :groupID"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPhone", query = "SELECT u FROM User u WHERE u.phone = :phone"),
    @NamedQuery(name = "User.findByStaffID", query = "SELECT u FROM User u WHERE u.staffID = :staffID"),
    @NamedQuery(name = "User.findByStatusID", query = "SELECT u FROM User u WHERE u.statusID = :statusID"),
    @NamedQuery(name = "User.findByCreatedBy", query = "SELECT u FROM User u WHERE u.createdBy = :createdBy")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusers")
    private Integer idusers;
    @Size(max = 45)
    @Column(name = "username")
    private String username;
    @Size(max = 45)
    @Column(name = "pword")
    private String pword;
    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "lastLogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "department")
    private int department;
    @Column(name = "groupID")
    private Integer groupID;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "phone")
    private int phone;
    @Column(name = "staffID")
    private Integer staffID;
    @Column(name = "statusID")
    private Integer statusID;
    @Column(name = "createdBy")
    private Integer createdBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actors")
    private Collection<Deploymentunit> deploymentunitCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    private Collection<Deploymentunit> deploymentunitCollection1;
    @OneToMany(mappedBy = "createdBy")
    private Collection<Usergroup> usergroupCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    private Collection<Deployment> deploymentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actor")
    private Collection<Deployment> deploymentCollection1;
    @OneToMany(mappedBy = "createdBy")
    private Collection<Status> statusCollection;

    public User() {
    }

    public User(Integer idusers) {
        this.idusers = idusers;
    }

    public User(Integer idusers, int department, int phone) {
        this.idusers = idusers;
        this.department = department;
        this.phone = phone;
    }

    public Integer getIdusers() {
        return idusers;
    }

    public void setIdusers(Integer idusers) {
        this.idusers = idusers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPword() {
        return pword;
    }

    public void setPword(String pword) {
        this.pword = pword;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    @XmlTransient
    public Collection<Deploymentunit> getDeploymentunitCollection() {
        return deploymentunitCollection;
    }

    public void setDeploymentunitCollection(Collection<Deploymentunit> deploymentunitCollection) {
        this.deploymentunitCollection = deploymentunitCollection;
    }

    @XmlTransient
    public Collection<Deploymentunit> getDeploymentunitCollection1() {
        return deploymentunitCollection1;
    }

    public void setDeploymentunitCollection1(Collection<Deploymentunit> deploymentunitCollection1) {
        this.deploymentunitCollection1 = deploymentunitCollection1;
    }

    @XmlTransient
    public Collection<Usergroup> getUsergroupCollection() {
        return usergroupCollection;
    }

    public void setUsergroupCollection(Collection<Usergroup> usergroupCollection) {
        this.usergroupCollection = usergroupCollection;
    }

    @XmlTransient
    public Collection<Deployment> getDeploymentCollection() {
        return deploymentCollection;
    }

    public void setDeploymentCollection(Collection<Deployment> deploymentCollection) {
        this.deploymentCollection = deploymentCollection;
    }

    @XmlTransient
    public Collection<Deployment> getDeploymentCollection1() {
        return deploymentCollection1;
    }

    public void setDeploymentCollection1(Collection<Deployment> deploymentCollection1) {
        this.deploymentCollection1 = deploymentCollection1;
    }

    @XmlTransient
    public Collection<Status> getStatusCollection() {
        return statusCollection;
    }

    public void setStatusCollection(Collection<Status> statusCollection) {
        this.statusCollection = statusCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusers != null ? idusers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idusers == null && other.idusers != null) || (this.idusers != null && !this.idusers.equals(other.idusers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.User[ idusers=" + idusers + " ]";
    }
    
}
