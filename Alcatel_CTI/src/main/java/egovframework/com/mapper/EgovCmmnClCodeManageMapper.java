package egovframework.com.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.let.sym.ccm.ccc.service.CmmnClCode;
import egovframework.let.sym.ccm.ccc.service.CmmnClCodeVO;

@Mapper("CmmnClCodeManageMapper")
public interface EgovCmmnClCodeManageMapper {

	
	    public List<CmmnClCodeVO> selectCmmnClCodeListByPagination(CmmnClCodeVO vo);
		
		public List<CmmnClCode> selectCmmnClCodeList();
		
		public CmmnClCode selectCmmnClCodeDetail(String clCode);
		
		public int insertCmmnClCode(CmmnClCode vo);
		
		public int updateCmmnClCode(CmmnClCode vo);
		
		public int deleteCmmnClCode(String clCode);
		
		// 카운터 
		public int selectCmmnClCodeListTotCnt (CmmnClCodeVO searchVO);
}
