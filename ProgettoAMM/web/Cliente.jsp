<%-- 
    Document   : Cliente
    Created on : 29-apr-2016, 19.53.11
    Author     : Daniele  Caschili
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>SPACEZON:Catalogo Acquisti</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Daniele Caschili">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, ACQUISTI, PAGINA CLIENTE">
        <meta name="description" content="Pagina dedicata al cliente con possibilt&agrave; di acquistare i prodotti">
        <link rel="stylesheet" href="style.css" type="text/css" media="screen">
    </head>

    <body id="cliente">
        <!-- INIZIO ELEMENTO LAYOUT PAGE -->
        <div id="page">

            <jsp:include page="./jspGeneriche/Header.jsp"/>
            <jsp:include page="./jspGeneriche/NavigationSideBar.jsp"/>    
           
            <!-- INIZIO ELEMENTO LAYOUT CONTENT -->
            <div id="content">

                <!-- TABELLA PRODOTTI -->

                <h2>
                    Pagina Cliente
                </h2>
                
                <jsp:include page="./jspGeneriche/TabellaOggetti.jsp"/>
                    
                    
                
                
            </div>

            <footer></footer>
        </div>
    </body>
</html>


