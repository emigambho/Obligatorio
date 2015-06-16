/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario.util;

import entidad.Administrador;
import entidad.Jugador;
import entidad.Usuario;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class UsuarioOAuth {
    
    private Usuario usuario;
    private Date activoHasta;
    private String token;

    public UsuarioOAuth() {
    }

    public UsuarioOAuth(Usuario usuario, Date activoHasta, String token) {
        this.usuario = usuario;
        this.activoHasta = activoHasta;
        this.token = token;
    }
    
    public Boolean esJugador(){
        return this.usuario instanceof Jugador;
    }
    
    public Boolean esAdministrador(){
        return this.usuario instanceof Administrador;
    }
    
    public Jugador getJugador(){
        if(esJugador())
            return (Jugador)usuario;
        return null;
    }
    
     public Administrador getAdministrador(){
        if(esAdministrador())
            return (Administrador)usuario;
        return null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getActivoHasta() {
        return activoHasta;
    }

    public void setActivoHasta(Date activoHasta) {
        this.activoHasta = activoHasta;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
    
    
}
