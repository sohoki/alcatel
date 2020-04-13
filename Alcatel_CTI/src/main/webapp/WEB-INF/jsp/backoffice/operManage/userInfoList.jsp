<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ page import ="egovframework.com.cmm.AdminLoginVO" %>
<% AdminLoginVO loginVO = (AdminLoginVO)session.getAttribute("AdminLoginVO"); %>
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
    <script type="text/javascript" src="/js/cms_display.js"></script>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript" src="/js/cms_agent.js"></script>

    <link rel="stylesheet" href="/css/needpopup.css">
	<style>
		.joinSubTxt{
			float:right;
		} 
		.id-pw-warning{
			font-weight: bold;
			color : #EA4335;
		}
		.id-pw-posible{
			font-weight: bold;
			color : #34A853;
		}
		.join_pop_main_subTxt{
			float:left;
			font-weight: bold;
		}
		.pw_pop_main_subTxt{
			float:left;
			font-weight: bold;
		}		
	</style>
</head>
<body>
<div id="wrapper">	
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/AgentInfoList.do">
<c:import url="/backoffice/inc/top_inc.do" />
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="mode" id="mode" >

<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span>
                        <spring:message code="menu.menu02" /><span>
                        <span>></span>
                        <strong><spring:message code="menu.menu02_1" /><span></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
                <section class="Bclear">
                   	    
                    <select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value="phoneNumber" <c:if test="${searchVO.searchCondition == 'phoneNumber' }"> selected="selected" </c:if>><spring:message code="page.user.phoneNumber" /></option>
								<option value="loginId" <c:if test="${searchVO.searchCondition == 'loginId' }"> selected="selected" </c:if>><spring:message code="page.user.loginId" /></option>
					</select>
					<input class="nameB" type="text" name="searchKeyword" id="searchKeyword" value="${searchVO.searchKeyword}">                    
                    <a href="javascript:search_form();"><span class="redBtn"><spring:message code="button.inquire" /></span></a>
                    <div class="rightB">
                         <a href="#" onclick="fn_excelPop()"><span class="deepBtn"><spring:message code="page.commo.excelUpload" /></a>
                        <a href="#" onclick="fn_agentPop('Ins','0')" data-needpopup-show="#agent_pop"><span class="deepBtn"><spring:message code="button.create" /></a>
                    </div>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><spring:message code="page.user.phoneNumber" /></th>
                            <th><spring:message code="page.user.phoneGubun" /></th>
                            <th><spring:message code="page.user.loginId" /></th>
                            <th><spring:message code="page.user.userNm" /></th>
                            <th><spring:message code="common.UseYn.title" /></th>
                            <th><spring:message code="button.delete" /></th>
                        </tr>
                    </thead>
                    <tbody>
                     <c:forEach items="${resultList }" var="userInfo" varStatus="status">
                        <tr>
                            <td><a href="#" onclick="fn_agentPop('Edt','${ userInfo.phoneNumber }')"  data-needpopup-show="#agent_pop" class="underline" >${ userInfo.phoneNumber }</a></td>
                            <td>${userInfo.phoneGubunTxt }</td>
                            <td style="text-align:left">${userInfo.loginId }</td>
                            <td style="text-align:left">${userInfo.userName }</td>
                            <td>${userInfo.phoneUseyn }</td>
                            <td><a href="javascript:del_check(''${ userInfo.phoneNumber }');" class="grayBtn">
                                <spring:message code="button.delete" />
                                </a>
                            </td>
                        </tr>
                      </c:forEach>                        
                    </tbody>
                </table>
            </div>
            <div class="new_pagenum">
                <div class="new_pager">
                	<ol>
                		<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="linkPage"  />
                   </ol>
                </div>
            </div>
        </div>
    </div>
    <div id='agent_pop' class="needpopup">  
        <!-- popheader-->                        
        <div class="popHead">
            <h2>사용자 관리</h2>
        </div>
        <!-- pop contents-->   
        <div class="popCon">
            <!--// 팝업 필드박스-->
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.user.phoneNumber" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="phoneNumber" size="20" maxlength="20" id="phoneNumber"  style="width:150px;"  />
                    <span id="uniCheck" ></span>
                </div>                
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.user.phoneGubun" /> <span class="join_id_comment joinSubTxt"></span></p>
                   <form:select path="phoneGubun" id="phoneGubun" title="전화번호구분" >
							     <option value=""><spring:message code="combobox.text" /></option>
		                         <form:options items="${selectPhoneGubun}" itemValue="code" itemLabel="codeNm"/>
					</form:select>
                </div>                
            </div>
            <!--// 팝업 필드박스-->
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.user.loginId" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="loginId" size="20" maxlength="20" id="loginId"   />
					<span id="uniCheckID" ></span>
                </div>   
            </div>
            <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.user.loginPassword" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  type="password" path="loginPassword" size="20" maxlength="20" id="loginPassword"   />
                </div>   
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="page.user.userNm" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <form:input  path="userName" size="20" maxlength="20" id="userName"   />
                </div>   
            </div>
             <div class="pop_box50">
                <div class="padding15">
                    <p class="pop_tit">*<spring:message code="common.UseYn.title" /> <span class="join_id_comment joinSubTxt"></span></p>
                    <input type="radio" name="phoneUseyn" id="phoneUseyn" value="Y"  />
                    <label><spring:message code="button.use" /></label>
					<input type="radio" name="phoneUseyn" id="phoneUseyn" value="N" />
					<label><spring:message code="button.nouse" /></label>
                </div>                
            </div>
             
            <div class="clear"></div>   
            
        </div>
        <div class="pop_footer">
            <span id="join_confirm_comment" class="join_pop_main_subTxt"><spring:message code="page.common.InputMessage" /></span>
            <a href="javascript:fn_CheckForm();" class="redBtn" id="btnUpdate"><spring:message code="button.update" /></a>
        	<div class="clear"></div>
        </div>
    </div>
