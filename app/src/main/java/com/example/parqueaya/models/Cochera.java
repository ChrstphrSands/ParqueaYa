package com.example.parqueaya.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cochera {
    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("HorarioAtencion")
    private String horarioAtencion;

    @SerializedName("CocheraEstadoId")
    private int cocheraEstadoId;

    @SerializedName("Foto")
    private String foto;

    @SerializedName("Telefono")
    private String telefono;

    @SerializedName("CodigoPostal")
    private String codigoPostal;

    @SerializedName("Longitud")
    private String longitud;

    @SerializedName("Direccion")
    private String direccion;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("Latitud")
    private String latitud;

    @SerializedName("EmpresaId")
    private int empresaId;

    @SerializedName("CocheraId")
    private int cocheraId;

    @SerializedName("Servicio")
    private List<Servicio> servicios;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setCocheraEstadoId(int cocheraEstadoId) {
        this.cocheraEstadoId = cocheraEstadoId;
    }

    public int getCocheraEstadoId() {
        return cocheraEstadoId;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setEmpresaId(int empresaId) {
        this.empresaId = empresaId;
    }

    public int getEmpresaId() {
        return empresaId;
    }

    public void setCocheraId(int cocheraId) {
        this.cocheraId = cocheraId;
    }

    public int getCocheraId() {
        return cocheraId;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }

    @Override
    public String toString() {
        return
            "Cochera{" +
                "nombre = '" + nombre + '\'' +
                ",horarioAtencion = '" + horarioAtencion + '\'' +
                ",cocheraEstadoId = '" + cocheraEstadoId + '\'' +
                ",foto = '" + foto + '\'' +
                ",telefono = '" + telefono + '\'' +
                ",codigoPostal = '" + codigoPostal + '\'' +
                ",longitud = '" + longitud + '\'' +
                ",direccion = '" + direccion + '\'' +
                ",descripcion = '" + descripcion + '\'' +
                ",latitud = '" + latitud + '\'' +
                ",empresaId = '" + empresaId + '\'' +
                ",cocheraId = '" + cocheraId + '\'' +
                "}";
    }
}
