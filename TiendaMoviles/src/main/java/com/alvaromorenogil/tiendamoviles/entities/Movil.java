
package com.alvaromorenogil.tiendamoviles.entities;

import java.io.Serializable;
import java.util.Date;


@javax.persistence.Entity
@javax.persistence.Table(name = "MOVIL")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Movil.findAll", query = "SELECT m FROM Movil m"),
    @javax.persistence.NamedQuery(name = "Movil.findById", query = "SELECT m FROM Movil m WHERE m.id = :id"),
    @javax.persistence.NamedQuery(name = "Movil.findByNombre", query = "SELECT m FROM Movil m WHERE m.nombre = :nombre"),
    @javax.persistence.NamedQuery(name = "Movil.findByModelo", query = "SELECT m FROM Movil m WHERE m.modelo = :modelo"),
    @javax.persistence.NamedQuery(name = "Movil.findByCapacidad", query = "SELECT m FROM Movil m WHERE m.capacidad = :capacidad"),
    @javax.persistence.NamedQuery(name = "Movil.findByAncho", query = "SELECT m FROM Movil m WHERE m.ancho = :ancho"),
    @javax.persistence.NamedQuery(name = "Movil.findByAlto", query = "SELECT m FROM Movil m WHERE m.alto = :alto"),
    @javax.persistence.NamedQuery(name = "Movil.findByGrosor", query = "SELECT m FROM Movil m WHERE m.grosor = :grosor"),
    @javax.persistence.NamedQuery(name = "Movil.findByPeso", query = "SELECT m FROM Movil m WHERE m.peso = :peso"),
    @javax.persistence.NamedQuery(name = "Movil.findByChip", query = "SELECT m FROM Movil m WHERE m.chip = :chip"),
    @javax.persistence.NamedQuery(name = "Movil.findByCamaras", query = "SELECT m FROM Movil m WHERE m.camaras = :camaras"),
    @javax.persistence.NamedQuery(name = "Movil.findByPantalla", query = "SELECT m FROM Movil m WHERE m.pantalla = :pantalla"),
    @javax.persistence.NamedQuery(name = "Movil.findBySistemaOperativo", query = "SELECT m FROM Movil m WHERE m.sistemaOperativo = :sistemaOperativo"),
    @javax.persistence.NamedQuery(name = "Movil.findByFechaSalida", query = "SELECT m FROM Movil m WHERE m.fechaSalida = :fechaSalida"),
    @javax.persistence.NamedQuery(name = "Movil.findByPrecio", query = "SELECT m FROM Movil m WHERE m.precio = :precio"),
    @javax.persistence.NamedQuery(name = "Movil.findByCincoG", query = "SELECT m FROM Movil m WHERE m.cincoG = :cincoG")})
public class Movil implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "MODELO")
    private String modelo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "CAPACIDAD")
    private String capacidad;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ANCHO")
    private String ancho;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ALTO")
    private String alto;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "GROSOR")
    private String grosor;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "PESO")
    private String peso;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "CHIP")
    private String chip;
    @javax.persistence.Column(name = "CAMARAS")
    private Character camaras;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "PANTALLA")
    private String pantalla;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "SISTEMA_OPERATIVO")
    private String sistemaOperativo;
    @javax.persistence.Column(name = "FECHA_SALIDA")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaSalida;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "PRECIO")
    private String precio;
    @javax.persistence.Column(name = "CINCO_G")
    private Boolean cincoG;
    @javax.persistence.JoinColumn(name = "MARCA", referencedColumnName = "ID")
    @javax.persistence.ManyToOne
    private Marca marca;

    public Movil() {
    }

    public Movil(Integer id) {
        this.id = id;
    }

    public Movil(Integer id, String nombre, String modelo, String capacidad, String ancho, String alto, String grosor, String peso, String chip, String pantalla, String sistemaOperativo, String precio) {
        this.id = id;
        this.nombre = nombre;
        this.modelo = modelo;
        this.capacidad = capacidad;
        this.ancho = ancho;
        this.alto = alto;
        this.grosor = grosor;
        this.peso = peso;
        this.chip = chip;
        this.pantalla = pantalla;
        this.sistemaOperativo = sistemaOperativo;
        this.precio = precio;
    }

    public Movil(int i, String apple, String string, String _g) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    public String getAlto() {
        return alto;
    }

    public void setAlto(String alto) {
        this.alto = alto;
    }

    public String getGrosor() {
        return grosor;
    }

    public void setGrosor(String grosor) {
        this.grosor = grosor;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getChip() {
        return chip;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public Character getCamaras() {
        return camaras;
    }

    public void setCamaras(Character camaras) {
        this.camaras = camaras;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Boolean getCincoG() {
        return cincoG;
    }

    public void setCincoG(Boolean cincoG) {
        this.cincoG = cincoG;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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
        if (!(object instanceof Movil)) {
            return false;
        }
        Movil other = (Movil) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.alvaromorenogil.tiendamoviles.entities.Movil[ id=" + id + " ]";
    }
    
}
