package com.sohoki.backoffice.util.service;

import com.sohoki.backoffice.util.service.UniUtilInfo;

public interface UniUtilManageService {

    int selectIdDoubleCheck(UniUtilInfo vo)throws Exception;
	
	int deleteUniStatement(UniUtilInfo vo) throws Exception;
	
	String selectMaxValue(UniUtilInfo vo) throws Exception;
}
