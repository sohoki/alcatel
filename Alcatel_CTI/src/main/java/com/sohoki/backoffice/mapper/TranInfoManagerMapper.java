package com.sohoki.backoffice.mapper;

import java.util.List;

import com.sohoki.backoffice.sts.tran.service.TransInfo;
import com.sohoki.backoffice.sts.tran.service.TransInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("TranInfoManagerMapper")
public interface TranInfoManagerMapper {

	
	public List<TransInfoVO> selectTranInfoManageListByPagination (TransInfoVO searchVO);
	
	public TransInfoVO  selectTranInfoManageDetail(String tranSeq);
	
	public int selectTranProcessCount(String tranProcessName);
	
	public int selectTrannfoManageListTotCnt_S(TransInfoVO searchVO);
	
	public int insertTranInfoManage(TransInfo VO);
	
	public int updateTraninfoManage(TransInfo VO);
}
