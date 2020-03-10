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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>국민건강보험</title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/popup.css">    
    <script src="/js/popup.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
<form:form commandName="regist" name="regist" method="post">											   	
<form:hidden path="mode"  id="mode"/>        
<form:hidden path="deptId"  id="deptId"/>
<form:hidden path="empNo"  id="empNo"/>
      <div class="pop_container">
        <!--//팝업 타이틀-->
        <div class="pop_header">
            <div class="pop_contents">
                <h2>관리자 관리</h2>
            </div>
        </div>
        <!--팝업타이틀//-->
        <!--//팝업 내용-->
        <div class="pop_contents">
            <table class="pop_table thStyle">
                <tbody>
                    <tr>
                        <th>부서(직급)</th>
                        <td><form:input  path="deptName" size="10" maxlength="20" id="deptName"   value="${regist.deptName }" /></td>
                        <th>이름(사번)</th>
                        <td><form:input  path="adminName" size="10" maxlength="20" id="adminName"   value="${regist.adminName }" />
                        <span id="empCheck" ></span>
                        </td>
                    </tr>
                    <tr>
                        <th>아이디</th>
                        <td><form:input  path="adminId" size="10" maxlength="20" id="adminId"   value="${regist.adminId }" />
                        <span id="uniCheck" ></span>
                        </td>
                        <th>연락처</th>
                        <td><form:input  path="adminTel" size="10" maxlength="20" id="adminTel"   value="${regist.adminTel }" /></td>
                    </tr>
                    <tr>
                        <th>비밀번호</th>
                        <td><form:password path="adminPassword" id="adminPassword" title="비밀번호" size="20" maxlength="20" /></td>
                        <th>비밀번호확인</th>
                        <td><input name="adminPassword2" id="adminPassword2" title="비밀번호확인" type="password" size="20" maxlength="20" /></td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td><form:input  path="adminEmail" size="10" maxlength="20" id="adminEmail"   value="${regist.adminEmail }" /></td>
                        <th>등록일</th>
                        <td><span id="reg_date"></span></td>

                    </tr>
                    <tr>
                        <th>사용자구분</th>
                        <td>
                            <form:select path="adminLevel" id="adminLevel" title="소속">
						         <form:option value="" label="--선택하세요--"/>
		                         <form:options items="${selectState}" itemValue="authorCode" itemLabel="authorNm"/>
						    </form:select>										
                        </td>
                        <th>사용여부</th>
                        <td>
                            <input type="radio" name="useYn" value="Y" <c:if test="${regist.useYn == 'Y' }"> checked </c:if> />사용
							<input type="radio" name="useYn" value="N" <c:if test="${regist.useYn == 'N' }"> checked </c:if> />사용안함					
                        </td>
                    </tr>
                    
                       
                </tbody>
            </table>
        </div>
        <!--팝업 내용//-->
        <!--//팝업 버튼-->
        <div class="footerBtn">
            <a href="Badmin_modify.html" class="redBtn" id="btnUpdate">수정</a>
            <a href="" class="deepBtn">닫기</a>
        </div>
        <!--팝업 버튼//-->
    </div>
</form:form>
<script type="text/javascript">
	$(document).ready(function() {     
		//alert("${status}");
		
		if ("${status}" != ""){
			if ("${status}" == "SUCCESS") {
				alert("정상 처리 되었습니다");  
				opener.document.location.reload();
			}else{
				alert("작업 도중 문제가 발생 하였습니다.");
			}						
		}		
	    if ($("#mode").val() == "Ins"){   	       
	 		$("form[name=regist]").append("<input type='hidden'  id='idCheck' name='idCheck' />");	 		
	 		$("#uniCheck").html("<a href='javascript:check_id();' class='reCheck'>중복검사</a>");
	 		$("#empCheck").html("<a href='javascript:user_search();' class='reCheck'>담당자검색</a>");	
	 		$("#btnUpdate").text("입력");
	    }	else {
	    	$("#btnUpdate").text("수정");
	   	    $("#uniCheck").html();
	   	    $("input[name=adminId]").attr("readOnly", true);
	    }
	});	
	function user_search(){
		
	}
	function check_id(){	  
	    if ( $("#adminId").val()!= ""   ){	    	
	    apiExecute(
				"POST", 
				"/backoffice/sub/basicManage/IdCheck.do",
				{
					userID : $("#adminId").val()
				},
				null,				
				function(result) {							
					if (result != null) {					
						if (result == "0"){
							alert("사용 하실수 있는 아이디 입니다.");
							$("#idCheck").val("Y");							
						}else {
							alert("사용 하실수 없는 아이디 입니다.");
							$("#idCheck").val("N");
						}
						
					}
				},
				null,
				null
			);
	    }else {
	    	alert ("아이디를 입력해 주세요");
	    	$("#adminId").focus();
	    	return;
	    }
	}
	function check_form(){	  
		   if ($("#mode").val() == "Ins"){
			   if ($("#idCheck").val() == "N"){
				   alert("중복 아이디 체크를 하지 않았습니다");
				   return ;			   
			   }
			   if ($("#empNo").val() == ""){
				   alert("관리자를 검색해 주세요");
				   return ;			   
			   }
			   
			   if(!chkPwd( $.trim($('#adminPassword').val()))){ 
				   alert('비밀번호를 확인하세요.₩n(영문,숫자를 혼합하여 10~20자 이내)');    
				   $('#adminPassword').val('');
				   $('#adminPassword').focus(); return ;
				   }
			   if ( $.trim($('#adminPassword').val()) !=   $.trim($('#adminPassword2').val())  ){
				   alert("비밀 번호가 일치 하지 않습니다.")
			   }
		   }   	
		   if (any_empt_line_id("adminLevel", "권한을 선택 하지 않았습니다.") == false) return;	   		   	
		   $("form[name=regist]").attr("action", "/backoffice/sub/basicManage/managerUpdate.do").submit();
		   return;
	}
	function del_form(){
		
	    if (confirm("삭제 하시겠습니까?")== true){
	    	apiExecute(
					"POST", 
					"/backoffice/sub/basicManage/managerDelete.do",
					{
						adminId : $("#adminId").val()
					},
					null,				
					function(result) {				
						if (result != null) {
							if (result == "O"){
								alert("정상적으로 삭제되었습니다.");
							}else {
								alert("삭제시 문제가 생겼습니다");
							}					
						}else {
						    alert("삭제시 문제가 생겼습니다");
						  
						}
						$("form[name=regist]").attr("action", "/backoffice/sub/basicManage/manageList.do").submit();					
					},
					null,
					null
				);	
	    }else {
	    	return;
	    }
	   	  	   
	}
	function chkPwd(str){
		 var reg_pwd = /^.*(?=.{10,20})(?=.*[0-9])(?=.*[a-zA-Z]).*$/;
		 if(!reg_pwd.test(str)){
		  return false;
		 }
		 return true;
	}
	
</script>    
</body>
</html>