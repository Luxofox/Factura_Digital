/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController {

    private String username;
    private String password;
    private List<UsuarioData> listaUsuarios = new ArrayList<UsuarioData>();

    @PostConstruct
    public void inicializar() {
        UsuarioData  usuarioData =  new UsuarioData("jaime", "Jaime Liu", "Vive en San Jose");
        listaUsuarios.add(usuarioData);
        
        usuarioData =  new UsuarioData("claudio", "Claudio Cordero", "Vive en Coronado");
        listaUsuarios.add(usuarioData);
        
        usuarioData =  new UsuarioData("luis", "Luis Estanislao", "Nacio en España");
        listaUsuarios.add(usuarioData);
        
        usuarioData =  new UsuarioData("federico", "Federico Melendez", "Nacio en Alajuela y vive en Alajuela");
        listaUsuarios.add(usuarioData);
    
    }
    
    public String ingresar() {
       // System.out.println("EL valor de usuario es: " + this.getUsername());
       // System.out.println("EL valor de Contaseña es: " + this.getPassword());

        if (!this.getUsername().equalsIgnoreCase("jaime")) {
            FacesContext.getCurrentInstance().
                    addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Usuario Incorrecto", "No esta registradio"));
            return "index";
        } else {
            return "homePage.xhtml";
        }
    }
    public LoginController() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String contrasena) {
        this.password = contrasena;
    }

    public List<UsuarioData> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UsuarioData> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    
}
