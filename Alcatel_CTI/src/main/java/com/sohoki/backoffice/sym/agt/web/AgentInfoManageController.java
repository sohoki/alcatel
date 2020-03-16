package com.sohoki.backoffice.sym.agt.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.sts.error.service.ErrorInfo;
import com.sohoki.backoffice.sym.cnt.service.CenterInfoVO;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.let.uat.uia.service.AdminInfoVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;




//전화기
import com.sohoki.backoffice.sym.agt.service.TelephoneInfo;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoVO;
import com.sohoki.backoffice.sym.agt.service.TelephoneInfoManageService;
//사용자
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfo;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoManageService;
import com.sohoki.backoffice.util.service.UniUtilManageService;

@Controller
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
	 
	 @Resource(name="UniUtilManageService")
	 private UniUtilManageService utilService;
		
	 @Resource(name="ErrorInfoManageMapper")
	 private ErrorInfoManageMapper errorInfo;
	
	 @RequestMapping(value="/backoffice/operManage/userList")
	 public ModelAndView selectUserListManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
																				  , @ModelAttribute("searchVO") UserPhoneInfoVO searchVO
																				  , HttpServletRequest request
																				  , BindingResult bindingResult						
																				  , ModelMap model) throws Exception {
																	
				ModelAndView  mv = new ModelAndView("/backoffice/operManage/userList");
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
					
					
					
					List<UserPhoneInfoVO> userList =  (List<UserPhoneInfoVO>) userService.selectUserPhoneInfoManageListByPagination(searchVO);
					mv.addObject("resultList",  userList);      
					
					
					int totCnt = userList.size() > 0 ? userList.get(0).getTotalRecordCount() : 0;  
					//int totCnt = userManagerService.selectAdminUserManageListTotCnt_S(searchVO);
					paginationInfo.setTotalRecordCount(totCnt);
					mv.addObject("paginationInfo", paginationInfo);
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
	 
	 @RequestMapping(value="/backoffice/operManage/agentList")
	 public ModelAndView selectAgentListManageListByPagination(@ModelAttribute("loginVO") AdminLoginVO loginVO
																				  , @ModelAttribute("searchVO") TelephoneInfoVO searchVO
																				  , HttpServletRequest request
																				  , BindingResult bindingResult						
																				  , ModelMap model) throws Exception {

		      ModelAndView  mv = new ModelAndView("/backoffice/operManage/agentList");
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
	 @RequestMapping(value="/backoffice/operManage/agentDetail")
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
	 @RequestMapping(value="/backoffice/operManage/agentUpdate")
	 public ModelAndView agentInfoUpdate (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
													   ,@RequestBody  TelephoneInfoVO vo
													   , BindingResult bindingResult) throws Exception{
		
			ModelAndView model = new ModelAndView("jsonView");
			String meesage = "";
			
			try{
				
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
}