/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Factura;
import entidades.Usuario;
import servicios.ServicioFactura;
import servicios.ServicioUsuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerUsuario")
@SessionScoped
public class ControllerUsuario {
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    ServicioFactura servicioFactura = new ServicioFactura();
    Factura factura = new Factura();
    
    public boolean insertarUsuario(Usuario u) throws Exception {
        if (u != null) {
            return false;
        } else {
            servicioUsuario.insert(u);
            return true;
        }
    }

    public boolean modificarUsuario(Usuario u) throws Exception {
        if (u != null) {
            return servicioUsuario.modify(u)!=false;
        } else {
            return false;
        }
    }

    public Usuario leerUsuario(Usuario u) throws Exception {
        if (u != null) {
            return servicioUsuario.read(u);
        }else 
            return null;
    }
    public boolean eliminarUsuario(Usuario u) throws Exception {
        if (u != null) {
            if (servicioUsuario.list(u) != null) {
                for (Factura f : servicioFactura.list(factura)) {
                    if (f.getUsuario().getId()== u.getId()) {
                        if(f.getEstado()!=5&&(f.getEstado() != 1 && f.getEstado() != 4)){
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
}
