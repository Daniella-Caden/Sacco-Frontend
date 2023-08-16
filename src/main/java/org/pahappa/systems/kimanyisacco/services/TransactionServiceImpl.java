package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.daos.TransactionDAO;
import org.pahappa.systems.kimanyisacco.daos.AddUser;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.constants.*;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

   TransactionDAO transDAO = new TransactionDAO();

   public boolean createTransaction(Transaction trans) {
      return transDAO.save(trans);

   }

   public Transaction getPending(String userName) {
      return transDAO.getPending(userName);
   }

   public int getWithdraws() {
      return (transDAO.getWithdrawalRequests()).size();
   }

   public List<Transaction> getWithdrawalRequests() {
      return transDAO.getWithdrawalRequests();
   }

  
   public List<Transaction> getNotifications(String userName) {
      return transDAO.getNotifications(userName);
   }

   public Transaction getRecent(String userName) {
      Transaction lastObject = new Transaction();
      List<Transaction> recent = transDAO.getHistory(userName);
      int lastIndex = recent.size() - 1;

      if (lastIndex >= 0) {
         lastObject = recent.get(lastIndex);
      }

      return lastObject;

   }

   public List<Transaction> getHistory(String userName) {
      return transDAO.getHistory(userName);
   }

   public void updateStatus(long id, String decision) {
      transDAO.updateStatus(id, decision);
   }

   public void updateWithdraw(Transaction trans) {
      transDAO.updateWithdraw(trans);
   }

   public List<Transaction> getTransaction() {
      return transDAO.getTransaction();

   }
public Account getAccountBalance(String userName){
   return transDAO.getAccountBalance(userName);
}
   public int getWithdrawType() {
      return (transDAO.getWithdrawType()).size();
   }

   public int getDepositType() {
      return (transDAO.getDepositType()).size();
   }

   public int getTotalDeposits(){
      return (transDAO.getAllDeposits()).size();
   }

   public int getTotalWithdraws(){
      return (transDAO.getAllWithdraws()).size();
   }

}