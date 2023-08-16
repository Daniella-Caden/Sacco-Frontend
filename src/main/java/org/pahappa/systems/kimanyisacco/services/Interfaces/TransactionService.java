package org.pahappa.systems.kimanyisacco.services.Interfaces;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;

import java.util.List;

public interface TransactionService {

    int getTotalDeposits();

    int getTotalWithdraws();
    int getWithdraws();

    Transaction getRecent(String userName);

    List<Transaction> getWithdrawalRequests();

    void updateStatus(long id, String decision);

    Account getAccountBalance(String userName);

    List<Transaction> getHistory(String userName);

    int getWithdrawType();

    int getDepositType();

    boolean createTransaction(Transaction transaction);

    Transaction getPending(String userName);

    List<Transaction> getNotifications(String userName);
    
    void updateWithdraw(Transaction transaction);
}
