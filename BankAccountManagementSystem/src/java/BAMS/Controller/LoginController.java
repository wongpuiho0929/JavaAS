/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BAMS.Controller;

import BAMS.DAO.*;
import BAMS.Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author User
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    private CustomerDAO customerDB;

    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");

        //set Database to application bean
        if (this.getServletContext().getAttribute("accountDB") == null) {
            DAO.setting(dbUrl, dbUser, dbPassword);
            this.getServletContext().setAttribute("accountDB", (AccountDAO) DAO.getDAO("Account"));
        }
        if (this.getServletContext().getAttribute("bankDB") == null) {
            this.getServletContext().setAttribute("bankDB", (BankDAO) DAO.getDAO("Bank"));
        }
        if (this.getServletContext().getAttribute("customerDB") == null) {
            this.getServletContext().setAttribute("customerDB", (CustomerDAO) DAO.getDAO("Customer"));
        }
        if (this.getServletContext().getAttribute("exchangeRateDB") == null) {
            this.getServletContext().setAttribute("exchangeRateDB", (ExchangeRateDAO) DAO.getDAO("ExchangeRate"));
        }
        if (this.getServletContext().getAttribute("historyDB") == null) {
            this.getServletContext().setAttribute("historyDB", (HistoryDAO) DAO.getDAO("History"));
        }

        customerDB = (CustomerDAO) this.getServletContext().getAttribute("customerDB");

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        if (doAuthenticate(request, response)) {
            String username = request.getParameter("username");
            HttpSession session = request.getSession(true);
            session.setAttribute("customer", customerDB.findByUsername(username));
            loginSuccess(out);
        } else {
            loginFail(out);
        }
    }

    private void loginSuccess(PrintWriter out) {
        out.println("{\"login\":true}");
    }

    private void loginFail(PrintWriter out) {
        out.println("{\"login\":false}");
    }

    private boolean doAuthenticate(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean success = false;
//        System.out.println(password + ',' + username);
//        System.out.println(customerDB.getCount());
        Customer c;
        if (customerDB.isUsernameExist(username)) {
            c = customerDB.findByUsername(username);
            success = c.checkPassword(password);
        }

        return success;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
