<%-- 
    Document   : generaJSONlistaOggetti
    Created on : 9-giu-2016, 10.12.42
    Author     : Daniele  Caschili
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<json:array>
    <c:forEach var="oggetti" items="${listaOggetto}">
        <json:object>
            <json:property name="nome" value="${listaOggetto.nome}"/>
            <json:property name="immagine" value="${listaOggetto.urlimmagine}"/>
            <json:property name="numeropezzi" value="${listaOggetto.numeropezzi}"/>
            <json:property name="prezzo" value="${listaOggetto.prezzo}"/>
        </json:object>
    </c:forEach>
</json:array>
