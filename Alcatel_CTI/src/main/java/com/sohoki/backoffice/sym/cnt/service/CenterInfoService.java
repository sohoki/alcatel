package com.sohoki.backoffice.sym.cnt.service;

import java.util.List;


public interface CenterInfoService {

    List<CenterInfoVO> selectCenterInfoPageInfoManageListByPagination(CenterInfoVO serarch)throws Exception;
	
	List<CenterInfoVO> selectCenterCombo() throws Exception;
	
	int selectCenterInfoPageInfoManageListToCnt_s(CenterInfoVO serarch)throws Exception;
	
	CenterInfoVO selectCenterInfoDetail(String centerId)throws Exception;
	
	int updateCenterInfoManage(CenterInfo vo) throws Exception;
}
