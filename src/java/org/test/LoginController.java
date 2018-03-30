/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import servicios.ServiceLogin;


@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController {

    ServiceLogin servicioLogin = new ServiceLogin();
    private String username;
    private String password;
    

    
    public String ingresar() throws Exception {


        if (!servicioLogin.login(username, password)) {
            return "index";
        } else {
            return "homePage";
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

    
}
