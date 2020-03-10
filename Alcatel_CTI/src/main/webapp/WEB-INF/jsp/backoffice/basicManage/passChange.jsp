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
	<title>SSG</title>
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
					<input type="text" name="adminId" id="adminId" placeholder="아이디" >	
				</div>
				<div>
					<p>신규 비밀번호 1</p>
					<input type="password" name="adminPassword1" id="adminPassword1" placeholder="패스워드1" >	
				</div>
				<div>
					<p>신규 비밀번호 2</p>
					<input type="password" name="adminPassword2" id="adminPassword2" placeholder="패스워드2">	
				</div>
				<a href="javascript:form_check();" class="loginBtn">Login</a>
			</div>
		</div>
	</div>
	
	<!--contents//-->
    <div id="footer">
        <div class="contents">
            <span>
                 COPYRIGHT ⓒ 2019 SSG.COM Foundation. ALL RIGHTS RESERVED.
            </span>
            <div class="clear"></div>
        </div>
    </div>
    </form>
    <script type="text/javascript">
    function form_check(){
 	   if (any_empt_line_id('adminId', '<spring:message code="page.emp.message06" />') == false) return;
 	   if (any_empt_line_id('adminPassword', '<spring:message code="page.emp.message07" />') == false) return; 	 
 	   
 	  if(!chkPwd( $.trim($('#adminPassword').val()))){ 
		   alert('<spring:message code="page.emp.message02" />');    
		   $('#adminPassword').val('');
		   $('#adminPassword').focus(); return ;
		   }
	   if ( $.trim($('#adminPassword').val()) !=   $.trim($('#adminPassword2').val())  ){
		   alert('<spring:message code="page.emp.message03" />')
		   return;
	   }
	   
 	   $("form[name=regist]").attr("action", "/backoffice/passChange.do").submit(); 	   
    }       
    $(document).ready(function() {
	    	if ("${status}" != "") {
	    		  if ("${status}" == "SUCCESS"){
	    			  alert('<spring:message code="page.emp.message11" />')
	    			  location.href="/backoffice/login.do";  
	    		  }else {
	    			  alert('<spring:message code="page.emp.message12" />');
		    		  $("#adminId").focus() ;	    			  
	    		  }				
	    	}    	           	    	
    });  
    </script>
</body>
</html>