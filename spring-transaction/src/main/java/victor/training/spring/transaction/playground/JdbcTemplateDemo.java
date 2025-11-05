package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JdbcTemplateDemo {
  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public void varargs() {
    jdbcTemplate.update("""
            insert into MESSAGE(id, message) 
            values (?, ?)
            """,
        100L, "SQL");
  }

  public void rowMapper() {
    List<Message> query = jdbcTemplate.query("""
                SELECT id, message
                FROM Message
                WHERE ID = ?
            """, this::fromResultSet,
        100);

  }

  private Message fromResultSet(ResultSet rs, int rowNum) throws SQLException {
    Message message = new Message();
    message.setId(rs.getLong("id"));
    message.setMessage(rs.getString("message")); // or a null-safe friend
    return message;
  }

  public void dynamicQuery(String message) {
    List<String> sqlParts = new ArrayList<>();
    Map<String, Object> params = new HashMap<>();
    // language=sql
    sqlParts.add("""
        SELECT m.id, m.message
        FROM MESSAGE m
        WHERE 1=1""");

    if (message != null) {
      sqlParts.add("AND m.message = :message"); // or LIKE
      params.put("message", message);
    }

    String sql = String.join(" ", sqlParts);
    List<Message> list = namedParameterJdbcTemplate.query(sql, params, this::fromResultSet);
  }
}
