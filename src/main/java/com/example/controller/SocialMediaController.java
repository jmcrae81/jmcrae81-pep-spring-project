package com.example.controller;

import com.example.entity.Account;
import com.example.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;

import java.io.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {
    
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> registerUser(@RequestBody Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        int responseStatus = 400;

        if(isDuplicateUser(account)){
            responseStatus = 409;
            
        }else if (!username.equals("") && (password.length() > 4)){
                int id = 0;
                Account returnedAccount = accountService.persistAccount(account);
                responseStatus = 200;

                logAccountToFile(returnedAccount);

                return ResponseEntity.status(responseStatus).body(returnedAccount);
        } 
        return ResponseEntity.status(responseStatus).body(null);        
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account){
        int responseStatus = 401;
        int id = account.getAccountId();

        String username = account.getUsername();
        String password = account.getPassword();

        Account accountToReturn = accountService.getAccountByUsernameAndPassword(username, password);
        if(accountToReturn != null){
            responseStatus = 200;
        }

        return ResponseEntity.status(responseStatus).body(accountToReturn);
        
    }
    /*
    @PostMapping("/messages")

    @GetMapping("/messages")

    @GetMapping("/messages/{messageId}")

    @DeleteMapping("/messages/{messageId}")

    @PatchMapping("/messages/{messageId}")

    @GetMapping("/accounts/{accountId}/messages")

   */ 
    public boolean isDuplicateUser(Account account){
        List<Account> currentUsers = new ArrayList<Account>();
        currentUsers = accountService.getAllAccounts();
        String username = account.getUsername();
        String compareTo = new String();

        if(currentUsers.isEmpty()){
            return false;
        }

        for( Account current : currentUsers){
            compareTo = current.getUsername();
            if( username.equals(compareTo) ){
                return true;
            }
        }

        return false;
    }

    public void logAccountToFile(Account account){
        int id=account.getAccountId();
        String username = account.getUsername();
        String password = account.getPassword();
        try{

            FileWriter fw = new FileWriter("accounts.txt", true);

            String currentInfo = id + " " + username + " " + password + "\n";
            fw.write(currentInfo, 0, currentInfo.length());
            fw.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }

}

