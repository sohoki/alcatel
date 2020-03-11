package egovframework.let.uat.uia.service;

import java.util.List;


public interface AdminInfoManageService {

	int deleteAdminUserManage(String mberId) throws Exception;
	
	int updateAdminUserManage(AdminInfoVO vo) throws Exception;
	
	int updateAdminUserLockManage(String adminId) throws Exception;
	
	int updatePassChange(AdminInfo vo) throws Exception;
	
	AdminInfoVO selectAdminUserManageDetail(AdminInfoVO vo) throws Exception;	
	
	List<?> selectAdminUserManageListByPagination(AdminInfoVO searchVO) throws Exception;  
	
    int selectAdminUserManageListTotCnt_S(AdminInfoVO searchVO) throws Exception;
    
    int selectAdminUserMangerIDCheck(String code) throws Exception;
    
    
}
