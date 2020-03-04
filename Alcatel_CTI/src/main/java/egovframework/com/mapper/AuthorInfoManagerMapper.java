package egovframework.com.mapper;

import java.util.List;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.let.src.ram.service.AuthorInfo;

@Mapper("AuthorInfoManagerMapper")
public interface AuthorInfoManagerMapper {

	public List<AuthorInfo> selectAuthorIInfoManageCombo();
}
