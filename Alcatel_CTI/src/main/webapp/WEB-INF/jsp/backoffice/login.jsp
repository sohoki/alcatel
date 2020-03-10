<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><spring:message code="URL.TITLE" /></title>
	<link rel="stylesheet" href="/css/reset.css">
	<link href="/css/page.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
</head>
<body>
	<!--//header-->
	<form name="regist" method="post" action="/backoffice/SecurityLogin.do">
    <header class="Mwrap">
        <section>
            <div class="subwidth">
                <h1><img src="/images/logo.png" alt="logo" /></h1>
            </div>
        </section>
    </header>
	<!--header//-->

	<!--//contents-->
	
	<div id="container">
		<div class="loginBox">
			<div class="login_title">
				<p>Login</p>
			</div>
			<div class="login_con">
				<div>
					<p>아이디</p>
					<input type="text" name="adminId" id="adminId" placeholder="아이디">	
				</div>
				<div>
					<p>비밀번호</p>
					<input type="password" name="adminPassword" id="adminPassword" placeholder="패스워드">	
				</div>
				<a href="javascript:form_check();" class="loginBtn">Login</a>
			</div>
		</div>
	</div>
	
	<!--contents//-->
    <div id="footer">
        <div class="contents">
            <span>
                COPYRIGHT ⓒ 2019 <title><spring:message code="URL.CopyInfo" /></title>
            </span>
            <div class="clear"></div>
        </div>
    </div>
    </form>
    <script type="text/javascript">
    function form_check(){
 	   if (any_empt_line_id('adminId', '<spring:message code="page.emp.message06" />') == false) return;
 	   if (any_empt_line_id('adminPassword', '<spring:message code="page.emp.message07" />') == false) return; 	   
 	   $("form[name=regist]").attr("action", "/backoffice/SecurityLogin.do").submit(); 	   
    }       
    $(document).ready(function() {
	    	if ("${message}" != "") {
	    		  if ("${message}" == "login_ok"){
	    			  location.href="/backoffice/basicManage/empList.do";  
	    		  }else {
	    			  alert('<spring:message code="page.emp.message08" />');
		    		  $("#admin_id").focus() ;	    			  
	    		  }				
	    	}    	           	    	
    });  
    </script>
</body>
</html>