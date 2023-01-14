package com.dmdev;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

import com.dmdew.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class HibernateRunnerTest {

  @Test
  void checkReflectionApi() throws SQLException, NullPointerException, IllegalAccessException {
    var user = Users.builder()
        .username("user1")
        .firstname("firstname1")
        .lastname("lastname1")
        .birthDate(LocalDate.of(2001, 11, 11))
        .age(21)
        .build();

    String sql = """
        insert
        into
        %s
        (%s)
        values
        (%s)
        """;

    var tableName = ofNullable(user.getClass().getAnnotation(Table.class))
        .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
        .orElse(user.getClass().getName());

    var declaredFields = user.getClass().getDeclaredFields();

    var columnNames = Arrays.stream(declaredFields)
        .map(field -> ofNullable(field.getAnnotation(Column.class))
            .map(Column::name)
            .orElse(field.getName()))
        .collect(joining(", "));

    var columnValues = Arrays.stream(declaredFields)
        .map(field -> "?")
        .collect(joining(", "));

    System.out.printf((sql) + "%n", tableName, columnNames, columnValues);

    Connection connection = null;

    var preparedStatement = connection.prepareStatement((sql) + "%n");

    for (Field declaredField : declaredFields) {
      declaredField.setAccessible(true); //to get value
      preparedStatement.setObject(1, declaredField.get(user));
    }
  }

}