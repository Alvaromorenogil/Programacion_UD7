
package com.alvaromorenogil.tiendamoviles.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@javax.persistence.Entity
@javax.persistence.Table(name = "MARCA")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Marca.findAll", query = "SELECT m FROM Marca m"),
    @javax.persistence.NamedQuery(name = "Marca.findById", query = "SELECT m FROM Marca m WHERE m.id = :id"),
    @javax.persistence.NamedQuery(name = "Marca.findByNombre", query = "SELECT m FROM Marca m WHERE m.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Marca.findByFechaFundacion", query = "SELECT m FROM Marca m WHERE m.fechaFundacion = :fechaFundacion"),
    @javax.persistence.NamedQuery(name = "Marca.findByLocalizacion", query = "SELECT m FROM Marca m WHERE m.localizacion = :localizacion"),
    @javax.persistence.NamedQuery(name = "Marca.findByFundador", query = "SELECT m FROM Marca m WHERE m.fundador = :fundador"),
    @javax.persistence.NamedQuery(name = "Marca.findByImagen", query = "SELECT m FROM Marca m WHERE m.imagen = :imagen")})
public class Marca implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.Column(name = "FECHA_FUNDACION")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFundacion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "LOCALIZACION")
    private String localizacion;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "FUNDADOR")
    private String fundador;
    @javax.persistence.Column(name = "IMAGEN")
    private String imagen;
    @javax.persistence.OneToMany(mappedBy = "marca")
    private Collection<Movil> movilCollection;

    public Marca() {
    }

    public Marca(Integer id) {
        this.id = id;
    }

    public Marca(Integer id, String nombre, String localizacion, String fundador) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.fundador = fundador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(Date fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getFundador() {
        return fundador;
    }

    public void setFundador(String fundador) {
        this.fundador = fundador;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Collection<Movil> getMovilCollection() {
        return movilCollection;
    }

    public void setMovilCollection(Collection<Movil> movilCollection) {
        this.movilCollection = movilCollection;
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
        if (!(object instanceof Marca)) {
            return false;
        }
        Marca other = (Marca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.alvaromorenogil.tiendamoviles.entities.Marca[ id=" + id + " ]";
    }
    
}
