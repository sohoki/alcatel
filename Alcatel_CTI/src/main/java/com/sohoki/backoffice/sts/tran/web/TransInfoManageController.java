package com.sohoki.backoffice.sts.tran.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.sohoki.backoffice.alcatel.service.alcatelServiceInfo;
import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.sts.tran.service.TransInfoManageService;
import com.sohoki.backoffice.util.service.UniUtilInfo;
import com.sohoki.backoffice.util.service.UniUtilManageService;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;

import com.sohoki.backoffice.sts.tran.service.TransInfo;
import com.sohoki.backoffice.sts.tran.service.TransInfoVO;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;

@RestController
@RequestMapping("/backoffice/operManage")
public class TransInfoManageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransInfoManageController.class);
	
	@Resource(name="TransInfoManageService")
    protected TransInfoManageService tranService;
    
	
	 @Resource(name="CmmnDetailCodeManageService")
	 private EgovCcmCmmnDetailCodeManageService detailService;
	 
	 @Resource(name="UniUtilManageService")
	 private UniUtilManageService utilService;
		
	 @Resource(name="ErrorInfoManageMapper")
	 private ErrorInfoManageMapper errorInfo;
    
    /*
	@Resource(name="SchduleInfoManageService")
	private SchduleInfoManageService schService;
	*/
	@Resource(name="alcatelServiceInfo")
	private alcatelServiceInfo alcatelService;
	

	
    @Resource(name = "fileProperties")
	private Properties fileProperties;
	
	@Autowired
    protected EgovPropertyService propertiesService;
	
	@Autowired
	protected EgovMessageSource egovMessageSource;
	
	@Autowired
	private DefaultBeanValidator beanValidator;
	
	
	
	@RequestMapping ("tranList.do")
	public ModelAndView selectTranLst(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
									, @ModelAttribute("searchVO") TransInfoVO searchVO
									, HttpServletRequest request
									, BindingResult bindingResult) throws Exception {
		
          ModelAndView model = new ModelAndView("/backoffice/operManage/tranList");
		  //공용 확인 하기 
          
          try{
        	  Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
    	      if(!isAuthenticated) {
    	    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    	    		model.setViewName("/backoffice/login");
    	    		return model;
    	      }
    		      
    		  if(  searchVO.getPageUnit() > 0  ){    	   
    	    	      searchVO.setPageUnit(searchVO.getPageUnit());
    		  }else {
    				   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));   
    		  }
    		  searchVO.setPageSize(propertiesService.getInt("pageSize"));
    	      
    	      
    	      /** pageing */       
    	   	  PaginationInfo paginationInfo = new PaginationInfo();
    		  paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
    		  paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
    		  paginationInfo.setPageSize(searchVO.getPageSize());

    		  searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
    		  searchVO.setLastIndex(paginationInfo.getFirstRecordIndex() + searchVO.getPageSize());
    		  searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

    		  List<TransInfoVO> list = tranService.selectTranInfoManageListByPagination(searchVO);
    	      model.addObject("resultList", list);
    	      model.addObject("selectWorkgubun", detailService.selectCmmnDetailCombo("WORK_GUBUN"));
    	      model.addObject("selectSendgubun", detailService.selectCmmnDetailCombo("SEND_GUBUN"));
    	      int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
    	      
    		  paginationInfo.setTotalRecordCount(totCnt);
    	      model.addObject("paginationInfo", paginationInfo);
    	      model.addObject("totalCnt", totCnt);
    	      model.addObject("regist", searchVO);
    	      
          }catch(Exception e){
        	  LOGGER.debug("selectXmlLst error: " + e.toString());
          }
	      
	      return model;
	}
	@RequestMapping(value = "tranDel.do")
	 public ModelAndView tranInfoDelete(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												   ,@RequestBody  TransInfoVO vo
												   ,BindingResult bindingResult) throws Exception{
		 
		 ModelAndView  model = new ModelAndView("jsonView");
			
			
		    UniUtilInfo utilInfo = new UniUtilInfo();
			utilInfo.setInTable("tb_sendmessageinfo");
			utilInfo.setInCondition("TRAN_SEQ=["+vo.getTranSeq()+"[");
			String result = "F";
			try{
				
				
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			    if(!isAuthenticated) {
			    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
			    		model.addObject("status", "LOGIN FAIL");
			    		return model;
			    }
			    
			    int ret = 	utilService.deleteUniStatement(utilInfo);	
			     
			    if (ret > 0 ) {		    	  
			    	  model.addObject("message", egovMessageSource.getMessage("success.common.delete"));
					  model.addObject("status", "SUCCESS");  
			    }else {
			    	  throw new Exception();		    	  
			    }
			}catch (Exception e){
				LOGGER.error("tranInfoDelete  error: "  + e.toString());
				model.addObject("message", egovMessageSource.getMessage("fail.common.delete"));	
				model.addObject("status", "FAIL");
			}
		 return model;
		 
	 }
	@RequestMapping("tranView.do")
	public ModelAndView selectViewTran(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												, @ModelAttribute("XmlInfoVO") TransInfoVO vo
												, HttpServletRequest request
												, BindingResult bindingResult) throws Exception {
		     
		    ModelAndView model = new ModelAndView("/backoffice/operManage/tranView");
		    Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    try{
		    	if(!isAuthenticated) {
		    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
		    		model.setViewName("/backoffice/login");
		    		return model;
		        }
			    model.addObject("selectWorkgubun", detailService.selectCmmnDetailCombo("WORK_GUBUN"));
			    model.addObject("selectSendgubun", detailService.selectCmmnDetailCombo("SEND_GUBUN"));
			    if (vo.getMode().equals("Viw")){
			    	model.addObject("regist", tranService.selectTranInfoManageDetail(vo.getTranSeq()) );
			    }else {
			    	model.addObject("regist", vo);
			    }
			    model.addObject("registView", vo);
		    }catch(Exception e){
		    	model.addObject("status", Globals.STATUS_FAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.request.msg"));	
		    }
			return model;
	}
	@RequestMapping("tranDetail.do")
	public ModelAndView selectTranXml(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												,  TransInfoVO vo
												, HttpServletRequest request
												, BindingResult bindingResult			
												, BindingResult result) throws Exception {
		
		  ModelAndView model = new ModelAndView("/backoffice/operManage/tranDetail");
		  Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	      if(!isAuthenticated) {
	    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
	    		model.setViewName("/backoffice/login");
	    		return model;
	      }
	      model.addObject("selectWorkgubun", detailService.selectCmmnDetailCombo("WORK_GUBUN"));
		  model.addObject("selectSendgubun", detailService.selectCmmnDetailCombo("SEND_GUBUN"));
		    
		   model.addObject("regist", vo);
		   if (!vo.getMode().equals("Ins")){
			   model.addObject("regist", tranService.selectTranInfoManageDetail(vo.getTranSeq()) );
		   }
		   return model;			
	}
	
	@RequestMapping("tranUpdate.do")
	public ModelAndView updateTran(@RequestBody  TransInfoVO vo
								  , HttpServletRequest request
								  , BindingResult bindingResult			
								  , BindingResult result)throws Exception{
		
		ModelAndView model = new 	ModelAndView("jsonView");
		
		model.addObject("regist", vo);
		String meesage = "";
		    
		
		try{
			
			
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
		    		model.addObject("status", "LOGIN FAIL");
		    		return model;
		    }
			
			int ret  = 0;
			vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString());
			
			ret = tranService.updateTraninfoManage(vo) ;
			if (vo.getMode().equals("Ins")){
				UniUtilInfo util = new UniUtilInfo();
				util.setInCheckName("TRAN_SEQ");
				util.setInTable("tb_sendmessageinfo");
			    String tranSeq =  utilService.selectMaxValue(util);
			    vo.setTranSeq(tranSeq);
				meesage = "sucess.common.insert";
			}else {
				meesage = "sucess.common.update";
			}	
			LOGGER.debug("ret:" + ret);
			if (ret >0){
				model.addObject("status", Globals.STATUS_SUCCESS);
				model.addObject("message",  egovMessageSource.getMessage(meesage));
				
			}else {
				throw new Exception();
			}
			
		}catch (Exception e){
			model.addObject("status", Globals.STATUS_FAIL);
			model.addObject("message", egovMessageSource.getMessage("fail.request.msg"));	
		}finally {
			
		}					
		return model;
		
	}
	// groupCodeJson 체크 
	@RequestMapping("jsonAuthView.do")
	public ModelAndView selectJsonPage(HttpServletRequest request) throws Exception{	
		   
		   ModelAndView model = new 	ModelAndView("jsonView");
		   try{
			   String tranSeq = request.getParameter("tranSeq") != null ? request.getParameter("tranSeq") : "";	
			   
			   Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			   if(!isAuthenticated) {
			    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
			    		model.addObject("status", "LOGIN FAIL");
			    		return model;
			   }
			    
			   model.addObject("json_res", jsonDoc(tranService.selectTranInfoManageDetail(tranSeq) ));
			   model.addObject("status", Globals.STATUS_SUCCESS);
		   }catch(Exception e){
			   LOGGER.error("jsonAuthView :" + e.toString());
			   model.addObject("status", Globals.STATUS_FAIL);
			   model.addObject("message", egovMessageSource.getMessage("fail.common.select"));	
		   }
		   
		   return model;
	}
	@RequestMapping({"jsonAuthReq.do"})
	public ModelAndView selectJsonSendPage(HttpServletRequest request)   throws Exception {
		
		ModelAndView model = new 	ModelAndView("jsonView");
		try{
			 String tranSeq = request.getParameter("tranSeq") != null ? request.getParameter("tranSeq") : "";
			 String json = jsonDoc(this.tranService.selectTranInfoManageDetail(tranSeq));
			    
		
		     json = json.replace("'", "\"");
		    
		     JSONParser  jsonparse = new JSONParser(); 		
		     JSONObject jsonObject = (JSONObject) jsonparse.parse(json);						 
			 String commandType = jsonObject.get("command_type").toString();
			
			 int ProcessCk = tranService.selectTranProcessCount(commandType);
			 if (ProcessCk > 0){	
				 model.addObject("status", Globals.STATUS_SUCCESS);
				 model.addObject("json_res", jsonDocResult(json));
			 }else {
				 model.addObject("status", Globals.STATUS_FAIL);
				 model.addObject("json_res","NO_JSON");
			 }	
			 
			 
		}catch(Exception e){
			 LOGGER.error("jsonAuthReq.do :" + e.toString());
			 model.addObject("status", Globals.STATUS_FAIL);
			 model.addObject("message", egovMessageSource.getMessage("fail.common.select"));	
		}
	    return model;
	  }
	// jsonDoc 만들기
	public String jsonDoc(TransInfo vo)
	{	
		String[] inputParamArrays ;
		inputParamArrays = vo.getTranInputParam().split(",");
		String[] inputParamSampleArrays ;
		inputParamSampleArrays = vo.getTranInputParamSample().split(",");
		
		
		JSONObject obj = new JSONObject();
		obj.put("command_type", vo.getTranProcessName());
		
		try {			
			 JSONArray dataArray = new JSONArray();
			 
			 JSONObject sObject = new JSONObject();//배열 내에 들어갈 json			
			for (int i = 0; i < inputParamArrays .length; i++){			
				sObject.put(inputParamArrays[i].toString().trim(), inputParamSampleArrays[i].toString().trim());				
			}
			dataArray.add(sObject);
			obj.put("command_data", dataArray);
		}catch (JSONException e) {
			e.printStackTrace();
			LOGGER.debug("jsonDoc:"+ e.toString());			
		}		
	   return obj.toJSONString(); 	
	}
	@RequestMapping("jsonAuth.do")
	public String selectJsonResultPage(HttpServletRequest request) throws Exception{
		String json =   request.getParameter("json") != null ? request.getParameter("json") : "";
	    String jsonResult = "";
	    
	    json = json.replace("'", "\"");
	    
	    JSONParser  jsonparse = new JSONParser(); 		
	    JSONObject jsonObject = (JSONObject) jsonparse.parse(json);						 
		String commandType = jsonObject.get("command_type").toString();
		
		int ProcessCk = tranService.selectTranProcessCount(commandType);
		jsonResult = (ProcessCk > 0) ?  jsonDocResult(json) : "NO_JSON";
		
		return jsonResult;
		
	}
	//전문 처리 확인 
    public String jsonDocResult(String json) throws Exception{
		
		JSONParser  jsonparse = new JSONParser();
		String resultTxt = null; 
		try {
              JSONObject jsonObject = (JSONObject) jsonparse.parse(json);						 
			  String commandType = jsonObject.get("command_type").toString();
			  
			 
			  JSONArray dataInfoArray = (JSONArray) jsonObject.get("command_data");			 
			  JSONObject dataObject = (JSONObject) dataInfoArray.get(0);	
			  
			  TelephoneInfoVO  agentInfo = new TelephoneInfoVO();
			  /*SendMsgInfo sminfo = new SendMsgInfo();*/
			  
			  //commandType input type 값 split 으로 정리 해서 넘기기
			  
			  /*
			  TransInfoVO info = tranService.selectTranProcessInfo(commandType);
			  String[] inputParams = info.getTranInputParam().split(",");
			  for (String strTemp : inputParams){
				  dataObject.get(strTemp ).toString()
			  }
			  */
			  if (commandType.equals("SP_LOGIN")){
				  
				  
				  agentInfo.setAgentCode(dataObject.get("USER_ID").toString());
				  agentInfo.setAgentCode(dataObject.get("SEAT_ID").toString());
				  LOGGER.debug("SP_LOGIN");
				  
				  Boolean result  = alcatelService.userAuthentication(dataObject.get("USER_ID").toString(), dataObject.get("SEAT_ID").toString());
			      String message =  ( result == true) ? "OK": "FALSE";
				  resultTxt = "{'command_type':'"+commandType+"','result:': '" + message +"'}"; 
				  //단말기 구분을 통해 값 변경 전달 
				  //resultTxt = "{'command_type':'"+commandType+"','result':[{'"+sendGubun+"' : '"+sendInfo+"','ORD_CNT' : '"+orderCnt+"','MSG_CNT' : '"+msgCnt+"'}]}";
				  
			  }else if (commandType.equals("SP_LOGOUT")){
				  agentInfo.setAgentCode(dataObject.get("USER_ID").toString());
				  agentInfo.setAgentCode(dataObject.get("SEAT_ID").toString());
				  
				  Boolean result  = alcatelService.userSessionOut(dataObject.get("SEAT_ID").toString());
			      String message =  ( result == true) ? "OK": "FALSE";
				  resultTxt = "{'command_type':'"+commandType+"','result:': '" + message +"'}"; 
			  }
			  
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return resultTxt;
    }
    private String sendJson(String sendGubun, String jsonInfo , String sendUrl )throws Exception{
    	String returnJson = "";
    	try{
    		
    		URL url = new URL( sendUrl);
    		HttpURLConnection con = (HttpURLConnection) url.openConnection();
    		con.setRequestMethod(sendGubun);
    		con.setRequestProperty("Content-Type","application/json;utf-8");
    		con.setRequestProperty("Accept", "application/json");
    		con.setRequestProperty("Accept", "*/*");
    		//post 전송 
    		con.setDoOutput(true);
    		try(OutputStream os = con.getOutputStream()){
    			byte[] input = jsonInfo.getBytes("utf-8");
    			os.write(input, 0, input.length);
    			os.close();
    		}
    		try(BufferedReader br =new  BufferedReader(
    			new InputStreamReader(con.getInputStream(), "utf-8") )){
    			StringBuilder response = new StringBuilder();
    			while ((returnJson = br.readLine()) != null){
    				response.append(returnJson.trim());
    			}
    			br.close();
    		}
    		
    		con.disconnect();
    	}catch(Exception e){
    		LOGGER.debug("");
    	}
    	return returnJson;
    	
    }
}
