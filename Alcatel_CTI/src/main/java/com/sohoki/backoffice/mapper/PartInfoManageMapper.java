package com.sohoki.backoffice.mapper;

import java.util.List;

import com.sohoki.backoffice.uat.uia.service.PartInfo;
import com.sohoki.backoffice.uat.uia.service.PartInfoVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("PartInfoManageMapper")
public interface PartInfoManageMapper {

	
    public List<PartInfoVO> selectPartInfoPageInfoManageListByPagination(PartInfoVO serarch)throws Exception;
	
	public List<PartInfoVO> selectPartInfoCombo() throws Exception;
	
	public int selectPartInfoPageInfoManageListToCnt_s(PartInfoVO serarch);
	
	public PartInfoVO selectPartInfoDetail(String PartId);
	
	public int insertPartInfoManage(PartInfo vo);
	
	public int updatePartInfoManage(PartInfo vo);
}
