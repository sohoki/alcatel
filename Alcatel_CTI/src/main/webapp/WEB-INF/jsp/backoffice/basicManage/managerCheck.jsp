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
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script src="/js/popup.js"></script>
</head>
<body>
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/empList.do">	   	  
	

<form:hidden path="mode"  id="mode"/>        
<form:hidden path="searchCondition" id="searchCondition" />
<form:hidden path="searchKeyword"  id="searchKeyword"/>
<form:hidden path="pageIndex"  id="pageIndex"/>
<form:hidden path="pageUnit"  id="pageUnit"/>
<form:hidden path="regDate"  id="regDate"/>

<c:import url="/backoffice/inc/top_inc.do" />

<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span><spring:message code="menu.menu03" /><span>></span><strong><spring:message code="menu.menu03_1" /></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap tableArea viewArea">
                <div class="view_contents">
                <table class="pop_table thStyle">
                <tbody>
                    <tr>
                        <th><spring:message code="page.emp.usrid" /></th>
                        <td style="text-align:left"><form:input  path="adminId" size="10" maxlength="20" id="adminId"   value="${regist.adminId }" />
                        <span id="uniCheck" ></span>
                        </td>
                        <th><spring:message code="page.emp.title" /></th>
                        <td style="text-align:left"><form:input  path="adminName" size="10" maxlength="20" id="adminName"   value="${regist.adminName }" />
                        (<form:input  path="empNo" size="10" maxlength="20" id="empNo"   value="${regist.empNo }" />)
                        </td>
                        
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.usertel" /></th>
                        <td style="text-align:left"><form:input  path="adminTel" size="25" maxlength="25" id="adminTel"   value="${regist.adminTel }" /></td>
                        <th><spring:message code="page.emp.userEmail" /></th>
                        <td style="text-align:left"><form:input  path="adminEmail" size="40" maxlength="120" id="adminEmail"   value="${regist.adminEmail }" /></td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.password" /></th>
                        <td style="text-align:left"><form:password path="adminPassword" id="adminPassword" title='<spring:message code="page.emp.password" />' size="20" maxlength="20" /></td>
                        <th><spring:message code="page.emp.passwordCk" /></th>
                        <td style="text-align:left"><input name="adminPassword2" id="adminPassword2" title='<spring:message code="page.emp.passwordCk" />' type="password" size="20" maxlength="20" /></td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.userReg" /></th>
                        <td style="text-align:left"><span id="reg_date"></span></td>
                        <th><spring:message code="common.UseYn.title" /></th>
                        <td style="text-align:left">
                            <input type="radio" name="useYn" value="Y" <c:if test="${regist.useYn == 'Y' }"> checked </c:if> /><spring:message code="button.use" />
							<input type="radio" name="useYn" value="N" <c:if test="${regist.useYn == 'N' }"> checked </c:if> /><spring:message code="button.nouse" />					
                        </td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.userAuth" /> </th>
                        <td style="text-align:left">
                            <form:select path="adminLevel" id="adminLevel" title="소속" onClick="javascript:fn_ComboView()">
						         <form:option value="" label='--선택하세요--'/>
		                         <form:options items="${selectState}" itemValue="authorCode" itemLabel="authorNm"/>
						    </form:select>										
                        </td>
                        <th><spring:message code="page.emp.part" /></th>
                        <td style="text-align:left">
                            <form:select path="partId" id="partId" title="소속">
						         <form:option value="" label='--선택하세요--'/>
		                         <form:options items="${selectGroup}" itemValue="partId" itemLabel="partNm"/>
						    </form:select>
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
	
<script type="text/javascript">
	function linkPage() {
		$("form[name=regist]").attr("action","/backoffice/basicManage/empList.do").submit();
	}	
	$(document).ready(function() {     
			
		
	    if ($("#mode").val() == "Ins"){   
	    	
	 		$("form[name=regist]").append("<input type='hidden'  id='idCheck' name='idCheck' />");	
	 		$("#adminId").val("");
	 		$("#uniCheck").html('<a href="javascript:check_id();" class="deepBtn"><spring:message code="page.emp.userCheck" /></a>');
	 		$("#btnUpdate").text('<spring:message code="button.create" />');
	 		$("#partId").hide();
	    }	else {
	    	fn_ComboView();
	    	$("#btnUpdate").text('<spring:message code="button.update" />');
	   	    $("#uniCheck").html("");
	   	    $("#reg_date").html($("#regDate").val());
	   	    $("input[name=adminId]").attr("readOnly", true);
	    }
	});	
	function check_id(){	  
	    if ( $("#adminId").val()!= ""   ){	  
	    	var params = {adminId : $("#adminId").val() };
	    	uniAjax("/backoffice/basicManage/IdCheck.do", params, 
	       			function(result) {
  				    		if (result == "0"){
  								alert('<spring:message code="page.emp.message13" />');
  								$("#idCheck").val("Y");							
  							}else {
  								alert('<spring:message code="page.emp.message14" />');
  								$("#idCheck").val("N");
  							}
	  				    },
	  				    function(request){
	  					    alert("Error:" +request.status );	       						
	  				    }    		
	        );
	    }else {
	    	alert ('<spring:message code="page.emp.message05" />');
	    	$("#adminId").focus();
	    	return;
	    }
	}
	function check_form(){	  
		   if ($("#mode").val() == "Ins"){
			   if ($("#idCheck").val() == "N"){
				   alert('<spring:message code="page.emp.message01" />');
				   return ;			   
			   }			   
			   if(!chkPwd( $.trim($('#adminPassword').val()))){ 
				   alert('<spring:message code="page.emp.message02" />');    
				   $('#adminPassword').val('');
				   $('#adminPassword').focus(); return ;
				   }
			   if ( $.trim($('#adminPassword').val()) !=   $.trim($('#adminPassword2').val())  ){
				   alert('<spring:message code="page.emp.message03" />')
				   return;
			   }
		   }   	
		   if (any_empt_line_id('adminLevel', '<spring:message code="page.emp.message04" />') == false) return;	  
		   var params = $("#regist").serializeObject(); 
		   uniAjax("/backoffice/basicManage/managerUpdate.do", params, 
	     			function(result) {
			                alert(result.message);
					    	if (result.status == "SUCCESS"){
								$("form[name=regist]").attr("action", "/backoffice/basicManage/empList.do").submit();
							}else if (result.status == "LOGIN FAIL"){
	  							location.href="/backoffice/login.do";
	  						} else {
	  							
	  						}
					    },
					    function(request){
						    alert("Error:" +request.status );	       						
					    }    		
	      );
	}
	function del_form(){
		fn_uniDelJSON("/backoffice/basicManage/managerDelete.do"
					  , {adminId : $("#adminId").val()}
			          , "/backoffice/basicManage/empList.do");	
	}
</script>  
  
</body>
</html>