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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <link href="/css/calendar.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/new_calendar.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/jscript" src="/SE/js/HuskyEZCreator.js" ></script>
    <script type="text/javascript" src="/js/jquery-ui.js"></script>
    <script src="/js/popup.js"></script>
</head>
<body>
<div id="wrapper">	
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/AgentInfoList.do">
<c:import url="/backoffice/inc/top_inc.do" />
<input type="hidden" name="pageIndex" id="pageIndex" value="${regist.pageIndex }">
<input type="hidden" name="mode" id="mode" value="${regist.mode }">
<form:hidden path="pageSize" />
<form:hidden path="searchCondition" />
<form:hidden path="searchKeyword" />
<form:hidden path="agentCode" />


<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu01" /><span>
                        <span>></span>
                        <strong><spring:message code="menu.menu01_3" /><span></strong>
                    </div>
                </div>
            </div>

            
            <div class="Swrap tableArea viewArea">
                <div class="view_contents">
                <table class="pop_table thStyle">
	                <tbody>
	                    <tr>
	                        <th>단말기명</th>
	                        <td><form:input  path="agentNm" size="20" maxlength="100" id="agentNm"   value="${regist.agentNm }" /></td>	 
	                        <th><spring:message code="page.agent.osType" /></th>
	                        <td>  ${regist.agentGubun }
	                        <form:select path="agentGubun" id="agentGubun" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectAgentGubun}" itemValue="code" itemLabel="codeNm"/>
							</form:select>
	                        </td>	                        
	                    </tr>
	                    <tr>
	                        <th>비고</th>
	                        <td colspan="3">
	                        <form:input  path="agentRemark" size="80" maxlength="120" id="agentRemark"   value="${regist.agentRemark }" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <th>지점</th>
	                        <td>
	                        <form:select path="centerId" id="centerId" title="소속" onChange="javascript:fn_FlooerView()">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectCenterCombo}" itemValue="centerId" itemLabel="centerNm"/>
							</form:select>
							<span id="sp_flooerInfo"></span>
	                        </td>	
	                        <th>관리부서</th>
	                        <td>
	                        <form:select path="partId" id="partId" title="소속">
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectGroupCombo}" itemValue="partId" itemLabel="partNm"/>
							</form:select>
	                        </td>	                        
	                    </tr>
	                    <%-- <tr>
	                        <th><spring:message code="page.agent.IP" /></th>
	                        <td style="text-align:left">
	                        <form:input  path="agentIp" size="20" maxlength="20" id="agentIp"   value="${regist.agentIp }" />
	                        </td>	
	                        <th><spring:message code="page.agent.Mac" /></th>
	                        <td style="text-align:left">
	                        <form:input  path="agentMac" size="20" maxlength="20" id="agentMac"   value="${regist.agentMac }" />
	                        </td>
	                    </tr> --%>
	                    <tr>
	                        <th>단말기 시작시간</th>
	                        <td><form:input  path="agentStarttime" size="10" maxlength="5" id="agentStarttime"   value="${regist.agentStarttime }" /></td>	 
	                        <th>단말기 종료시간</th>
	                        <td><form:input  path="agentEndtime" size="10" maxlength="5" id="agentStarttime"   value="${regist.agentEndtime }" /></td>	                        
	                    </tr>
	                    
	                    <tr>
	                        <th><spring:message code="page.agent.displayNm" /></th>
	                        <td style="text-align:left">
	                                <form:select path="displaySeq" id="displaySeq" title="소속">
										     <option value=""><spring:message code="combobox.text" /></option>
					                         <form:options items="${selectCodeDM}" itemValue="displaySeq" itemLabel="displayTitle"/>
								    </form:select>	
	                        </td>	
	                        <th><spring:message code="common.UseYn.title" /></th>
	                        <td style="text-align:left">
	                        <input type="radio" name="agentUseYn" value="Y" <c:if test="${regist.agentUseYn == 'Y' }"> checked </c:if> />
	                        <label><spring:message code="button.use" /></label>
							<input type="radio" name="agentUseYn" value="N" <c:if test="${regist.agentUseYn == 'N' }"> checked </c:if> />
							<label><spring:message code="button.notUsed" /></label>	                            
	                        </td>
	                    </tr>	
	                                        	                    
	                </tbody>
	            </table>
            </div>
            </div>
            <div class="footerBtn">
	            <a href="javascript:check_form();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
	            <c:if test="${regist.mode != 'Ins' }">
	            <a href="javascript:del_form();" class="redBtn"><spring:message code="button.delete" /></a>
	            </c:if>
	            <a href="javascript:linkPage()" class="deepBtn"><spring:message code="button.list" /></a>
	        </div>
        </div>
    </div>

