import senBudgetUtils.FusionCharts;


public class ChartRes
{
 public String res()
 {

 FusionCharts area2dChart = new FusionCharts(
                    "Area2D",       // type of chart
                    "myFirstChart", // unique chart ID
                    "500","300",    // width and height of the chart
                    "chart",        // div ID of the chart container
                    "json",this.tes());



 return "tes";
//<%=area2dChart.render()%>
  }


 public String tes()
 {
  StringBuffer buff=new StringBuffer("");
  buff.append("{
                        "chart": {
                            "caption": "Sales of Liquor",
                            "subCaption": "Last week",
                            "xAxisName": "Day",
                            "yAxisName": "Sales",
                            "numberPrefix": "$",
                            "paletteColors": "#0075c2",
                            "bgColor": "#ffffff",
                            "showBorder": "0",
                            "showCanvasBorder": "0",
                            "plotBorderAlpha": "10",
                            "usePlotGradientColor": "0",
                            "plotFillAlpha": "50",
                            "showXAxisLine": "1",
                            "axisLineAlpha": "25",
                            "divLineAlpha": "10",
                            "showValues": "1",
                            "showAlternateHGridColor": "0",
                            "captionFontSize": " 14",
                            "subcaptionFontSize": "14",
                            "subcaptionFontBold": "0",
                            "toolTipColor": "#ffffff",
                            "toolTipBorderThickness": "0",
                            "toolTipBgColor": "#000000",
                            " toolTipBgAlpha": "80",
                            "toolTipBorderRadius": "2",
                            "toolTipPadding": "5"
                        },
                        "data": [{
                            "label": " Mon",
                            " value": "4123"
                        }, {
                            "label": "Tue",
                            "value": "4633"
                        }, {
                            "label": "Wed",
                            "value": "5507"
                        }, {
                            "label": "Thu",
                            "value": "4910"
                        }, {
                            "label": "Fri",
                            "value": "5529"
                        }, {
                            "label": "Sat",
                            "value": "5803"
                        }, {
                            "label": "Sun",
                            "value": "6202"
                        }]
                    }"
                );*/
 return buff.toString();;
}
