package victor.training.spring.transaction;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyBatisMapper {

//	@Select("SELECT id, message from MESSAGE where id = #{id}")
	Message search(int id); // in XML

	@Insert("INSERT INTO MESSAGE(id, message) values (#{id},#{message})")
	void insert(Message message);

	void insertMessage(Message message); // in XML

}