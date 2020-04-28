package com.sohoki.backoffice.sym.agt.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.sohoki.backoffice.util.service.UniUtilInfo;

import com.sohoki.backoffice.sym.cnt.service.CenterInfoService;
import com.sohoki.backoffice.uat.uia.service.PartInfoManageService;
import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.sts.error.service.ErrorInfo;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import egovframework.let.uat.uia.service.AdminInfoVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;




import java.io.IOException;

import com.sohoki.backoffice.util.web.ExcelReadOption;
import com.sohoki.backoffice.util.web.ExcelRead;

//전화기
import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoManageService;
//사용자
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfo;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoManageService;
import com.sohoki.backoffice.util.service.UniUtilManageService;

@RestController
@RequestMapping("/backoffice/operManage")
public class AgentInfoManageController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgentInfoManageController.class);
	 
	 
	 @Autowired
	 private DefaultBeanValidator beanValidator;
	 
	 
	 @Autowired
	 protected EgovMessageSource egovMessageSource;
		
	 @Autowired
	 protected EgovPropertyService propertiesService;
	 
	 @Autowired
	 private TelephoneInfoManageService telephoneService;
	 
	 @Autowired
	 private UserPhoneInfoManageService userService;
	 
	 @Autowired
	 private CenterInfoService centerService;
	 
	 @Autowired
	 private PartInfoManageService partService;
	 
	 @Resource(name="CmmnDetailCodeManageService")
	 private EgovCcmCmmnDetailCodeManageService detailService;
	 
	 @Resource(name="UniUtilManageService")
	 private UniUtilManageService utilService;
		
	 @Resource(name="ErrorInfoManageMapper")
	 private ErrorInfoManageMapper errorInfo;
	
	 @RequestMapping(value="userList.do")
	 public ModelAndView selectUserListManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
																				  , @ModelAttribute("searchVO") UserPhoneInfoVO searchVO
																				  , HttpServletRequest request
																				  , BindingResult bindingResult						
																				  , ModelMap model) throws Exception {
																	
				ModelAndView  mv = new ModelAndView("/backoffice/operManage/userInfoList");
				try{
					Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
					
					if(!isAuthenticated) {
					mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
					mv.setViewName("/backoffice/login");
					return mv;
					}
					
					LOGGER.debug("userId:" +EgovUserDetailsHelper.getAuthorities().toString() );
					
					searchVO.setPageUnit(propertiesService.getInt("pageUnit"));          
					searchVO.setPageSize(propertiesService.getInt("pageSize"));   
					
					mv.addObject("regist", searchVO);
					//** pageing *//       
					PaginationInfo paginationInfo = new PaginationInfo();
					paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
					
					paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
					paginationInfo.setPageSize(searchVO.getPageSize());
					searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
					searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
					searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
					
					
					
					List<UserPhoneInfoVO> userList =  (List<UserPhoneInfoVO>) userService.selectUserPhoneInfoManageListByPagination(searchVO);
					mv.addObject("resultList",  userList);      
					
					
					int totCnt = userList.size() > 0 ? userList.get(0).getTotalRecordCount() : 0;  
					//int totCnt = userManagerService.selectAdminUserManageListTotCnt_S(searchVO);
					paginationInfo.setTotalRecordCount(totCnt);
					mv.addObject("paginationInfo", paginationInfo);
					mv.addObject("selectPhoneGubun", detailService.selectCmmnDetailCombo("PHONE_GUBUN") );
					mv.addObject("totalCnt", totCnt);
				}catch(Exception e){
					LOGGER.debug("selectUserListManageListByPagination error:" + e.toString());
					
					ErrorInfo vo = new ErrorInfo();
					vo.setErrorType("ERROR_TYPE_1");
					vo.setErrorMessage("selectUserListManageListByPagination error:" + e.toString());
					errorInfo.insertErrorMessage(vo);
					mv.addObject("message", egovMessageSource.getMessage("fail.common.insert"));	
					mv.addObject("status", "FAIL");
				}
				return mv;
	 }
	 
	 @RequestMapping(value = "excelUploadPop.do")
	 public ModelAndView excelPop( HttpServletRequest request  )throws Exception{
		 
		 String excelGubun = request.getParameter("gubun") == null ? "" : request.getParameter("gubun") ;
		 ModelAndView  mv = new ModelAndView("/backoffice/popup/excelUpload");
		 mv.addObject("uploadGubun", excelGubun);
		 return mv;
	 }
	 @RequestMapping(value = "userPhoneSearch.do")
	 public ModelAndView userPhoneSearch(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												   ,@RequestBody  UserPhoneInfoVO vo
												   ,BindingResult bindingResult) throws Exception{
		 
		 ModelAndView  model = new ModelAndView("jsonView");
		 try{
			 model.addObject("status", Globals.STATUS_SUCCESS);
			 model.addObject("phoneNumber", userService.selectAgentCombophoneNumber(vo) );
			 
		 }catch(Exception e){
			 model.addObject("status", Globals.STATUS_FAIL);
			 model.addObject("message", e.toString() );
		 }
		 return model;
	 }
	 @RequestMapping(value = "userInfoDelete.do")
	 public ModelAndView userInfoDelete(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												   ,@RequestBody  UserPhoneInfoVO vo
												   ,BindingResult bindingResult) throws Exception{
		 
		 ModelAndView  model = new ModelAndView("jsonView");
			
			
		    UniUtilInfo utilInfo = new UniUtilInfo();
			utilInfo.setInTable("tb_userphoneinfo");
			utilInfo.setInCondition("PHONE_NUMBER=["+vo.getPhoneNumber()+"[");
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
				LOGGER.error("deleteEqupInfoManage  error: "  + e.toString());
				model.addObject("message", egovMessageSource.getMessage("fail.common.delete"));	
				model.addObject("status", "FAIL");
			}
		 return model;
		 
	 }
	 
	 @RequestMapping(value = "excelUpload.do", method = RequestMethod.POST)
	 public ModelAndView excelUploadAjax(MultipartHttpServletRequest request)  throws Exception{
		 
		 
	        MultipartFile excelFile =request.getFile("excelFile");
	        if(excelFile==null || excelFile.isEmpty()){
	            throw new RuntimeException("엑셀파일을 선택 해 주세요.");
	        }
	        //파일 업로드 
	       File destFile = new File(propertiesService.getString("Globals.fileStorePath")+excelFile.getOriginalFilename());
	        
	        try{
	            excelFile.transferTo(destFile);
	        }catch( IllegalStateException |  IOException e){
	        	 LOGGER.debug("ERROR" + e.getMessage() );
	            throw new RuntimeException(e.getMessage(),e);
	        }
	        
	        String returnMessage = null;
	        //String returnMessage =  userService.excelUpload(destFile);
	        
	        destFile.delete();
	        //userService.excelUpload(destFile); //서비스 부분을 삭제한다.
	        //파일 삭제
	        //FileUtils.forceDelete(destFile.getAbsolutePath());
	        
	        ModelAndView view = new ModelAndView("jsonView");
	        view.addObject("status", Globals.STATUS_SUCCESS);
	        view.addObject("message", returnMessage.equals("") ? "OK":  returnMessage);
	        
	        //view.setViewName("/user/list");
	        return view;
	 }


	 @RequestMapping(value="userDetail.do")
	 public ModelAndView selectUserInfoDetail (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
														   ,@RequestBody  UserPhoneInfoVO vo
														   ,BindingResult bindingResult) throws Exception{
			ModelAndView model = new ModelAndView("jsonView");
			try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
				model.addObject("status", Globals.STATUS_LOGINFAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
				}
				
				model.addObject("status", Globals.STATUS_SUCCESS);
				model.addObject("userInfo", userService.selectUsserPhoneInfoDetail(vo.getPhoneNumber())); 
				
			}catch(Exception e){
			    model.addObject("status", Globals.STATUS_FAIL);
		  	    model.addObject("message", "ERROR:" + e.toString());
			}
			return model;
	 }
	 @RequestMapping(value="agentInfoTelRest.do") 
	 public ModelAndView selectUserInfoUpdate (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
																	   ,BindingResult bindingResult) throws Exception{
		ModelAndView model = new ModelAndView("jsonView");
		String meesage = "";
		try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
				model.addObject("status", Globals.STATUS_LOGINFAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
				}
				
				String result = telephoneService.agentResetAll();
				
				int ret = 1;
				if (ret >0){
					model.addObject("status", Globals.STATUS_SUCCESS);
					model.addObject("message", egovMessageSource.getMessage(meesage));		
		        }else {	
		             throw new Exception();
		       }
		}catch(Exception e){
			   model.addObject("status", Globals.STATUS_FAIL);
			   model.addObject("message", "ERROR:" + e.toString());
		}
		return model;
	 }
	 @RequestMapping(value="agentInfoTelBaicNumber.do") 
	 public ModelAndView selectUserInfoBasicUpdate (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
																	   ,BindingResult bindingResult) throws Exception{
		ModelAndView model = new ModelAndView("jsonView");
		String meesage = "";
		try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
				model.addObject("status", Globals.STATUS_LOGINFAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
				}
				LOGGER.debug("agentInfoTelBaicNumber 시작");
				
				String result = telephoneService.agentResetBasicUpdate();
				
				int ret = 1;
				if (ret >0){
					model.addObject("status", Globals.STATUS_SUCCESS);
					model.addObject("message", egovMessageSource.getMessage(meesage));		
		        }else {	
		             throw new Exception();
		       }
		}catch(Exception e){
			   model.addObject("status", Globals.STATUS_FAIL);
			   model.addObject("message", "ERROR:" + e.toString());
		}
		return model;
	 }
	 @RequestMapping(value="userUpdate.do")
	 public ModelAndView selectUserInfoUpdate (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
														   ,@RequestBody  UserPhoneInfoVO vo
														   ,BindingResult bindingResult) throws Exception{
			ModelAndView model = new ModelAndView("jsonView");
			String meesage = "";
			try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
				model.addObject("status", Globals.STATUS_LOGINFAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
				}
				vo.setUserId(EgovUserDetailsHelper.getAuthorities().toString() );
				int ret = userService.updateUserPhoneInfo(vo) ;
				if (vo.getMode().equals("Ins")){
					meesage = "success.common.insert";
				}else {
					meesage = "success.common.update";
				}	
				System.out.println("ret:" + ret);
				if (ret >0){
					model.addObject("status", Globals.STATUS_SUCCESS);
					model.addObject("message", egovMessageSource.getMessage(meesage));		
				}else {	
					throw new Exception();
				}
			}catch(Exception e){
			    model.addObject("status", Globals.STATUS_FAIL);
		  	    model.addObject("message", "ERROR:" + e.toString());
			}
			return model;
	 }
	 @RequestMapping(value="agentList.do")
	 public ModelAndView selectAgentListManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
																				  , @ModelAttribute("searchVO") TelephoneInfoVO searchVO
																				  , HttpServletRequest request
																				  , BindingResult bindingResult						
																				  , ModelMap model) throws Exception {

		      ModelAndView  mv = new ModelAndView("/backoffice/operManage/agentInfoList");
		      try{
		    	  Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				   LOGGER.debug("isAuthenticated:" + isAuthenticated);
				   
			       if(!isAuthenticated) {
			    		mv.addObject("message", egovMessageSource.getMessage("fail.common.login"));
			    		mv.setViewName("/backoffice/login");
			    		return mv;
			       }
			       
				   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));          
			       searchVO.setPageSize(propertiesService.getInt("pageSize"));   
			       
			       mv.addObject("regist", searchVO);
			       //** pageing *//       
			   	   PaginationInfo paginationInfo = new PaginationInfo();
				   paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
				   
				   paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
				   paginationInfo.setPageSize(searchVO.getPageSize());
				   searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
				   searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
				   searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
				   
				   
				   
				   List<TelephoneInfoVO> adminList =  (List<TelephoneInfoVO>) telephoneService.selectAgentPageInfoManageListByPagination(searchVO);
			       mv.addObject("resultList",  adminList);      
			       
			       
			       int totCnt = adminList.size() > 0 ? adminList.get(0).getTotalRecordCount() : 0;  
			       //int totCnt = userManagerService.selectAdminUserManageListTotCnt_S(searchVO);
			       paginationInfo.setTotalRecordCount(totCnt);
			       mv.addObject("paginationInfo", paginationInfo);
			       
			       mv.addObject("selectCenterCombo", centerService.selectCenterCombo());
			       mv.addObject("selectGroupCombo", partService.selectPartInfoCombo());
			       mv.addObject("selectTelCombo", detailService.selectCmmnDetailCombo("TEL_CHANGE") );
			       mv.addObject("selectStateCombo", detailService.selectCmmnDetailCombo("PHONE_STATE") );
			       
			       mv.addObject("totalCnt", totCnt);
		      }catch(Exception e){
		    	  LOGGER.debug("selectUserManagerList error:" + e.toString());
					
					ErrorInfo vo = new ErrorInfo();
					vo.setErrorType("ERROR_TYPE_1");
					vo.setErrorMessage("selectUserManagerList error:" + e.toString());
					errorInfo.insertErrorMessage(vo);
					mv.addObject("message", egovMessageSource.getMessage("fail.common.insert"));	
					mv.addObject("status", "FAIL");
		      }
		      return mv;
	 }
	 @RequestMapping(value="agentDetail.do")
	 public ModelAndView selectAgentInfoDetail (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
														   ,@RequestBody  TelephoneInfoVO vo
														   ,BindingResult bindingResult) throws Exception{
			ModelAndView model = new ModelAndView("jsonView");
			try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
				model.addObject("status", Globals.STATUS_LOGINFAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
				}
				
				model.addObject("status", Globals.STATUS_SUCCESS);
				model.addObject("agentInfo", telephoneService.selectAgentPageInfoManageDetail(vo.getAgentCode())); 
				
			}catch(Exception e){
			    model.addObject("status", Globals.STATUS_FAIL);
		  	    model.addObject("message", "ERROR:" + e.toString());
			}
			return model;
	 }
	 @RequestMapping(value="agentInfoReset.do")
	 public ModelAndView agentInfoReset (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
															   ,@RequestBody  TelephoneInfoVO vo
															   , BindingResult bindingResult) throws Exception{
		 
		    ModelAndView model = new ModelAndView("jsonView");
			String meesage = "";
			try{
				LOGGER.debug("agentCode:" + vo.getAgentReset());
				meesage = telephoneService.agentReset(vo.getAgentReset());
				if (meesage.equals("")) {
					model.addObject("status", Globals.STATUS_SUCCESS);
				}else {
					model.addObject("status", Globals.STATUS_FAIL);
					model.addObject("message", meesage);
				}
				
			}catch(Exception e){
				  model.addObject("status", Globals.STATUS_FAIL);
			  	  model.addObject("message", "ERROR:" + e.toString());
			}
			return model;
		 
	 }
	 @RequestMapping(value="agentInfoTelChange.do")
	 public ModelAndView agentInfoTelChange (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
														   ,@RequestBody  TelephoneInfoVO vo
														   , BindingResult bindingResult) throws Exception{

		ModelAndView model = new ModelAndView("jsonView");
		String meesage = "";
		try{
			LOGGER.debug("agentInfoTelChange-------------------------------------------------------------------");
			LOGGER.debug("agentInfoTelChange" + vo.getAgentCode() + ":" + vo.getAgentNownumber());
			meesage = telephoneService.agentTelChange(vo) ;
			LOGGER.debug("meesage:" + meesage);
			if (meesage.equals("OK")) {
			    model.addObject("status", Globals.STATUS_SUCCESS);
			}else {
			    model.addObject("status", Globals.STATUS_FAIL);
			    model.addObject("message", meesage);
			}
		
		}catch(Exception e){
		    model.addObject("status", Globals.STATUS_FAIL);
		    model.addObject("message", "ERROR:" + e.toString());
		}
		return model;
		
	  }
	 @RequestMapping(value="agentInfoUpdate.do")
	 public ModelAndView agentInfoUpdate (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
													   ,@RequestBody  TelephoneInfoVO vo
													   , BindingResult bindingResult) throws Exception{
		
			ModelAndView model = new ModelAndView("jsonView");
			String meesage = "";
			
			try{
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				if(!isAuthenticated) {
				model.addObject("status", Globals.STATUS_LOGINFAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
				}
				vo.setUserId(EgovUserDetailsHelper.getAuthenticatedUser().toString() );
				
				int ret = telephoneService.updateAgentPageInfoManage(vo);
				if (vo.getMode().equals("Ins")){
					meesage = "success.common.insert";
				}else {
					meesage = "success.common.update";
				}	
				System.out.println("ret:" + ret);
				if (ret >0){
					model.addObject("status", Globals.STATUS_SUCCESS);
					model.addObject("message", egovMessageSource.getMessage(meesage));		
				}else {	
					throw new Exception();
				}
			}catch (Exception e){
				
				ErrorInfo errInfo = new ErrorInfo();
				errInfo.setErrorType("ERROR_TYPE_1");
				errInfo.setErrorMessage("mangerUpdate error:" + e.toString());
				errorInfo.insertErrorMessage(errInfo);
				
				model.addObject("status", Globals.STATUS_FAIL);
				model.addObject("message", egovMessageSource.getMessage("fail.common.insert"));	
			}	
			return model;
	 }
	 @RequestMapping(value = "agentInfoDelete.do")
	 public ModelAndView agentInfoDelete(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
												   ,@RequestBody  TelephoneInfoVO vo
												   ,BindingResult bindingResult) throws Exception{
		 
		 ModelAndView  model = new ModelAndView("jsonView");
			
			
		    UniUtilInfo utilInfo = new UniUtilInfo();
			utilInfo.setInTable("tb_telephoneinfo");
			utilInfo.setInCondition("AGENT_CODE=["+vo.getAgentCode()+"[");
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
				LOGGER.error("deleteEqupInfoManage  error: "  + e.toString());
				model.addObject("message", egovMessageSource.getMessage("fail.common.delete"));	
				model.addObject("status", "FAIL");
			}
		 return model;
		 
	 }
}
