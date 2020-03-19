package com.sohoki.backoffice.sts.tran.web;

import java.net.URLDecoder;
import java.util.List;

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
	
	@Resource(name="SendMsgInfoManageService")
	private SendMsgInfoManageService sendMsgService;
	*/

	

	
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
	@RequestMapping("tranView.do")
	public ModelAndView selectViewTran(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												, @ModelAttribute("XmlInfoVO") TransInfoVO vo
												, HttpServletRequest request
												, BindingResult bindingResult) throws Exception {
		     
		    ModelAndView model = new ModelAndView("/backoffice/operManage/xmlView");
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
		
		  ModelAndView model = new ModelAndView("/backoffice/operManage/xmlDetail");
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
				util.setInCheckName("XML_SEQ");
				util.setInTable("TB_SENDMESSAGETYPR");
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
			  
			  TelephoneInfo  agentInfo = new TelephoneInfo();
			  /*SendMsgInfo sminfo = new SendMsgInfo();*/
			  
			  /*if (commandType.equals("SP_AGENTSTATE")){
				  
				  
				  agentInfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  agentInfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  AgentStateService.updateAgentStateCnt(dataObject.get("AGENT_CODE").toString());
				  
				  sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  String sendInfo = "";
				  String sendGubun = "";
				  AgentInfoVO info = agentService.selectAgentUrlCheck(agentInfo);
				  if (info.getAgentContentgubun().equals("AGENT_CONTENT_1")){
					  sendInfo = info.getUpdateChange().equals("N") ? "Y" : "N";
					  sendGubun = "URL_CNT";
				  }else {
					  sendInfo = (agentGroup.selectAgentSchCount(dataObject.get("AGENT_CODE").toString()) > 0)  ? "Y" : "N";
					  sendGubun = "SCH_CNT";
				  }
				  String orderCnt = (sendMsgService.selectAgentOrderCount(sminfo) > 0) ? "Y":"N";
				  String msgCnt = (schService.selectScheduleSendInfoCnt(dataObject.get("AGENT_CODE").toString()) > 0 ) ? "Y":"N";
				  //단말기 구분을 통해 값 변경 전달 
				  resultTxt = "{'command_type':'"+commandType+"','result':[{'"+sendGubun+"' : '"+sendInfo+"','ORD_CNT' : '"+orderCnt+"','MSG_CNT' : '"+msgCnt+"'}]}";
				  
			  }else if (commandType.equals("SP_AGENTURL")){
				  agentInfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  agentInfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  AgentInfoVO info = agentService.selectAgentUrlCheck(agentInfo);
				  
				  String url = "/backoffice/basicManage/AgentInfoPreview.do?agentCode="+ info.getAgentCode();
				  
				  
				  //url변경 관련 멧세지 넣기 
				  sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  sminfo.setSendResult("N");
				  sminfo.setXmlProcessName(commandType);
				  sendMsgService.insertSendMsgInfoManage(sminfo);
				  String msgSeq = String.valueOf(sendMsgService.selectMaxSeq());
				  
				  resultTxt = "{'command_type':'"+commandType+"','result':[{'URL' : '"+url+"','MSG_SEQ' : '"+msgSeq+"'}]}";
				  
			  }else if (commandType.equals("SP_AGENTURLUPDATE")){
				  agentInfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  agentInfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  agentInfo.setUpdateChange(dataObject.get("URL_CHK").toString());
				  //변경 사항 등록
				  int ret =  agentService.updateAgentUrlRec(agentInfo);
				  
				  //변경 사항 등록
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  sminfo.setSendResult(dataObject.get("URL_CHK").toString());
				  sminfo.setXmlProcessName(commandType);
				  sminfo.setErrorMessage(dataObject.get("CHANGE_URL").toString());
				  sminfo.setMsgSeq(dataObject.get("MSG_SEQ").toString());
				  sendMsgService.updateSendMsgInfoManage(sminfo);
				  
				  
				  String result = (ret > 0) ? "O" : "F"; 
				  //url변경 관련 멧세지 넣기 
				  
				  resultTxt = "{'command_type':'"+commandType+"','result':'"+result+"'}";
				  
			  }else if (commandType.equals("SP_AGENTCONTENTLST")){
				  agentInfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  agentInfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  
				  List<AgentGroupInfoVO> resultContent =  agentGroup.selectDisplayPageInfoContentList(dataObject.get("AGENT_CODE").toString());
					
				  JSONObject obj = new JSONObject();
				  obj.put("command_type", commandType);
				  JSONArray dataArray = new JSONArray();
				  AgentGroupInfo info = new AgentGroupInfo();			
				  for (int i = 0; i < resultContent.size(); i++){
					JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
					sObject.put("GROUP_SEQ", resultContent.get(i).getGroupSeq());
					sObject.put("CONSCH_CODE", resultContent.get(i).getConschCode());
					sObject.put("DISPLAY_TITLE", URLDecoder.decode(resultContent.get(i).getDisplayTitle(), "UTF-8"));
					sObject.put("DISPLAY_WIDTH", resultContent.get(i).getDisplayWidth());
					sObject.put("DISPLAY_HEIGHT", resultContent.get(i).getDisplayHeight());
					sObject.put("DISPALY_TOTALTIME", resultContent.get(i).getDisplayTotalTime());
					sObject.put("DISPLAY_NEXTSEQ", resultContent.get(i).getDisplayNextseq());
					sObject.put("DISPLAY_LOCALFILE", resultContent.get(i).getDisplayLocalfile());
					dataArray.add(sObject);
					
					info.setGroupSeq(resultContent.get(i).getGroupSeq());
					info.setSendResult("O");
					agentGroup.updateAgentSendUpdate(info);
				  }			 	
				  info = null;
				  obj.put("CONINFO", dataArray);				 
				  resultTxt = obj.toJSONString();
				  obj.clear();   
				  
			  }else if (commandType.equals("SP_AGENTCONTENTPAGELSTUPDATECHECK")){
				  
				  sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  sminfo.setSendResult(dataObject.get("RECEIVED_RESULT").toString());
				  sminfo.setXmlProcessName(commandType);
				  sendMsgService.insertSendMsgInfoManage(sminfo);
				  
				  
				  
				  AgentGroupInfo info = new AgentGroupInfo();
				  info.setReceivedResult(dataObject.get("RECEIVED_RESULT").toString());
				  info.setGroupSeq(Integer.valueOf(dataObject.get("GROUP_SEQ").toString()));
				  int ret = agentGroup.updateAgentReceivedUpdate(info);
				  info = null;
				  String result = (ret > 0) ? "O" : "F"; 
				  //url변경 관련 멧세지 넣기 
				  
				  resultTxt = "{'command_type':'"+commandType+"','result':'"+result+"'}";
				  
			  }else if (commandType.equals("SP_AGENTCONTENTFILELST")){
				  AgentGroupInfo info = new AgentGroupInfo();
				  info.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  info.setGroupSeq( Integer.valueOf(dataObject.get("GROUP_SEQ").toString()));
				  
				  List<AgentGroupInfoVO> resultContent =  agentGroup.selectDisplayFileInfoContentList(info);
					
				  JSONObject obj = new JSONObject();
				  obj.put("command_type", commandType);
				  JSONArray dataArray = new JSONArray();
						
				  for (int i = 0; i < resultContent.size(); i++){
					JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
					sObject.put("GROUP_SEQ", resultContent.get(i).getGroupSeq());
					sObject.put("CONSCH_CODE", resultContent.get(i).getConschCode());
					sObject.put("DISPLAY_SEQ", resultContent.get(i).getDisplaySeq());
					sObject.put("REPORT_URL", resultContent.get(i).getReportUrl());
					sObject.put("ATCH_FILE_ID", resultContent.get(i).getAtchFileId());
					sObject.put("STRE_FILE_NM", resultContent.get(i).getStreFileNm());
					sObject.put("FILE_EXTSN", resultContent.get(i).getFileExtsn());
					sObject.put("FILE_SIZE", resultContent.get(i).getFileSize());
					dataArray.add(sObject);
					
				  }			 	
				  info = null;
				  obj.put("CONINFO", dataArray);				 
				  resultTxt = obj.toJSONString();
				  obj.clear();   
				  
			  }else if (commandType.equals("SP_AGENTCONTENTFILELSTUPDATECHECK")){
				  
				  sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  sminfo.setSendResult(dataObject.get("FILE_RESULT").toString());
				  sminfo.setXmlProcessName(commandType);
				  sendMsgService.insertSendMsgInfoManage(sminfo);
				  
				  
				  
				  AgentGroupInfo info = new AgentGroupInfo();
				  info.setReceivedFileResult(dataObject.get("FILE_RESULT").toString());
				  info.setGroupSeq(Integer.valueOf(dataObject.get("GROUP_SEQ").toString()));
				  int ret = agentGroup.updateAgentFileUpdate(info);
				  info = null;
				  String result = (ret > 0) ? "O" : "F"; 
				  //url변경 관련 멧세지 넣기 
				  
				  resultTxt = "{'command_type':'"+commandType+"','result':'"+result+"'}";
				  
			  }else if (commandType.equals("SP_AGENTORDERLST")){
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  
				  List<SendMsgInfoVO> resultLst = sendMsgService.selectAgentOrderLst(sminfo);
					
					JSONObject obj = new JSONObject();
				    obj.put("command_type", commandType);
				    JSONArray dataArray = new JSONArray();
				  			
				   for (int i = 0; i < resultLst.size(); i++){
					    JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
						sObject.put(  "MSG_SEQ", resultLst.get(i).getMsgSeq());
						sObject.put(  "XML_PROCESS_NAME", resultLst.get(i).getXmlProcessName());
						sObject.put(  "SEND_REGDATE", resultLst.get(i).getSendRegdate());						
						dataArray.add(sObject);
				   }			 	  
				   obj.put("CONINFO", dataArray);				 
				   resultTxt = obj.toJSONString();
				   obj.clear();				   
				  
			  }else if (commandType.equals("SP_AGENTORDERRESULT")){
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  
				  String result = dataObject.get("ERROR_MESSAGE").toString().equals("OK") ? "O" : "F";
				  sminfo.setSendResult(result);
				  sminfo.setXmlProcessName(commandType);
				  sminfo.setErrorMessage(dataObject.get("ERROR_MESSAGE").toString());
				  sminfo.setMsgSeq(dataObject.get("MSG_SEQ").toString());
				  int ret = sendMsgService.updateSendMsgInfoManage(sminfo);
				  result = (ret > 0)? "O" :"F";
				  resultTxt = "{'command_type':'"+commandType+"','result':'"+result+"'}";
				  
			  }else if (commandType.equals("SP_AGENTORD")){
				  //명령어 전송
				  agentInfo.setAgentCode(dataObject.get("DASAN_ID").toString());
				  agentInfo.setAgentMac(dataObject.get("DASAN_MAC").toString());
				  
				  AgentInfoVO info = agentService.selectAgentUrlCheck(agentInfo);
				  
				  
				  JSONObject jsonRes = new JSONObject();
				  OrderInfo orderInfo = orderService.selectAgentOrderList(info.getAgentCode());
				  
				  
				  resultTxt = "{'command_type':'"+commandType+"','result':{'ORD_SEQ': '"+orderInfo.getOrderSeq() +"','ORD_INFO',:'"+orderInfo.getAgentOrder()+"'}}";
				  
			  }else if (commandType.equals("SP_AGENTREBOOT")){
  					
				sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				sminfo.setXmlProcessName(commandType);
				sminfo.setSendResult("N");
				
				int ret =  sendMsgService.insertSendMsgInfoManage(sminfo);
				
				if ( ret > 0){
					int max_seq = sendMsgService.selectMaxSeq();
					resultTxt = "{'command_type':'"+commandType+"','result':'"+Integer.toString(max_seq)+"'}";
				}else {
					resultTxt = "{'command_type':'"+commandType+"','result':'F'}";
				}	
			  }else if (commandType.equals("SP_AGENTMESSAGEINFO")){
				  
				  
				  sminfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  sminfo.setAgentMac(dataObject.get("AGENT_MAC").toString());
				  // 메세지 전문 확인 후 메세지 TB_MESSAGEHISTORY 에 등록후 리스트 보여 주기 		
				  List<SchduleInfo> messageInfo = schService.selectScheduleSendInfo(dataObject.get("AGENT_CODE").toString());
				  
				  
				 
				  
				  JSONObject obj = new JSONObject();
				  obj.put("command_type", commandType);
				  JSONArray dataArray = new JSONArray();
				  for  (int i = 0; i < messageInfo.size(); i++){
					  JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
					  //SEND_MESSAGE, SEND_MESSAGESTARTDAY, SEND_MESSAGEENDDAY, SEND_MESSAGESTARTTIME, SEND_MESSAGEENDTIME, SEND_FONTTYPE
					  sObject.put(  "SCH_CODE", messageInfo.get(i).getSchCode());
					  sObject.put(  "SEND_MESSAGE", URLDecoder.decode(messageInfo.get(i).getSchMessage(), "UTF-8") );
					  sObject.put(  "SEND_MESSAGESTARTDAY", messageInfo.get(i).getSchStartDay());
					  sObject.put(  "SEND_MESSAGEENDDAY", messageInfo.get(i).getSchEndDay());
					  sObject.put(  "SEND_MESSAGESTARTTIME", messageInfo.get(i).getSchStartTime());
					  sObject.put(  "SEND_MESSAGEENDTIME", messageInfo.get(i).getSchEndTime());
					  sObject.put(  "SEND_FONTTYPE", messageInfo.get(i).getSchFonttype());
					  dataArray.add(sObject);
				  }
				  
				  obj.put("MESSAGEINFO", dataArray);				 
				  resultTxt = obj.toJSONString();
				  obj.clear();
				  
			  }else if (commandType.equals("SP_AGENTMESSAGEUPDATE")){
				  
				  SendMessageInfoVO sendInfo = new SendMessageInfoVO();
				  sendInfo.setAgentCode(dataObject.get("AGENT_CODE").toString());
				  sendInfo.setSchCode(dataObject.get("SCH_CODE").toString());
				  
                  if (dataObject.get("ERROR_MESSAGE").toString().equals("OK")){
                	  sendInfo.setMsgRecCheck("Y");
				  }else {
					  sendInfo.setMsgRecCheck("N");
				  }
                  int ret = sendService.updateSendMessageAgent(sendInfo);
                  //업데이트 확인 
                  if (ret > 0){
						resultTxt = "{'command_type':'"+commandType+"','result':'O'}";
				  }else {
						resultTxt = "{'command_type':'"+commandType+"','result':'F'}";
				  }
			  }*/
			  
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return resultTxt;
    }
}
