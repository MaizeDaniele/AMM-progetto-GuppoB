<%-- 
    Document   : Errore
    Created on : 1-mag-2016, 20.52.23
    Author     : Daniele  Caschili
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${sentinel eq '0'}">
                <!-- NESSUN ERRORE -->
            </c:when>
            <c:when test="${sentinel eq '1'}">
                <!-- PREZZO INSERITO <= 0 -->
                <h2>
                    Errore
                </h2>
                <p>
                    Il prezzo inserito &egrave; minore di zero, inserire un valore corretto!
                </p>
            </c:when>
            <c:when test="${sentinel eq '2'}">
                <!-- PREZZO INSERITO <= 0 E NUM PEZZI MINORE DI 0 -->
                <h2>
                    Errore
                </h2>
                <ul>
                    <li>
                        Il prezzo inserito &egrave; minore di zero, inserire un valore corretto!
                    </li>
                    <li>
                        Il numero di pezzi &egrave; minore di zero, inserire un valore corretto!
                    </li>
                </ul>
            </c:when>
            <c:when test="${sentinel eq '3'}">
                <!-- NUMERO PEZZI <= 0 -->
                <h2>
                    Errore
                </h2>
                <p>
                    Il numero di pezzi &egrave; minore di zero, inserire un valore corretto!
                </p>
            </c:when>
            <c:when test="${sentinel eq '4'}">
                <!-- PREZZO TROPPO ALTO PER L'ACQUISTO -->
                <h2>
                    Errore
                </h2>
                <p>
                    Non hai soldi a sufficienza per acquistare il prodotto, occorre effettuare una ricarica!
                </p>
            </c:when>
            <c:when test="${sentinel eq '5'}">
                <!-- SESSIONE SCADUTA -->
                <h2>
                    Errore
                </h2>
                <p>
                    La sessione è scaduta, rieffettua l'accesso!
                </p>
            </c:when> 
            <c:when test="${sentinel eq '6'}">
                <!-- CAMPI MANCANTI -->
                <h2>
                    Errore
                </h2>
                <p>
                    Devi compilare tutti i campi per procedere!
                </p>
            </c:when>     
            <c:when test="${sentinel eq '7'}">
                <!-- USERNAME ERRATO -->
                <h2>
                    Errore
                </h2>
                <p>
                    L'username inserito non è corretto!
                </p>
            </c:when>     
            <c:when test="${sentinel eq '8'}">
                <!-- PASSWORD ERRATA -->
                <h2>
                    Errore
                </h2>
                <p>
                    La password inserita non è corretta!
                </p>
            </c:when>     
            <c:when test="${sentinel eq '9'}">
                <!-- L'UTENTE HA SOLDI A SUFFICIENZA -->

                <p>
                    L'acquisto è avvenuto correttamente, grazie per aver acquistato da SPACEZON!
                </p>
            </c:when>     
            <c:when test="${sentinel eq '10'}">
                <!-- L'INSERIMENTO DELL'OGGETTO è STATO COMPLETATO -->

                <p>
                    L'oggetto &egrave; stato inserito con successo! La rigraziamo per aver scelto SPACEZON.
                </p>
            </c:when>     
            <c:when test="${sentinel eq '11'}">
                <!-- L'UTENTE NON è AUTENTICATO CORRETTAMENTE -->

                <p>
                    Accesso Negato! Non hai il diritto di accedere a quest'area!
                </p>
            </c:when>  
            <c:when test="${sentinel eq '12'}">
                <!-- ERRORE CON LA MODIFICA DEL DATABASE -->

                <p>
                    Errore sul database, riprova!
                </p>
            </c:when>
            <c:when test="${sentinel eq '13'}">
                <!-- RICARICA CREDITO ESEGUITA CON SUCCESSO -->

                <p>
                    La ricarica è stata eseguita con successo
                </p>
            </c:when>
            <c:when test="${sentinel eq '14'}">
                <!-- NUMERO PEZZI INSERITO SUPERA LA DISPONIBILITà -->

                <p>
                    Il numero di pezzi inserito supera la nostra disponibilità! 
                </p>
            </c:when>
            <c:when test="${sentinel eq '15'}">
                <!-- NESSUNA LISTA OGGETTI VENDITORE TROVATA -->

                <p>
                    Non ho trovato nessun oggetto messo in vendita dalla tua società, metti in vendita qualcosa prima di accedere
                    a questa pagina!
                </p>
            </c:when>
            <c:when test="${sentinel eq '16'}">
                <!-- OGGETTO ELIMINATO CON SUCCESSO -->

                <p>
                    L'oggetto &egrave; stato eliminato con successo!
                </p>
            </c:when>
        </c:choose>
    </body>
</html>

<!-- 

0   NESSUN ERRORE   
1   PREZZO INSERITO <0
2   PREZZO INSERITO <0 E NUM PEZZI MINORE <=0
3   NUMERO PEZZI <= 0
4   PREZZO OGGETTO > SALDO CLIENTE
5   SESSIONE SCADUTA
6   NON TUTTI I CAMPI SONO STATI COMPILATI
7   USERNAME ERRATO
8   PASSWORD ERRATA
9   UTENTE HA ABBASTANZA SOLDI, ACQUISTO COMPLETATO
10  INSERIMENTO OGGETTO COMPLETATO
11  UTENTE NON AUTENTICATO CORRETTAMENTE
12  ERRORE CON LA MODIFICA DATI DATABASE
13  RICARICA COMPLETATA
14  NUMERO OGGETTI INSERITI SUPERA DISPONIBILITà
15  LISTA OGGETTI VENDITORE NON TROVATA
16  OGGETTO ELIMINATO CON SUCCESSO

-->