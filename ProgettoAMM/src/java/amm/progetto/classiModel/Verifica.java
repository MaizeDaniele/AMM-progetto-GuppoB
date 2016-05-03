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
public class Verifica {
    
    
    //Riceve in ingresso un numero indeterminato di stringhe e verifica che siano tutti valori diversi dal valore ""
    //false -> non hai inserito tutti i valori
    //true -> hai inserito tutti i valori
    public boolean verificaInserimentoDati(String... inserimenti){
        for(String dati : inserimenti){
            if(dati.equals("") || (dati == null)){
                return false;
            }            
        }
        return true;
    }
    
    //Verifica che il prezzo inserito sia maggiore di zero
    public boolean verificaPrezzoVenditore(long prezzo){
        if(prezzo > 0){
            return true;
        }
        return false;
    }
    
    //Verifica che il numero di pezzi inserito sia maggiore di zero
    public boolean verificaQuantitàVenditore(int numPezzi){
        if(numPezzi >= 0){
            return true;
        }
        return false;
    }
}
