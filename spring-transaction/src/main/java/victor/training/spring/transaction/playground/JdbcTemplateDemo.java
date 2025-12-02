package victor.training.spring.transaction.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
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
            insert into MY_ENTITY(id, name) 
            values (?, ?)
            """,
        100L, "SQL");
  }

  public void rowMapper() {
    List<MyEntity> query = jdbcTemplate.query("""
                SELECT id, name
                FROM MY_ENTITY
                WHERE ID = ?
            """, this::fromResultSet,
        100);

  }

  private MyEntity fromResultSet(ResultSet rs, int rowNum) throws SQLException {
    MyEntity myEntity = new MyEntity();
    myEntity.setId(rs.getLong("id"));
    myEntity.setName(rs.getString("name")); // or a null-safe friend
    return myEntity;
  }

  public void dynamicQuery(String name) {
    List<String> sqlParts = new ArrayList<>();
    Map<String, Object> params = new HashMap<>();
    // language=sql
    sqlParts.add("""
        SELECT m.id, m.name
        FROM MY_ENTITY m
        WHERE 1=1""");

    if (name != null) {
      sqlParts.add("AND m.name = :name"); // or LIKE
      params.put("name", name);
    }

    String sql = String.join(" ", sqlParts);
    List<MyEntity> list = namedParameterJdbcTemplate.query(sql, params, this::fromResultSet);
  }
}
