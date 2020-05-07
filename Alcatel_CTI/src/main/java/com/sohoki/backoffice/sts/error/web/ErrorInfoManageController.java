package com.sohoki.backoffice.sts.error.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.sts.error.service.ErrorInfoManageService;
import com.sohoki.backoffice.sts.error.service.ErrorInfoVO;
import com.sohoki.backoffice.util.service.UniUtilManageService;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;



@RestController
@RequestMapping("/backoffice/operManage")
public class ErrorInfoManageController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorInfoManageController.class);
	

	 @Resource(name="CmmnDetailCodeManageService")
	 private EgovCcmCmmnDetailCodeManageService detailService;
	 
	 @Resource(name="UniUtilManageService")
	 private UniUtilManageService utilService;
		
	 @Resource(name="ErrorInfoManageService")
	 protected ErrorInfoManageService errorService;
	 
	 @Autowired
	 protected EgovPropertyService propertiesService;
		
	 @Autowired
	 protected EgovMessageSource egovMessageSource;
		
	 @Autowired
	 private DefaultBeanValidator beanValidator;
   
	
	 @RequestMapping ("errorList.do")
	 public ModelAndView selectErrorLst(@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
													 , @ModelAttribute("searchVO") ErrorInfoVO searchVO
													 , HttpServletRequest request
													 , BindingResult bindingResult) throws Exception {
			
	          ModelAndView model = new ModelAndView("/backoffice/operManage/errorList");
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
	    		  String currentDay =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	    		  if (searchVO.getSearchStartDay() == "") {searchVO.setSearchStartDay(currentDay); }
	    		  if (searchVO.getSearchEndDay() == "") {searchVO.setSearchEndDay(currentDay); }
	    		  
	    		
	    		  
	    	   	  PaginationInfo paginationInfo = new PaginationInfo();
	    		  paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
	    		  paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
	    		  paginationInfo.setPageSize(searchVO.getPageSize());

	    		  searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
	    		  searchVO.setLastIndex(paginationInfo.getFirstRecordIndex() + searchVO.getPageSize());
	    		  searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

	    		  List<ErrorInfoVO> list = errorService.selectErrorMessage(searchVO);
	    	      model.addObject("resultList", list);
	    	      model.addObject("selectErrorgubun", detailService.selectCmmnDetailCombo("ERROR_TYPE"));
	    	      int totCnt = list.size() > 0 ? list.get(0).getTotalRecordCount() : 0;
	    	      
	    		  paginationInfo.setTotalRecordCount(totCnt);
	    	      model.addObject("paginationInfo", paginationInfo);
	    	      model.addObject("totalCnt", totCnt);
	    	      model.addObject("regist", searchVO);
	    	      
	          }catch(Exception e){
	        	  LOGGER.debug("selectErrorLst error: " + e.toString());
	          }
		      
		      return model;
		}
	
}
