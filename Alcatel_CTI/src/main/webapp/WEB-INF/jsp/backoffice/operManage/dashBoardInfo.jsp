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
    <title><spring:message code="URL.TITLE" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <link rel="stylesheet" href="/css/anychart.css">
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script src="/js/popup.js"></script>  
    <script src="/js/anychart.js"></script>

</head>
<body>
<div id="wrapper">
<form:form name="regist" commandName="regist" method="post" action="/backoffice/sub/equiManage/dashboardState.do">
	<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<c:import url="/backoffice/inc/top_inc.do" />
<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu01" /><span>
                        <span>></span>
                        <strong><spring:message code="menu.menu01_5" /><span></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
            
	            <div class="con">
							<!--통합모니터링-->
							<div class="con_title whiteBox ">
								<h2><img src="/images/mediaText.png" />통합 모니터링 현황입니다.</h2>
								<!--차트시작-->
								<table border="0" style="width:100%;" id="dashboard" style="height:550px;">
								  <tbody>
								  </tbody>
								</table>
								<div class="clear"></div>		
											
							</div>
				</div>
				<div class="Swrap tableArea">
							<h2><img src="/images/didIconsmall.png" /> 총 장애 단말기 모니터링현황입니다.</h2>
							<table>
								<thead>
									<tr>
										<th>상태</th>
										<th>지점</th>
										<th>단말명</th>
										<th>최종 접속시간</th>
									</tr>
								</thead>
								<tbody>
                                  <c:forEach items="${resultList }" var="dashboard" varStatus="status">
									<tr>
										<td>
										<c:choose>
										   <c:when test="${dashboard.agentState eq 'F' }">장에</c:when>
										   <c:when test="${dashboard.agentState eq 'C' }">주의</c:when>
										   <c:otherwise>정상</c:otherwise>
										</c:choose>		
										</td>
										<td>${dashboard.centerNm  }</td>
										<td>${dashboard.agentNm  }:${dashboard.agentIp  }</td>
										<td>${dashboard.connDate  }</td>
									</tr>
									</c:forEach>	
								</tbody>
							</table>
							 <div class="new_pagenum">
								<div class="new_pager">
									<ol>
										<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"  />
								   </ol>
								</div>
							</div>					
						</div>
            </div>
            
         </div>
</div>
<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
<script type="text/javascript">
   $(document).ready(function() { 
			 	fn_dashInfo();		   
	});
	function fn_dashInfo(){
		uniAjax("/backoffice/basicManage/dashboardInfoAjax.do", {'ad':'12'}, 
	   			function(result) {
						if (result.status == "SUCCESS"){
							if (result.centerInfo.length > 0){
								var obj = result.centerInfo;
								$("#dashboard > tbody").empty();
								var setHtml = "<tr>";
								for (var i =0; i < obj.length; i++){
									setHtml += "<td style='height:500px;'><div id='ct_"+ obj[i].centerId+"' class='chart'  style='height:450px;'></div>";
									var agent = "";
									if (i == 0) {
										agent = result.agentState0;
									}else if (i == 1){
										agent = result.agentState1;
									}else if (i == 2){
										agent = result.agentState2;
									}else if (i == 3){
										agent = result.agentState3;
									}else if (i == 4){
										agent = result.agentState4;
									}else if (i == 5){
										agent = result.agentState5;
									}else if (i == 6){
										agent = result.agentState6;
									}else if (i == 7){
										agent = result.agentState7;
									}else if (i == 8){
										agent = result.agentState8;
									}else if (i == 9){
										agent = result.agentState9;
									}else if (i == 10){
										agent = result.agentState10;
									}
									
									for (var a =0; a < agent.length; a ++){
										setHtml += "<input type='hidden' id='"+ obj[i].centerId+"_"+agent[a].agentState+"' value='"+agent[a].agentCnt+"' />";
									}
									setHtml += "<span id='"+ obj[i].centerId+"_result'></td>";
								}
								setHtml += "</tr>";
								$("#dashboard >  tbody:last").append(setHtml);	
								for (var i =0; i < obj.length; i++){
									anyChartDr(obj[i].centerId, obj[i].centerNm);
								}
							}
						}else if (result.status == "LOGIN FAIL"){
							alert(result.message);
							location.href="/backoffice/login.do";
						}else {
							alert(result.message);
						}
				    },
				    function(request){
					    alert("Error:" +request.status );	       						
				    }    		
	   	   );	
	}
	function linkPage(pageNo) {
 		$(":hidden[name=pageIndex]").val(pageNo);		
 		$("form[name=regist]").attr("action", "/backoffice/basicManage/dashboardInfo.do").submit();
 	}	
    setInterval(function() {
                  fn_dashInfo();
    }, 300000); 
    function anyChartDr(centerId, centerNm){
    	anychart.onDocumentReady(function() {
    		 var normal = ($("#"+centerId+"_N") == undefined ) ? "0" : $("#"+centerId+"_N").val();
    		 var caution = ($("#"+centerId+"_C") == undefined ) ? "0" : $("#"+centerId+"_C").val();
    		 var failure = ($("#"+centerId+"_F") == undefined ) ? "0" : $("#"+centerId+"_F").val();
    		 $("#"+centerId+"+_result").html("정상:" + normal +"개  주의:" + caution +"개  장애:" + failure +"개");
    		 var data = [
   		             {x: "정상", value: normal},
   		             {x: "주의", value: caution},
   		             {x: "오프라인", value: failure}
   		      ];
    		  chart = anychart.pie(data);
    		  chart.innerRadius('65%').explode(25); 
    	      chart.title(centerNm+ "단말기 모니터링현황");
    	      var label = anychart.standalones.label();
    	      label.enabled(true)
    	          .text(centerNm+ "단말기 모니터링현황")
    	          .width('100%')
    	          .height('100%')
    	          .adjustFontSize(true, true)
    	          .minFontSize(10)
    	          .maxFontSize(25)
    	          .fontColor('#60727b')
    	          .position('center')
    	          .anchor('center')
    	          .hAlign('center')
    	          .vAlign('middle');
    	      chart.container("ct_"+centerId);

    	      chart.draw();
    		
    	});
       
    }
</script>
</body>
</html>