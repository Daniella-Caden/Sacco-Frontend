package org.pahappa.systems.kimanyisacco.views.withdrawalRequests;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.List;

@ManagedBean(name = "withdrawRequests")
@SessionScoped
public class WithdrawalRequests {

    TransactionService transactionService = new TransactionServiceImpl();
    public List<Transaction> viewRequests() throws IOException {

        return transactionService.getWithdrawalRequests();



    }
    public double getAccountBalance(String userName){
        Account account= transactionService.getAccountBalance(userName);
        return account.getAccountBalance();
    }
}
