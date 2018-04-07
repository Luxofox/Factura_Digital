/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Producto;
import java.util.List;
import servicios.ServicioProducto;
import servicios.ServiceLogin;
import entidades.Empresa;
import entidades.Empresausuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import servicios.ServicioEmpresa;

@ManagedBean(name = "controllerProducto")
@SessionScoped
public class ControllerProducto {
    ServicioProducto servicioProducto = new ServicioProducto();
    ServiceLogin servicioExtra = new ServiceLogin();  
    ServicioEmpresa servicioEmpresa = new ServicioEmpresa();
    Empresa empresa = new Empresa();  
    Producto producto = new Producto();
    List<Producto> listProduct;
    int reload = 1;

    public ControllerProducto() throws Exception{
        listProduct = servicioExtra.listProductoEmpresa(empresa.getId());
    }
    
    public boolean reload(){
        if(reload==1){
            reload=0;
            return true;
        }else
            return false;
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public void productoSelect(ValueChangeEvent e) throws Exception {        
        listProduct = servicioExtra.listProductoEmpresa(Integer.parseInt(e.getNewValue().toString()));
    }
    
    public List<Empresausuario> listaEmpresaUsuario(int idUsuario) throws Exception {
        List<Empresausuario> listEmpresaUsuario = servicioExtra.listEmpresaUsuario(idUsuario);
        return listEmpresaUsuario;
    }
    
    public void insertarProducto() throws Exception {
        empresa = servicioEmpresa.read(empresa);
        producto.setEmpresa(empresa);
        servicioProducto.insert(producto);
        producto = new Producto();
        empresa = new Empresa();
    }

    public void modificarProducto(int id, String nombre, String descripcion, double precio, double iv) throws Exception {
        this.producto.setId(id);
        this.producto.setNombre(nombre);
        this.producto.setDescripcion(descripcion);
        this.producto.setPrecio(precio);
        this.producto.setImpuestoVenta(iv);
        servicioProducto.modify(producto);
        producto = new Producto();
    }

    public Producto leerProducto(Producto p) throws Exception {
        if (p != null) {
            return servicioProducto.read(p);
        }else 
            return null;
    }
    
    public void eliminarProducto(int id) throws Exception {
        producto.setId(id);
        servicioProducto.delete(producto);
        producto = new Producto();
    }

    public List<Producto> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Producto> listProduct) {
        this.listProduct = listProduct;
    }
}
