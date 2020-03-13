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
    
    <link rel="stylesheet" href="/css/needpopup.min.css" />
    <style type="text/css">
	    .needpopup input {
	    width: 50%;
	    box-sizing: border-box;
	    margin-left: 0;
	    }
	    .tableArea table th {
            background-color: #efefef;
            font-size: 14px;
            font-weight: bold;
            color: #343434;
        }
    </style>
    
</head>
<body>
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/empList.do">	   	  
<c:import url="/backoffice/inc/top_inc.do" />	
 
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="mode" id="mode">

<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span><spring:message code="menu.menu03" />
                        <span>></span>
                        <strong><spring:message code="menu.menu03_1" /></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
                <section class="Bclear">
                   <select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value="adminId" <c:if test="${searchVO.searchCondition == 'adminId' }"> selected="selected" </c:if>>아이디</option>
								<option value="adminName" <c:if test="${searchVO.searchCondition == 'adminName' }"> selected="selected" </c:if>>이름</option>
							</select>	
                    <input class="nameB" type="text" name="searchKeyword" id="searchKeyword" placeholder="이름" value="${searchVO.searchKeyword }">
                    <a href="javascript:search_form()"><span class="redBtn"><spring:message code="button.inquire" /></span></a>
                    <div class="rightB">
                        <a href="#" onclick="fn_MBER('Ins','0')" data-needpopup-show="#groupPop" ><span class="deepBtn"><spring:message code="button.create" /></span></a>
                    </div>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><spring:message code="common.Number.title" /></th>
                            <th><spring:message code="page.emp.title" /></th>
                            <th><spring:message code="page.emp.usrid" /></th>
                            <th><spring:message code="page.emp.usertel" /></th>
                            <th><spring:message code="page.emp.userEmail" /></th>
                            <th><spring:message code="page.emp.passLock" /></th>
                            <th><spring:message code="page.emp.userReg" /></th>
                            <th><spring:message code="button.use" /></th>
                            <th><spring:message code="button.delete" /></th>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach items="${resultList }" var="userinfo" varStatus="status">
                        <tr>
                            <td><c:out value="${(searchVO.pageIndex - 1) * searchVO.pageSize + status.count}"/></td>
                            <td><a href="#" onclick="fn_MBER('Edt','${ userinfo.adminId }')" class="underline" data-needpopup-show="#groupPop">${ userinfo.adminName }(${ userinfo.empNo })</a></td>
                            <td>${ userinfo.adminId }</td>
                            <td>${ userinfo.adminTel }</td>
                            <td>${ userinfo.adminEmail }</td>
                            <td>${ userinfo.lockYn }</td>
                            <td>${ userinfo.regDate }</td>
                            <td>${ userinfo.useYn }</td>
                            <td><a href="javascript:del_check('${ userinfo.adminId }');" class="grayBtn"><spring:message code="button.delete" /></a></td>
                        </tr>
                        </c:forEach>                       
                    </tbody>
                </table>
            </div>
            <div class="new_pagenum">
               <div class='new_pager'>
                  <ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"  />
               </div> 
            </div>
        </div>
    </div>

<div id='groupPop' class="needpopup">
        <div class="user_top">
            <p >관리자 관리</p>
        </div>
        <div class="Swrap tableArea">
            <table class="pop_need_table" style="border-top: 0px solid #e8342f; ">
                <tbody>
                    <tr>
                        <th><spring:message code="page.emp.usrid" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminId" name="adminId" />
                        <span id="uniCheck" ></span>
                        </td>
                        <th><spring:message code="page.emp.title" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminName" name="adminName"  style="150px;" /> (<input type="text" id="empNo" name="empNo"  style="150px;" maxlength="20" />)
                        </td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.usertel" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminTel" name="adminTel" size="25" maxlength="25"/>
                        </td>
                        <th><spring:message code="page.emp.userEmail" /></th>
                        <td style="text-align:left">
                        <input type="text" id="adminEmail" name="adminEmail" size="40" maxlength="120"/>
                        </td>
                    </tr>
                    <tr>
                        <th><spring:message code="page.emp.password" /></th>
                        <td style="text-align:left">
                        <input type="password" id="adminPassword" name="adminPassword" size="20" maxlength="20" title='<spring:message code="page.emp.password" />'/>
                        </td>
                        <th><spring:message code="page.emp.passwordCk" /></th>
                        <td style="text-align:left">
                        <input name="adminPassword2" id="adminPassword2" title='<spring:message code="page.emp.passwordCk" />' type="password" size="20" maxlength="20" /></td>
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
                            <select id="partId" name="partId">
	                        </select>	
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="clear"></div>
            <div class="footerBtn">
	            <a href="javascript:fn_update();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
	            <a href="javascript:del_form();" class="redBtn"><spring:message code="button.delete" /></a>
	        </div>
	        <div class="clear"></div>
        </div>
    </div>
	<c:import url="/backoffice/inc/bottom_inc.do" />    
