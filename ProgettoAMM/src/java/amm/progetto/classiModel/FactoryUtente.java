/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiModel;

import java.util.ArrayList;

/**
 *
 * @author Daniele Caschili
 */
public class FactoryUtente {
    
    private static FactoryUtente instance = null;
    ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
    
    //COSTRUTTORE
    private FactoryUtente(){
        
        //Cliente1        
        Saldo saldoCliente1 = new Saldo(5000000000L);
        Cliente cliente1 = new Cliente("Mario", "Rossi", "Mrossi", "0", saldoCliente1, 1234);
        
        //Cliente2
        Saldo saldoCliente2 = new Saldo(450000000L);
        Cliente cliente2 = new Cliente("Anna", "Bianchi", "Abianchi", "1", saldoCliente2, 5678);
        
        //Venditore1
        Saldo saldoVenditore1 = new Saldo(999000000000L);
        Venditore venditore1 = new Venditore("Marco", "Blu", "Mblu", "2", saldoVenditore1, 3795, "GalacticPrice");
        
        //Venditore2
        Saldo saldoVenditore2 = new Saldo(550000000000L);
        Venditore venditore2 = new Venditore("Gianni", "Verde", "Gverde", "3", saldoVenditore2, 8751, "NavalSpace");
        
        listaUtenti.add(cliente1);
        listaUtenti.add(cliente2);
        listaUtenti.add(venditore1);
        listaUtenti.add(venditore2);
    }
    
    //Recupera l'unica istanza del Singleton FactoryUtente
    public FactoryUtente getInstance(){
        if(instance == null){
            instance = new FactoryUtente();            
        }
        return instance;
    }
    
    
    //Metodo che restituisce un dato utente in base all'ID
    public Utente searchUtenteById(int id){
        for(Utente u : listaUtenti){
            if(u.getId() == id){
                return u;
            }
        }
        return null;
    }
    
    
    //Metodo che restituisce un dato utente in base a username e password
    public Utente searchUtente(String username, String password){
        for(Utente u : listaUtenti){
            if(u.getUsername().equals(username)){
                if(u.getPassword().equals(password)){
                    return u;
                }
            }
        }
        return null;
    }
    
}
