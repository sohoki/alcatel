package com.sohoki.backoffice.mapper;

import com.sohoki.backoffice.util.service.UniUtilInfo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("UniUtilManageMapper")
public interface UniUtilManageMapper {

	public int selectIdDoubleCheck(UniUtilInfo vo); 
	
	public int deleteUniStatement(UniUtilInfo vo);
	
	public String selectMaxValue(UniUtilInfo vo);
}
