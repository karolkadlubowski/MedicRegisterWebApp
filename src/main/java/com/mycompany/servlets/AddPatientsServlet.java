/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.servlets;

import com.mycompany.model.DatabaseService;
import com.mycompany.model.database.domain.Patient;
import com.mycompany.model.exceptions.DatabaseException;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for displaying patient adding result.
 *
 * @author Karol Kadlubowski
 * @version 2.0
 */
@WebServlet(name = "AddPatientsServlet", urlPatterns = {"/AddPatientsServlet"})
public class AddPatientsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            try {
                DatabaseService databaseService = DatabaseService.getInstance();
                String name = request.getParameter("patientName");
                Long pesel;
                try {
                    pesel = Long.parseLong(request.getParameter("patientPesel"));
                } catch (Exception e) {
                    pesel = 0L;
                }


                if (databaseService.insertPatient(new Patient(name, pesel))) {
                    Cookie cookiePatientPesel = new Cookie("patientPeselCookie", String.valueOf(pesel));
                    response.addCookie(cookiePatientPesel);
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet AddPatientsServlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<p><h1>Patient added </h1></p>");
                    out.println("<p>Name: " + name + "</p>");
                    out.println("<p>Pesel: " + pesel + "</p>");
                    out.println("<h1></h1>");
                    out.println("</body>");
                    out.println("</html>");
                } else {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet AddPatientsServlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<p><h1>Patient not added </h1></p>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }catch (DatabaseException e){
                request.getSession().setAttribute("errorDescription",e.getMessage());
                response.sendRedirect("/RegisterWebApp_war/ErrorServlet");

            }

        }
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


}
