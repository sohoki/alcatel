package egovframework.let.sym.ccm.cca.web;

import egovframework.com.cmm.AdminLoginVO;
import egovframework.let.sym.ccm.cca.service.CmmnCode;
import egovframework.let.sym.ccm.cca.service.CmmnCodeVO;
import egovframework.let.sym.ccm.cca.service.EgovCcmCmmnCodeManageService;
import egovframework.let.sym.ccm.ccc.service.EgovCcmCmmnClCodeManageService;



import egovframework.let.sym.ccm.cde.web.EgovCcmCmmnDetailCodeManageController;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.Globals;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springmodules.validation.commons.DefaultBeanValidator;


@Controller 
public class EgovCcmCmmnCodeManageController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovCcmCmmnCodeManageController.class);
	
	@Resource(name = "CmmnCodeManageService")
    private EgovCcmCmmnCodeManageService cmmnCodeManageService;

	@Resource(name = "CmmnClCodeManageService")
    private EgovCcmCmmnClCodeManageService cmmnClCodeManageService;

	
	
	@Resource(name="egovMessageSource")
	protected EgovMessageSource egovMessageSource;
	
	
    /** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

	@Autowired
	private DefaultBeanValidator beanValidator;

	/**
	 * 공통코드를 삭제한다.
	 * @param AdminLoginVO
	 * @param cmmnCode
	 * @param model
	 * @return "forward:/sym/ccm/cca/EgovCcmCmmnCodeList.do"
	 * @throws Exception
	 */
    @RequestMapping(value="/backoffice/basicManage/codeDelete.do")
	public String deleteCmmnCode (@ModelAttribute("LoginVO") AdminLoginVO LoginVO
								  , CmmnCode cmmnCode
								  , HttpServletRequest request
								  , BindingResult bindingResult			
								  , ModelMap model) throws Exception {
    	
    	
    	//상세 코드 삭제 
    	int ret = cmmnCodeManageService.deleteCmmnCode(cmmnCode.getCodeId());
    	
    	if (ret > 0){
			model.addAttribute("status", Globals.STATUS_SUCCESS);
			model.addAttribute("message",   egovMessageSource.getMessage("success.common.delete"));    		
    	}else {
    		throw new Exception();    		
    	}
    	
        return "forward:/backoffice/basicManage/codeList.do";
	}

	
	
    @RequestMapping(value="/backoffice/basicManage/codeList.do")
	public String selectCmmnCodeList (@ModelAttribute("AdminLoginVO") AdminLoginVO LoginVO
										, @ModelAttribute("searchVO") CmmnCodeVO searchVO
										, HttpServletRequest request
										, BindingResult bindingResult			
										, ModelMap model
										) throws Exception {
    	
    	
    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
    		return "/backoffice/login";
        }
        
        
        
    	model.addAttribute("regist", searchVO);
    	
    	/** EgovPropertyService.sample */
    	searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
    	searchVO.setPageSize(propertiesService.getInt("pageSize"));

    	/** pageing */
    	PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        model.addAttribute("resultList", cmmnCodeManageService.selectCmmnCodeList(searchVO));

        int totCnt =cmmnCodeManageService.selectCmmnCodeListTotCnt(searchVO);
        
        model.addAttribute("totalCnt", totCnt);
        
		paginationInfo.setTotalRecordCount(totCnt);
        model.addAttribute("paginationInfo", paginationInfo);

        return "/backoffice/basicManage/codeList";
	}
   
    //ID 체크 
    @RequestMapping (value="/backoffice/basicManage/codeIDCheck.do")
    @ResponseBody 
    public String selectIdCheck(HttpServletRequest request)throws Exception{
    	
    	String codeId = request.getParameter("code") != null ? request.getParameter("code") : "";
    	int idCheck = cmmnCodeManageService.selectIDCheck(codeId);    	
    	return Integer.toString(idCheck);
    }
  //ajax 로 값 보내기 
  	@RequestMapping ("/backoffice/basicManage/codeDetail.do")
  	@ResponseBody 
  	public String selectGroupDetail(HttpServletRequest request) throws Exception{
  	    //	    
  		String codeId = request.getParameter("codeId") != null ? request.getParameter("codeId") : "";
  		
  		CmmnCode  cmmCode  =	(CmmnCode) cmmnCodeManageService.selectCmmnCodeDetail(codeId);
        String grInfo =   cmmCode.getCodeIdNm()+"/"+cmmCode.getUseAt();  	    
  	 	return grInfo;
  	}
  	@RequestMapping (value="/backoffice/basicManage/codeUpdate.do")
  	@SuppressWarnings("finally")	
  	public String  updateCmmCode (@ModelAttribute("adminLoginVO") AdminLoginVO LoginVO
  			                                          , @ModelAttribute("cmmnCode") CmmnCode vo
  			                                          , HttpServletRequest request
  			                          			      , BindingResult bindingResult						                          			
  			                          			      , ModelMap model
  			                          			  ) throws Exception {
  		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
        if(!isAuthenticated) {
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
    		return "/backoffice/login";
        }
        
  		model.addAttribute("regist", vo);
		String meesage = "";
		String url = "redirect:/backoffice/basicManage/codeList.do";  		
		
  	    try{
  	    	int ret  = 0;
  	    	vo.setLastUpdusrId(EgovUserDetailsHelper.getAuthenticatedUser().toString() );
  	    	
			if (vo.getMode().equals("Ins")){				
				ret = cmmnCodeManageService.insertCmmnCode(vo);
				meesage = "sucess.common.insert";
				url = "redirect:/backoffice/basicManage/codeList.do";				
			}else {
				 ret = cmmnCodeManageService.updateCmmnCode(vo);
				 meesage = "sucess.common.update";
				 url = "redirect:/backoffice/basicManage/codeList.do";
			}			
			if (ret >0){
				model.addAttribute("status", Globals.STATUS_SUCCESS);
				model.addAttribute("message", egovMessageSource.getMessage(meesage));				
			}else {
				throw new Exception();
			}
  	    	
  	    	
  	    }catch (Exception e){
  	    	System.out.println("error:"+ e.toString()  );
  	    	model.addAttribute("status", Globals.STATUS_FAIL);
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.insert"));			
			url = "redirect:/backoffice/basicManage/codeList.do";
  	    }
  	    finally{
  	    	return url;
  	    }
  		
  	}
}
