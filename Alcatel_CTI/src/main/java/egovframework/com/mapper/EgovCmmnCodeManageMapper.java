package egovframework.com.mapper;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.let.sym.ccm.cca.service.CmmnCode;
import egovframework.let.sym.ccm.cca.service.CmmnCodeVO;

@Mapper("CmmnCodeManageMapper")
public interface EgovCmmnCodeManageMapper {

	    public List<CmmnCodeVO> selectCmmnCodeListByPagination(CmmnCodeVO vo);
		
		public List<CmmnCode> selectCmmnCodeList(String clCode);
		
		public CmmnCode selectCmmnCodeDetail(String codeId);
		
		public int insertCmmnCode(CmmnCode vo);
		
		public int updateCmmnCode(CmmnCode vo);
		
		public int deleteCmmnCode(String codeId);
		
		public int selectCmmnCodeListTotCnt(CmmnCodeVO  vo);	
		//아이디 체크 
		public int selectIDCheck (String codeId);
}
