/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(
        function () {

            $("#ricerca").keyup(
                    function () {

                        //variabile locale contenente i valori inseriti dall'utente nel campo di input
                        var text = $("#ricerca").val();
                        $.ajax({
                            url: "filter.json",
                            data: {
                                cmd: "q",
                                text: text
                            },
                            dataType: 'json',
                            success: function (data, state) {
                                aggiornaTabellaOggetti(data);
                            },
                            error: function (data, state) {

                            }
                        });
                        //Funzione eseguita in caso di successo
                        function aggiornaTabellaOggetti(oggetti) {
                            //Ho ricevuto il file json contenente i dati da visualizzare, devo modificare la pagina dinamicamente mediante jQuery


                            //Elimino tutti gli elementi dalla tabella degli oggetti da acquistare
                            $("#tabella").empty();

                            //Elimino eventuali messaggi di errore creati in una ricerca precedente
                            $("#titleError").remove();
                            $("#paragraphError").remove();


                            length = oggetti.length;
                            
                            //Se la lunghezza non è definita vuol dire che non ho trovato oggetti
                            if(length === 0){
                                $("#tabella").remove();
                                creaMessaggioErrore();
                                return;
                            }
                            
                            //Per ogni oggetto trovato creo una riga della tabella
                            for (var i = 0; i < length; i++) {

                                var oggetto = oggetti[i];
                                
                                creaRigaTabella(oggetto);
                                                                
                            }

                            //Creo gli header delle colonne
                            $("#cliente #page #content").append("<table id=\"tabella\"></table>");
                            $("#tabella").prepend("<tr id=\"head\"></tr>");
                            $("#tabella #head").append("<th>Nome Prodotto</th>");
                            $("#tabella #head").append("<th>Immagine</th>");
                            $("#tabella #head").append("<th>Pezzi Disponibili</th>");
                            $("#tabella #head").append("<th>Prezzo €</th>");
                            $("#tabella #head").append("<th id=\"vuoto\"></th>");


                        }






                        //Funzione che crea un titolo e un paragrafo utili per il messaggio d'errore da mostrare al cliente
                        function creaMessaggioErrore() {

                            //Creo un messaggio di errore con un titolo e un paragrafo
                            $("#cliente #page #content").append("<h3>Errore!</h3>");
                            $("#cliente #page #content h3").attr("id", "titleError");
                            $("#cliente #page #content").append("<p>Nessun oggetto trovato, inserisci dei nuovi parametri di ricerca!</p>");
                            $("#cliente #page #content p").attr("id", "paragraphError");
                        }

                        //Funzione che crea una singola riga della tabella in base a un oggetto json passato
                        function creaRigaTabella(oggetto) {

                            //Creo la riga che ospiterà tutti i dati dell'oggetto e aggiungo un identificatore
                            $("#tabella").append("<tr id=\"" + oggetto.idOggetto + "\"></tr>");
                            //Creo tutti i td
                            //Colonna NOME
                            $("#tabella #" + oggetto.idOggetto).append("<td>" + oggetto.nome + "</td>");
                            //Colonna IMMAGINE
                            $("#tabella #" + oggetto.idOggetto).append("<td><img></img></td>");
                            $("#tabella #" + oggetto.idOggetto + " img").attr("title", oggetto.descrizione);
                            $("#tabella #" + oggetto.idOggetto + " img").attr("src", oggetto.immagine);
                            $("#tabella #" + oggetto.idOggetto + " img").attr("alt", oggetto.descrizione);
                            $("#tabella #" + oggetto.idOggetto + " img").attr("width", "320");
                            $("#tabella #" + oggetto.idOggetto + " img").attr("height", "200");
                            //Colonna NUMEROPEZZI
                            $("#tabella #" + oggetto.idOggetto).append("<td>" + oggetto.numeropezzi + "</td>");
                            //Colonna PREZZO
                            $("#tabella #" + oggetto.idOggetto).append("<td>" + oggetto.prezzo + "</td>");
                            //Colonna FORM
                            $("#tabella #" + oggetto.idOggetto).append("<td><form></form></td>");
                            //Attributi del form
                            $("#tabella #" + oggetto.idOggetto + " form").attr("action", "Cliente.html");
                            $("#tabella #" + oggetto.idOggetto + " form").attr("method", "post");
                            //Aggiungo gli elementi html interni al form
                            //LABEL
                            $("#tabella #" + oggetto.idOggetto + " form").append("<label>Quantit&agrave;</label>");
                            //ATTRIBUTI LABEL
                            $("#tabella #" + oggetto.idOggetto + " form label").attr("for", "quant");
                            //CAMPI INPUT
                            $("#tabella #" + oggetto.idOggetto + " form").append("<input type=\"number\" name=\"quant\" id=\"" + oggetto.idOggetto + "\" class=\"inputBox\"/>");
                            $("#tabella #" + oggetto.idOggetto + " form").append("<input type=\"hidden\" name=\"idOggetto\" value=\"" + oggetto.idOggetto + "\"/>");
                            $("#tabella #" + oggetto.idOggetto + " form").append("<input type=\"hidden\" name=\"visualizzazione\" value=\"riepilogo\"/>");
                            //BUTTON
                            $("#tabella #" + oggetto.idOggetto + " form").append("<button>Acquista</button>");
                            //ATTRIBUTI BUTTON
                            $("#tabella #" + oggetto.idOggetto + " form button").attr("id", "buttonAcquista");
                            $("#tabella #" + oggetto.idOggetto + " form button").attr("type", "submit");
                            $("#tabella #" + oggetto.idOggetto + " form button").attr("name", "Submit");
                            $("#tabella #" + oggetto.idOggetto + " form button").attr("class", "conferma");



                        }
                    });
        });