</form:form>    
     <script src="/js/needpopup.min.js" ></script> 
	 <script type="text/javascript">
		$(document).ready(function() {     
			
		});
		function fn_update(){
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
		function linkPage(pageNo) {
			$(":hidden[name=pageIndex]").val(pageNo);		
			$("form[name=regist]").attr("action","/backoffice/basicManage/empList.do").submit();
		}
		function fn_MBER(mode, userId){
			 $("#mode").val(mode);
			alert(mode);
			 if (mode == "Edt"){
				 
				  $("#btnUpdate").text('<spring:message code="button.update" />');	
				  url= "/backoffice/basicManage/managerDetail.do";
				  var param = {
			                'mode' : mode,
			                'adminId' : userId
			      };
				  uniAjax(url, param, 
		     			function(result) {
						       if (result.status == "LOGIN FAIL"){
						    	   alert(result.meesage);
		  						   location.href="/backoffice/login.do";
		  					   }else if (result.status == "SUCCESS"){
		  						    //여기 부분 수정 어떻게 할지 추후 생각
		  						    var obj = result.managerInfo;
		  						    $("#adminId").val(obj.adminId);
		  						    $("#empNo").val(obj.empNo);
			  						$("#adminTel").val(obj.adminTel);
			  						$("#adminEmail").val(obj.adminEmail);
			  						$("#radioTest:radio[value='"+obj.useYn +"']").attr("checked", true) ;
		 		            	    $('#adminLevel').val(obj.adminLevel);
		 		            	    
		  					   }
						    },
						    function(request){
							    alert("Error:" +request.status );	       						
						    }    		
		          );
				  fn_ComboView();
		    	  $("#btnUpdate").text('<spring:message code="button.update" />');
		   	      $("#uniCheck").html("");
		   	      $("#reg_date").html($("#regDate").val());
		   	      $("input[name=adminId]").attr("readOnly", true);
			 }else{
				 console.log("2");
				 $("form[name=regist]").append("<input type='hidden'  id='idCheck' name='idCheck' />");	
			 	 $("#adminId").val("");
			 	 $("#uniCheck").html('<a href="javascript:fn_checkId();" class="deepBtn"><spring:message code="page.emp.userCheck" /></a>');
			 	 $("#btnUpdate").text('<spring:message code="button.create" />');
			 	 $("input:radio[name='useYn']:radio[value='Y']").prop('checked', true); 
			 	 $("#partId").hide();
			 	 console.log("3");
			 }
		}
		function fn_checkId(){
			 if ( $("#adminId").val()!= ""   ){	  
		    	var params = {adminId : $("#adminId").val() };
		    	uniAjax("${pageContext.request.contextPath}/backoffice/basicManage/IdCheck.do", params, 
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
		function del_check(code){
			fn_uniDelJSON("/backoffice/basicManage/managerDelete.do"
					  , {adminId : code}
			          , "/backoffice/basicManage/empList.do");		
		}  
	    needPopup.config.custom = {
	            'removerPlace': 'outside',
	            'closeOnOutside': false,
	            onShow: function() {
	                console.log('needPopup is shown');
	            },
	            onHide: function() {
	                console.log('needPopup is hidden');
	            }
	    };
	    needPopup.init();
    </script>
</body>
</html>