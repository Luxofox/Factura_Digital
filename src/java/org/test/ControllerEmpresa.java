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
import servicios.ServicioEmpresa;
import entidades.Empresa;
import entidades.Empresacliente;
import entidades.Empresausuario;
import java.util.List;
import servicios.ServicioEmpresacliente;
import servicios.ServicioEmpresausuario;import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "controllerEmpresa")
@RequestScoped
public class ControllerEmpresa {

    ServicioEmpresa servicioEmpresa = new ServicioEmpresa();
    ServicioEmpresausuario sEU = new ServicioEmpresausuario();
    ServicioEmpresacliente sEC = new ServicioEmpresacliente();
    Empresa empresa = new Empresa();
    Empresausuario eU = new Empresausuario();
    Empresacliente eC = new Empresacliente();

    public boolean insertarEmpresa(Empresa e) throws Exception {
        if (e != null) {
            return false;
        } else {
            servicioEmpresa.insert(e);
            return true;
        }
    }

    public boolean modificarEmpresa(Empresa e) throws Exception {
        if (e != null) {
            return servicioEmpresa.modify(e) != false;
        } else {
            return false;
        }
    }

    public Empresa leerEmpresa(Empresa e) throws Exception {
        return servicioEmpresa.read(e);
    }

    public boolean eliminarEmpresa(Empresa e) throws Exception {
        if (e != null) {
            if (servicioEmpresa.list(e) != null) {
                for (Empresausuario em : sEU.list(eU)) {
                    if (em.getEmpresa().getId()== e.getId()) {
                        return false;
                    }
                }
                for (Empresacliente ec : sEC.list(eC)) {
                    if (ec.getEmpresa().getId()== e.getId()) {
                        return false;
                    }

                }
                e.setEstado("I");
                servicioEmpresa.modify(e);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public List<Empresa> listaEmpresa() throws Exception{
        return servicioEmpresa.list(empresa);
    }
}
