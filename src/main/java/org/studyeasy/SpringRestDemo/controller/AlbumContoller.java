package org.studyeasy.SpringRestDemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.studyeasy.SpringRestDemo.model.Account;
import org.studyeasy.SpringRestDemo.model.Album;
import org.studyeasy.SpringRestDemo.payload.album.AlbumPayloadDTO;
import org.studyeasy.SpringRestDemo.payload.album.AlbumViewDTO;
import org.studyeasy.SpringRestDemo.service.AccountService;
import org.studyeasy.SpringRestDemo.service.AlbumService;
import org.studyeasy.SpringRestDemo.utils.constants.AlbumError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/album")
@Tag(name = "Auth Controller", description = "Controller for album and photo management")
@Slf4j
public class AlbumContoller {
   @Autowired
   private AlbumService albumService;

   @Autowired
   private AccountService accountService;




  @PostMapping(value = "/albums/add", consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponse(responseCode = "400",description = "Please add valid name and description")
@ApiResponse(responseCode = "401",description = "Token missing")
@Operation(summary = "Add album")
@SecurityRequirement(name = "studyeasy-demo-api")
  public ResponseEntity<AlbumViewDTO> addAlbum(@Valid @RequestBody AlbumPayloadDTO albumPayloadDTO, Authentication authentication){
    try {
      Album album = new Album();
      album.setName(albumPayloadDTO.getName());
      album.setDescription(albumPayloadDTO.getDescription());

      String email = authentication.getUsername();
      Optional<Account> optionalAccount = accountService.findByEmail(email);

      Account account = optionalAccount.get();

      album.setAccount(account);
      album = albumService.save(album);

      AlbumViewDTO albumViewDTO = new AlbumViewDTO(album.getId(),album.getName(),album.getDescription());

      return ResponseEntity.ok(albumViewDTO);
    } catch (Exception e) {
     log.debug(AlbumError.ADD_ALBUM_ERROR.toString() + ": "+ e.getMessage());
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
  }
  
}
