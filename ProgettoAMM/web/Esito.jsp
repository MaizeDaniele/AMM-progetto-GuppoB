<%-- 
    Document   : Esito
    Created on : 1-mag-2016, 12.23.10
    Author     : Daniele  Caschili
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta name="author" content="Daniele Caschili">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, ACQUISTI, PAGINA ESITO">
        <meta name="description" content="Pagina dedicata all'esito dell'acquisto">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
        <title>SPACEZON: Esito Acquisto</title>
    </head>
    <body id='esito'>
        <div id='page'>
            <jsp:include page="./jspGeneriche/Header.jsp"/>
            <jsp:include page="./jspGeneriche/NavigationSideBar.jsp"/>

            <div id='content'>
                <jsp:include page="./jspGeneriche/Errore.jsp"/>
                <c:if test="${tipoSessione eq 'Cliente'}">
                    <a href="Cliente.jsp" class="esitoButton">
                        <button class="conferma">
                            Ok
                        </button>
                    </a>
                </c:if>
                <c:if test="${tipoSessione eq 'Venditore'}">
                    <a href="Venditore.jsp" class="esitoButton">
                        <button class="conferma">
                            Ok
                        </button>
                    </a>
                </c:if>
            </div> 
        </div>
    </body>
</html>
