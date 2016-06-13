<%-- 
    Document   : generaJSONlistaOggetti
    Created on : 9-giu-2016, 10.12.42
    Author     : Daniele  Caschili
--%>

<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<json:array>
    <c:forEach var="oggetto" items="${listaOggetto}">
        <json:object>
            <json:property name="nome" value="${oggetto.nome}"/>
            <json:property name="descrizione" value = "${oggetto.descrizione}"/>
            <json:property name="immagine" value="${oggetto.urlImmagine}"/>
            <json:property name="numeropezzi" value="${oggetto.numPezzi}"/>
            <json:property name="prezzo" value="${oggetto.prezzo}"/>
            <json:property name="idOggetto" value="${oggetto.id}"/>
        </json:object>
    </c:forEach>
</json:array>
