<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title><spring:message code="URL.TITLE" /></title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/popup.css">
    <script src="/js/popup.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>

    
</head>
<body>
   <form:form name="regist" commandName="regist" method="post" action="/backoffice/popup/agentState.do">	   	   
   <input type="hidden" name="seachAgentCode" id="seachAgentCode" value="${regist.seachAgentCode }">
   <input type="hidden" name="searchDay" id="searchDay" value="${regist.searchDay }">
   <input type="hidden" name="agentState" id="agentState" value="${agentState}">
   <div id="chart_div" style="width:700px; height:400px"></div>
   
   </form:form>
</body>
<script type="text/javascript">
           google.load("visualization", "1", {'packages':["corechart"]});    
		   google.setOnLoadCallback(drawChart);	// bar		  
		   function drawChart() {   
				var data = new google.visualization.DataTable();
				data.addColumn('string', '시간');
				data.addColumn('number', '카운터수');
				data.addRows( ${agentState}  );
				var options = { title: '시간별 접속 현황', fontSize: '12', fontName: '굴림체', 
						        hAxis: {title: '시간', titleTextStyle: {color: 'red', fontName: '굴림체'}} , 
						        vAxis: {title: '접속 카운트', titleTextStyle: {color: 'blue', fontName: '굴림체'}}
				};        
				var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
				chart.draw(data, options);   
				data = null;
				chart = null;

			}  

		   
</script>

</html>