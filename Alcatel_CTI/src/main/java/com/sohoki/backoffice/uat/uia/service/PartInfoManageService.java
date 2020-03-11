package com.sohoki.backoffice.uat.uia.service;

import java.util.List;

public interface PartInfoManageService {


	List<PartInfoVO> selectPartInfoPageInfoManageListByPagination(PartInfoVO serarch)throws Exception;
	
	List<PartInfoVO> selectPartInfoCombo() throws Exception;
	
	int selectPartInfoPageInfoManageListToCnt_s(PartInfoVO serarch)throws Exception;
	
	PartInfoVO selectPartInfoDetail(String partId)throws Exception;
	
	int updatePartInfoManage(PartInfo vo) throws Exception;
}
