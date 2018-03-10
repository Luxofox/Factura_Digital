/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Factura;
import java.util.ArrayList;
import java.util.List;
import servicios.ServicioFactura;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "controllerFactura")
@RequestScoped
public class ControllerFactura {

    ServicioFactura servicioFactura = new ServicioFactura();
    Factura factura = new Factura();

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
        List<Factura> listFactura = new ArrayList<>();
        for (Factura f : servicioFactura.list(factura)) {
            if (f.getUsuario().getId() == id) {
                listFactura.add(f);
            }
        }
        return listFactura;
    }
}
