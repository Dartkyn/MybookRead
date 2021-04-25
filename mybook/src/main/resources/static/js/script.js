
      $(document).ready(function(){   
         $("#loadcatalog").on("click", function(event){  
            $.ajax({  
               url:        'publishers',  
               type:       'GET',
               dataType:   'json',
               async:      true,  
               success: function(data) {  
                   var e = $('<tr><th><a type="button" id = "new" class="btn btn-secondary " href=# >Добавить</a></th></tr>');  
                   $('#catalog_tbody').html('');  
                   $('#catalog_tbody').append(e);  
                  
                   for(i = 0; i < data.length; i++) {  
                     publisher = data[i];
                     console.log(publisher); 
                     var e = $('<tr><td id = "name"></td><td></td></tr>');
                     
                      $('#name', e).html(publisher['name']);  
                     $('#catalog_tbody').append(e);  
                   } 
                  console.log(data);
                  $("table").on('click', '#new', function(event, data){
                     $.ajax({  
                              url:        '/publisher/new',  
                              type:       'GET',
                            async:      true,  
                        success: function(data) {  
                            var e = $('<tr><td><form th:action="/publisher/new" id = "newForm"th:object="${publisher}" method="post"><input type="text" id="publishName"placeholder= "Введите наименование издательства"name = "name" class="form-control" /></td><td><a type="button submit" id = "addNew" class="btn btn-secondary " href=# >Добавить</a></td></form></tr>');  
                           $('#catalog_tbody').html(data);  
                           $('#catalog_tbody').append(e); 
                        },  
                error : function(xhr, textStatus, errorThrown) {  
                   alert('Ajax request failed.');  
                }  
             });  
          });  
            $('table').on('click', '#addNew',function (event) {
               console.log('submitting');
               $.ajax({
                  type: "POST",
                  url: '/publisher/addnew',
                  data: $('#publishName'),
                  async: true,
                  success: function (data) {
                     console.log('success edit');
                  }
               });
               return false;
            });
               },  
               error : function(xhr, textStatus, errorThrown) {  
                  alert('Ajax request failed.');  
               }  
            });  
         }); 
      });  

