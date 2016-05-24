/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiController;

import amm.progetto.classiModel.FactoryOggetto;
import amm.progetto.classiModel.FactoryUtente;
import amm.progetto.classiModel.Oggetto;
import amm.progetto.classiModel.Verifica;
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

                    //L'utente ha confermato l'inserimento dei dati, devo aggiornare il database
                    String nomeOggetto = request.getParameter("nome_oggetto");
                    String urlImmagineOggetto = request.getParameter("url_immagine");
                    String descrizioneOggetto = request.getParameter("descrizione");
                    long prezzoOggetto = Long.parseLong(request.getParameter("prezzo"));
                    int pezziOggetto = Integer.parseInt(request.getParameter("pezzi"));

                    int idVenditore = Integer.parseInt(request.getParameter("venditore_id"));

                    Oggetto o;
                    
                    String prova = request.getParameter("nuovoOggetto");
                    
                    if(prova == null ){
                        prova = "";
                    }

                    if (prova.equals("no")) {
                        //STO MODIFICANDO UN OGGETTO ESISTENTE

                        int id = Integer.parseInt(request.getParameter("idOggetto"));

                        o = new Oggetto(nomeOggetto, descrizioneOggetto, urlImmagineOggetto,
                                pezziOggetto, prezzoOggetto, id, idVenditore);

                        sentinel = FactoryOggetto.getInstance().modificaOgg(o);
                        request.setAttribute("sentinel", sentinel);

                        //Ricarico la listaOggetti in quanto ho aggiunto un elemento
                        sessione.setAttribute("listaOggetti", FactoryOggetto.getInstance().getListaOggetto(0));

                        //Avendo completato l'inserimento ritorno alla pagina principale
                        RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                        dispatcher.forward(request, response);
                        break;
                    } else {

                        o = new Oggetto(nomeOggetto, descrizioneOggetto, urlImmagineOggetto,
                                pezziOggetto, prezzoOggetto, 0, idVenditore);

                        //STO INSERENDO UN NUOVO OGGETTO
                        sentinel = FactoryOggetto.getInstance().aggiungiOggetto(o);

                        request.setAttribute("sentinel", sentinel);

                        //Ricarico la listaOggetti in quanto ho aggiunto un elemento
                        sessione.setAttribute("listaOggetti", FactoryOggetto.getInstance().getListaOggetto(0));

                        //Avendo completato l'inserimento ritorno alla pagina principale
                        RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                        dispatcher.forward(request, response);
                        break;
                    }
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
                        String id = request.getParameter("idOggetto");
                        String idVenditore = request.getParameter("venditore_id");

                        Oggetto o ;
                        
                        
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
                            
                            String prova =  request.getParameter("nuovoOggetto");
                            
                            if(prova == null){
                                prova = "";
                            }
                            
                            if ( prova.equals("no")) {
                                //Sto modificando un oggetto esistente
                                request.setAttribute("nuovoOggetto", "no");
                                //TUTTI I DATI SONO CORRETTI
                                o = new Oggetto(nomeOggetto, descrizioneOggetto, urlImmagineOggetto, Integer.parseInt(pezziOggetto),
                                        Long.parseLong(prezzoOggetto), Integer.parseInt(id), Integer.parseInt(idVenditore));
                            } else {
                                //TUTTI I DATI SONO CORRETTI
                                o = new Oggetto(nomeOggetto, descrizioneOggetto, urlImmagineOggetto, Integer.parseInt(pezziOggetto),
                                        Long.parseLong(prezzoOggetto), 0, Integer.parseInt(idVenditore));
                            }
                            
                            //A seconda del valore di sentinel verrà mostrata la pagina di riepilogo dell'oggetto 
                            //oppure la pagina venditore con gli errori
                            sentinel = 0;
                            request.setAttribute("sentinel", sentinel);

                            request.setAttribute("Oggetto", o);

                            RequestDispatcher dispatcher = request.getRequestDispatcher("ConfermaOggetto.jsp");
                            dispatcher.forward(request, response);
                            break;

                        }

                        //MESSAGGIO ERRORE: tutti i campi devono essere compilati
                        sentinel = 6;
                        request.setAttribute("sentinel", sentinel);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;
                }

                case ("erroreAutenticazione"): {
                    //L'UTENTE VUOLE ACCEDERE ALLA PAGINA VENDITORE PUR ESSENDO LOGGATO COME CLIENTE
                    sentinel = 11;
                    request.setAttribute("sentinel", sentinel);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("Esito.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                case ("riepilogoOggetti"): {
                    //IL VENDITORE VUOLE VISUALIZZARE IL RIEPILOGO DEI SUOI OGGETTI IN VENDITA

                    //Devo recuperare la lista dei suoi oggetti, mi assicuro che la variabile di sessione non sia già riempita con 
                    //oggetti                    
                    ArrayList<Oggetto> listaoggetti = (ArrayList<Oggetto>) sessione.getAttribute("listaOggetti");

                    //se l'arraylist contiene già degli elementi lo pulisce
                    if (!listaoggetti.isEmpty()) {
                        listaoggetti.clear();
                    }

                    //Riempio la lista oggetti con i prodotti del venditore attualmente loggato
                    listaoggetti = FactoryOggetto.getInstance().getListaOggettoVenditore((int) sessione.getAttribute("id"));

                    if (!listaoggetti.isEmpty()) {

                        sessione.setAttribute("listaOggetti", listaoggetti);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("RiepilogoOggetti.jsp");
                        dispatcher.forward(request, response);
                        break;
                    }

                    //Non ho ottenuto nessuna listaoggetti, ricarico la pagina venditore con un messaggio di errore
                    sentinel = 15;
                    request.setAttribute("sentinel", sentinel);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                case ("modificaOggetto"): {
                    //IL VENDITORE VUOLE MODIFICARE UN OGGETTO
                    Oggetto o = FactoryOggetto.getInstance().getOggettoByID(Integer.parseInt(request.getParameter("idOggetto")));
                    
                    String azione = "modifica";
                    
                    //Imposto il valore di visualizzazione per modificare la pagina principale venditore
                    request.setAttribute("azione", azione);

                    //Carico l'oggetto sulla request in modo tale da poter recuperare i parametri da visualizzare
                    request.setAttribute("Oggetto", o);

                    //ricarico la pagina principale per la modifca di un oggetto
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                    dispatcher.forward(request, response);
                    break;

                }
                case ("eliminaOggetto"): {
                    //IL VENDITORE VUOLE ELIMINARE L'OGGETTO SELEZIONATO
                    FactoryOggetto.getInstance().eliminaOgg(Integer.parseInt(request.getParameter("idOggetto")));

                    /*
                    //recupero la listaoggetti aggiornata
                    ArrayList<Oggetto> listaoggetti = FactoryOggetto.getInstance().getListaOggettoVenditore((int)sessione.getAttribute("id"));
                    
                    //carico lista oggetti aggiornata sulla sessione
                    sessione.setAttribute("listaOggetti", listaoggetti);
                     */
                    //Imposto il valore di sentinel per avvisare il venditore dell'avvenuta eliminazione dell'oggetto
                    sentinel = 16;
                    request.setAttribute("sentinel", sentinel);

                    //Ritorno alla pagina principale del venditore
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
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
