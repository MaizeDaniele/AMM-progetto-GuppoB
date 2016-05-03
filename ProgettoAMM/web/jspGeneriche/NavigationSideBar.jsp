<%-- 
    Document   : NaviagationSideBar
    Created on : 30-apr-2016, 17.49.29
    Author     : Daniele  Caschili
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <body>


        <!-- INDICE DI NAVIGAZIONE -->
        <nav  id="navigation_sidebar" >
            <h2>Indice di Navigazione:</h2>
            <ul id="dropDownList">

                <c:choose>

                    <c:when test="${tipoSessione.equals('Cliente')}">
                        <!--PAGINA CLIENTE-->
                        <li>
                            <a href='Cliente.jsp'>
                                Prodotti
                            </a>
                        </li>
                        <li>
                            <a href='PaginaUtente.jsp'>
                                Utente
                            </a>
                        </li>
                        <li>
                            <form action="Cliente.html" method="post">
                                <input type="hidden" name="visualizzazione" value="erroreAutenticazione" />
                                <button type="submit" name="Submit">
                                    Cliente
                                </button>
                            </form>
                        </li>


                    </c:when>

                    <c:when test="${tipoSessione.equals('Venditore')}">                        
                        <!--PAGINA VENDITORE-->
                        <li>
                            <a href="Venditore.jsp">
                                Form
                            </a>

                        </li> 
                        <li>
                            <a href="PaginaUtente.jsp">
                                Utente
                            </a>
                        </li>
                        <li>
                            <form action="Venditore.html" method="post">
                                <input type="hidden" name="visualizzazione" value="erroreAutenticazione" />
                                <button type="submit" name="Submit">
                                    Cliente
                                </button>
                            </form>
                        </li>
                    </c:when>

                    <c:otherwise>
                        <!--PAGINA LOGIN-->
                        <li> 
                            <a href="Descrizione.jsp">
                                Descrizione
                            </a>

                        </li>

                    </c:otherwise>

                </c:choose>

            </ul>
        </nav>


    </body>

</html>
