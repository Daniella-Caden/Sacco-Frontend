package org.pahappa.systems.kimanyisacco.daos;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class AccountDAO{

    public List<Account> getAccounts(){
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Account.class);
            

            return criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}