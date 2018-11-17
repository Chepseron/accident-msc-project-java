/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amon.db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rinma.Iweriebor
 */
@Entity
@Table(name = "apikey")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apikey.findAll", query = "SELECT a FROM Apikey a")
    , @NamedQuery(name = "Apikey.findByIdapiKey", query = "SELECT a FROM Apikey a WHERE a.idapiKey = :idapiKey")
    , @NamedQuery(name = "Apikey.findByApikey", query = "SELECT a FROM Apikey a WHERE a.apikey = :apikey")})
public class Apikey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idapiKey")
    private Integer idapiKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "apikey")
    private String apikey;

    public Apikey() {
    }

    public Apikey(Integer idapiKey) {
        this.idapiKey = idapiKey;
    }

    public Apikey(Integer idapiKey, String apikey) {
        this.idapiKey = idapiKey;
        this.apikey = apikey;
    }

    public Integer getIdapiKey() {
        return idapiKey;
    }

    public void setIdapiKey(Integer idapiKey) {
        this.idapiKey = idapiKey;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idapiKey != null ? idapiKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apikey)) {
            return false;
        }
        Apikey other = (Apikey) object;
        if ((this.idapiKey == null && other.idapiKey != null) || (this.idapiKey != null && !this.idapiKey.equals(other.idapiKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.amon.db.Apikey[ idapiKey=" + idapiKey + " ]";
    }
    
}
