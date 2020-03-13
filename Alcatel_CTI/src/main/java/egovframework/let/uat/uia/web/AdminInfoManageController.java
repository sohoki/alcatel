package egovframework.let.uat.uia.web;

import java.util.List;

import egovframework.let.uat.uia.service.AdminInfo;
import egovframework.let.uat.uia.service.AdminInfoManageService;
import egovframework.let.uat.uia.service.AdminInfoVO;
import com.sohoki.backoffice.util.service.UniUtilInfo;
import com.sohoki.backoffice.util.service.UniUtilManageService;
import egovframework.com.cmm.AdminLoginVO;

import com.sohoki.backoffice.mapper.ErrorInfoManageMapper;
import com.sohoki.backoffice.sts.error.service.ErrorInfo;

import egovframework.let.src.ram.service.AuthorInfoManageService;
import egovframework.let.sym.ccm.cde.service.EgovCcmCmmnDetailCodeManageService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.com.cmm.EgovMessageSource;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.com.cmm.service.Globals;

//@Controller
@RestController
@RequestMapping("/backoffice/basicManage")
public class AdminInfoManageController {

   private static final Logger LOGGER = LoggerFactory.getLogger(AdminInfoManageController.class);
	
	
   @Resource(name="AdminInfoManageService")
	private AdminInfoManageService userManagerService;
	
	@Resource(name ="AuthorInfoManageService")
	private AuthorInfoManageService authorInfoManageService;
	
	/** EgovPropertyService */
   @Resource(name = "propertiesService")
   protected EgovPropertyService propertiesService;
   
   @Resource(name="CmmnDetailCodeManageService")
   private EgovCcmCmmnDetailCodeManageService cmmnDetailCodeManageService;
   
   
   @Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;

	@Autowired
	private DefaultBeanValidator beanValidator;
	
	@Resource(name="UniUtilManageService")
	private UniUtilManageService utilService;
	
	@Resource(name="ErrorInfoManageMapper")
	private ErrorInfoManageMapper errorInfo;
	
	
	
	@RequestMapping(value="intro.do")
	public ModelAndView selectUserManagerList() throws Exception {
		ModelAndView model = new ModelAndView("/backoffice/intro");
		return model;		
	}
	
	@RequestMapping(value="empList.do")
	public ModelAndView selectUserManagerList( @ModelAttribute("searchVO") AdminInfoVO searchVO
												, HttpServletRequest request
												, BindingResult bindingResult) throws Exception {  
		
		ModelAndView model = new ModelAndView("/backoffice/basicManage/empList");
	   try{
		   
		  
		   //공용 확인 하기 
		   Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		   LOGGER.debug("isAuthenticated:" + isAuthenticated);
		   
	       if(!isAuthenticated) {
	    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
	    		model.setViewName("/backoffice/login");
	    		return model;
	       }
	       
		   searchVO.setPageUnit(propertiesService.getInt("pageUnit"));          
	       searchVO.setPageSize(propertiesService.getInt("pageSize"));   
	       
	       model.addObject("regist", searchVO);
	       //** pageing *//       
	   	   PaginationInfo paginationInfo = new PaginationInfo();
		   paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		   
		   paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		   paginationInfo.setPageSize(searchVO.getPageSize());
		   searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		   searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		   searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		   
		   
		   if (searchVO.getUseYn() == null){ searchVO.setUseYn("");}
		   
		   List<AdminInfoVO> adminList =  (List<AdminInfoVO>) userManagerService.selectAdminUserManageListByPagination(searchVO);
	       model.addObject("resultList",  adminList);      
	       
	       
	       int totCnt = adminList.size() > 0 ? adminList.get(0).getTotalRecordCount() : 0;  
	       //int totCnt = userManagerService.selectAdminUserManageListTotCnt_S(searchVO);
	       paginationInfo.setTotalRecordCount(totCnt);
	       model.addObject("paginationInfo", paginationInfo);
	       model.addObject("authInfo", authorInfoManageService.selectAuthorIInfoManageCombo());
	       model.addObject("totalCnt", totCnt);
	       
	   }catch (Exception e){
			LOGGER.debug("selectUserManagerList error:" + e.toString());
			
			ErrorInfo vo = new ErrorInfo();
			vo.setErrorType("ERROR_TYPE_1");
			vo.setErrorMessage("selectUserManagerList error:" + e.toString());
			errorInfo.insertErrorMessage(vo);
			model.addObject("message", egovMessageSource.getMessage("fail.common.insert"));	
			model.addObject("status", "FAIL");
	   }
	   return model;
	}
	
