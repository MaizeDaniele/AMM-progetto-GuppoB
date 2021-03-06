/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiController;

import amm.progetto.classiModel.FactoryOggetto;
import amm.progetto.classiModel.FactoryUtente;
import amm.progetto.classiModel.Oggetto;
import amm.progetto.classiModel.UtenteCliente;
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
@WebServlet(name = "Cliente", urlPatterns = {"/Cliente.html"})
public class Cliente extends HttpServlet {

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

        Verifica controller = new Verifica();

        //Codice errore per il display delle jsp
        int sentinel = 0;

        //Inizializzo un oggetto HttpSession
        HttpSession sessione;

        //VERIFICO CHE LA SESSIONE SIA ANCORA ATTIVA
        if ((sessione = request.getSession(false)) != null) {

            //DEVO VERIFICARE SE LA SESSIONE è ATTIVA COME CLIENTE
            //SESSIONE ATTIVA
            switch (request.getParameter("visualizzazione")) {
                //IL CLIENTE STA EFFETTUANDO UN LOGOUT
                case ("logout"): {
                    //CHIUDO LA SESSIONE IN CORSO
                    sessione.invalidate();

                    //RITORNO ALLA PAGINA INIZIALE
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Descrizione.jsp");
                    dispatcher.forward(request, response);
                    break;
                }

                //IL CLIENTE HA PREMUTO SUL TASTO ACQUISTA DELLA TABELLA
                case ("riepilogo"): {
                    //Recupero parametri Request
                    String quantità = request.getParameter("quant");
                    String idOggetto = request.getParameter("idOggetto");

                    //VERIFICO CHE I CAMPI SIANO STATI COMPILATI
                    if (controller.verificaInserimentoDati(quantità, idOggetto)) {
                        //Tutti i campi son stati compilati

                        //Verifica correttezza dati
                        if (controller.verificaQuantitàVenditore(Integer.parseInt(quantità))) {

                            //I dati inseriti sono corretti
                            sentinel = 0;

                            //Recupero oggetto da acquistare per mostrare il riepilogo
                            Oggetto o = FactoryOggetto.getInstance().getOggettoByID(Integer.parseInt(idOggetto));

                            //Carico l'oggetto sulla request
                            request.setAttribute("Oggetto", o);
                            //Carico il numero di oggetti sulla request
                            request.setAttribute("quantità", quantità);

                            //Carico il codice errore sulla request
                            request.setAttribute("sentinel", sentinel);

                            //Richiamo una jsp per mostrare il riepilogo dell'oggetto da acquistare
                            RequestDispatcher dispatcher = request.getRequestDispatcher("ConfermaOggetto.jsp");
                            dispatcher.forward(request, response);
                        }

                        //La quantità inserita è minore di zero
                        sentinel = 3;

                        //Carico codice di errore sulla request
                        request.setAttribute("sentinel", sentinel);

                        //Richiamo una jsp per mostrare il messaggio di errore
                        RequestDispatcher dispatcher = request.getRequestDispatcher("Cliente.jsp");
                        dispatcher.forward(request, response);
                    }

                    //Non tutti i campi son stati compilati, mostra messaggio errore corrispondente
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Cliente.jsp");
                    dispatcher.forward(request, response);

                    break;
                }

                //IL CLIENTE HA CONFERMATO L'ACQUISTO DELL'OGGETTO
                case ("esito"): {
                    //Verifico che l'utente abbia i soldi necessari per completare l'acquisto

                    //Recupero l'oggetto che l'utente desidera acquistare
                    Oggetto o = FactoryOggetto.getInstance().getOggettoByID(Integer.parseInt(request.getParameter("oggettoId")));

                    //Recupero l'utente che ha effettuato l'acquisto
                    UtenteCliente cliente = (UtenteCliente) sessione.getAttribute("utente");

                    long prezzo = o.getPrezzo();
                    int quantità = Integer.parseInt(request.getParameter("quant"));

                    if (quantità > o.getNumPezzi()) {
                        //Il numero di oggetti inseriti supera la disponibilità
                        sentinel = 14;
                        request.setAttribute("sentinel", sentinel);

                        //Richiamo jsp esito
                        RequestDispatcher dispatcher = request.getRequestDispatcher("Esito.jsp");
                        dispatcher.forward(request, response);

                    }
                    long costo = prezzo * quantità;

                    if (cliente.getSaldo().verificaCoperture(costo)) {
                        //L'utente ha abbastanza soldi per comprare l'oggetto

                        
                        if(sentinel != 14){
                        //Acquisto completato e database aggiornato
                        sentinel = FactoryUtente.getInstance().completaAcquisto(o.getVendId(), costo,
                                cliente.getId(), quantità, o.getId());
                        
                        //Ricarico la lista oggetti in quanto è stata modificata
                        
                        sessione.setAttribute("listaOggetti", FactoryOggetto.getInstance().getListaOggetto(0));
                        
                        
                        //Ricarico l'utente in quanto è stato modificato 
                        sessione.setAttribute("utente", FactoryUtente.getInstance().searchClienteById(cliente.getId()));
                        }
                        
                        //Carico l'attributo sentinel per l'eventuale visualizzazione dell'errore
                        request.setAttribute("sentinel", sentinel);

                        //Richiamo jsp esito
                        RequestDispatcher dispatcher = request.getRequestDispatcher("Esito.jsp");
                        dispatcher.forward(request, response);

                    } else {
                        //L'utente non ha abbastanza soldi, mostra messaggio di errore
                        sentinel = 4;
                        request.setAttribute("sentinel", sentinel);

                        //Richiamo jsp esito
                        RequestDispatcher dispatcher = request.getRequestDispatcher("Esito.jsp");
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
                case ("ricaricaCredito"): {
                    //L'UTENTE VUOLE RICARICARE IL CREDITO DISPONIBILE 
                    long ricarica;
                    ricarica = Long.parseLong(request.getParameter("ricarica"));

                    int id = (int) sessione.getAttribute("id");

                    //Aggiorna il database e restituisce un codice errore
                    sentinel = FactoryUtente.getInstance().ricaricaCliente(ricarica, id);

                    //Carico il codice errore sulla request
                    request.setAttribute("sentinel", sentinel);

                    //Aggiorno l'utente in sessione
                    sessione.setAttribute("utente", FactoryUtente.getInstance().searchClienteById(id));

                    RequestDispatcher dispatcher = request.getRequestDispatcher("PaginaUtente.jsp");
                    dispatcher.forward(request, response);
                    break;
                }

                /*
                case ("scrollTabellaAvanti"): {
                    int inizio = (int) request.getAttribute("indiceTabella");
                    
                    //Incremento l'inidiceTabella
                    inizio = inizio + 5;
                    
                    //Modifico la riga da cui parte la visualizzazione della tabella
                    sessione.setAttribute("listaOggetti", FactoryOggetto.getInstance().getListaOggetto(inizio));

                    //Passo l'inidiceTabella alla request
                    request.setAttribute("indiceTabella", inizio);

                    //Ricarico la pagina per far cambiare la visualizzazione
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Cliente.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                case ("scrollTabellaIndietro"): {
                    int inizio = (int) request.getAttribute("indiceTabella");

                    
                    //Aggiorno l'indice tabella, riduco di 5
                    if (inizio >= 5) {
                        inizio = inizio - 5;
                    }

                    //Recupero i prossimi 5 elementi da visualizzare e li salvo nella variabile sessione
                    sessione.setAttribute("listaOggetti", FactoryOggetto.getInstance().getListaOggetto(inizio));

                    //Passo l'indiceTabella alla request
                    request.setAttribute("indiceTabella", inizio);

                    //Ricarico la pagina per far cambiare la visualizzazione
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Cliente.jsp");
                    dispatcher.forward(request, response);
                    break;
                }
                 */
            }
        }

        //La sessione non è più attiva, si ritorna alla pagina login con un messaggio di errore
        sentinel = 5;
        request.setAttribute("sentinel", sentinel);

        //Richiamo jsp Login
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