<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
	</div>
<script type="text/javascript">
	function linkPage() {
		$("form[name=regist]").submit();
	}	
	$(document).ready(function() {     
		//콤보박스 및  확인 	
		
		if ($("#mode").val() == "Ins"){
	 		$("#btnUpdate").text('<spring:message code="button.create" />');
	    }	else {
	    	$("#btnUpdate").text('<spring:message code="button.update" />');	
	    	fn_FlooerView();
	    }
	});	
	function fn_FlooerView(){
		
		if ($("#centerId").val()  != ""){
			var params = { centerId : $("#centerId").val() };
			
			uniAjax("/backoffice/basicManage/centerInfo.do", params, 
		       			function(result) {
		  	    	            if (result.status == "SUCCESS"){
		  	    	            	if (result.regist.centerFloorTxt != "" && result.regist.centerFloorEndTxt != ""){
		  	    	            		var startFloor = result.regist.centerFloorTxt.replace(/층/gi,"");
		  	    	            		var endFloor = result.regist.centerFloorEndTxt.replace(/층/gi,"");
		  	    	            	}else if (result.regist.centerFloorTxt != "" && result.regist.centerFloorEndTxt == ""){
		  	    	            		var startFloor = result.regist.centerFloorTxt.replace(/층/gi,"");
		  	    	            		var endFloor = result.regist.centerFloorTxt.replace(/층/gi,"");
		  	    	            	}
		  	    	            	var comboHtml = "<select id='agentFloor' name='agentFloor'>";
	  	    	            	    for (var i = startFloor; i <= endFloor; i ++){
	  	    	            	    	comboHtml += "<option value='"+ i +"'>"+i+"층</option>";
	  	    	            	    }         
	  	    	            	    comboHtml += "</select>"
	  	    	            	    $("#sp_flooerInfo").html(comboHtml);
	  	    	            	    
	  	    	            	    if ('${regist.agentFloor}' != "0"){
	  	    	            	    	$("#agentFloor").val('${regist.agentFloor}');
	  	    	            	    }
		  	    	            	    
		  						}else if (result.status == "LOGIN FAIL"){
		  							location.href="/backoffice/login.do";
		  						}else{
		  							alert(result.message);
		  						}
		  				    },
		  				    function(request){
		  					    alert("Error:" +request.status );	       						
		  				    }    		
		    );
		}
		 
	}
	function check_form(){
		   if (any_empt_line_id("agentNm", '<spring:message code="page.agent.alert1" />') == false) return;
		   if (any_empt_line_id("agentRemark", '<spring:message code="page.agent.alert2" />') == false) return;
		   
		   var params = $("#regist").serializeObject(); 
		   uniAjax("/backoffice/basicManage/agentInfoUpdate.do", params, 
	     			function(result) {
					    	if (result.status == "SUCCESS"){
					    		$("#agentCode").val(result.agentCode);
								$("form[name=regist]").attr("action", "/backoffice/basicManage/AgentInfoList.do").submit();
							}else if (result.status == "LOGIN FAIL"){
	  							location.href="/backoffice/login.do";
	  						}else{
	  							alert(result.message);
	  						}
					    },
					    function(request){
						    alert("Error:" +request.status );	       						
					    }    		
	      );
		   
		   
	}
	function del_form(){
    	fn_uniDelJSON("/backoffice/basicManage/agentInfoDelete.do"
				  , {agentCode : $("#agentCode").val()}
		          , "/backoffice/contentManage/AgentInfoList.do");	
	}	
		
</script>  
</body>
</html>