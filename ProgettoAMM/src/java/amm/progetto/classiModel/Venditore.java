/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiModel;

/**
 *
 * @author Daniele Caschili
 */
public class Venditore extends Utente {
    private String società;
    
    public Venditore(String nome, String cognome, String username, String password, Saldo saldo,int id, String società){
        super(nome, cognome, username, password, saldo, id);
        this.società = società;
    }
}
