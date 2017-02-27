//////////////////////////////////// senpageAjax ///////////////////////
var senpageSetRptXmlHttp = null;
var senpageGetLoginInfoXmlHttp = null;
var senpageGetCurrUserXmlHttp = null;
var reqType = null;
var url = null;

function senpageInitXMLHttpRequest()
{
 var req;

 try
 {
  req = new XMLHttpRequest();
 } catch (e)
 {
  try
  {
   req = new ActiveXObject("Msxml2.XMLHTTP");
  } catch (e)
  {
   try
   {
    req = new ActiveXObject("Microsoft.XMLHTTP");
   } catch (e)
   {
    alert("Your browser does not support AJAX!");
    return false;
   }
  }
 }
 return req;
}

function res()
{
	alert("res");
}

//////////////////////////////// senpageSetRpt///////////////////////////////////////

function senpageSetRpt(requestType)
{
 reqType = requestType;

 alert("requestType: " + reqType);

 if(reqType == "main")
 {
  url = "servlet/senpageCtrl/?request_view=senpageAjaxSetRpt&requestType=" + reqType;
  senpageSetRptSetURL();
 }
 else if (reqType == "senpageAutoComplete")
 {alert('kes');
  /*var category = document.getElementById("category").value;
  url = "servlet/senpageCtrl/?request_view=senpageSetRpt&requestType=" + reqType + "&category=" + encodeURIComponent(category);
  senpageSetRptSetURL();*/
 }
 else if(reqType == "test")  ///////////////////////// Test
 {alert("in test reqtype");
		var term=document.getElementById("tem").value;
  url = "servlet/senpageCtrl/?request_view=senpageAjaxSetRpt&requestType=" + reqType+"&term="+encodeURIComponent(term);
  senpageSetRptSetURL();
 }
 else
 {
  url = "servlet/senpageCtrl/?request_view=senpageAjaxSetRpt&requestType=" + reqType;
  senpageSetRptSetURL();
 }
}

//////////////////////////////////////////////////////////senpageSetRptSetURL////////////////////////////////////////////////////////
function senpageSetRptSetURL()
{
 senpageSetRptXmlHttp = new senpageInitXMLHttpRequest();
 senpageSetRptXmlHttp.onreadystatechange = senpageSetRptStateChanged;
 senpageSetRptXmlHttp.open("GET", url, true);
 senpageSetRptXmlHttp.send(null);

 return;
}
/*/////////////////////////////////////// senpageSetRptStateChanged////////////////////////////*/
function senpageSetRptStateChanged()
{
 if(reqType == "main")//main
 {
  if(senpageSetRptXmlHttp.readyState == 4 || senpageSetRptXmlHttp.readyState == "complete")
  {
   document.getElementById("senpageReportWaiter").innerHTML = senpageSetRptXmlHttp.responseText;  //resArr[0];
   //senpageGetCurrUser();
  }
  else
  {
   document.getElementById("senpageReportWaiter").innerHTML = "<i class=\"fa fa-refresh fa-spin\"></i>";
  }
 }
 else if(reqType == "senpageGetJasonOut")
 {
  if(senpageSetRptXmlHttp.readyState == 4 || senpageSetRptXmlHttp.readyState == "complete")
  {
   //document.getElementById("senpageReportWaiter").innerHTML = senpageSetRptXmlHttp.responseText;
   // Javascript function JSON.parse to parse JSON data
   var jsonObj = JSON.parse(senpageSetRptXmlHttp.responseText);

   // jsonObj variable now contains the data structure and can
   // be accessed as jsonObj.name and jsonObj.country.
   //document.getElementById("Name").innerHTML = jsonObj.name;
   //document.getElementById("Country").innerHTML = jsonObj.country;

   for(var key in jsonObj)
   {
    if (jsonObj.hasOwnProperty(key))
    {
     var val = jsonObj[key];
     document.getElementById("senpageReportWaiter").innerHTML =val;
     console.log(val);
    }
   }
  }
  else
  {
   document.getElementById("senpageReportWaiter").innerHTML = "<i class=\"fa fa-refresh fa-spin\"></i>";
  }
 }
 else if(reqType == "senpageSignOff")
 {
  if(senpageSetRptXmlHttp.readyState == 4 || senpageSetRptXmlHttp.readyState == "complete")
  {
   document.getElementById("lpms").disabled = true;                                                                                                                                                                                                                                                                                                                                                            //disable drop down menu(s)
   document.getElementById("home").className = "navbar navbar-default2 navbar-fixed-top";
   document.getElementById("senpageReportWaiter").innerHTML = senpageSetRptXmlHttp.responseText;
   //senpageGetCurrUser();
  }
  else
  {
   document.getElementById("senpageReportWaiter").innerHTML = "<i class=\"fa fa-refresh fa-spin\"></i>";
  }
 }
 else                                                                                                                                                                                                                                                                                                                                                                                                          // finally unknown Request
 {
  if(senpageSetRptXmlHttp.readyState == 4 || senpageSetRptXmlHttp.readyState == "complete")
  {
   document.getElementById("senpageReportWaiter").innerHTML = senpageSetRptXmlHttp.responseText;
  }
  else
  {
   document.getElementById("senpageReportWaiter").innerHTML = "<i class=\"fa fa-refresh fa-spin\"></i>";
  }
 }
}

