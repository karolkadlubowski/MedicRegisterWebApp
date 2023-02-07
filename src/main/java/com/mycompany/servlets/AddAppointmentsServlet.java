package com.mycompany.servlets;

import com.mycompany.model.DatabaseService;
import com.mycompany.model.database.domain.Patient;
import com.mycompany.model.exceptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet responsible for adding appointment to the database.
 *
 * @author Karol Kadlubowski
 * @version 2.0
 */
@WebServlet(name = "AddAppointmentsServlet",urlPatterns = {"/AddAppointmentsServlet"})
public class AddAppointmentsServlet extends HttpServlet {

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
            Cookie[] cookies = request.getCookies();
            String cookiePesel=null;
            for( Cookie cookie : cookies)
                if(cookie.getName().equals("patientPeselCookie"))
                    cookiePesel=cookie.getValue();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddAppointmentsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Add appointment</h1>");

            out.println("<form action=\"AddAppointmentsResultServlet\" method=\"GET\">");
            out.println("Pick appointment's date <input type=text size=20 name=appointmentDate>");
            try {


                DatabaseService databaseService = DatabaseService.getInstance();

                var appointmentsTimes = databaseService.initTimes();
                out.println("Pick appointment's time");
                out.println("<select name=\"timePickerAddAppointment\">");
                for (String time : appointmentsTimes) {
                    out.println("<option value=\"" + time + "\">" + time + "</option>");
                }
                out.println("</select><hr>");

                var patientList = databaseService.getPatients();


                out.println("Pick patient's pesel   <select name=\"patientPeselPickerAddAppointment\">");
                for (Patient patient : patientList) {
                    out.println("<option");
                    if (cookiePesel.equals(String.valueOf(patient.getPesel())))
                        out.println("selected");

                    out.println("value=\"" + patient.getPesel() + "\">");
                    out.println(patient.getPesel() + "</option>");
                }
                out.println("</select><br>");
                out.println("<p><input type=\"submit\" value=\"Schedule appointment\" /></p>");
                out.println("</form>");


                //out.println("Pick appointment's time <input type=text size=20 name=appointmentsTime>");


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