<c:import url="/backoffice/inc/bottom_inc.do" />
</form:form>
</div>
<script type="text/javascript">

   $(document).ready(function() { 
	   //시작값
	   
	});
   function fn_excelPop(){
	   window.open("/backoffice/operManage/excelUploadPop.do", "zip_code", "width=500, height=400, toolbar=no, menubar=no, scrollbars=no, resizable=auto" );	
   }
   function fn_agentPop(mode, code){
		  $('#mode').val(mode);
		  $('#phoneNumber').val(code);
		  if (mode == "Edt"){
			 
			  
			  $("#btnUpdate").text('<spring:message code="button.update" />');	
			  url= "/backoffice/operManage/userDetail.do";
			  var param = {
		                'mode' : $('#mode').val(),
		                'phoneNumber' : $('#phoneNumber').val()
		      };
			  uniAjax(url, param, 
	     			function(result) {
					       if (result.status == "LOGIN FAIL"){
					    	   alert(result.meesage);
	  						   location.href="/backoffice/login.do";
	  					   }else if (result.status == "SUCCESS"){
	  						    //여기 부분 수정 어떻게 할지 추후 생각
	  						    var obj = result.userInfo;
	  						    $("#phoneNumber").val(obj.phoneNumber);
	  						    $("#phoneGubun").val(obj.phoneGubun);
		  						$("#loginId").val(obj.loginId);
		  						$("#userName").val(obj.userName);
	 		            	    $('input:radio[name=phoneUseyn]:input[value=' + obj.phoneUseyn + ']').attr("checked", true);	 
	 		            	    $('#phoneNumber').attr("readOnly", true);
	 		            	    
	  					   }
					    },
					    function(request){
						    alert("Error:" +request.status );	       						
					    }    		
	          );
		  }else {
			  $("form[name=regist]").append("<input type='hidden'  id='phoneCheck' name='phoneCheck' />");
			  $("#uniCheck").html('<a href="javascript:fn_checkId();" class="deepBtn"><spring:message code="page.user.phoneCheck" /></a>');
			  $("#btnUpdate").text('<spring:message code="button.create" />');
		  }
   }
   function del_check(code){	
	    var params = {phoneNumber : code};
    	fn_uniDelJSON("/backoffice/operManage/userInfoDelete.do"
				  , params
		          , "/backoffice/operManage/userList.do");	
   }  
   function fn_checkId(){
	   fn_uniCheck(   {inTable : "tb_userphoneinfo",
	       inCheckName : "PHONE_NUMBER",
	       inCondition : "PHONE_NUMBER=["+$("#phoneNumber").val()+"["
	      }, "phoneNumber", "phoneCheck", "<spring:message code='page.xml.confirm' />"
	    );
	   
   }

   
   function fn_CheckForm(){
	   if (any_empt_line_id("phoneNumber", '<spring:message code="page.user.alert01" />') == false) return;
	   if (any_empt_line_id("loginId", '<spring:message code="page.user.alert02" />') == false) return;
	   if ($("#mode").val() == "Ins" ) {
		    if ($("#phoneCheck").val() != "Y"){
		    	alert("전화번호 중복 확인이 안되었습니다.");
				return; 	
		    }
		    if (any_empt_line_id("loginPassword", '<spring:message code="page.user.alert03" />') == false) return;
	   }
	   
	   var params = {  
			           phoneNumber : $("#phoneNumber").val(),
			           phoneGubun : $("#phoneGubun").val(),
			           loginId : $("#loginId").val(),
			           loginPassword : $("#loginPassword").val(),
			           userName : $("#userName").val(),
			           phoneUseyn :  fn_emptyReplace($('input[name="phoneUseyn"]:checked').val(),"Y"),
					   mode : $("#mode").val()
			        };
	            uniAjax("/backoffice/operManage/userUpdate.do", params, 
		     			function(result) {
						    	if (result.status == "SUCCESS"){
						    		location.reload();
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
   function linkPage(pageNo) {
		$(":hidden[name=pageIndex]").val(pageNo);	
		$('#displaySeq').val("0");
		$("form[name=regist]").attr("action","/backoffice/operManage/userList.do").submit();
   }
   function search_form(){	
		  $(":hidden[name=pageIndex]").val("1");	
		  $('#displaySeq').val("0");
		  $("form[name=regist]").attr("action", "/backoffice/operManage/userList.do").submit();		  
   }
</script>
</body>
</html>