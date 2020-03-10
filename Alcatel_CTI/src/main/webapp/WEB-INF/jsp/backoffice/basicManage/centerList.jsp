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
<div id="wrapper">
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<form:form name="regist" commandName="regist" method="post" action="/backoffice/basicManage/centerList.do">	   	  
<c:import url="/backoffice/inc/top_inc.do" />	
 
<input type="hidden" name="pageIndex" id="pageIndex" value="${searchVO.pageIndex }">
<input type="hidden" name="centerId" id="centerId" />
<input type="hidden" name="mode" id="mode" />
<div class="Aconbox">
        <div class="rightT">
            <div class="Smain">
                <div class="Swrap Stitle">
                    <div class="infomenuA">
                        <img src="/images/home.png" alt="homeicon" />
                        <span>></span><spring:message code="menu.menu03" /><span>></span><strong><spring:message code="menu.menu03_3" /></strong>
                    </div>
                </div>
            </div>

            <div class="Swrap Asearch">
                <div class="Atitle"><spring:message code="page.common.pageCnt" arguments="${totalCnt}"/></div>
                <section class="Bclear">
                   <select name="searchCondition"  id="searchCondition">
								<option value=""><spring:message code="combobox.text" /></option>
								<option value="centreId" <c:if test="${searchVO.searchCondition == 'centreId' }"> selected="selected" </c:if>>
								<spring:message code="page.center.centerId" />
								</option>
								<option value="CenterNm" <c:if test="${searchVO.searchCondition == 'CenterNm' }"> selected="selected" </c:if>>
								<spring:message code="page.center.centerNm" />
								</option>
							</select>	
                    <input class="nameB" type="text" name="searchKeyword" id="searchKeyword" placeholder="이름" value="${searchVO.searchKeyword }">
                    <a href="javascript:search_form()"><span class="redBtn"><spring:message code="button.inquire" /></span></a>
                    <div class="rightB">
                        <a href="javascript:fn_Center('Ins','0')"><span class="deepBtn"><spring:message code="button.create" /></span></a>
                    </div>
                </section>
            </div>

            <div class="Swrap tableArea">
                <table class="margin-top30 backTable">
                    <thead>
                        <tr>
                            <th><spring:message code="page.center.centerImg" /></th>
                            <th><spring:message code="page.center.centerNm" /></th>
                            <th><spring:message code="page.center.agentInfo" /></th>
                            <th><spring:message code="page.center.groupNm" /></th>
                            <th><spring:message code="page.center.centerAddr" /></th>
                            <th><spring:message code="button.use" /></th>
                            <th><spring:message code="button.delete" /></th>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach items="${resultList }" var="centerInfo" varStatus="status">
                        <tr>
                            <td>
							 <a href="javascript:fn_Center('Edt','${centerInfo.centerId }')">
								<c:choose>
						                <c:when test="${centerInfo.centerImg eq ''}">
						                  <img id="atchFileId" name="atchFileId" src="/img/no_image.png"  width="100" height="60"/>
						                </c:when>
						        		<c:otherwise>
						        		  <img src="<c:url value='/upload/${centerInfo.centerImg}' />" alt="${centerInfo.centerNm }" width="150px;" />
						        		</c:otherwise>					        							        
						        </c:choose>
							 </a>
							</td>
							<td><a href="javascript:fn_Center('Edt','${centerInfo.centerId }')">${centerInfo.centerNm }</a></td>
							<td><a href="javascript:fn_Center('AGT','${centerInfo.centerId }')">${centerInfo.centerAgentCnt }</a></td>
							<td>${centerInfo.partNm }</td>
                            
                            <td style="text-align:left">${centerInfo.centerZipcode }<br>
                                ${centerInfo.centerAddr1 } ${centerInfo.centerAddr2 }
                            </td>
                            
                            <td><c:choose>
								   <c:when test="${centerInfo.centerUseYn eq 'Y' }"><spring:message code="button.use" /></c:when>
								   <c:otherwise><spring:message code="button.nouse" /></c:otherwise>
								</c:choose>	
							</td>
                            <td><a href="javascript:del_check('${ centerInfo.centerId }');" class="grayBtn"><spring:message code="button.delete" /></a></td>
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
<c:import url="/backoffice/inc/bottom_inc.do" />    
</form:form>    
	
<script type="text/javascript">
	$(document).ready(function() { 
		if ("${status}" != "" ){
			if ("${status}" == "SUCCESS" ){
				alert('<spring:message code="success.common.update" />');					
			}else  {
				alert('<spring:message code="fail.common.msg" />');			
			}	
		}						   
	}); 
	function linkPage(pageNo) {
		$(":hidden[name=pageIndex]").val(pageNo);		
		$("form[name=regist]").attr("action","/backoffice/basicManage/centerList.do").submit();
	}
	function fn_Center(mode, code){
		$("#centerId").val(code);
		$("#mode").val(mode)
		url = (mode == "Ins") ? "/backoffice/basicManage/centerDetail.do" : "/backoffice/basicManage/centerView.do";
		$("form[name=regist]").attr("action",url).submit();  
	}
	function del_check(code){
		fn_uniDel("/sohoki/util/UnideleteParam.do"
				  , {tableId: "tb_centerinfo", conField: "CENTER_ID", value : code }
		          , "/backoffice/basicManage/centerList.do");				
	}  
    </script>
</body>
</html>