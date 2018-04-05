
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

/**
 *
 * @author Li
 */
import entidades.Cliente;
import servicios.ServicioCliente;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerCliente")
@SessionScoped
public class ControllerCliente {

    ServicioCliente servicioCliente = new ServicioCliente();
    Cliente cliente = new Cliente();
    
        public boolean insertarCliente(Cliente c) throws Exception {
        if (c != null) {
            return false;
        } else {
            servicioCliente.insert(c);
            return true;
        }
    }

    public boolean modificarCliente(Cliente c) throws Exception {
        if (c != null) {
            return servicioCliente.modify(c) != false;
        } else {
            return false;
        }
    }

    public Cliente leerCliente(Cliente c) throws Exception {
        return servicioCliente.read(c);
    }

    public boolean eliminarCliente(Cliente c) throws Exception {
        if (c != null) {
            return servicioCliente.delete(c);
        } else {
            return false;
        }
    }
}
