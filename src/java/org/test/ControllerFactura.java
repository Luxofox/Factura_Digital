/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Cliente;
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
import servicios.ServicioEmpresa;
import servicios.ServicioItem;
import servicios.ServicioProducto;
import servicios.ServicioUsuario;

@ManagedBean(name = "controllerFactura")
@SessionScoped
public class ControllerFactura implements Serializable {

    ServicioFactura servicioFactura = new ServicioFactura();
    ServiceLogin servicioExtra = new ServiceLogin();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    ServicioItem servicioItem = new ServicioItem();
    ServicioEmpresa servicioEmpresa = new ServicioEmpresa();
    Factura factura = new Factura();
    Item item = new Item();
    List<Item> carrito = new ArrayList();
    List<Producto> listProducto;
    List<Cliente> listCliente;
    Producto producto = new Producto();
    Empresa empresa = new Empresa();
    Cliente cliente = new Cliente();
    double subtotal;
    double total;
    double impuestoVenta;
    double descuento;
    int cantidadProducto;
    int reload = 1;

    public ControllerFactura() throws Exception {
        this.listProducto = servicioExtra.listProductoEmpresa(empresa.getId());
    }
    
    public boolean reload(){
        if(reload==1){
            reload=0;
            return true;
        }else
            return false;
    }
    public List<Cliente> getListCliente() {
        return listCliente;
    }

    public void setListCliente(List<Cliente> listCliente) {
        this.listCliente = listCliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public Producto getProducto() {
        return this.producto;
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
        
        listProducto = servicioExtra.listProductoEmpresa(Integer.parseInt(e.getNewValue().toString()));
    }

    public void addListaCarrito() throws Exception {
        producto = servicioProducto.read(producto);
        item.setCantidad(getCantidadProducto());
        item.setProducto(servicioProducto.read(producto));
        item.setDescuento(getDescuento());
        item.setProducto_Empresa_idEmpresa(empresa.getId());
        carrito.add(item);
        setImpuestoVenta(item.getProducto().getImpuestoVenta());
        setSubtotal(subtotal + (item.getProducto().getPrecio() * item.getCantidad())+(item.getCantidad() * ((item.getProducto().getPrecio() * getImpuestoVenta()) / 100)));
        setTotal(total + subtotal - (subtotal * (item.getDescuento() / 100)));
        producto = new Producto();
        setCantidadProducto(0);
        setDescuento(0);
        item = new Item();
    }

    public void deleteListaCarrito(Item item) throws Exception {
        setTotal(total - (subtotal - (subtotal * (item.getDescuento() / 100))));
        impuestoVenta = (item.getCantidad() * ((item.getProducto().getPrecio() * item.getProducto().getImpuestoVenta()) / 100));
        setSubtotal(subtotal - ((item.getProducto().getPrecio() * item.getCantidad())+impuestoVenta));
        carrito.remove(item);
        setImpuestoVenta(0);
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

    public void agregarFactura() throws Exception {
        Empresacliente empresacliente = servicioExtra.listEmpresaCliente(empresa.getId(), cliente.getId());
        Usuario usuario = new Usuario();
        usuario.setId(1);
        if (carrito != null) {
            factura.setEstado(0);
            factura.setItems(carrito);
            factura.setSubtotal(subtotal);
            factura.setTotalImpuestoVenta(impuestoVenta);
            factura.setTotal(total);
            factura.setUsuario(servicioUsuario.read(usuario));
            factura.setEmpresacliente(empresacliente);
            factura.setConsecutivoFactura(empresacliente.getEmpresa().getConsecutivo_Ini());
            servicioFactura.insert(factura);
            empresa = servicioEmpresa.read(empresa);
            empresa.setCantidadRestanteFacturas(empresa.getCantidadRestanteFacturas()-1);
            empresa.setConsecutivo_Ini(empresa.getConsecutivo_Ini()+1);
            servicioEmpresa.modify(empresa);
            carrito.clear();
            for(Item i:carrito){
                servicioItem.insert(i);
            }
            setSubtotal(0);
            setTotal(0);
            setImpuestoVenta(0);
            setDescuento(0);
        } 
    }
}