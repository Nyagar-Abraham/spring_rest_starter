package org.studyeasy.SpringRestDemo.payload.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountViewDTO {
  private Long id;

  private String email;

  private String authorities;
}
