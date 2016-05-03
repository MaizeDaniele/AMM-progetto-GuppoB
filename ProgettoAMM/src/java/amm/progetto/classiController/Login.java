/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiController;

import amm.progetto.classiModel.UtenteCliente;
import amm.progetto.classiModel.FactoryOggetto;
import amm.progetto.classiModel.FactoryUtente;
import amm.progetto.classiModel.Oggetto;
import amm.progetto.classiModel.Utente;
import amm.progetto.classiModel.Verifica;
import com.sun.jmx.snmp.defaults.DefaultPaths;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniele Caschili
 */
public class Login extends HttpServlet {

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
        
        //Instance Variables
        String username;
        String password;
        Utente u;
        Verifica controller = new Verifica();
        int sentinel = 0;
        
        //VERIFICO CHE IL SUBMIT SIA STATO PREMUTO DOPO AVER INSERITO QUALCHE VALORE
        if(request.getParameter("Submit") != null){
        
            //Recupero i parametri dalla request
            username = request.getParameter("username");
            password = request.getParameter("password");
        
            //Verifico che i campi siano stati compilati
            if(!controller.verificaInserimentoDati(username, password)){
                //Almeno un campo non è stato compilato
                
                //IMPOSTO CODICE ERRORE
                sentinel = 6;
                request.setAttribute("sentinel", sentinel);
                
                //RICHIAMO JSP LOGIN 
                RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
            }
        
        
            //CONTROLLO VALIDITà DEI DATI DI ACCESSO
        
            //Verifica che l'username inserito non esista e rimanda l'errore corrispondente
            if(!FactoryUtente.getInstance().searchUsername(username)){
            
                //L'utente ha inserito un username errato, va mostrato un messaggio di errore
                sentinel = 7;
                request.setAttribute("sentinel", sentinel);
                
                //RICHIAMO JSP LOGIN
                RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
            
            }
            else if(FactoryUtente.getInstance().searchUtente(username, password) != null){
            
                //L'utente ha inserito entrambi i campi corretti.
                //Va effettuato il login
                
                //Azzero codice di errore
                sentinel = 0;
                
                //Recupero oggetto utente
                u = FactoryUtente.getInstance().searchUtente(username, password);
                                
                //CREAZIONE SESSIONE
                HttpSession sessione = request.getSession();
                
                //IMPOSTO I DATI DI SESSIONE
                sessione.setAttribute("loggedIn", true);
                sessione.setAttribute("id", u.getId());                
                sessione.setAttribute("utente", u);
                
                
                //VERIFICA CLIENTE O VENDITORE
                if(u instanceof UtenteCliente){
                    
                    sessione.setAttribute("tipoSessione", "Cliente");
                    
                    
                    ArrayList<Oggetto> listaOggetti = FactoryOggetto.getInstance().getListaOggetto();
                    sessione.setAttribute("listaOggetti", listaOggetti);                    
                                                            
                    //RICHIAMA LA JSP CLIENTE
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Cliente.jsp");
                    dispatcher.forward(request, response);
                }            
                else{
                    sessione.setAttribute("tipoSessione", "Venditore");
                    //RICHIAMA LA JSP VENDITORE
                    RequestDispatcher dispatcher = request.getRequestDispatcher("Venditore.jsp");
                    dispatcher.forward(request, response);
                }
            }        
            else{
                //L'utente ha sbagliato la password.
                //Visualizzare il messaggio corrispondente
                sentinel = 8;
                request.setAttribute("sentinel", sentinel);
                
                //RICHIAMO JSP LOGIN
                RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
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

