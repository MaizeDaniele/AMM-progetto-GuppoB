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
public class UtenteVenditore extends Utente {

    private String società;

    public UtenteVenditore(String nome, String cognome, String username, String password, Saldo saldo, int id, String società) {
        super(nome, cognome, username, password, saldo, id);
        this.società = società;
    }

    public UtenteVenditore() {
        super(null, null, null, null, null, 0);
        this.società = null;
    }

    /**
     * @return the società
     */
    
    public String getSocietà() {
        return società;
    }

    /**
     * @param società the società to set
     */
    public void setSocietà(String società) {
        this.società = società;
    }

}
