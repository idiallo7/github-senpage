$(document).ready(function() {alert("today: "+Date());
    $(function() {
        $("#term").autocomplete( {
         source: function(request, response) {
           $.ajax( {
            url: "servlet/senpageCtrl/?request_view=senpageAjaxSetRpt&requestType=test",
            type: "GET",
            data: {
             term: request.term
            },
            dataType: "json",
            success: function(data) {
              response(data);
            }
           });
         }
        });
      });
  })
 