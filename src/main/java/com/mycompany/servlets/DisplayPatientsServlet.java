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
 * Servlet responsible for displaying list of patients.
 *
 * @author Karol Kadlubowski
 * @version 2.0
 */
@WebServlet(name = "DisplayPatientsServlet", urlPatterns = {"/DisplayPatientsServlet"})
public class DisplayPatientsServlet extends HttpServlet {

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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DisplayPatientsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>List of patients</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Pesel</th>");
            out.println("</tr>");
            try {


                DatabaseService databaseService = DatabaseService.getInstance();
                for (Patient patient : databaseService.getPatients()
                ) {
                    out.println("<tr>");
                    out.println("<th>" + patient.getId() + "</th>");
                    out.println("<th>" + patient.getName() + "</th>");
                    out.println("<th>" + patient.getPesel() + "</th>");
                    //out.println("<td><button>Delete</button></td>");
                    out.println("<td><form action=\"DeletePatientResultServlet\" method=\"POST\">\n" +
                            "        <p><input type=\"submit\" name=" + patient.getPesel() + " value=\"Delete\" /></p>\n" +
                            "    </form></td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
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
