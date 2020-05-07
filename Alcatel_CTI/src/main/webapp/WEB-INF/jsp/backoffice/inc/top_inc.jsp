<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>  
<%@ page import ="egovframework.com.cmm.AdminLoginVO" %>
<%
AdminLoginVO loginVO = (AdminLoginVO)session.getAttribute("AdminLoginVO");


if(loginVO == null ){
	
	%>
	<script type="text/javascript">
	location.href="/backoffice/login.do";
	</script>
	<%        
}else{ 
	%> 
    <header class="Mwrap">
        <section>
            <div class="subwidth">
                <h1><a href="/backoffice/boardManage/boardList.do?boardGubun=NOT"><img src="/images/logo.png" alt="logo" /></h1>
                <nav id="topMenu">
                    <ul>
                        <li class="dropdown">
                            <a href="/backoffice/operManage/errList.do" class="dropbtn"><spring:message code="menu.menu02_3" /></a>
                        </li>
                        <li class="dropdown">
                            <a href="/backoffice/basicManage/AgentInfoList.do" class="dropbtn">
                            <spring:message code="menu.menu02" />
                            </a>
                            <ul class="dropdown-content sedrop">
                               
                                <li><a href="/backoffice/operManage/agentList.do"><spring:message code="menu.menu02_1" /></a></li>
                                <li><a href="/backoffice/operManage/userList.do"><spring:message code="menu.menu02_2" /></a></li>  
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="/backoffice/basicManage/empList.do" class="dropbtn"><spring:message code="menu.menu03" /></a>
                            <ul class="dropdown-content">
                                <li><a href="/backoffice/basicManage/empList.do"><spring:message code="menu.menu03_1" /></a></li>
                                <li><a href="/backoffice/basicManage/partList.do"><spring:message code="menu.menu03_2" /></a></li>
                                <li><a href="/backoffice/basicManage/centerList.do"><spring:message code="menu.menu03_3" /></a></li>  
                                <li><a href="/backoffice/basicManage/codeList.do"><spring:message code="menu.menu03_4" /></a></li>  
                                <li><a href="/backoffice/operManage/tranList.do"><spring:message code="menu.menu03_5" /></a></li>  
                            </ul>
                        </li>
                    </ul>
                </nav>
                <p>   
                    <a href="<c:url value='/backoffice/actionLogout.do'/>"><spring:message code="button.logout" /></a>
                </p>
            </div>
        </section>
    </header>
        <% } %>	    
