/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Caschili
 */
public class FactoryOggetto {

    //Variabile da usare per creare le connessioni con il database
    private String connectionString;

    private static FactoryOggetto instance = null;
    ArrayList<Oggetto> listaOggetto = new ArrayList<Oggetto>();

    //COSTRUTTORE
    private FactoryOggetto(){

        
    }

    public static FactoryOggetto getInstance() {
        if (instance == null) {
            instance = new FactoryOggetto();
        }
        return instance;
    }

    //Ricerca nel database un oggetto che abbia quel dato id
    public Oggetto getOggettoByID(int id)
            {

        Connection conn = null;

        //Dato che l'utente non ha inserito dati posso usare un semplice statement
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            String sql = "SELECT * "
                    + "FROM oggetto "
                    + "WHERE id = "
                    + id;

            stmt = conn.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            //Essendo l'id impostato da un campo nascosto so per certo che troverò quest'oggetto nel database, posso evitare di fare un controllo
            //in quanto in ogni caso gli errori dovuti al DB sono gestiti dal blocco try catch
            Oggetto o = null;
            if(set.next()){
            o = new Oggetto(set.getString("nome"),
                    set.getString("descrizione"),
                    set.getString("urlimmagine"),
                    set.getInt("numeropezzi"),
                    set.getLong("prezzo"),
                    set.getInt("id"),
                    set.getInt("venditore_id"));
            }

            stmt.close();
            conn.close();
            return o;
        } catch (SQLException ex) {
            Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            if (stmt != null && conn != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    //Metodo che restituisce l'arrayList
    public ArrayList<Oggetto> getListaOggetto(int inizio ) {
        Connection conn = null;
        Statement stmt = null;

        try {
            
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");
            
            //Pulisto la lista oggetti per evitare copie degli oggetti
            listaOggetto.clear();
            
            //Recupero gli oggetti dal database
            String sql = "SELECT * "
                    + "FROM oggetto ";
                    
                    

            stmt = conn.createStatement();            
                                    
            ResultSet set = stmt.executeQuery(sql);

            while (set.next()) {

                Oggetto o = new Oggetto(
                        set.getString("nome"),
                        set.getString("descrizione"),
                        set.getString("urlimmagine"),
                        set.getInt("numeropezzi"),
                        set.getLong("prezzo"),
                        set.getInt("id"),
                        set.getInt("venditore_id"));

                listaOggetto.add(o);
            }

            stmt.close();
            conn.close();
            return listaOggetto;

        } catch (SQLException ex) {
            Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            if (stmt != null && conn != null) {
                try {
                    stmt.close();
                    conn.close();               
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        
    }

    //Metodi per impostare la connectionString
    public void setConnectionString(String s) {
        this.connectionString = s;
    }

    public String getConnectionString() {
        return connectionString;
    }

}
