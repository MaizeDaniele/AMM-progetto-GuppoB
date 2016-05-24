<%-- 
    Document   : RiepilogoOggetti
    Created on : 23-mag-2016, 15.14.50
    Author     : Daniele  Caschili
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Daniele Caschili">
        <meta name="description" content="Riepilogo oggetti in vendita">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, OGGETTI IN VENDITA">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
        <title>SPACEZON: Riepilogo Oggetti in Vendita</title>
    </head>
    
    <body id="riepilogoOggetti">
        <div id='page'>

            <jsp:include page="./jspGeneriche/Header.jsp"/>
            <jsp:include page="./jspGeneriche/NavigationSideBar.jsp"/>

            <div id='content'>

                <jsp:include page="./jspGeneriche/Errore.jsp"/>

                <jsp:include page="./jspGeneriche/TabellaOggetti.jsp"/>
            </div>
        </div>
    </body>
</html>
