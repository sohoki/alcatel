package com.sohoki.backoffice.util.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import com.sohoki.backoffice.mapper.UniUtilManageMapper;
import com.sohoki.backoffice.util.service.UniUtilInfo;
import com.sohoki.backoffice.util.service.UniUtilManageService;

@Service("UniUtilManageService")
public class UniUtilManageServiceImpl extends EgovAbstractServiceImpl implements  UniUtilManageService {
	
	@Resource(name="UniUtilManageMapper")
	private UniUtilManageMapper uniMapper;

	@Override
	public int selectIdDoubleCheck(UniUtilInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return uniMapper.selectIdDoubleCheck(vo);
	}

	@Override
	public int deleteUniStatement(UniUtilInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return uniMapper.deleteUniStatement(vo);
	}

	@Override
	public String selectMaxValue(UniUtilInfo vo) throws Exception {
		// TODO Auto-generated method stub
		return uniMapper.selectMaxValue(vo);
	}

}
