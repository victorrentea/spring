package com.example.demo;

import com.example.demo.jooq.tables.Person;
import com.example.demo.jooq.tables.records.PersonRecord;
import org.jooq.DSLContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class ARepository {
  private final JdbcTemplate jdbcTemplate;
  private final DSLContext dsl;

  public ARepository(JdbcTemplate jdbcTemplate, DSLContext dsl) {
    this.jdbcTemplate = jdbcTemplate;
    this.dsl = dsl;
  }

  public String hi() {
    var list = jdbcTemplate.queryForList(
        "SELECT name from person", String.class);
    return list.toString();
  }

  public String hiJooq() {
    List<String> list = dsl.select(Person.PERSON.NAME)
        .from(Person.PERSON)
        .fetch()
        .getValues(Person.PERSON.NAME);
    return list.toString();
  }
  public int create(String name) {
    System.out.println(name + " created");
    return dsl.insertInto(Person.PERSON, Person.PERSON.NAME)
        .values(name)
        .returning(Person.PERSON.ID)
        .fetchOne()
        .getValue(Person.PERSON.ID);

    // OOP:
//    PersonRecord r = new PersonRecord();
//    r.setName(name);
//    dsl.executeInsert(r);
//    return r.getId();
  }
}
