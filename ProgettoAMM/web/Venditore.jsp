<%-- 
    Document   : Venditore
    Created on : 29-apr-2016, 19.53.41
    Author     : Daniele  Caschili
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>SPACEZON:Pagina Venditore</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Daniele Caschili">
        <meta name="description" content="Pagina del venditore, possibilità di aggiungere merci">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, ACQUISTI, PAGINA VENDITORE">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
    </head>

    <body id="venditore">
        <!-- INIZIO ELEMENTO LAYOUT PAGE -->
        <div id="page">

            <jsp:include page="./jspGeneriche/Header.jsp"/>
            <jsp:include page="./jspGeneriche/NavigationSideBar.jsp"/>



            <!-- INIZIO ELEMENTO LAYOUT CONTENT -->
            <div id="content">
                <jsp:include page="./jspGeneriche/Errore.jsp"/>
                <h2>Inserisci dati prodotto:</h2>

                <!-- SEZIONE DATI -->
                <!-- va inserito il valore action, URL della pagina del server che riceverà i dati inseriti nel form -->
                <form action="Venditore.html" method="post">



                    <label for="nome_oggetto">Nome Oggetto:</label>
                    <input type="text" id="nome_oggetto" name="nome_oggetto" class="inputBox" value="${Oggetto.getNome()}"/>

                    <label for="url_immagine">URL:</label>
                    <input type="text" id="url_immagine" name="url_immagine"  class="inputBox" value="${Oggetto.getUrlImmagine()}"/>
                    <label for="descrizione" >Descrizione:</label>
                    <textarea id="descrizione" name="descrizione" rows="4" cols="30" class="inputBox" >                        
                        ${Oggetto.getDescrizione()}
                    </textarea>



                    <label id="labelPrezzo" for="inputPrezzo">
                        Prezzo in €: 
                    </label>
                    <input type="number" id="inputPrezzo" name="prezzo" min="1" class="inputBox" value="${Oggetto.getPrezzo()}"/>
                    <label id="labelPezzi" for="inputPezzi">
                        Quantit&agrave; pezzi:
                    </label>
                    <input type="number" id="inputPezzi" name="pezzi" min="1" class="inputBox" value="${Oggetto.getNumPezzi()}"/>

                    <input type="hidden" name="visualizzazione" value="riepilogo"/>
                    <input type="hidden" name="venditore_id" value="${utente.getId()}"/>
                    <c:if test="${azione eq 'modifica'}">
                        <input type="hidden" name="nuovoOggetto" value="no"/>
                        <input type="hidden" name="idOggetto" value="${Oggetto.getId()}" id="id"/>
                    </c:if>


                    <button type="submit" name="Submit" class="conferma" >
                        Conferma
                    </button>

                </form>


            </div> <!-- FINE ELEMENTO LAYOUT CONTENT -->

            <footer></footer>

        </div> <!-- FINE ELEMENTO LAYOUT PAGE -->

    </body>
</html>
