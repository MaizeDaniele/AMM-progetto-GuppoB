/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiController;

import amm.progetto.classiModel.FactoryOggetto;
import amm.progetto.classiModel.Oggetto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniele Caschili
 */
@WebServlet(name = "Filter", urlPatterns = {"/filter.json"})
public class Filter extends HttpServlet {

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
        //Riceve una richiesta da parte del cliente autenticato
        //Effettua una ricerca sugli oggetti in vendita, cercando ricorrenze del testo salvato nel parametro q, sia nel nome sia nella descrizione
        //Restituisce un oggetto o un elenco di oggetti json con gli stessi attributi visualizzati nella tabella
        
        HttpSession sessione;
        
        if ((sessione = request.getSession(false)) != null) {
            //Verifico che sia stato inoltrato un comando
            String q = request.getParameter("cmd");
            String txt = request.getParameter("text");
        
            if (q != null) {
                //Devo cercare la stringa q sia nel nome che nella descrizione

                //Recupero la lista degli oggetti trovati
                ArrayList<Oggetto> listaOggetto = FactoryOggetto.getInstance().getListaOggettoByString(txt);
                
                //carico nella request, come attributo, la listaOggetti trovata
                request.setAttribute("listaOggetto", listaOggetto);
                
                // Quando si restituisce del json e' importante segnalarlo ed evitare il caching
                response.setContentType("application/json");
                response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
                response.setHeader("Cache-Control", "no-store, no-cache, "
                        + "must-revalidate");
                
                //Passo la request alla jsp per il json
                request.getRequestDispatcher("generaJSONlistaOggetti.jsp").forward(request, response);
                
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
