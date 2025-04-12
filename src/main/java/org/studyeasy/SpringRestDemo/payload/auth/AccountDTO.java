package org.studyeasy.SpringRestDemo.payload.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
  @Email
  @Schema(description = "Email Address", example = "abraham@gmail.com",requiredMode = RequiredMode.REQUIRED)
  private String email;

  @Size(min = 6, max = 20)
  @Schema(description = "password", example = "pass747word",requiredMode = RequiredMode.REQUIRED, minLength = 6, maxLength = 20)
  private String password;

}
