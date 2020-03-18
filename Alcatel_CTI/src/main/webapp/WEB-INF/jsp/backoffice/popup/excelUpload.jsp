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
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title><spring:message code="URL.TITLE" /></title>
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/popup.css">
    <script src="/js/popup.js"></script>
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script src="http://malsup.github.com/jquery.form.js"></script> 
</head>
<body>
<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post"  action= "${pageContext.request.contextPath}/backoffice/operManage/excelUpload.do">
    <div class="contents">
        <div>첨부파일은 한개만 등록 가능합니다.</div>
 
        <dl class="vm_name">
                <dt class="down w90">첨부 파일</dt>
                <dd><input id="excelFile" type="file" name="excelFile" /></dd>
        </dl>        
    </div>
    <div>
       <span id="uploadResult"></span>
    </div>
            
    <div class="bottom">
        <button type="button" id="addExcelImpoartBtn" class="btn" onclick="check()" ><span>추가</span></button> 
    </div>
</form> 

<script type="text/javascript">



function check() {
    var file = $("#excelFile").val();
    if (file == "" || file == null) {
        alert("파일을 선택해주세요.");
        return ;
    } else if (!checkFileType(file, "xlsx/xls")) {
        alert("엑셀 파일만 업로드 가능합니다.");
        return ;
    }
    //업로드 
    if (confirm("업로드 하시겠습니까?")) {
        var options = {
            success : function(data) {
               if (data.status == "SUCCESS"){
            	   var message = ""
            	   if (data.message == "OK"){
            		   message = "OK"
            	   }else {
            		  var errorInfo = data.message.substring(1).split("/");
            		  for (var i =0; i < errorInfo.length; i++){
            			  message += message +"<br>";
            		  }
            	   }
            	   $("#uploadResult").html("업로드 결과:" + message);
            	   $("#uploadResult").click(function(){
            		 opener.document.location.reload();  
            		 self.close();
            	   });
               }
            },
            type : "POST"
        };
        $("#excelUploadForm").ajaxSubmit(options);

    }
}
</script>
</body>
</html>