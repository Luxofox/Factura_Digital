/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Empresa;
import entidades.Empresacliente;
import entidades.Empresausuario;
import entidades.Factura;
import entidades.Item;
import entidades.Producto;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import servicios.ServicioFactura;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import servicios.ServiceLogin;
import servicios.ServicioProducto;
import servicios.ServicioUsuario;

@ManagedBean(name = "controllerFactura")
@SessionScoped
public class ControllerFactura implements Serializable {

    ServicioFactura servicioFactura = new ServicioFactura();
    ServiceLogin servicioExtra = new ServiceLogin();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    Factura factura = new Factura();
    Item item = new Item();
    List<Item> carrito = new ArrayList();
    List<Producto> listProducto;
    Producto producto = new Producto();
    Empresa empresa = new Empresa();
    double subtotal = 0;
    double total = 0;
    double impuestoVenta = 0;
    double descuento;
    int cantidadProducto;

    public ControllerFactura() throws Exception {
        this.listProducto = servicioExtra.listProductoEmpresa("1");
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public double getImpuestoVenta() {
        return impuestoVenta;
    }

    public void setImpuestoVenta(double impuestoVenta) {
        this.impuestoVenta = impuestoVenta;
    }

    public Producto getProducto(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("No hay id");
        }
        for (Producto producto : listProducto) {
            if (id.equals(producto.getId())) {
                return producto;
            }
        }
        return null;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public List<Producto> getListaProducto() {
        return listProducto;
    }

    public void setListaProducto(List<Producto> listProducto) {
        this.listProducto = listProducto;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

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

    public void productoSelect(ValueChangeEvent e) throws Exception {
        listProducto = servicioExtra.listProductoEmpresa(e.getNewValue().toString());
    }

    public void addListaCarrito(int productoId) throws Exception {
        producto.setId(productoId);
        item.setCantidad(this.cantidadProducto);
        item.setProducto(servicioProducto.read(producto));
        item.setDescuento(this.descuento);
        carrito.add(item);
        setSubtotal(subtotal + (item.getProducto().getPrecio() * item.getCantidad()));
        setTotal(total + subtotal + ((item.getProducto().getPrecio()*item.getProducto().getImpuestoVenta())/100) - ((item.getProducto().getPrecio()*item.getDescuento())/100));
    }

    public void deleteListaCarrito(Item item) {
        setImpuestoVenta(impuestoVenta-(((item.getProducto().getPrecio()*item.getProducto().getImpuestoVenta())/100)));
        setTotal(total - subtotal - ((item.getProducto().getPrecio()*item.getProducto().getImpuestoVenta())/100) + ((item.getProducto().getPrecio()*item.getDescuento())/100));
        setSubtotal(subtotal - (item.getProducto().getPrecio() * item.getCantidad()));
        carrito.remove(item);
    }

    public List<Item> getlistaCarrito() {
        return carrito;
    }

    public void setCarrito(List<Item> carrito) {
        this.carrito = carrito;
    }

    public int getCantidadCarrito() {
        return carrito.size();
    }

    public boolean renderedBoton(int estado) {
        return estado == 0;
    }

    public String agregarFactura(int idEmpresa, int idCliente) throws Exception {
        Empresacliente empresacliente = servicioExtra.listEmpresaCliente(idEmpresa, idCliente);
        List<Empresacliente> empresaClientes= new ArrayList();
        empresaClientes.add(empresacliente);
        Usuario usuario = new Usuario();
        usuario.setId(1);
        if (carrito != null) {
            Item item1 = carrito.get(1);
            factura.setEstado(0);
            factura.setItems(carrito);
            factura.setSubtotal(subtotal);
            factura.setTotalImpuestoVenta(impuestoVenta);
            factura.setTotal(total);
            factura.setUsuario(servicioUsuario.read(usuario));
            factura.setEmpresaclientes(empresaClientes);
            servicioFactura.insert(factura);
        }else
            return "El carrito esta vacio";
        return "facturaCRUD.xhtml";
    }
}
