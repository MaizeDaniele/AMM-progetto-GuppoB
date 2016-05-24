<%-- 
    Document   : ConfermaOggetto
    Created on : 1-mag-2016, 11.29.39
    Author     : Daniele  Caschili
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Daniele Caschili">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, ACQUISTI, PAGINA CONFERMA ACQUISTO">
        <meta name="description" content="Pagina dedicata alla conferma dell'acquisto">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">                
        <title>SPACEZON: Conferma Acquisto</title>
    </head>
    <body id='confermaOggetto'>
        <div id='page'>
            <jsp:include page="./jspGeneriche/Header.jsp"/>
            <jsp:include page="./jspGeneriche/NavigationSideBar.jsp"/>

            <div id="content">
                <h1 id="titoloConferma">Conferma Inserimento Dati</h1>



                <h2>Nome</h2>
                <p>${Oggetto.getNome()}</p>

                <h2>Descrizione</h2>
                <p>${Oggetto.getDescrizione()}</p>

                <h2>Url Immagine</h2>
                <p>${Oggetto.getUrlImmagine()}</p>

                <h2>Numero Pezzi</h2>
                <p>${Oggetto.getNumPezzi()}</p>

                <h2>Prezzo</h2>
                <p>${Oggetto.getPrezzo()} €</p> 




                <c:choose>
                    <c:when test="${tipoSessione eq 'Cliente'}">
                        <h2>Numero di oggetti:</h2>
                        <p>${quantità}</p>

                        <h2>Totale</h2>
                        <p>${quantità * Oggetto.getPrezzo()} €</p>
                        <form action="Cliente.html" method="post">
                            <input type="hidden" name="visualizzazione" value="esito"/>
                            <input type="hidden" name="oggettoId" value="${Oggetto.getId()}"/>
                            <input type="hidden" name="quant" value="${quantità}"/>

                            <button type="submit" name="Submit" class="conferma confermaOggetto">
                                Conferma
                            </button>
                        </form>

                        <a href="Cliente.jsp" id="annulla">Annulla</a>                                           
                    </c:when>

                    <c:when test="${tipoSessione eq 'Venditore'}">
                        <form action="Venditore.html" method="post">
                            <input type="hidden" name="visualizzazione" value="principale"/>

                            <input type="hidden" name="nome_oggetto" value="${Oggetto.getNome()}"/>
                            <input type="hidden" name="url_immagine" value="${Oggetto.getUrlImmagine()}"/>
                            <input type="hidden" name="descrizione" value="${Oggetto.getDescrizione()}"/>
                            <input type="hidden" name="prezzo" value="${Oggetto.getPrezzo()}"/>
                            <input type="hidden" name="pezzi" value="${Oggetto.getNumPezzi()}"/>
                            <input type="hidden" name="idOggetto" value="${Oggetto.getId()}"/>
                            <input type="hidden" name="venditore_id" value="${Oggetto.getVendId()}"/>
                            
                            <c:if test="${nuovoOggetto eq 'no'}">
                               <input type="hidden" name="nuovoOggetto" value="no"/>
                           </c:if>
                            
                            <button type="submit" name="Submit" class="conferma confermaOggetto">
                                Conferma
                            </button>
                        </form>

                        <!-- RITORNA AL FORM DELLA PAGINA VENDITORE -->
                        <a href="Venditore.jsp" id="annulla">Annulla</a>
                    </c:when>

                </c:choose>

            </div>
        </div>
    </body>
</html>