	@RequestMapping(value="passChangeView.do")
	public ModelAndView pagssChangeView(@ModelAttribute("AdminInfo") AdminInfo vo, 
										    HttpServletResponse response,
										    HttpServletRequest request) throws Exception {
		
		ModelAndView model = new ModelAndView("/backoffice/basicManage/passChange");
		
		return model;
	}
	@RequestMapping(value="passChange.do")
    public ModelAndView updatePasswordChange( @ModelAttribute("AdminInfo") AdminInfo vo, 
										    HttpServletResponse response,
										    HttpServletRequest request) throws Exception {
			
		ModelAndView model = new ModelAndView("jsonView");
		
    	try{
    		int ret =  userManagerService.updatePassChange(vo);
        	if (ret > 0){
        		model.addObject("status", Globals.STATUS_SUCCESS);
        		model.addObject("message", egovMessageSource.getMessage("page.emp.message11"));
        	}else {
        		
        		throw new Exception();
        	}
    	}catch (Exception e ){
			model.addObject("status", Globals.STATUS_FAIL);
			model.addObject("message", egovMessageSource.getMessage("page.emp.message10"));
    	}
    	return model;
    }
	
	/*@RequestMapping(value="managerCheck.do")
	public ModelAndView userView( AdminInfoVO adminVO
							, HttpServletRequest request
							, BindingResult bindingResult) throws Exception{
		
		//공용 확인 하기 
		ModelAndView model = new ModelAndView("/backoffice/basicManage/managerCheck");
	    Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
        	model.addObject("status", "LOGIN FAIL");
    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
    		model.setViewName("/backoffice/login");
    		return model;
        }
		
		model.addObject("selectState", authorInfoManageService.selectAuthorIInfoManageCombo());		
		model.addObject("regist", adminVO);
		if (adminVO.getMode().equals("Edt")){
			AdminInfoVO vo = userManagerService.selectAdminUserManageDetail(adminVO);
			model.addObject("regist", vo);			
		}				
		return model;
	}*/

	@RequestMapping(value="managerDetail.do")
	public ModelAndView managerDetail (@ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
									   ,@RequestBody  AdminInfoVO vo
									   ,BindingResult bindingResult) throws Exception{
		ModelAndView model = new ModelAndView("jsonView");
		try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
	        if(!isAuthenticated) {
	        	model.addObject("status", Globals.STATUS_LOGINFAIL);
	    		model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
	        }
	        model.addObject("status", Globals.STATUS_SUCCESS);
	        model.addObject("managerInfo", userManagerService.selectAdminUserManageDetail(vo));
	        
		}catch(Exception e){
			model.addObject("status", Globals.STATUS_FAIL);
	        model.addObject("message", "ERROR:" + e.toString());
		}
		return model;
	}
	@RequestMapping(value="managerUpdate.do")
	public ModelAndView mangerUpdate ( @ModelAttribute("AdminLoginVO") AdminLoginVO loginVO
									   ,@RequestBody  AdminInfoVO vo
									   , BindingResult bindingResult) throws Exception{
		
		
		ModelAndView model = new ModelAndView("jsonView");
		
		
		
		model.addObject("regist", vo);
		String meesage = "";
		
		try{
			
			int ret = userManagerService.updateAdminUserManage(vo);
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
	@RequestMapping(value="managerDelete.do")
	public ModelAndView deleteMber(@RequestBody AdminInfo vo) throws Exception {
		
		ModelAndView model = new ModelAndView("jsonView");
        
		UniUtilInfo utilInfo = new UniUtilInfo();
		utilInfo.setInTable("tb_managerinfo");
		utilInfo.setInCondition("admin_id=["+vo.getAdminId()+"[");
		try{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    if(!isAuthenticated) {
		    	model.addObject("status", "LOGIN FAIL");
		    	model.addObject("message", egovMessageSource.getMessage("fail.common.login"));
		    	return model;
		    }
		    
		    int ret = utilService.deleteUniStatement(utilInfo);	
		    model.addObject("message", egovMessageSource.getMessage("success.request.msg"));	
			model.addObject("status", "SUCCESS"); 
		    
		}catch (Exception e){
			//result = "F";
			model.addObject("message", egovMessageSource.getMessage("fail.request.msg"));	
			model.addObject("status", "FAIL");
		}
		return model;
	}
	@RequestMapping(value="IdCheck.do")
	public String selectUserMangerIDCheck(@RequestBody AdminInfoVO vo) throws Exception {
		int IDCheck = userManagerService.selectAdminUserMangerIDCheck(vo.getAdminId());		
		return Integer.toString( IDCheck ) ;
	}
	
}
