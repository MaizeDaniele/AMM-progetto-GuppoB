<%-- 
    Document   : Descrizione
    Created on : 29-apr-2016, 19.53.21
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
        <title>SPACEZON:Descrizione Sito</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Daniele Caschili">
        <meta name="keywords" content="ASTRONAVI, SPAZIO, ACQUISTI, DESCRIZIONE">
        <meta name="description" content="Pagina di descrizione del sito Spacezon">
        <link rel="stylesheet" type="text/css" href="style.css" media="screen">
    </head>
    <body id="descrizione">

        <!-- INIZIO ELEMENTO LAYOUT PAGE -->
        <div id="page">
                        
            <jsp:include page="./jspGeneriche/Header.jsp"/>
            
            <!-- INDICE DI NAVIGAZIONE -->
            <nav  id="navigation_sidebar" >
                <h2>Indice di Navigazione:</h2>
                <ul id="dropDownList">
                    <li>
                        <a href="Login.jsp">
                            Login
                        </a>
                    </li>
                </ul>
            </nav>

            <!-- INIZIO ELEMENTO LAYOUT CONTENT -->
            <div id="content" >

                <!-- INDICE -->
                <div id="descrizioneNav">
                    <p>Benvenuti su SPACEZON, il miglior sito di e-commerce di tutto l'universo!</p>
                    
                    <ol>
                        <li>
                            <!-- Url Relativo, link ai paragrafi di questa stessa pagina -->
                            
                                I nostri prodotti:
                            
                            <ul>
                                <li>
                                    <a href="#sezione_astronavi">
                                        Astronavi
                                    </a>
                                </li>
                                
                               
                            </ul>    
                        </li>
                        <li>
                            
                                Funzionalit&agrave; del sito:
                            

                            <ul>
                                <li>
                                    <a href="#sezione_cliente">
                                        Cliente
                                    </a>
                                </li>
                                <li>
                                    <a href="#sezione_venditore">
                                        Venditore
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ol>
                </div>


                <!-- PRODOTTI IN VENDITA -->    
                <article id='prodotti'>
                    
                        <h2>
                            I NOSTRI PRODOTTI
                        </h2>
                    

                    <p id="primoParagrafoProdotti">
                        Il nostro sito vi dà la possibiltà di acquistare tutto ciò di cui avete bisogno
                        per preparare il vostro viaggio interstellare:
                    </p>

                    <a id="sezione_astronavi">                                                                
                    <h3 id='titoloAstronavi'>
                            ASTRONAVI
                        </h3>
                    </a>
                        
                    <p id='paragrafoAstronavi'>
                        Nel nostro sito potrete trovare navi di tutte le dimensioni e per ogni necessità, 
                        da grossi cargo mercantili a incrociatori capaci di giungere fino ai confini dell’universo conosciuto.
                        Siamo i migliori in questo campo, perfino la marina imperiale acquista le nostre astronavi per potenziare le sue flotte, dunque la 
                        qualit&agrave; &egrave; garantita!
                        <br/>Se non sarete soddisfatti dei nostri prodotti sarete rimborsati istantaneamente, entro e non oltre due settimane dall'acquisto!
                    </p>

                    
                </article>


                
                <!-- FUNZIONI SITO -->
                
                <article id='funzioni'>
                    <a id="sezione_funz">
                        <h2>
                            FUNZIONALIT&Aacute; SITO
                        </h2>
                    </a>

                    <p>
                        Sia che siate pirati che devono vendere i loro bottini o 
                        semplici impiegati che hanno bisogno di una navicella per raggiungere il proprio ufficio 
                        il nostro sito vi offre tutta l'assistenza che desiderate. Di seguito sono elencate le funzionalit&agrave; principali di SPACEZON:
                    </p>
                    
                    <a id="sezione_cliente">
                        <h3>
                            CLIENTE
                        </h3>
                    </a>
                    
                    <p id="paragrafoCliente">
                        Dopo aver effettuato il login da cliente potrete accedere al nostro catalogo con 
                        foto e accurate descrizioni dei prodotti.  
                        Ovviamente con la giusta quantità di fondi potrete comprare quello che volete.
                    </p>

                    <a id="sezione_venditore">
                        <h3>
                            VENDITORE
                        </h3>
                    </a>
                    
                    <p id="paragrafoVenditore">
                        Dopo aver effettuato il login da venditore potrete aggiungere i vostri prodotti.
                        Vi verr&agrave; richiesto di inserire il nome, 
                        un'immagine, una breve descrizione e il prezzo dell'oggetto. Inoltre dovrete inserire la quantit&agrave; di pezzi in vendita.
                        Penseremo noi al ritiro dai vostri magazzini e alle spese di spedizione.
                    </p>
                </article>

            </div> <!-- FINE ELEMENTO LAYOUT CONTENT -->
                    
            <footer></footer>

        </div><!-- FINE ELEMENTO LAYOUT PAGE -->
    </body>
</html>
