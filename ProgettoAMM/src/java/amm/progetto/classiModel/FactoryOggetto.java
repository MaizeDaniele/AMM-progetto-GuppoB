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
    ArrayList<Oggetto> listaOggetto = new ArrayList<>();

    //COSTRUTTORE
    private FactoryOggetto() {

    }

    public static FactoryOggetto getInstance() {
        if (instance == null) {
            instance = new FactoryOggetto();
        }
        return instance;
    }

    //Ricerca nel database un oggetto che abbia quel dato id
    public Oggetto getOggettoByID(int id) {

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
            if (set.next()) {
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
    public ArrayList<Oggetto> getListaOggetto(int inizio) {
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

    //Metodo che elimina un oggetto dal database
    public void eliminaOgg(int id) {
        Connection conn = null;
        Statement stmt = null;

        String sql = "DELETE "
                + "FROM oggetto "
                + "WHERE id = "
                + id;

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            stmt = conn.createStatement();

            int rows = stmt.executeUpdate(sql);
            if (rows == 1) {
                stmt.close();
                conn.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
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

    //Metodo che aggiorna i dati di un oggetto sul database
    public int modificaOgg(Oggetto o) {

        Connection conn = null;
        PreparedStatement stmt = null;

        String sql = "UPDATE oggetto "
                + "SET nome = ? , descrizione = ? , urlimmagine = ? , numeropezzi = ? , prezzo = ? , venditore_id = ? "
                + "WHERE id = ? ";

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, o.getNome());
            stmt.setString(2, o.getDescrizione());
            stmt.setString(3, o.getUrlImmagine());
            stmt.setInt(4, o.getNumPezzi());
            stmt.setLong(5, o.getPrezzo());
            stmt.setInt(6, o.getVendId());
            stmt.setInt(7, o.getId());

            int rows = stmt.executeUpdate();

            if (rows != 1) {
                //L'aggiornamento del database non è andato a buon fine
                stmt.close();
                conn.close();
                return 12;
            }

            //L'aggiornamento è andato a buon fine
            stmt.close();
            conn.close();
            return 10;
        } catch (SQLException ex) {
            Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
            return 12;
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

    //Metodo che recupera la lista degli oggetti di un venditore in base al suo id
    public ArrayList<Oggetto> getListaOggettoVenditore(int id) {

        Connection conn = null;
        Statement stmt = null;

        ArrayList<Oggetto> listaoggetti = new ArrayList<>();
        Oggetto o;

        String sql = "SELECT * "
                + "FROM oggetto "
                + "WHERE venditore_id = "
                + id;

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            stmt = conn.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            while (set.next()) {
                o = new Oggetto(set.getString("nome"),
                        set.getString("descrizione"),
                        set.getString("urlimmagine"),
                        set.getInt("numeropezzi"),
                        set.getLong("prezzo"),
                        set.getInt("id"),
                        set.getInt("venditore_id"));

                listaoggetti.add(o);
            }

            stmt.close();
            conn.close();

            return listaoggetti;
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

    //Aggiorna l'elenco degli oggetti del venditore
    public int aggiungiOggetto(Oggetto o) {

        Connection conn = null;

        PreparedStatement stmt = null;

        String query = "INSERT INTO oggetto(id, nome, descrizione, urlimmagine, numeropezzi, prezzo, venditore_id) "
                + "VALUES (default, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            stmt = conn.prepareStatement(query);
            stmt.setString(1, o.getNome());
            stmt.setString(2, o.getDescrizione());
            stmt.setString(3, o.getUrlImmagine());
            stmt.setInt(4, o.getNumPezzi());
            stmt.setLong(5, o.getPrezzo());
            stmt.setInt(6, o.getVendId());

            int rows = stmt.executeUpdate();

            if (rows != 1) {
                //NON HO AGGIORNATO IL DATABASE

                stmt.close();
                conn.close();

                //Codice di errore per la jsp
                return 12;
            }

            //Il database è stato aggiornato
            stmt.close();
            conn.close();
            return 10;

        } catch (SQLException ex) {
            Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
            return 12;
        } finally {
            if (conn != null && stmt != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //Ricerca gli oggetti aventi una data stringa nel campo nome o descrizione
    public ArrayList<Oggetto> getListaOggettoByString(String nome){
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String sql = "SELECT * "
                + "FROM oggetto "
                + "WHERE nome LIKE ? OR descrizione LIKE ? ";
        
        ArrayList<Oggetto> listaOggetti = new ArrayList<>();
        
        try{
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");
            
            String ricerca = "%" + nome + "%";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, ricerca);
            stmt.setString(2, ricerca);
            
            ResultSet set = stmt.executeQuery();
            //Pulisco la lista per evitare duplicati
            listaOggetti.clear();
            
            while(set.next()){
                //Creo un oggetto con i dati trovati
                Oggetto o = new Oggetto(set.getString("nome"),
                        set.getString("descrizione"),
                        set.getString("urlimmagine"),
                        set.getInt("numeropezzi"),
                        set.getLong("prezzo"),
                        set.getInt("id"),
                        set.getInt("venditore_id"));
                
                //Aggiungo l'oggetto alla lista di oggetti da restituire 
                listaOggetti.add(o);
            }
                stmt.close();
                conn.close();
                
                return listaOggetti;
                
            
        }catch(SQLException ex){
             Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
             
        }finally{
            if(stmt != null && conn != null ){
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryOggetto.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
        return null;
    }
}
