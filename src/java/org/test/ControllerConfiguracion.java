/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import entidades.Usuario;
import javax.annotation.PostConstruct;
import servicios.ServicioUsuario;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controllerConfig")
@SessionScoped
public class ControllerConfiguracion {
    ServicioUsuario userService = new ServicioUsuario();
    Usuario user = new Usuario();
    
    @PostConstruct
    public void onLoad(){
        //LoginController login = new LoginController();
        //user = login.user;  
    }
    
    public boolean modifyUser() throws Exception {
        boolean result = true;
        try {
            if (Integer.toString(this.user.getId()).equals("")) {
                result = false;
            } else {
                userService.modify(user);
        }
        } catch(Exception ex) {
            System.out.println(ex);
            result = false;
        }
        return result;
    }     

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }  
}
