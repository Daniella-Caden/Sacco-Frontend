package org.pahappa.systems.kimanyisacco.services;
import org.pahappa.systems.kimanyisacco.daos.AccountDAO;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.services.Interfaces.AccountService;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    AccountDAO accountDAO = new AccountDAO();
    public List<Account> getAccounts(){
        return accountDAO.getAccounts();
    }

    public double getTotalFunds(){
        double sum=0;
        List<Account> accounts =accountDAO.getAccounts();

        for(Account account:accounts){
            sum+=account.getAccountBalance();
        }

        return sum;
    }

    
}