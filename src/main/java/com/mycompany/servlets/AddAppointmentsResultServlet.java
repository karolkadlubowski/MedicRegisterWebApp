package com.mycompany.servlets;

import com.mycompany.model.DatabaseService;
import com.mycompany.model.database.domain.Appointment;
import com.mycompany.model.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;


/**
 * Servlet responsible for displaying result of appointment inserting to the database.
 *
 * @author Karol Kadlubowski
 * @version 2.0
 */
@WebServlet(name = "AddAppointmentsResultServlet",urlPatterns = {"/AddAppointmentsResultServlet"})
public class AddAppointmentsResultServlet extends HttpServlet {




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

        try{

        try (PrintWriter out = response.getWriter()) {

            DatabaseService databaseService = DatabaseService.getInstance();
            boolean isAddingCompleted = false;
            try {
                String pesel = request.getParameter("patientPeselPickerAddAppointment");
                String date = request.getParameter("appointmentDate");
                String time = request.getParameter("timePickerAddAppointment");
                isAddingCompleted = databaseService.insertAppointment(pesel, date, time);
            } catch (Exception e) {
                request.getSession().setAttribute("errorDescription", e.getMessage());
                response.sendRedirect("/RegisterWebApp_war/ErrorServlet");
            }

            //response.sendRedirect("/RegisterWebApp_war/ErrorServlet");
            //getServletContext().getRequestDispatcher("/ErrorServlet").forward(request, response);

            if (isAddingCompleted) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet AddAppointmentsResultServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Adding completed successfully</h1>");

            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet AddAppointmentsResultServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Added not completed</h1>");
                out.println("Please pick another term");

            }

            out.println("<form action=\"index.html\" method=\"GET\">\n" +
                    "        <p><input type=\"submit\" value=\"Go back\" /></p>\n" +
                    "    </form>");

            out.println("</body>");
            out.println("</html>");
        }
        catch (DatabaseException e){
            request.getSession().setAttribute("errorDescription",e.getMessage());
            response.sendRedirect("/RegisterWebApp_war/ErrorServlet");
        }


        }catch (Exception e){
            request.getSession().setAttribute("errorDescription",e.getMessage());
            response.sendRedirect("/RegisterWebApp_war/ErrorServlet");
            //getServletContext().getRequestDispatcher("/ErrorServlet").forward(request, response);
           // request.getSession().setAttribute("errorDescription",e.getMessage());
            //response.sendRedirect("/RegisterWebApp_war/ErrorServlet");
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
