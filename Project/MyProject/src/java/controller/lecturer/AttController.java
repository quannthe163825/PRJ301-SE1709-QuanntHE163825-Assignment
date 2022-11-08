/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import dal.SessionDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.assignment.Attandance;
import model.assignment.Lecturer;
import model.assignment.Session;
import model.assignment.Student;

/**
 *
 * @author sonnt
 */
public class AttController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            response.sendRedirect("../loginServlet");
        } else {
            Lecturer lecturer = (Lecturer) session.getAttribute("user");
            int sesid = lecturer.getId();
            SessionDBContext sesDB = new SessionDBContext();
            Session ses = sesDB.get(sesid);
            request.setAttribute("ses", ses);
            request.getRequestDispatcher("../view/lecturer/att.jsp").forward(request, response);
        }

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
        Session ses = new Session();
        HttpSession session = request.getSession();
        Lecturer lecturer = (Lecturer) session.getAttribute("user");
        ses.setId(lecturer.getId());
        String[] stdids = request.getParameterValues("stdid");
        String description = "";

        for (String stdid : stdids) {
            Attandance a = new Attandance();
            Student s = new Student();
            a.setStudent(s);
            if (request.getParameter("description" + stdid) != null) {
                description = request.getParameter("description" + stdid);
            }
            a.setDescription(description);
            a.setPresent(request.getParameter("present" + stdid).equals("present"));
            s.setId(Integer.parseInt(stdid));
            ses.getAtts().add(a);
        }
        SessionDBContext db = new SessionDBContext();
        db.update(ses);
        response.sendRedirect("takeatt");
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
