package com.sohoki.backoffice.sym.agt.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sohoki.backoffice.sym.agt.service.UserPhoneInfo;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoManageService;
import com.sohoki.backoffice.sym.agt.service.UserPhoneInfoVO;
import com.sohoki.backoffice.util.web.ExcelRead;
import com.sohoki.backoffice.util.web.ExcelReadOption;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import com.sohoki.backoffice.mapper.UserPhoneInfoManageMapper;

@Service("UserPhoneInfoManageService")
public class UserPhoneInfoManageServiceImpl extends EgovAbstractServiceImpl implements UserPhoneInfoManageService {

	
	@Resource(name="UserPhoneInfoManageMapper")
	private UserPhoneInfoManageMapper phoneInfo;

	@Override
	public List<UserPhoneInfoVO> selectUserPhoneInfoManageListByPagination(UserPhoneInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUserPhoneInfoManageListByPagination(searchVO);
	}

	@Override
	public List<UserPhoneInfoVO> selectUserPhoneInfoManageListByCombo(UserPhoneInfoVO searchVO) throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUserPhoneInfoManageListByCombo(searchVO);
	}

	@Override
	public UserPhoneInfoVO selectUsserPhoneInfoDetail(String phoneNumber)
			throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUsserPhoneInfoDetail(phoneNumber);
	}

	@Override
	public int selectUserPhoneInfoManageListByCnt(UserPhoneInfoVO searchVO)
			throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.selectUserPhoneInfoManageListByCnt(searchVO);
	}

	@Override
	public int updateUserPhoneInfo(UserPhoneInfo vo) throws Exception {
		// TODO Auto-generated method stub
		int ret = 0;
		if (vo.getMode().equals("Ins")){
			ret = phoneInfo.insertUserPhoneInfo(vo);
		}else{
			ret = phoneInfo.updateUserPhoneInfo(vo);
		}
		return ret;
	}

	@Override
	public int deleteUserPhoneInfo(String phoneNumber) throws Exception {
		// TODO Auto-generated method stub
		return phoneInfo.deleteUserPhoneInfo(phoneNumber);
	}

	@Override
	public String excelUpload(File excelFile)throws Exception {
		    
		  String errorTelNumber = "";
		  
		  //Service 단에서 가져온 코드 
	      ExcelReadOption excelReadOption = new ExcelReadOption();
	      excelReadOption.setFilePath(excelFile.getAbsolutePath());
	      excelReadOption.setOutputColumns("A","B","C","D","E");
	      excelReadOption.setStartRow(2);
	      //System.out.println("step01");  
	      try{
	    	  List<Map<String, String>>excelContent =ExcelRead.read(excelReadOption);
			  
	    	   System.out.println("excelContent:" + excelContent.size());  
	    	  
			   UserPhoneInfo phoneinfo = new UserPhoneInfo();
			   //int ret = 0;
			   
			   for(Map<String, String> article: excelContent){
				   
				   
				     System.out.print("0");
					 phoneinfo.setPhoneNumber( String.valueOf( Integer.parseInt( article.get("A")) ) );
					 phoneinfo.setPhoneGubun(article.get("B"));
					 phoneinfo.setLoginId(article.get("C"));
					 phoneinfo.setLoginPassword(article.get("D"));
					 phoneinfo.setPhoneUseyn(article.get("E"));
					 phoneinfo.setMode("Ins");
					 if (phoneInfo.selectUserNumberIdCheck(phoneinfo) > 0){
						 System.out.print("1");
						 errorTelNumber += "/" + phoneinfo.getPhoneNumber()+ ":" + phoneinfo.getLoginId();
					 }else {
						 System.out.print("0");
						 phoneInfo.insertUserPhoneInfo(phoneinfo);
						 
					 }
					 
			   } 
			   phoneinfo = null;
	      }catch(Exception e){
	    	  System.out.print("ERROR" + e.toString());
	    	  errorTelNumber = "ERROR:" + e.toString();
	      }
	      
			 
		return errorTelNumber;
	};
	
	
	
}
