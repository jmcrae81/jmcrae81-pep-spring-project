package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import java.io.*;

@Service
public class AccountService {
    AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepo){
        this.accountRepository = accountRepo;
    }

    public Account persistAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        Account returnedAccount;
        returnedAccount = this.accountRepository.save(account);

        return returnedAccount;
    }

    public Account getAccountByUsernameAndPassword(String name, String password){
        return this.accountRepository.findAccountByUsernameAndPassword(name, password);
    }

    public List<Account> getAllAccounts(){
        return this.accountRepository.findAll();
    }
    
    public void logAccountToFile(Account account){
        int id=account.getAccountId();
        String username = account.getUsername();
        String password = account.getPassword();
        try{

            FileWriter fw = new FileWriter("from_service.txt", true);

            String currentInfo = id + " " + username + " " + password + "\n";
            fw.write(currentInfo, 0, currentInfo.length());
            fw.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
