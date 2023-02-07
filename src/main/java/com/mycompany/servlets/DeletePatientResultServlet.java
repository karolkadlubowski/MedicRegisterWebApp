package com.mycompany.servlets;

import com.mycompany.model.DatabaseService;
import com.mycompany.model.database.domain.Patient;
import com.mycompany.model.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet responsible for displaying patient deleting result.
 *
 * @author Karol Kadlubowski
 * @version 2.0
 */
@WebServlet(name = "DeletePatientResultServlet", urlPatterns = {"/DeletePatientResultServlet"})
public class DeletePatientResultServlet extends HttpServlet {



    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try{


            DatabaseService databaseService = DatabaseService.getInstance();
            var patients = databaseService.getPatients();
            for(Patient patient : patients){
                if(request.getParameter(String.valueOf(patient.getPesel()))!=null){
                    databaseService.deletePatient(patient.getPesel());
                }
            }
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DeletePatientResultServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Patient deleted</h1>");
            out.println("<form action=\"index.html\" method=\"GET\">\n" +
                    "        <p><input type=\"submit\" value=\"Go back\" /></p>\n" +
                    "    </form>");
            out.println("</body>");
            out.println("</html>");
            }catch (DatabaseException e){
                request.getSession().setAttribute("errorDescription",e.getMessage());
                response.sendRedirect("/RegisterWebApp_war/ErrorServlet");
            }
        }
    }

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
