
      $(document).ready(function(){   
         $("#loadcatalog").on("click", function(event){  
            $.ajax({  
               url:        'publishers',  
               type:       'GET',
               dataType:   'json',
               async:      true,  
               success: function(data) {  
                   var e = $('<tr><th><a type="button" id = "edit" class="btn btn-secondary " href=# >Редактировать</a></th></tr>');  
                   $('#catalog_tbody').html('');  
                   $('#catalog_tbody').append(e);  
                  
                   for(i = 0; i < data.length; i++) {  
                     publisher = data[i];
                     console.log(publisher); 
                     var e = $('<tr><td id = "name"></td><td><button type="button" id = "editID" class="btn btn-secondary">Редактировать</button></td></tr>');
                     
                      $('#name', e).html(publisher['name']);  
                     $('#catalog_tbody').append(e);  
                   } 
                  console.log(data); 
               },  
               error : function(xhr, textStatus, errorThrown) {  
                  alert('Ajax request failed.');  
               }  
            });  
         });
       $("table").on('click', '#edit', function(){
              console.log( $(this));
             $.ajax({  
                url:        'publisher/1',  
                type:       'GET',
                async:      true,  
                success: function(data) {  
                   console.log(data); 
                },  
                error : function(xhr, textStatus, errorThrown) {  
                   alert('Ajax request failed.');  
                }  
             });  
          });  
      });  

