package org.studyeasy.SpringRestDemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.studyeasy.SpringRestDemo.model.Account;
import org.studyeasy.SpringRestDemo.service.AccountService;
import org.studyeasy.SpringRestDemo.utils.constants.Authority;

@Component
public class SeedData implements CommandLineRunner {

  @Autowired
  private AccountService accountService;

  @Override
  public void run(String... args) throws Exception {
   Account account01 = new Account();
   Account account02 = new Account();

   account01.setEmail("abraham@gmail.com");
   account01.setPassword("pass747word");
   account01.setAuthorities(Authority.USER.toString());
   
   account02.setEmail("nyagar@gmail.com");
   account02.setPassword("pass747word");
   account02.setAuthorities(Authority.ADMIN.toString()+ " " + Authority.USER.toString());

   accountService.save(account01);
   accountService.save(account02);

  }
  
  
}
