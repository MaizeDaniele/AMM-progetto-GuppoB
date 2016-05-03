/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiController;

import amm.progetto.classiModel.Oggetto;
import amm.progetto.classiModel.Verifica;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Venditore", urlPatterns = {"/Venditore.html"})
public class Venditore extends HttpServlet {

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

        //Dichiaro le instance variables
        HttpSession sessione;
        Verifica controller = new Verifica();

        /*
        Variabile di controllo errore:
        0 Nessun errore
        1 Alcuni errori nei campi 
         */
        int sentinel = 0;

        //VERIFICO CHE UNA SESSIONE SIA ATTIVA
        if ((sessione = request.getSession(false)) != null) {
            switch (request.getParameter("visualizzazione")) {
                //CLIENTE HA CONFERMATO L'INSERIMENTO DEI DATI DEL FORM
                case ("logout"): {
                    //CHIUDO LA SESSIONE IN CORSO
                    sessione.invalidate();

                    //RITORNO ALLA PAGINA INIZIALE
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Descrizione.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                
                case ("principale"): {
                    //DEVO AGGIORNARE IL DATABASE
                    //per ora ritorno semplicemente al form
                    sentinel = 10;
                    request.setAttribute("sentinel", sentinel);
                    
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                    dispatcher.forward(request, response);
                    break;
                }

                //CLIENTE HA COMPILATO IL FORM, DEVO MOSTRARE IL RIEPILOGO
                case ("riepilogo"): {
                    //VERIFICO CHE IL TASTO SIA STATO PREMUTO DOPO AVER COMPILATO ALMENO UN CAMPO
                    if (request.getParameter("Submit") != null) {

                        //Recupero tutti i parametri dalla request
                        String nomeOggetto = request.getParameter("nome_oggetto");
                        String urlImmagineOggetto = request.getParameter("url_immagine");
                        String descrizioneOggetto = request.getParameter("descrizione");
                        String prezzoOggetto = request.getParameter("prezzo");
                        String pezziOggetto = request.getParameter("pezzi");
                        String idOggetto = request.getParameter("id_oggetto");

                        

                        //Verifico che tutti i campi del form siano stati compilati
                        if (controller.verificaInserimentoDati(nomeOggetto, urlImmagineOggetto,
                                descrizioneOggetto, prezzoOggetto, pezziOggetto)) {

                            //VERIFICO CHE IL PREZZO SIA MAGGIORE DI ZERO
                            if (!controller.verificaPrezzoVenditore(Long.parseLong(prezzoOggetto))) {
                                //Il prezzo inserito è minore di zero, andrà printato un mex nella pagina venditore
                                sentinel = 1;

                            }

                            //VERIFICO CHE IL NUMERO DI PEZZI SIA MAGGIORE DI ZERO
                            if (!controller.verificaQuantitàVenditore(Integer.parseInt(pezziOggetto))) {
                                //Il numero di pezzi è minore di 0
                                if (sentinel == 1) {
                                    //vanno printati entrambi i messaggi
                                    sentinel = 2;

                                    request.setAttribute("sentinel", sentinel);
                                    RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                                    dispatcher.forward(request, response);
                                }
                                //va printato solo il messaggio relativo alla quantità
                                sentinel = 3;

                                request.setAttribute("sentinel", sentinel);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                                dispatcher.forward(request, response);
                            }

                            //TUTTI I DATI SONO CORRETTI
                            Oggetto o = new Oggetto(nomeOggetto, descrizioneOggetto, urlImmagineOggetto, Integer.parseInt(pezziOggetto),
                                Long.parseLong(prezzoOggetto), idOggetto);
                            //A seconda del valore di sentinel verrà mostrata la pagina di riepilogo dell'oggetto 
                            //oppure la pagina venditore con gli errori
                            sentinel = 0;
                            request.setAttribute("sentinel", sentinel);
                            request.setAttribute("Oggetto", o);

                            RequestDispatcher dispatcher = request.getRequestDispatcher("ConfermaOggetto.jsp");
                            dispatcher.forward(request, response);

                        }

                        //MESSAGGIO ERRORE: tutti i campi devono essere compilati
                        sentinel = 6;
                        request.setAttribute("sentinel", sentinel);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;
                }
                
                case("erroreAutenticazione"):{
                     //L'UTENTE VUOLE ACCEDERE ALLA PAGINA VENDITORE PUR ESSENDO LOGGATO COME CLIENTE
                    sentinel = 11;
                    request.setAttribute("sentinel", sentinel);
                    
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Esito.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                
                
               


            }
        }

        //La sessione non è più attiva, si ritorna alla pagina login con un messaggio di errore
        RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
        dispatcher.forward(request, response);
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
