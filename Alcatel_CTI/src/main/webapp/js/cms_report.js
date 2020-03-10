//페이지 창 띠웅기
function fn_PageView(seq, url){
		var url = "/backoffice/contentManage/reportPreView.do?reportSeq="+seq ;//;?report_seq="+seq;
		window.open(url, 'url','menubar=no,width=1920,height=1080,toolbar=no,scrollbars=no');
		
}
//콘텐츠 파일 창 띠우기 
function view_Con(atchId, w, h){
	   	var url = "/backoffice/popup/fileView.do?atchFileId="+atchId;
	   	if((w == 1024 && h == 768) || (w==h)){
	   		  w = "768";
	   		  h = "768";
	   	}else if(w - h > 0){
	   		  // 가로
	   		  w = "832";
	   		  h = "468";
	   	}else{
	   		  w = "468";
	   		  h = "832";
	   	}
   	    window.open(url,"파일 업로드", 'width='+w+',height='+h+',top=100,left=250,scrollbars=auto');
}