/*/                                                                                                                                                                                                                                                                                                                                                                                                            /////////////////////////////////////////////////////////////// senpageGetLoginInfo ///////////////////////////////////////////////////////
function senpageGetLoginInfo(user, grp)
{
 senpageGetLoginInfoXmlHttp = new senpageInitXMLHttpRequest();
 var url = "servlet/senpageCtrl/?request_view=senpageGetLoginInfo&user="+encodeURIComponent(user)+"&grp="+encodeURIComponent(grp);
 senpageGetLoginInfoXmlHttp.onreadystatechange = senpageGetLoginInfoStateChanged;
 senpageGetLoginInfoXmlHttp.open("GET",url,true);
 senpageGetLoginInfoXmlHttp.send(null);

 return;
}

function senpageGetLoginInfoStateChanged()
{
 if(senpageGetLoginInfoXmlHttp.readyState==4 || senpageGetLoginInfoXmlHttp.readyState=="complete")
 {
  senpageSetRpt('main');
  document.getElementById("senpageReportWaiter").innerHTML = senpageGetLoginInfoXmlHttp.responseText;
  window.status = "Done";
  document.body.style.cursor = "default";
 }
 else
 {
  document.getElementById("senpageReportWaiter").innerHTML = "<i class=\"fa fa-refresh fa-spin\"></i>";
  window.status = "Please wait...";
  document.body.style.cursor = "wait";
 }
}


//////////////////////////////////////////////////////senpageGetCurrUser() ///////////////////////////////////
function senpageGetCurrUser()
{
 senpageGetCurrUserXmlHttp = new senpageInitXMLHttpRequest();
 var url = "servlet/senpageCtrl?request_view=senpageSetRpt&requestType=senpageCurrUser";
 senpageGetCurrUserXmlHttp.onreadystatechange = senpageGetCurrUserStateChanged;
 senpageGetCurrUserXmlHttp.open("GET",url,true);
 senpageGetCurrUserXmlHttp.send(null);

 return;
}

function senpageGetCurrUserStateChanged()
{
 if(senpageGetCurrUserXmlHttp.readyState==4 || senpageGetCurrUserXmlHttp.readyState=="complete")
 {
  var userInfo = senpageGetCurrUserXmlHttp.responseText.split("~~");
  document.getElementById("senpageCurrUserWaiter").innerHTML = userInfo[0];
  document.getElementById("senpageCurrGrpWaiter").innerHTML = userInfo[1];
 }
 else
 {
  document.getElementById("senpageCurrUserWaiter").innerHTML = "<i class=\"fa fa-refresh fa-spin\"></i>";
 }
}
*/
//////////////////////////////////////////////////////////validateElement////////////////////////////////////////////////
function validateElement(arrElmts, num)
{
 var ok = true;

 for(var i = 0; i < arrElmts.length; i++)
 {
  if(arrElmts[i] == "")
  {
   ok = false;
  }
  else
  {
   if(num)
   {
    currVal = arrElmts[i].replace(/\,/g, "");
    if(!isNumeric(currVal))
    {
     ok = false;
    }
   }
  }
 }
 return ok;
}


//////////////////////////////////////////////isNumeric///////////////////////////////////////////
function isNumeric(n)
{
 return !isNaN(parseFloat(n)) && isFinite(n);
}

////////////////////////////////////////////// removeComma//////////////////////////////////////////
function removeComma(str)
{
 var val = str.replace(/\,/g, "");
 return val;
}



function addCommas(val)
{
 val = Math.round(val * 100) / 100;
 return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

//////////////////////////////////////////////////////// getId /////////////////////////////////////////////////////////////
function getId(str)
{
 return str.slice(-1);
}
/////////////////////////////////////////////////////// getSuffix //////////////////////////////////////////////////////
function getSuffix(str)
{
 return  parseInt(str.match(/\d+/), 10);
}

///////////////////////////////////////////////////////fixEditPrefix/////////////////////////////////////////////////
function fixEditPrefix(cellId)
{
 if(id_suffix != getSuffix(cellId))
 {
  id_suffix = getSuffix(cellId);
 }
}

///////////////////////////////////////////////////// processDataTbl//////////////////////////////////////////////////
function processDataTbl(tblId, requestType)
{
 $(document).ready(function() {
     var oTable = $('#' + tblId).dataTable(
     {
      "processing": true,
      "responsive": true,
      "ajax": {

       "url": "/senpage/servlet/senpageCtrl?request_view=senpageSetRpt&requestType=" + requestType + "",
       "dataSrc": "" + tblId + "",
       "type": "GET"
      }
     });
   });
}
////////////////////////////////////////////////// processChartData //////////////////////////////////////////////////
function processChartData(requestType, chartType)
{
 $(document).ready(function() {
     $.ajax( {

      url: "servlet/senpageCtrl?request_view=senpageSetRpt&requestType=" + requestType,
      type: 'GET',
      success: function(data) {

        var  chartData = data;
        var chartProperties = {
         "caption": "Top 10 Months",
         "xAxisName": "months",
         "yAxisName": "amounts",
         "numberPrefix": "$",
         "paletteColors": "#dd4a49",
         "rotatevalues": "1",
         "theme": "fint"
        };

        var jsonRes = JSON.parse(chartData);

        apiChart = new FusionCharts( {
         //type: 'column2d',
         type: chartType,
         renderAt: 'chart-container',
         width: '100%',
         height: '100%',
         dataFormat: 'json',
         dataSource: {
          "chart": chartProperties,
          "data": jsonRes
         }
        });

        apiChart.render();
      }
     });
   });
}

/*/                                                                                                                                                                                                                                                                                                                                                                                                            //////////////////////////ajaxTest1///////////////////////////*/
function ajaxTest1(requestType)
{
 $(document).ready(function() {
     $('#userName').blur(function(event) {
         var name = $('#userName').val();
         $.get('JqueryServlet', {
          userName: name
         }, function(responseText) {
             $('#ajaxResponse').text(responseText);
           });
       });
   });
}










































