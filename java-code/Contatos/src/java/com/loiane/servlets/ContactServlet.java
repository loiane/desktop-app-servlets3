/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loiane.servlets;

import com.google.gson.Gson;
import com.loiane.dao.ContactJpaController;
import com.loiane.entity.Contact;
import com.loiane.entity.ExtGridSerializer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author Loiane Groner
 * http://loiane.com
 */
@WebServlet(name = "ContactServlet", urlPatterns = {"/Contact"})
public class ContactServlet extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private ContactJpaController dao;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        assert emf != null;  //Make sure injection went through correctly.
        EntityManager em = null;
        try {
            em = emf.createEntityManager();

            dao = new ContactJpaController(utx, emf);

            String action = request.getParameter("action");

            //create a new JSON array to send the list of items
            Gson gson = new Gson();
            List<Contact> list = new ArrayList();
            int total = 0;

            //printwriter to send the JSON response back
            PrintWriter out = response.getWriter();
            //set content type
            response.setContentType("text/html");

            if (action.equalsIgnoreCase("read")) {

                String start = request.getParameter("start");
                String limit = request.getParameter("start");
                int maxResults = 0, firstResult = 0;
                if (start != null && !start.equals("")) {
                    firstResult = Integer.parseInt(start);
                }
                if (limit != null && !limit.equals("")) {
                    maxResults = Integer.parseInt(limit);
                }

                list = dao.findContactEntities(maxResults, firstResult);
                total = dao.getContactCount();

            } else if (action.equalsIgnoreCase("create") || 
                    action.equalsIgnoreCase("update") ||
                    action.equalsIgnoreCase("destroy")) {

                String data = request.getParameter("data");
                Contact c = gson.fromJson(data, Contact.class);
                total = 1;
                list.add(c);
                
                if (action.equalsIgnoreCase("create")) {
                    dao.create(c);
                } else if (action.equalsIgnoreCase("update")) {
                    dao.edit(c);
                }if (action.equalsIgnoreCase("destroy")) {
                    dao.destroy(c.getId());
                }                
            } 

            //convert the JSON object to string and send the response back
            out.println(gson.toJson(new ExtGridSerializer(list, total)));
            out.close();

        } catch (Exception ex) {
            throw new ServletException(ex);
        } finally {
            //close the em to release any resources held up by the persistebce provider
            if (em != null) {
                em.close();
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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