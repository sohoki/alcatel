package egovframework.let.uat.uia.web;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.AdminLoginVO;

//import egovframework.let.sym.log.clg.service.EgovLoginLogService;
//import egovframework.let.sym.log.clg.service.LoginLog;

import egovframework.let.uat.uia.service.EgovLoginService;
import egovframework.let.utl.sim.service.EgovClntInfo;
import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EgovLoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovLoginController.class);
	
	
    /** EgovLoginService */
	@Resource(name = "loginService")
    private EgovLoginService loginService;

	/** EgovMessageSource */
    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;


	/** EgovPropertyService */
    @Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;
    
    

    /** TRACE */
    @Resource(name="leaveaTrace")
    LeaveaTrace leaveaTrace;
    
	/*@Resource(name="EgovLoginLogService")
	private EgovLoginLogService loginLogService;    */
    
    /**
	 * 로그인 화면으로 들어간다
	 * @param vo - 로그인후 이동할 URL이 담긴 LoginVO
	 * @return 로그인 페이지
	 * @exception Exception
	 */
    @RequestMapping(value="/backoffice/login.do")
	public String loginUsrView(@ModelAttribute("adminLoginVO") AdminLoginVO adminLoginVO,
								HttpServletRequest request,
								HttpServletResponse response,
								ModelMap model)
								throws Exception {
    	
    	return "/backoffice/login";
	}
    /**
	 * 일반(스프링 시큐리티) 로그인을 처리한다
	 * @param vo - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @exception Exception
	 */    
    @RequestMapping(value="/backoffice/SecurityLogin.do")
    public String actionSecurityLogin(@ModelAttribute("AdminLoginVO") AdminLoginVO adminLoginVO, 
    		                                           HttpServletResponse response,
						    		                   HttpServletRequest request,
						    		                   ModelMap model)
                                                      throws Exception {

    	// 접속IP
    	String userIp = EgovClntInfo.getClntIP(request);
        
    	
    	// 
    	AdminLoginVO resultVO = loginService.actionLogin(adminLoginVO);   
        if (resultVO != null && resultVO.getAdminId() != null ) {
        	
            // 2. spring security 연동        
        	request.getSession().setAttribute("AdminLoginVO", resultVO);
        	UsernamePasswordAuthenticationFilter springSecurity = null;
        	ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        	@SuppressWarnings("rawtypes")
        	Map beans = act.getBeansOfType(UsernamePasswordAuthenticationFilter.class);
        	
        	
        	if (beans.size() > 0) {
        		springSecurity = (UsernamePasswordAuthenticationFilter)beans.values().toArray()[0];
        	} else {
        		throw new IllegalStateException("No AuthenticationProcessingFilter");
        	}

        	springSecurity.setContinueChainBeforeSuccessfulAuthentication(false);	// false 이면 chain 처리 되지 않음.. (filter가 아닌 경우 false로...)
        	
        	//여기 부분 다시 확인 필요
        	springSecurity.doFilter(new RequestWrapperForSecurity(request, resultVO.getAdminName() + resultVO.getAdminId() , resultVO.getAdminId()), response, null);

        	Authentication authentication = new UsernamePasswordAuthenticationToken(resultVO.getAdminId(), "USER_PASSWORD");        	
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);	
            
            //날짜 체크 이후 값 확인 하기 
            String currentDay =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
     	   
            try{
            	 if ( Integer.valueOf(resultVO.getUpdatePassword()) < Integer.valueOf(currentDay) ){
                 	model.addAttribute("message", "패스워드가 만료 되었습니다.");
                 	return "/backoffice/backoffice/passChangeView.do";
                 }else {
                	model.addAttribute("message", "login_ok"); 
                 	return "forward:/backoffice/actionMain.do";	// 성공 시 페이지.. (redirect 불가)
                 }
            }catch (Exception e1){
            	LOGGER.debug("login Error:" + e1.toString());
            	return "/backoffice/login";
            }
           
        } else {
        	model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "/backoffice/login";
        }
    }

    
    /**
	 * 로그인 후 메인화면으로 들어간다
	 * @param
	 * @return 로그인 페이지
	 * @exception Exception
	 */
    @RequestMapping(value="/backoffice/actionMain.do")
	public String actionMain( HttpServletRequest request, ModelMap model)  {
    	try{
    		
    		
    		    // 1. Spring Security 사용자권한 처리
		    	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		    	if(!isAuthenticated) {
		    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		    		return "/backoffice/login";
		    	}
		    	
		    	HttpSession httpSession = request.getSession(true);
		    	AdminLoginVO loginVO = (AdminLoginVO)httpSession.getAttribute("AdminLoginVO");
		    	 
		     	return "forward:backoffice/basicManage/user_list.do";
		     	
    	} catch(Exception e){
    		LOGGER.debug("login Error:" + e.toString());
    		model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
        	return "/backoffice/login";
    	}		    	
	}

    /**
	 * 로그아웃한다.
	 * @return String
	 * @exception Exception
	 */
    @RequestMapping(value="/backoffice/actionLogout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {
    	request.getSession().setAttribute("LoginVO", null);
    	
    	return "redirect:/backoffice/login.do";
    }
    
    
}
class RequestWrapperForSecurity extends HttpServletRequestWrapper {
	private String username = null;
	private String password = null;

	public RequestWrapperForSecurity(HttpServletRequest request, String username, String password) {
		super(request);
		this.username = username;
		this.password = password;		
		
	}

	@Override
	public String getRequestURI() {
		return ((HttpServletRequest)super.getRequest()).getContextPath() + "/j_spring_security_check";
	}

	@Override
	public String getParameter(String name) {
        if (name.equals("j_username")) {
        	return username;
        }

        if (name.equals("j_password")) {
        	return password;
        }

        return super.getParameter(name);
    }
}