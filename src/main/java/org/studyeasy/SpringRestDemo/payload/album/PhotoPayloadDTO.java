package org.studyeasy.SpringRestDemo.payload.album;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhotoPayloadDTO {
  @NotBlank
   @Schema(description = "Photo name", example = "selfie",requiredMode = RequiredMode.REQUIRED)
  private String name;
  @NotBlank
  @Schema(description = "Description of photo", example = "Description",requiredMode = RequiredMode.REQUIRED)
  private String description;
}
