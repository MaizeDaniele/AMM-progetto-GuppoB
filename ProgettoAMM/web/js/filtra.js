/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(
        function(){
           
            $('#ricerca').keyup(
                    function(){
                        
                        //variabile locale contenente i valori inseriti dall'utente nel campo di input
                        var text = $('#ricerca').val();
                        
                        $.ajax({
                            url: 'filter.json',
                            data:{
                                cmd : 'q',
                                text : text
                            },
                            dataType: 'json',
                            success: function (data, textStatus, jqXHR) {
                                aggiornaTabellaOggetti(data);
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                
                            }
                        });
                     
                        
                        //Funzione eseguita in caso di successo
                        function aggiornaTabellaOggetti(oggetti){
                            //Ho ricevuto il file json contenente i dati da visualizzare, devo modificare la pagina dinamicamente mediante jQuery
                        }
                        
                        
                    }
                    );
            
        });