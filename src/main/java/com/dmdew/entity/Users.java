package com.dmdew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "new_users")
public class Users {
  @Id
  private String username;
  private String firstname;
  private String lastname;
  @Column(name = "birth_date")
  private LocalDate birthDate;
  private Integer age;
}
