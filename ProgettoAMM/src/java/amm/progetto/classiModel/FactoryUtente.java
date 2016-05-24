/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.progetto.classiModel;

import amm.progetto.classiController.Cliente;
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
public class FactoryUtente {

    //Variabile da usare per creare le connessioni con il database
    private String connectionString;

    private static FactoryUtente instance = null;

    //COSTRUTTORE
    private FactoryUtente() {

    }

    //Recupera l'unica istanza del Singleton FactoryUtente
    public static FactoryUtente getInstance() {
        if (instance == null) {
            instance = new FactoryUtente();
        }
        return instance;
    }

    //Metodo che verifica se l'username è corretto
    //FALSE non ho trovato username
    //TRUE ho trovato username
    public boolean searchUsername(String username) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            String sql1 = "SELECT * "
                    + "FROM cliente "
                    + "WHERE username = ? ";

            String sql2 = "SELECT * "
                    + "FROM venditore "
                    + "WHERE username = ? ";

            stmt = conn.prepareStatement(sql1);

            stmt.setString(1, username);

            ResultSet set1 = stmt.executeQuery();

            if (!set1.next()) {
                //NON HO TROVATO NESSUN CLIENTE CON QUELL'USERNAME
                //CONTROLLO TRA I VENDITORI

                stmt = conn.prepareStatement(sql2);
                stmt.setString(1, username);
                ResultSet set2 = stmt.executeQuery();

                if (!set2.next()) {
                    //NON HO TROVATO NESSUN VENDITORE CON QUELL'USERNAME

                    stmt.close();
                    conn.close();
                    return false;
                } else {
                    //HO TROVATO UN UTENTE VENDITORE
                    stmt.close();
                    conn.close();
                    return true;
                }
            } else {
                //HO TROVATO UN UTENTE CLIENTE
                stmt.close();
                conn.close();
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            if (stmt != null && conn != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    //Metodo che restituisce un dato utente in base a username e password
    public Utente searchUtente(String username, String password) {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            //INIZIO PROVANDO A CERCARE L'UTENTE TRA I CLIENTI
            String sql = "SELECT * "
                    + "FROM cliente "
                    + "WHERE username = ? AND password = ? ";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                //HO TROVATO UN CLIENTE

                UtenteCliente c = new UtenteCliente();

                Saldo s = new Saldo(set.getLong("saldo"));

                c.setId(set.getInt("id"));
                c.setNome(set.getString("nome"));
                c.setCognome(set.getString("cognome"));
                c.setUsername(set.getString("username"));
                c.setPassword(set.getString("password"));
                c.setSaldo(s);

                stmt.close();
                conn.close();

                return c;
            } else {
                //NON HO TROVATO L'UTENTE TRA I CLIENTI PROVO TRA I VENDITORI
                sql = "SELECT * "
                        + "FROM venditore "
                        + "WHERE username = ? AND password = ? ";

                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);

                set = stmt.executeQuery();

                if (set.next()) {
                    //HO TROVATO L'UTENTE TRA I VENDITORI
                    UtenteVenditore v = new UtenteVenditore();

                    Saldo s = new Saldo(set.getLong("saldo"));

                    v.setId(set.getInt("id"));
                    v.setNome(set.getString("nome"));
                    v.setCognome(set.getString("cognome"));
                    v.setUsername(set.getString("username"));
                    v.setPassword(set.getString("password"));
                    v.setSaldo(s);
                    v.setSocietà(set.getString("società"));

                    stmt.close();
                    conn.close();
                    return v;
                } else {
                    //I DATI INSERITI NON CORRISPONDONO A NESSUN UTENTE
                    conn.close();
                    stmt.close();
                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            if (conn != null && stmt != null) {

                try {
                    conn.close();
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
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

    //Aggiorna il saldo del venditore e dell'utente, ho già verificato la disponibilità di fondi del cliente.
    public int completaAcquisto(int venditore_id, long costo, int cliente_id, int numPezzi, int id_oggetto) {

        Connection conn = null;

        long saldoAggiornatoCliente = 0;
        long saldoAggiornatoVenditore = 0;
        
        int numPezziAggiornato = 0;

        //uso uno statement classico in quanto non rischio sqlInjection
        Statement ottieniSaldoCliente = null;
        Statement ottieniSaldoVenditore = null;
        Statement aggiornaSaldoCliente = null;
        Statement aggiornaSaldoVenditore = null;
        PreparedStatement ottieniNumeroPezzi = null;
        PreparedStatement aggiornaNumeroPezzi = null;

        //recupera il saldo del cliente che sta acquistando
        String query1 = "SELECT saldo "
                + "FROM cliente "
                + "WHERE id = "
                + cliente_id;

        //recupera il saldo del venditore proprietario dell'oggetto acquistato
        String query2 = "SELECT saldo "
                + "FROM venditore "
                + "WHERE id = "
                + venditore_id;

        //Aggiorna la disponibilità dei pezzi dell'oggetto selezionato
        String query5 = "UPDATE oggetto "
                + "SET numeropezzi = ? "
                + "WHERE id = ? ";
        
        //Recupero il numero di pezzi attualmente salvato su database
        String query6 = "SELECT numeropezzi "
                + "FROM oggetto "
                + "WHERE id = ? ";
        

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            conn.setAutoCommit(false);

            ottieniSaldoCliente = conn.createStatement();
            ottieniSaldoVenditore = conn.createStatement();
            aggiornaSaldoCliente = conn.createStatement();
            aggiornaSaldoVenditore = conn.createStatement();
            aggiornaNumeroPezzi = conn.prepareStatement(query5);
            ottieniNumeroPezzi = conn.prepareStatement(query6);
            
            
            
            //Recupera il saldo del cliente e del venditore
            ResultSet setCliente = ottieniSaldoCliente.executeQuery(query1);
            ResultSet setVenditore = ottieniSaldoVenditore.executeQuery(query2);
            
            
            ottieniNumeroPezzi.setInt(1, id_oggetto);
            
            //Recupero il numero di pezzi disponibili
            ResultSet setNumeroPezzi = ottieniNumeroPezzi.executeQuery();
            
            if(setNumeroPezzi.next()){
                //Ho recuperato il numero di pezzi disponibili e aggiorno
                numPezziAggiornato = setNumeroPezzi.getInt("numeropezzi") - numPezzi;
            }
            else{
                //Non son riuscito a recuparare la disponibilità
                conn.rollback();
                ottieniSaldoCliente.close();
                ottieniSaldoVenditore.close();                
                ottieniNumeroPezzi.close();
                conn.close();
                return 12;
            }
            
            aggiornaNumeroPezzi.setInt(1, numPezziAggiornato);
            aggiornaNumeroPezzi.setInt(2, id_oggetto);

            //Eseguio l'aggiornamento del database
            int rows = aggiornaNumeroPezzi.executeUpdate();

            if (rows != 1) {
                //il database non è stato aggiornato
                conn.rollback();
                ottieniSaldoCliente.close();
                ottieniSaldoVenditore.close();               
                aggiornaNumeroPezzi.close();
                ottieniNumeroPezzi.close();
                conn.close();
                return 12;
            }

            if (setCliente.next() && setVenditore.next()) {
                
                //Calcola il saldo aggiornato del cliente e del venditore
                saldoAggiornatoCliente = setCliente.getLong("saldo") - costo;
                saldoAggiornatoVenditore = setVenditore.getLong("saldo") + costo;
                
            } else {
                //FALLITA LA MODIFICA DEL DATABASE
                conn.rollback();
                ottieniSaldoCliente.close();
                ottieniSaldoVenditore.close();               
                aggiornaNumeroPezzi.close();
                ottieniNumeroPezzi.close();
                conn.close();
                return 12;
            }

            //aggiorna il saldo del cliente
            String query3 = "UPDATE cliente "
                    + "SET saldo = "
                    + saldoAggiornatoCliente
                    + " WHERE id = "
                    + cliente_id;

            //aggiorna il saldo del venditore
            String query4 = "UPDATE venditore "
                    + "SET saldo = "
                    + saldoAggiornatoVenditore
                    + " WHERE id = "
                    + venditore_id;

            rows = aggiornaSaldoCliente.executeUpdate(query3);

            if (rows != 1) {

                //FALLITA LA MODIFICA DEL DATABASE
                conn.rollback();
                ottieniSaldoCliente.close();
                ottieniSaldoVenditore.close();
                aggiornaSaldoCliente.close();
                aggiornaSaldoVenditore.close();
                aggiornaNumeroPezzi.close();
                ottieniNumeroPezzi.close();
                conn.close();
                return 12;
            }

            rows = aggiornaSaldoVenditore.executeUpdate(query4);

            if (rows != 1) {
                //FALLITA LA MODIFICA DEL DATABASE
                conn.rollback();
                ottieniSaldoCliente.close();
                ottieniSaldoVenditore.close();
                aggiornaSaldoCliente.close();
                aggiornaSaldoVenditore.close();
                aggiornaNumeroPezzi.close();
                ottieniNumeroPezzi.close();
                conn.close();
                return 12;
            }

            //LA MODIFICA è ANDATA A BUON FINE
            conn.commit();
            conn.setAutoCommit(true);
            ottieniSaldoCliente.close();
            ottieniSaldoVenditore.close();
            aggiornaSaldoCliente.close();
            aggiornaSaldoVenditore.close();
            aggiornaNumeroPezzi.close();
            ottieniNumeroPezzi.close();
            conn.close();
            return 9;

        } catch (SQLException ex) {
            Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
            return 12;
        } finally {
            if (conn != null
                    && ottieniSaldoCliente != null
                    && ottieniSaldoVenditore != null
                    && aggiornaSaldoCliente != null
                    && aggiornaSaldoVenditore != null
                    && aggiornaNumeroPezzi != null) {
                try {
                    conn.close();
                    ottieniSaldoCliente.close();
                    ottieniSaldoVenditore.close();
                    aggiornaSaldoCliente.close();
                    aggiornaSaldoVenditore.close();
                    aggiornaNumeroPezzi.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    //Ricerca un cliente mediante il suo id
    public Utente searchClienteById(int id) {
        Connection conn = null;
        Statement stmt = null;

        String sql = "SELECT * "
                + "FROM cliente "
                + "WHERE id = "
                + id;

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            stmt = conn.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            UtenteCliente c = null;

            if (set.next()) {
                c = new UtenteCliente();

                Saldo s = new Saldo(set.getLong("saldo"));

                c.setId(set.getInt("id"));
                c.setNome(set.getString("nome"));
                c.setCognome(set.getString("cognome"));
                c.setUsername(set.getString("username"));
                c.setPassword(set.getString("password"));
                c.setSaldo(s);
            }

            stmt.close();
            conn.close();
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            if (stmt != null && conn != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    //Ricerca un venditore mediante il suo id
    public Utente searchVenditoreById(int id) {

        Connection conn = null;
        Statement stmt = null;

        String sql = "SELECT * "
                + "FROM venditore "
                + "WHERE id = "
                + id;

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            stmt = conn.createStatement();

            ResultSet set = stmt.executeQuery(sql);

            UtenteVenditore c = null;

            if (set.next()) {
                c = new UtenteVenditore();

                Saldo s = new Saldo(set.getLong("saldo"));

                c.setId(set.getInt("id"));
                c.setNome(set.getString("nome"));
                c.setCognome(set.getString("cognome"));
                c.setUsername(set.getString("username"));
                c.setPassword(set.getString("password"));
                c.setSaldo(s);
                c.setSocietà(set.getString("società"));
            }

            stmt.close();
            conn.close();
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            if (stmt != null && conn != null) {
                try {
                    stmt.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    //Ricarica il saldo di un cliente
    public int ricaricaCliente(long ricarica, int id) {

        long saldoCorrente = 0;
        long saldoAggiornato = 0;

        Connection conn = null;

        PreparedStatement aggiornaSaldo = null;
        Statement recuperaSaldoAttuale = null;

        //Recupera il saldo dell'utente loggato
        String sql1 = "SELECT saldo "
                + "FROM cliente "
                + "WHERE id = "
                + id;

        //Aggiorna il saldo del cliente
        String sql2 = "UPDATE  cliente "
                + "SET saldo = ? "
                + "WHERE id = ?";

        try {
            conn = DriverManager.getConnection(connectionString, "maizedaniele", "1234");

            //Inizio della transizione
            conn.setAutoCommit(false);
            recuperaSaldoAttuale = conn.createStatement();

            ResultSet set = recuperaSaldoAttuale.executeQuery(sql1);

            if (set.next()) {
                saldoCorrente = set.getLong("saldo");
            }

            //Calcola il saldo aggiornato del cliente
            saldoAggiornato = saldoCorrente + ricarica;

            aggiornaSaldo = conn.prepareStatement(sql2);
            aggiornaSaldo.setLong(1, saldoAggiornato);
            aggiornaSaldo.setInt(2, id);

            //Aggiorno il database con il preparedstatement
            int row = aggiornaSaldo.executeUpdate();

            if (row == 1) {
                //L'aggiornamento è andato a buon fine 
                conn.commit();
                conn.setAutoCommit(true);

                aggiornaSaldo.close();
                recuperaSaldoAttuale.close();
                conn.close();
                return 13;
            }

            //La ricarica non è andata a buon fine
            conn.commit();
            conn.setAutoCommit(true);

            aggiornaSaldo.close();
            recuperaSaldoAttuale.close();
            conn.close();
            return 12;

        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return 12;

        } finally {
            if (conn != null && aggiornaSaldo != null && recuperaSaldoAttuale != null) {

                try {
                    aggiornaSaldo.close();
                    recuperaSaldoAttuale.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(FactoryUtente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
