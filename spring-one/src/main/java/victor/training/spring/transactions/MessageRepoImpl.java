package victor.training.spring.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class MessageDto {
   private final long id;
   private final String message;

   MessageDto(long id, String message) {
      this.id = id;
      this.message = message;
   }
}
@RequiredArgsConstructor
public class MessageRepoImpl implements MessageRepoCustom{
   private final EntityManager em;

   @Override
   public List<MessageDto> search(MessageSearchCriteria searchCriteria) {
      String jpql = "SELECT new victor.training.spring.transactions.MessageDto(m.id, m.message)" +
                    " FROM Message m" +
//                    " LEFT JOIN m.client c " +
                    " WHERE 1=1 ";//" AND (m.message =:message OR ISNULL(:message)) ";
      Map<String, Object> map = new HashMap<>();

      if (searchCriteria.message != null) {
         jpql += " AND m.message = :messageX ";
         map.put("messageX", searchCriteria.message);
      }
//      if (searchCriteria.clientId != null) {
//         jpql += " AND m.client.id = :clientId ";
//         map.put("clientId", searchCriteria.clientId);
//      }
//      if (searchCriteria.hasActiveClient != null) {
//         jpql += " AND c.active = true ";
//      }




      TypedQuery<MessageDto> query = em.createQuery(jpql, MessageDto.class);
      for (Entry<String, Object> paramEntry : map.entrySet()) {
         query.setParameter(paramEntry.getKey(), paramEntry.getValue());
      }

      return query.getResultList();
   }

   public void method() {

      JdbcTemplate jdbc = null;

//      jdbc.query("SELECT id,... FROM CITY ...", new RowMapper<Map<String, Object>>() {
//         @Override
//         public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", rs.getInt("id"));
////            String json = new ObjectMapper().writeValueAsString(map);
//            return map;
//         }
//      });


      Map<String, Object> data = new HashMap<>();


      String columns = "id,label";
      String tableName = "CITY";
      String questions = "?,?";
      List<Object> args = new ArrayList<>();
      args.add(data.get("id"));
      args.add(data.get("label"));
      jdbc.update("INSERT INTO " + tableName + "(" + columns + ") VALUES (" + questions + ")", args.toArray(new Object[0]));
   }



}
