/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Factura;
import entidades.Usuario;
import java.util.List;
import servicios.ServicioFactura;
import servicios.ServicioUsuario;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerUsuario")
@SessionScoped
public class ControllerUsuario {

    ServicioUsuario servicioUsuario = new ServicioUsuario();
    ServicioFactura servicioFactura = new ServicioFactura();
    Factura factura = new Factura();
    Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
        public String insertarUsuario() throws Exception {
        if (usuario == null) {
            return "UsuarioCrear";
        } else {
            usuario.setEstado("A");
            servicioUsuario.insert(usuario);
            clearUsuario();
            return "CrudUsuario";
        }
    }

    public String insertarUsuarioRegister() throws Exception {
        if (usuario == null) {
            return "registro";
        } else {
            usuario.setEstado("A");
            servicioUsuario.insert(usuario);
            clearUsuario();
            return "index.xhtml";
        }
    }
    
    public void clearUsuario(){
    
            usuario.setId(0);
            usuario.setContraseña("");
            usuario.setCorreo("");
            usuario.setEstado("");
            usuario.setNombreUsuario("");
            usuario.setTelefono(0);
    
    }

    public void editPage(Usuario user) {

        if (user != null) {

            usuario.setId(user.getId());
            usuario.setContraseña(user.getContraseña());
            usuario.setCorreo(user.getCorreo());
            usuario.setEstado(user.getEstado());
            usuario.setNombreUsuario(user.getNombreUsuario());
            usuario.setTelefono(user.getTelefono());

        }

    }

    public void modificarUsuario() throws Exception {

        if (usuario == null) {

            System.out.println("Usuario Nulo");

        } else {
            servicioUsuario.modify(usuario);
            
        }
    }

    public Usuario leerUsuario(Usuario u) throws Exception {
        if (u != null) {
            return servicioUsuario.read(u);
        } else {
            return null;
        }
    }

    public boolean eliminarUsuario(Usuario u) throws Exception {
        if (u != null) {
            if (servicioUsuario.list(u) != null) {
                for (Factura f : servicioFactura.list(factura)) {
                    if (f.getUsuario().getId() == u.getId()) {
                        if (f.getEstado() != 5 && (f.getEstado() != 1 && f.getEstado() != 4)) {
                        } else {
                            return false;
                        }
                    }
                }
                u.setEstado("I");
                servicioUsuario.modify(u);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Usuario> listaUsuario() throws Exception {
        return servicioUsuario.list(usuario);
    }

}
