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
public class Oggetto {
    private String nome;
    private String descrizione;
    private String urlImmagine;
    private int numPezzi;
    private long prezzo;
    private int id;
    private int venditore_id;
    
    
    
    //COSTRUTTORI
   
    public Oggetto(String nome, String descrizione, String urlImmagine,
                    int numPezzi, long prezzo, int id, int vendId){
            this.nome = nome;
            this.descrizione = descrizione;
            this.urlImmagine = urlImmagine;
            this.numPezzi = numPezzi;
            this.prezzo = prezzo;
            this.id = id;
            this.venditore_id = vendId;
            }

    
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the urlImmagine
     */
    public String getUrlImmagine() {
        return urlImmagine;
    }

    /**
     * @param urlImmagine the urlImmagine to set
     */
    public void setUrlImmagine(String urlImmagine) {
        this.urlImmagine = urlImmagine;
    }

    /**
     * @return the numPezzi
     */
    public int getNumPezzi() {
        return numPezzi;
    }

    /**
     * @param numPezzi the numPezzi to set
     */
    public void setNumPezzi(int numPezzi) {
        this.numPezzi = numPezzi;
    }

    /**
     * @return the prezzo
     */
    public long getPrezzo() {
        return prezzo;
    }

    /**
     * @param prezzo the prezzo to set
     */
    public void setPrezzo(long prezzo) {
        this.prezzo = prezzo;
    }
    
              
    //Metodo per incrementare il numero di pezzi di un oggetto i vendita
    public void incrementaPezzi(int incremento){
        numPezzi = numPezzi + incremento;
    }
    
    
    
    //Metodo per decrementare il numero di oggetti messi in vendita
    public void decrementaPezzi(int decremento){
        numPezzi = numPezzi - decremento;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public void setVendId(int vendId){
        this.venditore_id = vendId;
    }
    
    public int getVendId(){
        return venditore_id;                
    }
    
    
}
