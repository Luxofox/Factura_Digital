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
import entidades.Plan;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import servicios.ServicioEmpresacliente;
import servicios.ServicioEmpresausuario;
import servicios.ServicioPlan;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

@ManagedBean(name = "controllerEmpresa")
@ViewScoped
public class ControllerEmpresa implements Serializable {

    ServicioEmpresa servicioEmpresa = new ServicioEmpresa();
    ServicioEmpresausuario sEU = new ServicioEmpresausuario();
    ServicioEmpresacliente sEC = new ServicioEmpresacliente();
    Empresa empresa = new Empresa();
    Empresausuario eU = new Empresausuario();
    Empresacliente eC = new Empresacliente();
    List<Empresa> listCompany;
    List<Plan> listPlan;
    ServicioPlan servicePlan = new ServicioPlan();
    Plan plan = new Plan();
    Part file;

    public void insertarEmpresa() throws Exception {
        try {
            plan = servicePlan.read(plan);
            
            this.empresa.setPlan(plan);
            this.empresa.setCantidadRestanteFacturas(plan.getCantidad());

            if (partToByteArray() != null) {
                this.empresa.setLogo(partToByteArray());
            }
            
            servicioEmpresa.insert(this.empresa);
            empresa = new Empresa();
            file = null;
            plan = new Plan();
        } catch (Exception e) {
            System.out.println("Error al insertar.");
            System.out.println(e);
        }
    }

    private byte[] partToByteArray() {
        byte[] logo;
        try {
            InputStream input = file.getInputStream();
            logo = IOUtils.toByteArray(input);
            return logo;
        } catch (Exception e) {
            System.out.println("El logo esta vacio o no a sido seleccionado.");
            System.out.println(e);
            return null;
        }
    }

    public void modificarEmpresa(int companyId, String companyName, int companyCedula, String companyPersona,
            int companyTelefono, String companyEmail, int companyConsecutivo, String companyEstado, int companyPlanId) throws Exception {
        try {
            this.plan.setId(companyPlanId);

            plan = new Plan();
            empresa = new Empresa();
            file = null;
            
            this.empresa.setId(companyId);
            this.plan.setId(companyPlanId);

            plan = servicePlan.read(plan);
            empresa = servicioEmpresa.read(empresa);

            if (this.empresa.getPlan().getId() == this.plan.getId()) {
                System.out.println("Same plan.");
            } else {
                this.empresa.setCantidadRestanteFacturas(this.empresa.getCantidadRestanteFacturas() + this.plan.getCantidad());
            }

            this.empresa.setNombre(companyName);
            this.empresa.setCedula(companyCedula);
            this.empresa.setTipo_Persona(companyPersona);
            this.empresa.setTelefono(companyTelefono);
            this.empresa.setCorreo(companyEmail);
            this.empresa.setConsecutivo_Ini(companyConsecutivo);
            this.empresa.setEstado(companyEstado);
            this.empresa.setPlan(plan);

            if (partToByteArray() != null) {
                this.empresa.setLogo(partToByteArray());
            }

            servicioEmpresa.modify(empresa);

            empresa = new Empresa();
            file = null;
            plan = new Plan();
            
            System.out.println("Modificado.");
        } catch (Exception e) {
            System.out.println("Error al modificar.");
            System.out.println(e);
        }
    }

    public void linkCompany(Empresa company) throws Exception {
        this.empresa = company;
    }

    public Empresa leerEmpresa() throws Exception {
        return servicioEmpresa.read(empresa);
    }

    public void eliminarEmpresa(int companyId) throws Exception {
        boolean result = true;
        Empresa company = new Empresa();
        System.out.println(companyId);
        company.setId(companyId);
        if (companyId != 0) {
            if (servicioEmpresa.list(company) != null) {
                company = servicioEmpresa.read(company);
                for (Empresausuario em : sEU.list(eU)) {
                    if (em.getEmpresa().getId() == company.getId()) {
                        result = false;
                        System.out.println("La empresa tiene datos ligados.");
                    }
                }
                for (Empresacliente ec : sEC.list(eC)) {
                    if (ec.getEmpresa().getId() == company.getId()) {
                        result = false;
                        System.out.println("La empresa tiene datos ligados.");
                    }
                }
                if (result = true) {
                    company.setEstado("I");
                    servicioEmpresa.modify(company);
                    System.out.println("Eliminado.");
                    empresa = new Empresa();
                    file = null;
                    plan = new Plan();
                }
            } else {
                System.out.println("La empresa no existe.");
            }
        } else {
            System.out.println("No ha seleccionado ninguna empresa.");
        }
    }

    @PostConstruct
    private void charge() {
        try {
            this.listCompany = this.servicioEmpresa.list(this.empresa);
            this.listPlan = this.servicePlan.list(this.plan);
        } catch (Exception e) {
            System.out.println("Error al cargar los datos.");
        }
    }

    public List<Empresa> getListCompany() {
        return listCompany;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Plan> getListPlan() {
        return listPlan;
    }

    public void setListPlan(List<Plan> listPlan) {
        this.listPlan = listPlan;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
}