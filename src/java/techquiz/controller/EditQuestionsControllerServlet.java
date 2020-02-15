/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package techquiz.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import techquiz.dao.UserDAO;
import techquiz.dto.UserDetails;


public class EditQuestionsControllerServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setDateHeader("Expires", -1);
        try (PrintWriter out = response.getWriter()) {
            RequestDispatcher rd = null;
            HttpSession session = request.getSession();
            try 
            {
                String username = (String) session.getAttribute("username");
                String usertype = (String) session.getAttribute("usertype");
                System.out.println("userid " + username);
                if (username == null || usertype == null) {
                    session.invalidate();
                    System.out.println("failed redirect");
                    response.sendRedirect("loginpage.html");
                    return;
                }

                if (usertype.equalsIgnoreCase("student")) {
                    session.invalidate();
                    response.sendRedirect("accessdenied.html");
                    return;
                } 
                else if (usertype.equalsIgnoreCase("teacher")) {
                    String queryof = (String) request.getParameter("code");
                    if (queryof.equalsIgnoreCase("editexam")) {
                        String examid = (String) request.getParameter("data");

                        rd = request.getRequestDispatcher("userdetails.jsp");
                    }
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("exception", e);
                rd = request.getRequestDispatcher("showexception.jsp");
            } 
            finally {
                rd.forward(request, response);
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
            protected void doGet
            (HttpServletRequest request, HttpServletResponse response)
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
            protected void doPost
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                processRequest(request, response);
            }

            /**
             * Returns a short description of the servlet.
             *
             * @return a String containing servlet description
             */
            @Override
            public String getServletInfo
            
            
                () {
        return "Short description";
            }// </editor-fold>

        }
