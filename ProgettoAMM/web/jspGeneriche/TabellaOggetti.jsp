<%-- 
    Document   : TabellaOggetti
    Created on : 1-mag-2016, 10.07.36
    Author     : Daniele  Caschili
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="./js/jquery-2.2.4.js"></script>
        <script type="text/javascript" src="./js/filtra.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <table id="tabella">
            <tr>
                <th>
                    Nome Prodotto
                </th>
                <th>
                    Immagine 
                </th>

                <th>
                    Pezzi disponibili
                </th>
                <th>
                    Prezzo €
                </th>
                <th id="vuoto"></th>
            </tr> <!-- Intestazioni colonne -->

            <c:forEach var="oggetto" items="${listaOggetti}">
                <tr>
                    <td>
                        ${oggetto.getNome()}
                    </td>
                    <td>
                        <img title="${oggetto.getDescrizione()}" src="${oggetto.getUrlImmagine()}" 
                             alt="${oggetto.getDescrizione()}" width='320' height='200'/>

                    </td>
                    <td>
                        ${oggetto.getNumPezzi()}
                    </td>
                    <td>
                        ${oggetto.getPrezzo()}
                    </td>
                    <td>
                        <c:if test="${tipoSessione eq 'Cliente'}">
                            <form action="Cliente.html" method="post">
                                <label for="quant">
                                    Quantit&agrave;
                                </label>
                                <input type="number"  name="quant" id="${oggetto.getId()}" 
                                       class="inputBox"/>
                                <input type="hidden" name="idOggetto" value="${oggetto.getId()}"/>

                                <!-- Imposta una variabile per gestire la logica della servlet, informandola sulla jsp attuale
                                    nello specifico fa gestire alla servlet la tabella-->
                                <input type="hidden" name="visualizzazione" value="riepilogo"/>

                                <button type="submit" name="Submit" class="conferma" id="buttonAcquista">
                                    Acquista
                                </button>
                            </form>
                        </c:if>
                        <c:if test="${tipoSessione eq 'Venditore'}">
                            <form action="Venditore.html" method="post">
                                <input type="hidden" name="idOggetto" value="${oggetto.getId()}" />
                                <input type="hidden" name="visualizzazione" value="modificaOggetto"/>
                                <button type="submit" name="Submit" class="conferma" id="buttonAcquista">
                                    Modifica
                                </button>
                            </form>
                            <form action="Venditore.html" method="post">
                                <input type="hidden" name="idOggetto" value="${oggetto.getId()}"/>
                                <input type="hidden" name="visualizzazione" value="eliminaOggetto"/>
                                <button type="submit" name="Submit" class="conferma" id="buttonAcquista">
                                    Elimina
                                </button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>


        

        
    </body>
</html>

