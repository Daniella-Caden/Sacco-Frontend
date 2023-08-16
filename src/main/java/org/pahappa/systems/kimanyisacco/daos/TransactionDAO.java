package org.pahappa.systems.kimanyisacco.daos;


import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.constants.Status;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;

import javax.persistence.criteria.JoinType;

public class TransactionDAO {



    public boolean save(Transaction trans) {

        boolean bal = true;
        org.hibernate.Transaction transaction = null;
        boolean Transactionsuccess = false;
        Member member = trans.getMember();
        Account account = getAccountBalance(member.getUserName());

        Session session = SessionConfiguration.getSessionFactory().openSession();
        transaction = session.beginTransaction();


        try {

           

            System.out.println("Current:" + member.getUserName());

            if (trans.getTransactionType()==TransactionType.DEPOSIT) {
                trans.setStatus(Status.DONE);

                session.save(trans);
                Transactionsuccess = true;

            }

            else {

                if (trans.getAmount() >= account.getAccountBalance()) {
                    bal = false;

                }

                else {

                    trans.setStatus(Status.PENDING);
                    session.save(trans);

                }

            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        try {
            if (Transactionsuccess) {
                            
                System.out.println("OLD:"+account.getAccountBalance());
                account.setAccountBalance(account.getAccountBalance() + trans.getAmount());
                System.out.println("NEW:"+account.getAccountBalance());
                session.update(account);
                

            }
        } catch (Exception e) {
            System.out.println("error");
        }

        try {
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("error");
        }
        return bal;

    }

    public List<Transaction> getAllDeposits() {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("transactionType", TransactionType.DEPOSIT));


            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Transaction> getAllWithdraws() {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("transactionType", TransactionType.WITHDRAW));


            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Transaction> getWithdrawalRequests() {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("status", Status.PENDING));

            
            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Transaction getPending(String userName) {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            // Add restriction to filter by transactionType
            criteria.add(Restrictions.eq("transactionType", TransactionType.WITHDRAW));
            criteria.add(Restrictions.eq("status", Status.PENDING));

            // Create an alias for the member property and use it to add a restriction for
            // userName
            criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
            criteria.add(Restrictions.eq("m.userName", userName));

            return (Transaction) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account getAccountBalance(String userName) {
        Session session = SessionConfiguration.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(Account.class);
        criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
        criteria.add(Restrictions.eq("m.userName", userName));

        return (Account) criteria.uniqueResult();
    }

    public List<Transaction> getNotifications(String userName) {
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            // Add restriction to filter by transactionType
            criteria.add(Restrictions.eq("transactionType", TransactionType.WITHDRAW));
            Disjunction statusRestrictions = Restrictions.disjunction();
            statusRestrictions.add(Restrictions.eq("status", Status.APPROVED));
            statusRestrictions.add(Restrictions.eq("status", Status.REJECTED));
            criteria.add(statusRestrictions);

            // Create an alias for the member property and use it to add a restriction for
            // userName
            criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
            criteria.add(Restrictions.eq("m.userName", userName));

            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transaction> getHistory(String userName) {
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("status", Status.DONE));

            // Add restriction to filter by transactionType
            // Create an alias for the member property and use it to add a restriction for
            // userName
            criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
            criteria.add(Restrictions.eq("m.userName", userName));

            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateAccountBalance(Double balance, String userName) {
        org.hibernate.Transaction transaction = null;
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Account.class);
            criteria.createAlias("member", "m", CriteriaSpecification.INNER_JOIN);
            criteria.add(Restrictions.eq("m.userName", userName));

            Account account = (Account)criteria.uniqueResult();
           

            account.setAccountBalance(balance);

            session.update(account);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public void updateStatus(long id, String decision) {
        org.hibernate.Transaction transaction = null;
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Transaction trans = (Transaction) session.get(Transaction.class, id);

            if (decision.equals("APPROVE")) {
                trans.setStatus(Status.APPROVED);
                trans.setNotifications("Your withdrawal of " + trans.getAmount() + " has been approved");

            }

            else {
                trans.setStatus(Status.REJECTED);
                trans.setNotifications("Your withdrawal of " + trans.getAmount()
                        + " has been rejected.You have reached the withdrawal limit.Please contact the admin for more information");
            }

            session.update(trans);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public void updateWithdraw(Transaction trans) {

        org.hibernate.Transaction transaction = null;
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Member member = trans.getMember();
            Transaction tran = (Transaction) session.get(Transaction.class, trans.getTransactionId());
            Account account = getAccountBalance(member.getUserName());
            account.setAccountBalance(account.getAccountBalance() - trans.getAmount());

            updateAccountBalance(account.getAccountBalance(), member.getUserName());

            LocalDate localDateToStore = LocalDate.now();

            tran.setCreatedOn(localDateToStore);
            tran.setStatus(Status.DONE);

            session.update(tran);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public List<Transaction> getTransaction() {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);

            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Transaction> getWithdrawType() {
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("transactionType", TransactionType.WITHDRAW));

            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Transaction> getDepositType() {
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("transactionType", TransactionType.DEPOSIT));

            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
