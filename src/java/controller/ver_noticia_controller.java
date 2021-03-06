/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Conexion.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.categoria;
import modelo.noticias;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author equipo_2
 */
@WebServlet(name = "ver_noticia_controller", urlPatterns = {"/ver_noticia_controller"})
public class ver_noticia_controller extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        Conexion con = new Conexion();
        try (PrintWriter out = response.getWriter()) {
            String html = "";
            String evento = request.getParameter("evento");
            switch(evento){ 
                case "login": html=login(request,response,con); break;
                case "cargar_menu": html=cargar_menu(request,response,con); break;
                case "portadas": html=portadas(request,response,con); break;
                case "cargar_noticia": html=cargar_noticia(request,response,con); break;
            }
            con.Close();            
            out.write(html);       
        } catch (JSONException ex) {
            Logger.getLogger(ver_noticia_controller.class.getName()).log(Level.SEVERE, null, ex);
            con.Close();
        } catch (SQLException ex) {
            Logger.getLogger(ver_noticia_controller.class.getName()).log(Level.SEVERE, null, ex);
            con.Close();
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    private String login(HttpServletRequest request, HttpServletResponse response, Conexion con) throws JSONException {
        request.getSession().setAttribute("usr", null);
        JSONObject obj = new JSONObject();
        obj.put("ingresar", true);
        return obj.toString();
    }
    
    private String cargar_menu(HttpServletRequest request, HttpServletResponse response, Conexion con) throws JSONException, SQLException, IOException {                
        return new categoria(con).menu().toString();        
    }

    private String portadas(HttpServletRequest request, HttpServletResponse response, Conexion con) throws SQLException, JSONException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        return new noticias(con).fotos(id).toString();
    }

    private String cargar_noticia(HttpServletRequest request, HttpServletResponse response, Conexion con) throws SQLException, JSONException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new noticias(con).suma_vistos(id);
        return new noticias(con).buscar(id).toString();
    }
    
    

}
