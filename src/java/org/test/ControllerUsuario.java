/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Factura;
import entidades.Usuario;
import entidades.Empresa;
import entidades.Plan;
import java.util.List;
import servicios.ServicioFactura;
import servicios.ServicioUsuario;
import servicios.ServicioEmpresa;
import servicios.ServicioPlan;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
//import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerUsuario")
@SessionScoped
public class ControllerUsuario {

    ServicioUsuario servicioUsuario = new ServicioUsuario();
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioEmpresa servicioEmpresa = new ServicioEmpresa();
    ServicioPlan servicePlan = new ServicioPlan();
    Factura factura = new Factura();
    Usuario usuario = new Usuario();
    Empresa empresa = new Empresa();
    Plan plan = new Plan();

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
            return "registerUser";
        } else {
            usuario.setEstado("A");
            servicioUsuario.insert(usuario);
            clearUsuario();
            return "index";
        }
    }    

    public String insertarUsuarioEmpresaRegister() throws Exception {
        if (usuario == null) {
            return "registro";
        } else {
            usuario.setEstado("A");
            
            
            plan.setId(1);
            plan = servicePlan.read(plan);
            
            empresa.setNombre(usuario.getNombreUsuario());
            empresa.setCedula(usuario.getCedulaUsuario());
            empresa.setTelefono(usuario.getTelefono());
            empresa.setCorreo(usuario.getCorreo());
            empresa.setPlan(plan);
            empresa.setCantidadRestanteFacturas(plan.getCantidad());
            empresa.setTipo_Persona("F");
            empresa.setConsecutivo_Ini(1);
            empresa.setEstado("A");
            servicioEmpresa.insert(this.empresa);
            servicioUsuario.insert(usuario);
            empresa = new Empresa();
            plan = new Plan();
            
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
            usuario.setCedulaUsuario(0);
    
    }

    public void editPage(Usuario user) {

        if (user != null) {

            usuario.setId(user.getId());
            usuario.setContraseña(user.getContraseña());
            usuario.setCorreo(user.getCorreo());
            usuario.setEstado(user.getEstado());
            usuario.setNombreUsuario(user.getNombreUsuario());
            usuario.setTelefono(user.getTelefono());
            usuario.setCedulaUsuario(user.getCedulaUsuario());

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
