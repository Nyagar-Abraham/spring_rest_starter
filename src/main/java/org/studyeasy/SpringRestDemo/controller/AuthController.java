package org.studyeasy.SpringRestDemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.studyeasy.SpringRestDemo.model.Account;
import org.studyeasy.SpringRestDemo.payload.auth.AccountDTO;
import org.studyeasy.SpringRestDemo.payload.auth.AccountViewDTO;
import org.studyeasy.SpringRestDemo.payload.auth.PasswordDTO;
import org.studyeasy.SpringRestDemo.payload.auth.ProfileDTO;
import org.studyeasy.SpringRestDemo.payload.auth.TokenDTO;
import org.studyeasy.SpringRestDemo.payload.auth.UserLoginDTO;
import org.studyeasy.SpringRestDemo.service.AccountService;
import org.studyeasy.SpringRestDemo.service.TokenService;
import org.studyeasy.SpringRestDemo.utils.constants.AccountError;
import org.studyeasy.SpringRestDemo.utils.constants.AccountSuccess;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;




@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "The auth API")
@Slf4j
public class AuthController {

  @Autowired
  private  AuthenticationManager authenticationManager;

  @Autowired
  private  TokenService tokenService;

  @Autowired
  private AccountService accountService;

 
  
@PostMapping("/token")
 @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<TokenDTO>  token(@Valid @RequestBody UserLoginDTO userLogin) throws AuthenticationException {

    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
      
      return ResponseEntity.ok(new TokenDTO(tokenService.generateToken(authentication)));
    } catch (Exception e) {
       log.debug(AccountError.TOKEN_GENERATION_ERROR.toString() + ": " + e.getMessage());
       return new ResponseEntity<>(new TokenDTO(null), HttpStatus.BAD_REQUEST);
    }

}


@PostMapping(value = "/users/add", consumes = "application/json")
@ResponseStatus(HttpStatus.OK)
@ApiResponse(responseCode = "400",description = "please provide valid email and password")
@ApiResponse(responseCode = "200",description = "Account added")
@Operation(summary = "Add new User")
public ResponseEntity<String> addUser(@Valid @RequestBody AccountDTO accountDTO){
  try {
    Account account = new Account();
    account.setEmail(accountDTO.getEmail());
    account.setPassword(accountDTO.getPassword());
  
  
    accountService.save(account);

    return ResponseEntity.ok(AccountSuccess.ACCOUNT_ADDED.toString());
  } catch (Exception e) {
    log.debug(AccountError.ADD_ACCOUNT_ERROR.toString() + ": "+ e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
  }
}



@GetMapping(value = "/users", produces = "application/json")
@ApiResponse(responseCode = "200",description = "list of users")
@ApiResponse(responseCode = "401",description = "Token missing")
@ApiResponse(responseCode = "401",description = "Token Error")
@Operation(summary = "Get all users")
 @SecurityRequirement(name = "studyeasy-demo-api")
public List<AccountViewDTO> users(){
  List<AccountViewDTO> accounts = new ArrayList<>();

  for(Account account : accountService.findAll()){
    accounts.add(new AccountViewDTO(account.getId(),account.getEmail(),account.getAuthorities()));
  }
 return accounts;
}


@GetMapping(value = "/profile", produces = "application/json")
@ApiResponse(responseCode = "200",description = "User profile")
@ApiResponse(responseCode = "401",description = "Token missing")
@ApiResponse(responseCode = "401",description = "Token Error")
@Operation(summary = "View profile")
 @SecurityRequirement(name = "studyeasy-demo-api")
public ProfileDTO profile(Authentication authentication){
  String email = authentication.getName();
  Optional<Account> optionalAccount = accountService.findByEmail(email);

  if(optionalAccount.isPresent()){
    Account account = optionalAccount.get();
    ProfileDTO profileDTO = new ProfileDTO(account.getId(),account.getEmail(),account.getAuthorities());

    return profileDTO;
  }
  return null;
}

@PutMapping(value = "/profile/update-password", produces = "application/json", consumes = "application/json")
@ApiResponse(responseCode = "200",description = "update profile")
@ApiResponse(responseCode = "401",description = "Token missing")
@ApiResponse(responseCode = "401",description = "Token Error")
@Operation(summary = "Update profile")
 @SecurityRequirement(name = "studyeasy-demo-api")
public AccountViewDTO update_password(@Valid @RequestBody PasswordDTO passwordDTO, Authentication authentication){
  String email = authentication.getName();

  Optional<Account> optionalAccount = accountService.findByEmail(email);

  if(optionalAccount.isPresent()){
    Account account = optionalAccount.get();
    account.setPassword(passwordDTO.getPassword());
    accountService.save(account);

    AccountViewDTO accountViewDTO = new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities());

    return accountViewDTO;
  }
  return null;
}

}