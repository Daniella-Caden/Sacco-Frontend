package org.pahappa.systems.kimanyisacco.daos;
import org.pahappa.systems.kimanyisacco.models.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class AdminDAO{
    public boolean registerAdmin(Admin admin){
        Transaction transaction = null;
        boolean isSuccess = true;
        try{
            
           Session session = SessionConfiguration.getSessionFactory().openSession();
           transaction = session.beginTransaction();
          
        
            
            session.save(admin);
            transaction.commit();
            
        
            
            
        }catch(Exception ex){
            if(transaction!=null){
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return isSuccess;
    }
    public Admin getAdmin(String userName){

        try  {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            
            
            Criteria criteria = session.createCriteria(Admin.class);
            criteria.add(Restrictions.eq("email", userName));
            
            return (Admin) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Admin getAdminByCredentials(String userName) {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Admin.class);
            criteria.add(Restrictions.eq("email", userName));

        

            return (Admin) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void updateAdmin(Admin adminDetails){
        Transaction transaction = null;
        try{
            Session session = SessionConfiguration.getSessionFactory().openSession();
             transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Admin.class);
            criteria.add(Restrictions.eq("email",adminDetails.getEmail()));
            Admin admin = (Admin) criteria.uniqueResult();

            admin.setFirstName(adminDetails.getFirstName());
            admin.setLastName(adminDetails.getLastName());
            session.update(admin);
            transaction.commit();
            
        }catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public void updatePassword(String password, String email) {
        Transaction transaction = null;
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Admin.class);
            criteria.add(Restrictions.eq("email",email));
            Admin admin = (Admin) criteria.uniqueResult();

            admin.setPassword(password);

            session.update(admin);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}