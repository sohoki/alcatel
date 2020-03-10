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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="URL.TITLE" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">    
    <link href="/css/reset.css" rel="stylesheet" />
    <link href="/css/global.css" rel="stylesheet" />
    <link href="/css/page.css" rel="stylesheet" />
    <script type="text/javascript" src="/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/jscript" src="/SE/js/HuskyEZCreator.js" ></script>
    <script type="text/javascript" src="/js/html2canvas.min.js"></script>
</head>
<body>
<div id="wrapper" style="min-height:90%;">
    <input type="hidden" id="preview" />
    <input type="hidden" id="imgData" />
    
     <table class="pop_table thStyle">
	      <tbody>
	          <tr>
	              <th style="height:28px;"><spring:message code="page.Title" /></th>
	              <td style="text-align:left">
	              <input type="text" name="reportTitle" id="reportTitle" value="${regist.reportTitle }" size="100" maxlength="200"/>
	              </td>
	          </tr>
	          <tr>
	             <td colspan="2" style="text-align:left">
	                <textarea name="ir1" id="ir1" style="width:100%; height:760px; display:none;"></textarea>
	             </td>
	          </tr>
	     </tbody>
    </table>
    
</div>

<div id="previewDiv" style="top:0px;left:0px;width:100%;height:80%;z-index:999;position: absolute;display:none;">

</div>
<div class="footerBtn" style="top:92%;z-index:999;position: absolute;">
                <a href="#" onclick="fn_preview();" class="redBtn"><spring:message code="button.preview" /></a>
	            <a href="#" onclick="fn_Checkform();" class="redBtn"><spring:message code="button.create" /></a>
	            <a href="self.close();" class="deepBtn"><spring:message code="button.close" /></a>
</div>
<iframe border="no" width="100%" height="1080px" id="frame_url" name="frame_url"></iframe>
 <script type="text/javascript">
        var oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "ir1",                        
            sSkinURI: "/SE/SmartEditor2Skin.html",
            htParams: { bUseToolbar: true,
                fOnBeforeUnload: function () { },
                //boolean 
                fOnAppLoad: function () { }
                //예제 코드
            },
            fCreator: "createSEditor2"
        });
        // ResizeFrame(document.ifrmae[0]);
        function fn_preview(){
        	if ($("#preview").val() == ""){
        		$("#preview").val("P");
        		$("#previewDiv").show();
        		$("#wrapper").hide();
        		var sHTML1 = oEditors.getById["ir1"].getIR();
        		console.log("sHTML1:"+sHTML1);
        		$("#previewDiv").html(sHTML1);
        	}else {
        		$("#preview").val("");
        		$("#previewDiv").hide();
        		$("#wrapper").show();
        		$("#previewDiv").html("");
        	}
        }
        //이미지 생성 확인 
        function fn_Checkform(){
        	 if (any_empt_line_id("reportTitle", '<spring:message code="page.report.alert01" />') == false) return;
        	 var sHTML1 = oEditors.getById["ir1"].getIR();
        	 $("#frame_url").contents().find('body').html("<body>"+sHTML1+"</body>");
        	 var body = $("#frame_url").contents().find('body')[0];
        	 
			  html2canvas(body, {
				background :'#FFFFFF',
                onrendered: function(canvas) {
                    if (typeof FlashCanvas != "undefined") {
                        FlashCanvas.initElement(canvas);
                    }
                    $("#imgData").val(canvas.toDataURL("image/jpeg", 1.0));
                    var params = { imgData : $("#imgData").val(),
                    		reportTitle : $("#reportTitle").val()
                    };
                    apiExecute(
          				  "POST", 
          				  "/backoffice/contentManage/reportCreateUpdate.do",
  	       					params,
  	       					null,				
  	       					function(result) {							
  	       						if (result != null) {	   
  	       							alert(result.message);
	  	       					    self.close();
	                    		    opener.document.reload();
  	       						}
  	       					},
  	       					function(request){
  	       						alert(request.status );	       						
  	       					},
  	       					null
  	     			); 
                }
            }); 
        }
  </script>  
</body>
</html>