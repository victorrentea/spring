package victor.training.spring.transactions;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MyBatisMapper {

	@Select("SELECT id, message from MESSAGE where id = #{id}")
	Message search(@Param("id") int id);

	@Insert("INSERT INTO MESSAGE(id, message) values (#{id},#{message})")
	void insert(Message message);

}