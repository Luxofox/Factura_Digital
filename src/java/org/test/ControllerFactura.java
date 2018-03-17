/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Empresa;
import entidades.Empresausuario;
import entidades.Factura;
import entidades.Item;
import entidades.Producto;
import java.util.List;
import servicios.ServicioFactura;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import servicios.ServiceLogin;

@ManagedBean(name = "controllerFactura")
@RequestScoped
public class ControllerFactura {

    ServicioFactura servicioFactura = new ServicioFactura();
    ServiceLogin servicioExtra = new ServiceLogin();
    Factura factura = new Factura();
    List<Item> carrito;

    public boolean insertarFactura(Factura f) throws Exception {
        if (f != null) {
            return false;
        } else {
            servicioFactura.insert(f);
            return true;
        }
    }

    public boolean modificarFactura(Factura f) throws Exception {
        if (f != null) {
            return servicioFactura.modify(f) != false;
        } else {
            return false;
        }
    }

    public Factura leerFactura(Factura f) throws Exception {
        if (f != null) {
            return servicioFactura.read(f);
        } else {
            return null;
        }
    }

    public List<Factura> listaFactura(int id) throws Exception {
        List<Factura> listFactura = servicioExtra.listFactura(id);
        return listFactura;
    }
    
    public List<Empresausuario> listaEmpresaUsuario(int idUsuario) throws Exception {
        List<Empresausuario> listEmpresaUsuario = servicioExtra.listEmpresaUsuario(idUsuario);
        return listEmpresaUsuario;
    }
    
    public List<Producto> listaProductoEmpresa(int id) throws Exception{
       List<Producto> listProducto = servicioExtra.listProductoEmpresa(id);
       return listProducto;
    }
    
    public void addListaCarrito(Item item) {
        carrito.add(item);
    }

    public void deleteListaCarrito(Item item) {
        carrito.remove(item);
    }

    public List<Item> listaCarrito() {
        return carrito;
    }

    public void setCarrito(List<Item> carrito) {
        this.carrito = carrito;
    }

    public int getCantidad() {
        return carrito.size();
    }
    
    public boolean renderedBoton(int estado){
        if(estado != 0){
            return false;
        }else
            return true;
    }
}
