/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.Controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class Checking{
    public static boolean isLogin(HttpServletRequest request, HttpServletResponse response){
        return request.getSession().getAttribute("customer") != null;
    }
}
