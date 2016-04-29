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
public class Cliente extends Utente {
    public Cliente(String nome, String cognome, String username, String password, Saldo saldo, int id){
        super(nome, cognome, username, password, saldo, id);
    }
}
