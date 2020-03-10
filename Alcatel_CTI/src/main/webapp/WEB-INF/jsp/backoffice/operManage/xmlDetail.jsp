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
    <title><spring:message code="URL.TITLE" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script src="/js/popup.js"></script>    
    <link rel="stylesheet" href="/css/needpopup.min.css" />
</head>
<body>
<div id="wrapper">	
<form:form name="regist" commandName="regist" method="post" action="/backoffice/operManage/xmlList.do">	    	   
	<input type="hidden" name="pageIndex" id="pageIndex" value="${regist.pageIndex }">
	<input type="hidden" name="mode" id="mode" value="${regist.mode }">
	<input type="hidden" name="xmlSeq" id="xmlSeq" value="${regist.xmlSeq }">	    
	<c:import url="/backoffice/inc/top_inc.do" />
	<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu02" />
                        <span>></span>
                        <strong><spring:message code="menu.menu02_2" /></strong>
                    </div>
                </div>
            </div>
            <div class="Swrap tableArea viewArea">
                <div class="view_contents">
                <table>
					<tbody>
						<tr>
							<th><spring:message code="page.xml.processId" /></th>
							<td style="text-align:left">
							<form:input path="xmlProcessName" id="xmlProcessName" title="프로세스ID이피" size="15" value="${regist.xmlProcessName }"/>							
							 <a href="javascript:processCheck();" class="grayBtn">중복검사</a></td>
							<th><spring:message code="page.xml.processJob" /></th>		
							<td style="text-align:left">
							<form:input path="processRemark" id="processRemark" title="전문설명" size="25" value="${regist.processRemark }"/>
							</td>				
						</tr>
						<tr>
							<th><spring:message code="page.xml.inParam" /></th>
							<td style="text-align:left">
							<textarea name="xmlInputParam" id="xmlInputParam" style="width:250px; height:50px;">${regist.xmlInputParam }</textarea>
							</td>
							<th><spring:message code="page.xml.inParamExample" /></th>
							<td style="text-align:left">
							<textarea name="xmlInputParamSample" id="xmlInputParamSample" style="width:250px; height:50px;">${regist.xmlInputParamSample }</textarea>
							</td>		
						</tr>	
						<tr>
						    <td colspan="4">
						          내용 보여주기 
						    </td>
						</tr>
						<tr>
							<th><spring:message code="page.xml.inParamExp" /></th>		
							<td style="text-align:left">
							  <textarea name="xmlExplain" id="xmlExplain" style="width:250px; height:50px;">${regist.xmlExplain }</textarea>
							</td>
							<th><spring:message code="page.xml.otParam" /></th>		
							<td style="text-align:left">
							<textarea name="xmlOutputParam" id="xmlOutputParam" style="width:250px; height:50px;">${regist.xmlOutputParam }</textarea>
							</td>
											
						</tr>	
						<tr>
							<th><spring:message code="page.xml.sendGubun" /></th>
							<td style="text-align:left">
								<form:select path="workGubun" id="workGubun" title="전문구분">
							        <option value><spring:message code="combobox.text" /></option>
			                        <form:options items="${selectWorkgubun}" itemValue="code" itemLabel="codeNm"/>
							   </form:select>
							</td>
							<th><spring:message code="page.xml.result" /></th>		
							<td style="text-align:left">
							<form:input path="resultCodeExample" id="resultCodeExample" title="전문설명" size="15" value="${regist.resultCodeExample }"/>
							</td>				
						</tr>
						<tr>
							<th><spring:message code="page.xml.ListCheck" /></th>
							<td style="text-align:left">
							<form:input path="etc1" id="etc1" title="전문설명" size="15" value="${regist.etc1 }"/>
							</td>
							<th><spring:message code="page.xml.confirm" /></th>		
							<td style="text-align:left">
							<input type="radio" name="testOk" value="Y" <c:if test="${regist.testOk == 'Y' }"> checked </c:if> /><spring:message code="button.confirm" />
							<input type="radio" name="testOk" value="N" <c:if test="${regist.testOk == 'N' }"> checked </c:if> /><spring:message code="button.undefine" />
							</td>				
						</tr>																														
					</tbody>
				</table>
                 </div>
            </div>
            <div class="footerBtn">
	            <a href="javascript:check_form();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
	            <a href="javascript:pageList()" class="deepBtn"><spring:message code="button.list" /></a>
	        </div>
        </div>
    </div>
    <c:import url="/backoffice/inc/bottom_inc.do" />
	
  </form:form>	
  <script type="text/javascript">
   $(document).ready(function () {
	  if ($("#mode").val() == "Ins"){
		  $("btnUpdate").text('<spring:message code="button.create" />');
	  } else {
		  $("btnUpdate").text('<spring:message code="button.update" />');
	  }		   
   });
   function pageList(){
	   $("form[name=regist]").attr("action", "/backoffice/operManage/xmlList.do").submit();
   }
   function check_form(){
	   var params = $("#regist").serializeObject(); 
	   if ($("#mode").val() == "Ins" && $("#idCheck").val() != "Y"){
		   
	   }
	   uniAjax("/backoffice/operManage/xmlUpdate.do", params, 
     			function(result) {
				    	if (result.status == "SUCCESS"){
							$("#xmlSeq").val(result.regist.xmlSeq);
							$("#mode").val("Edt");
							$("form[name=regist]").attr("action", "/backoffice/operManage/xmlView.do").submit();
						}
				    },
				    function(request){
					    alert("Error:" +request.status );	       						
				    }    		
      );
   }
   
   //전문 체크
   function processCheck(){
	   fn_uniCheck(   {inTable : "TB_SENDMESSAGETYPR",
		               inCheckName : "XML_PROCESS_NAME",
		               inCondition : "XML_PROCESS_NAME=["+$("#xmlProcessName").val()+"["
		              }, "xmlProcessName", "idCheck", "<spring:message code='page.xml.confirm' />"
		            );
   }
  </script>
</body>
</html>