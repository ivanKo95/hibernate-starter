package com.dmdew;

import com.dmdew.entity.Users;
import java.time.LocalDate;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {

  public static void main(String[] args) {
    var configuration = new Configuration();
    configuration.configure();
    configuration.addAnnotatedClass(Users.class);
    configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
    try (var sessionFactory = configuration.buildSessionFactory();
         var session = sessionFactory.openSession()) {
      session.beginTransaction();
      var user = Users.builder()
          .username("user1")
          .firstname("firstname1")
          .lastname("lastname1")
          .birthDate(LocalDate.of(2001, 11, 11))
          .age(21)
          .build();

      session.persist(user);
      session.getTransaction().commit();
    }
  }
}
