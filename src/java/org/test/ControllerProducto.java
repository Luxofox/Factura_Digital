/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Producto;
import java.util.List;
import servicios.ServicioProducto;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerProducto")
@SessionScoped
public class ControllerProducto {
    ServicioProducto servicioProducto = new ServicioProducto();
    Producto producto = new Producto();

    public boolean insertarProducto(Producto p) throws Exception {
        if (p != null) {
            return false;
        } else {
            servicioProducto.insert(p);
            return true;
        }
    }

    public boolean modificarProducto(Producto p) throws Exception {
        if (p != null) {
            return servicioProducto.modify(p)!=false;
        } else {
            return false;
        }
    }

    public Producto leerProducto(Producto p) throws Exception {
        if (p != null) {
            return servicioProducto.read(p);
        }else 
            return null;
    }
    
    public boolean eliminarProducto(Producto p) throws Exception {
        if (p != null) {
            return servicioProducto.delete(p);
        } else {
            return false;
        }
    }
    
    public List<Producto> listaProducto() throws Exception{
        return servicioProducto.list(producto);
    }
}
