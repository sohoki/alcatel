package com.sohoki.backoffice.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import com.sohoki.backoffice.sym.cnt.service.CenterInfoVO;
import com.sohoki.backoffice.sym.cnt.service.CenterInfo;


@Mapper("CenterInfoManageMapper")
public interface CenterInfoManageMapper {
	
    public List<CenterInfoVO> selectCenterInfoManageListByPagination(CenterInfoVO serarch);
	
    public List<CenterInfoVO> selectCenterInfoManageCombo();
	
    public int selectCenterInfoManageListTotCnt_S(CenterInfoVO serarch);
	
    public CenterInfoVO selectCenterInfoManageDetail(String centerId);
	
    public int updateCenterInfoManage(CenterInfo vo);
    
    public int updateFileDetailInfo(CenterInfo vo);
    
    public int insertCenterInfoManage(CenterInfo vo);
}
