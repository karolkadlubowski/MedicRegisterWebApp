package com.mycompany.servlets;

import com.mycompany.model.DatabaseService;
import com.mycompany.model.database.domain.Appointment;
import com.mycompany.model.database.domain.Patient;
import com.mycompany.model.exceptions.NoPatientException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet responsible for displaying list of certain patient's appointments.
 *
 * @author Karol Kadlubowski
 * @version 2.0
 */
@WebServlet(name = "DisplayPatientAppointmentsServlet", urlPatterns = {"/DisplayPatientAppointmentsServlet"})
public class DisplayPatientAppointmentsServlet extends HttpServlet {



    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Patient patientData = null;
        Long pesel;
        try {
            pesel = Long.parseLong(request.getParameter("patientPeselToDisplayAppointments"));
        }catch (Exception e){
            pesel = 0L;
        }
        try{
            DatabaseService databaseService = DatabaseService.getInstance();
            patientData = databaseService.getPatientByPesel(pesel);
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet DisplayPatientAppointmentsServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>"+patientData.getName()+"'s list of appointments</h1>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>Pesel</th>");
                out.println("<th>Date</th>");
                out.println("<th>Time</th>");
                out.println("</tr>");

                for (Appointment appointment: databaseService.getPatientAppointments(patientData.getPesel())
                ) {
                    out.println("<tr>");
                    out.println("<th>"+appointment.getId()+"</th>");
                    out.println("<th>"+appointment.getPatientPesel()+"</th>");
                    out.println("<th>"+appointment.getDate()+"</th>");
                    out.println("<th>"+appointment.getTime()+"</th>");
                    //out.println("<td><button>Delete</button></td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</body>");
                out.println("</html>");
            }
        }catch (Exception e){
            request.getSession().setAttribute("errorDescription",e.getMessage());
            response.sendRedirect("/RegisterWebApp_war/ErrorServlet");
        }
        //Patient patientData = databaseService.getPatients().stream().filter(patient -> patient.getPesel() == pesel).collect();


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
