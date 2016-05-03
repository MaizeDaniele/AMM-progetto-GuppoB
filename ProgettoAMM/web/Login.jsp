<%-- 
    Document   : Login
    Created on : 29-apr-2016, 19.53.30
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
        <title>SPACEZON:Login</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Daniele Caschili">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, ACQUISTI, LOGIN">
        <meta name="description" content="pagina di login del sito Spacezon">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
    </head>

    <body id="login">
        <!-- ELEMENTO LAYOUT PAGE -->
        <div id="page">

            <jsp:include page="./jspGeneriche/Header.jsp"/>
            <jsp:include page="./jspGeneriche/NavigationSideBar.jsp"/>    






            <!--SEZIONE LOGIN -->
            <div id="content">
                
                <jsp:include page="./jspGeneriche/Errore.jsp"/>
                
                <h3>
                    Inserisci i dati d'accesso:
                </h3>
                <!-- va inserito l'attributo action, l'URL della pagine del server che riceverà i dati contenuti nel form -->
                <form action='Login.html' method='post'>
                    <label for="username">Username:</label>
                    <input type="text" name="username" id="username" class="inputBox"  />
                    <label for="password" id="labelPassword">Password:</label>
                    <input type="password" name="password"  id="password" class="inputBox"  />
                    <button type='submit' name="Submit" class="conferma">Conferma</button>
                    
                </form>

            </div><!-- FINE ELEMENTO LAYOUT CONTENT -->

            <footer></footer>

        </div> <!-- FINE ELEMENTO LAYOUT PAGE -->
    </body>
</html>
