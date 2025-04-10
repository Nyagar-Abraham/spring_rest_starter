package org.studyeasy.SpringRestDemo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.studyeasy.SpringRestDemo.payload.auth.Token;
import org.studyeasy.SpringRestDemo.payload.auth.UserLogin;
import org.studyeasy.SpringRestDemo.service.TokenService;




@RestController
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public AuthController(TokenService tokenService,
  AuthenticationManager authenticationManager){
    this.tokenService = tokenService;
    this.authenticationManager = authenticationManager;
  }
  
  @PostMapping("/token")
  @ResponseBody
  public Token token(@RequestBody UserLogin userLogin) throws AuthenticationException {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password()));

    System.out.println("TOKEN"+tokenService.generateToken(authentication));
    
    return new Token(tokenService.generateToken(authentication));
  
}

}