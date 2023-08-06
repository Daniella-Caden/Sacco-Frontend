package org.pahappa.systems.kimanyisacco.daos;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;

import javax.persistence.criteria.JoinType;
public class TransactionDAO {

    public boolean save(Transactions trans){

       boolean bal = true;
        Transaction transaction = null;
        boolean transactionSuccess = false;
        Members member = trans.getMember();
        Session session = SessionConfiguration.getSessionFactory().openSession();
       
   
     
        try{
            double accountBal=0;
          
            transaction = session.beginTransaction();

       

            System.out.println("Current:"+member.getAccountBalance());
            System.out.println("Current:"+member.getUserName());

            

            if(trans.getTransactionType().equals("deposit")){
                trans.setStatus("APPROVED");
                
                 
                
                 session.save(trans);
                 transactionSuccess = true;
                 
                 }

                 else{
       

                    if(trans.getAmount()>=member.getAccountBalance()){
                        bal = false;
                        
                       
                    }

                   
                    else{
                        
                        trans.setStatus("PENDING");
                         session.save(trans);
                        
                 
                 
                    }

                    
                 }

                

//             Members member = new Members();
//             member = getMemberBalance(member.getUserName());
//  System.out.println(member.getAccountBalance());           
// if(trans.getTransactionType().equals("deposit")){
//     accountBal = member.getAccountBalance()+trans.getAmount();
// }

            
        }catch(Exception ex){
           
            ex.printStackTrace();
        }

        try{
if(transactionSuccess){
    Members m = (Members) session.get(Members.class,member.getUserName());

   
 
    m.setAccountBalance(member.getAccountBalance()+trans.getAmount());

    
    session.update(m);

        }}catch(Exception e){
            System.out.println("error");
        }

        try{
            transaction.commit();
        }catch(Exception e){
            if(transaction!=null){
                transaction.rollback();
            }
            System.out.println("error");
        }
return bal;
        
    }

   
    // public Members getMemberBalance(String userName){
    //     try  {
    //         Session session = SessionConfiguration.getSessionFactory().openSession();
            
    //         Criteria criteria = session.createCriteria(Members.class);
    //         criteria.add(Restrictions.eq("userName", userName));
            
    //         return (Members) criteria.uniqueResult();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }

    // }
    public List<Transactions> getWithdrawalRequests(){
        
        try  {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("status", "PENDING"));
            
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
 
    
      

}



public Transactions getPending(String userName){
    
    try {
        Session session = SessionConfiguration.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(Transactions.class);
        // Add restriction to filter by transactionType
        criteria.add(Restrictions.eq("transactionType", "withdraw"));
        criteria.add(Restrictions.eq("status", "PENDING"));
        

        // Create an alias for the member property and use it to add a restriction for userName
        criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
        criteria.add(Restrictions.eq("m.userName", userName));

        return (Transactions)criteria.uniqueResult();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}





// ...

public List<Transactions> getNotifications(String userName) {
    try {
        Session session = SessionConfiguration.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(Transactions.class);
        // Add restriction to filter by transactionType
        criteria.add(Restrictions.eq("transactionType", "withdraw"));
        Disjunction statusRestrictions = Restrictions.disjunction();
        statusRestrictions.add(Restrictions.eq("status", "DONE"));
        statusRestrictions.add(Restrictions.eq("status", "REJECTED"));
        criteria.add(statusRestrictions);

        // Create an alias for the member property and use it to add a restriction for userName
        criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
        criteria.add(Restrictions.eq("m.userName", userName));

        return criteria.list();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


 
    public List<Transactions> getHistory(String userName) {
    try {
        Session session = SessionConfiguration.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(Transactions.class);
        criteria.add(Restrictions.eq("status","APPROVED"));
        // Add restriction to filter by transactionType
        // Create an alias for the member property and use it to add a restriction for userName
        criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
        criteria.add(Restrictions.eq("m.userName", userName));

        return criteria.list();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
      



    public void updateAccountBalance(Double balance,String userName){
        Transaction transaction = null;
    try{
    Session session = SessionConfiguration.getSessionFactory().openSession();
    transaction = session.beginTransaction();
    Members member = (Members) session.get(Members.class,userName);

    
    member.setAccountBalance(balance);

    
    session.update(member);
    transaction.commit();
    }catch(Exception ex) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    }


    public void updateStatus(int id,String decision){
        Transaction transaction = null;
        try{
        Session session = SessionConfiguration.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Transactions trans = (Transactions) session.get(Transactions.class,id);
    
        if(decision.equals("APPROVE")){
        trans.setStatus("DONE");
        trans.setNotifications("Your withdrawal of " + trans.getAmount() + " has been approved");
    
    }

        else{
            trans.setStatus("REJECTED");
            trans.setNotifications("Your withdrawal of " + trans.getAmount() + " has been rejected.You have reached the withdrawal limit.Please contact the admin for more information");
        }
        

        
     
        session.update(trans);
        transaction.commit();
        }catch(Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    
    }


    public void updateWithdraw(Transactions trans){

            Transaction transaction = null;
        try{
        Session session = SessionConfiguration.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Members member = trans.getMember();
      Transactions tran = (Transactions) session.get(Transactions.class,trans.getId());
      member.setAccountBalance(member.getAccountBalance()-trans.getAmount());

      updateAccountBalance(member.getAccountBalance(),member.getUserName());
        
       LocalDate localDateToStore = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = localDateToStore.format(dateFormatter);
            tran.setCreatedOn(formattedDate);
            tran.setStatus("APPROVED");
    
        
     
        session.update(tran);
        transaction.commit();
        }catch(Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    
    }

    public List<Transactions> getTransactions(){

        try  {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Transactions.class);
            
            
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public List<Transactions> getWithdrawType(){
        try  {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("transactionType", "withdraw"));
            
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transactions> getDepositType(){
        try  {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            
            Criteria criteria = session.createCriteria(Transactions.class);
            criteria.add(Restrictions.eq("transactionType", "deposit"));
            
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    }





