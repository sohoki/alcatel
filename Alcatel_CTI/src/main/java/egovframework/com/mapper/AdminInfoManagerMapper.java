package egovframework.com.mapper;

import java.util.List;

import egovframework.let.uat.uia.service.AdminInfo;
import egovframework.let.uat.uia.service.AdminInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


@Mapper("AdminInfoManagerMapper")
public interface AdminInfoManagerMapper {

	public int deleteAdminUserManage(String mberId) throws Exception;
	
	public int insertAdminUserManage(AdminInfoVO vo) throws Exception;
	
	public int updateAdminUserManage(AdminInfoVO vo) throws Exception;
	
	public int updateAdminUserLockManage(String adminId) throws Exception;
	
	public int updatePassChange(AdminInfo vo) throws Exception;
	
	public AdminInfoVO selectAdminUserManageDetail(AdminInfoVO vo) throws Exception;
	
	public List<?> selectAdminUserManageListByPagination(AdminInfoVO searchVO) throws Exception;
	
	public int selectAdminUserManageListTotCnt_S(AdminInfoVO searchVO) throws Exception;

	public int selectAdminUserMangerIDCheck(String code) throws Exception;
	
}