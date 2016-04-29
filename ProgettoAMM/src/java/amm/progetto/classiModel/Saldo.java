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
public class Saldo {
    private long saldo;
    
    
    //COSTRUTTORE
    public Saldo(long saldo){
        this.saldo = saldo;
    }
    
    
    
    /**
     * @return the saldo
     */
    public long getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    
    
    //Per ricaricare il saldo dell'utente
    public void incrementaSaldo(int incasso){
        saldo = saldo + incasso;
    }
    
    //Per scalare il prezzo dell'oggetto acquistato
    public void decrementaSaldo(int spesa){
        saldo = saldo - spesa;
    }
    
    
}
