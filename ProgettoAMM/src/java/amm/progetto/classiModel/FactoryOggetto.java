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
public class FactoryOggetto {
    
    private static FactoryOggetto instance = null;
    ArrayList<Oggetto> listaOggetto = new ArrayList<Oggetto>();
    
    
    //COSTRUTTORE
    private FactoryOggetto(){
        
        //Incrociatore Javelin
        String descrizioneJavelin = "Incrociatore da guerra, grosso e cattivo!\n" +
                                        "Le sue dimensioni la rendono lenta e difficile da manovrare,\n" +
                                        "ma sarà difficile da abbattere!";
        Oggetto incJavelin = new Oggetto("Incrociatore Javelin", descrizioneJavelin, "Immagini/Javelin.jpg",
                                            10, 55000000000L, "incJav");
        
        //Sabre
        String descrizioneSabre = "Sabre, velocissima! Riuscirete a seminare qualunque inseguitore!\n" +
                                    "Non può reggere scontri prolungati!";
        Oggetto sabre = new Oggetto("Sabre", descrizioneSabre, "Immagini/Sabre.jpg", 
                                        500, 980000000L, "sabre");
        
        //The endeavor
        String descrizioneEndeavor = "Endeavor, GIGANTESCA! Lunga 100Km!\n" +
                                        "Potrete raggiungere gli angoli più remoti dell'universo senza problemi!\n" +
                                        "ELETTA ASTRONAVE DELL'ANNO SPACEZON!";
        Oggetto endeavor = new Oggetto("The endeavor", descrizioneEndeavor, "Immagini/The_Endeavor.jpg", 
                                        1, 90000000000L, "endeavor");
        
        //Cutlas Red
        String descrizioneCutlasRed = "Nave di medie dimensioni. Massima qualità a un prezzo contenuto.";
        Oggetto cutlasRed = new Oggetto("Cutlas Red", descrizioneCutlasRed, "Immagini/CutlasRed.jpg", 
                                            250, 5500000000L, "cutlasRed");
        
        //M50 Interceptor
        String descrizioneInterceptor = "Nave da battaglia, piccola e veloce. Notevole potenza di fuoco nonostante le "
                + "dimensioni ridotte!";
        Oggetto m50Interceptor = new Oggetto("M50 Interceptor", descrizioneInterceptor, "Immagini/M50Interceptor.jpg",
                                                1000, 10000000L, "m50Interceptor");
        /*
        //Mappa Universo Singola Zona
        String descrizioneSingZona = "Singola mappa di una zona a scelta!";
        Oggetto mappaSingZona = new Oggetto("Mappa Singola Zona", descrizioneSingZona, "Immagini/Mappe_Universo.jpg",
                                               10, 3000000L, "mapSingZona" );
        
        //Mappa Universo Abbonamento
        String descrizioneAbbonamento = "Tutto le galassie attualmente conosciute sono a vostra disposizione.\n"
                                            + "Avrete accesso illimitato al nostro database";
        Oggetto mappaAbbonamento = new Oggetto("Abbonamento Mappe", descrizioneAbbonamento, "Immagini/Mappe_Universo.jpg",
                                                    10, 5000000L, "mapAbb");
        
        listaOggetto.add(mappaSingZona);
        listaOggetto.add(mappaAbbonamento);
        */
        
        listaOggetto.add(incJavelin);
        listaOggetto.add(sabre);
        listaOggetto.add(endeavor);
        listaOggetto.add(cutlasRed);
        listaOggetto.add(m50Interceptor);
        
        
    }
    
    
    public static FactoryOggetto getInstance(){
        if(instance == null){
            instance = new FactoryOggetto();
        }
        return instance;
    }
    
    
    //Itera tutto l'arrayList in cerca di un Oggetto con un dato nome
    public Oggetto getOggettoByID(String id){
        
        for(Oggetto o : listaOggetto){
            if(o.getId().equals(id)){
                return o;
            }
            
        }
        return null;
    }
    
    //Metodo che restituisce l'arrayList
    public ArrayList<Oggetto> getListaOggetto(){
        return listaOggetto;
    }
    
}
    

