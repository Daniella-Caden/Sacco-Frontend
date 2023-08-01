package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.daos.TransactionDAO;
import org.pahappa.systems.kimanyisacco.daos.AddUser;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;



import java.util.List;


public class TransactionsImpl{

    TransactionDAO transDAO = new TransactionDAO();

    public boolean createTransaction(Transactions trans){
       return transDAO.save(trans);
   
        
    }
  public int getWithdraws(){
    return (transDAO.getWithdrawalRequests()).size();
  }

    public List<Transactions> getWithdrawalRequests(){
        return transDAO.getWithdrawalRequests();
    }

   
 public List<Transactions> getNotifications(String userName){
        return transDAO.getNotifications(userName);
    }
    public Transactions getRecent(String userName){
        Transactions lastObject = new Transactions();
        List<Transactions> recent = transDAO.getHistory(userName);
        int lastIndex = recent.size()-1;

        if(lastIndex>=0){
             lastObject = recent.get(lastIndex);
        }

        return lastObject;


    }
    public List<Transactions> getHistory(String userName){
        return transDAO.getHistory(userName);
    }
    public void updateStatus(int id,String decision){
        transDAO.updateStatus(id,decision);
     } 

     public void updateWithdraw(Transactions trans){
        transDAO.updateWithdraw(trans);
     }
   
     public List<Transactions> getTransactions(){
    return transDAO.getTransactions();

     }

     public int getWithdrawType(){
        return (transDAO.getWithdrawType()).size();
     }

     public int getDepositType(){
        return (transDAO.getDepositType()).size();
     }

    
}