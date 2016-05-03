<%-- 
    Document   : PaginaUtente
    Created on : 2-mag-2016, 18.43.01
    Author     : Daniele  Caschili
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Daniele Caschili">
        <meta name="description" content="Pagina Utente, riepilogo informazioni">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, ACQUISTI, PAGINA UTENTE">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
        <title>SPACEZON: Pagina Utente</title>
    </head>

    <body>
        <div id='page'>

            <jsp:include page="./jspGeneriche/Header.jsp"/>
            <jsp:include page="./jspGeneriche/NavigationSideBar.jsp"/>
            
            <div id='content'>
                
                <h1>Riepilogo Dati</h1>
                
                <h2>Nome</h2>
                <p>${utente.getNome()}</p>

                <h2>Cognome</h2>
                <p>${utente.getCognome()}</p>

                <h2>Username</h2>
                <p>${utente.getUsername()}</p>

                <h2>Password</h2>
                <p>${utente.getPassword()}</p>

                <h2>Saldo</h2>
                <p>${utente.getSaldo().getFondi()} €</p>

                <h2>ID</h2>
                <p>${utente.getId()}</p>

                <c:if test="${tipoSessione eq 'Venditore'}">
                    <h2>Societ&agrave;</h2>
                    <p>${utente.getSocietà()}</p>

                    <form action="Venditore.html" method="post">
                        <input type="hidden" name='visualizzazione' value="logout"/>
                        <button type="submit" name="Submit" class="conferma">
                            Logout
                        </button>
                    </form>

                </c:if>

                <c:if test="${tipoSessione eq 'Cliente'}">


                    <form action="Cliente.html" method="post">
                        <input type="hidden" name='visualizzazione' value="logout"/>
                        <button type="submit" name="Submit" class="conferma">
                            Logout
                        </button>
                    </form>

                </c:if>



            </div>
        </div>
    </body>
</html>
