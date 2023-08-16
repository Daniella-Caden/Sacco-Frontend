package org.pahappa.systems.kimanyisacco.daos;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.pahappa.systems.kimanyisacco.config.SessionConfiguration;
import org.pahappa.systems.kimanyisacco.constants.Status;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class MemberDAO {

    public boolean save(Member member) {
        Transaction transaction = null;
        boolean isSuccess = false;
        try {

            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Member memberEmail = getMemberByUsername(member.getEmail());
            if (memberEmail == null) {
                isSuccess = true;
                session.save(member);
                transaction.commit();

            }

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return isSuccess;
    }

    public List<Member> getAllMember() {

        Session session = SessionConfiguration.getSessionFactory().openSession();
        return session.createCriteria(Member.class).list();

    }

   

    public Member getMemberByUsername(String userName) {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Member.class);
            criteria.add(Restrictions.eq("userName", userName));

            return (Member) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Member> getJoinRequests() {

        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Member.class);
        criteria.add(Restrictions.eq("status", Status.PENDING));

        return criteria.list();

    }

    public List<Member> getMembers() {

        Session session = SessionConfiguration.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Member.class);
        criteria.add(Restrictions.eq("status", Status.APPROVED));

        return criteria.list();

    }

    public void updateStatus(String userName, Status status) {
        boolean updateSuccess = false;
        Transaction transaction = null;
        Session session = SessionConfiguration.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Member member = (Member) session.get(Member.class, userName);
        try {

            member.setStatus(status);

            session.update(member);
            updateSuccess = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (updateSuccess) {
            try {

                Account account = new Account();
                account.setMember(member);
                account.setAccountBalance(0);

                session.save(account);

            } catch (Exception e) {

                e.printStackTrace();

            }
        }
        try {
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public void updateMember(Member updatedDetails) {
        System.out.println("name" + updatedDetails.getUserName());
        Transaction transaction = null;

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Member member = (Member) session.get(Member.class, updatedDetails.getUserName());

            member.setLocation(updatedDetails.getLocation());

            member.setTelephoneContact(updatedDetails.getTelephoneContact());

            member.setCurrentEmployment(updatedDetails.getCurrentEmployment());
            member.setEmployerName(updatedDetails.getEmployerName());
            member.setEmployerPhoneNumber(updatedDetails.getEmployerPhoneNumber());
            member.setJobPosition(updatedDetails.getJobPosition());
            member.setMonthlySalary(updatedDetails.getMonthlySalary());
            member.setSourcesOfIncome(updatedDetails.getSourcesOfIncome());
            member.setRefereeName(updatedDetails.getRefereeName());
            member.setRefereePhoneNumber(updatedDetails.getRefereePhoneNumber());
            member.setRefereeJobPosition(updatedDetails.getRefereeJobPosition());

            session.update(member);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public boolean registerAdmin(Member adminMember) {
        boolean admin = true;
        Transaction transaction = null;

        try {

            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.save(adminMember);
            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }

        return admin;
    }

    public Member getMemberByCredentials(String userName) {

        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();

            Criteria criteria = session.createCriteria(Member.class);
            criteria.add(Restrictions.eq("userName", userName));

            Disjunction statusRestrictions = Restrictions.disjunction();
            statusRestrictions.add(Restrictions.eq("status", Status.APPROVED));

            criteria.add(statusRestrictions);

            return (Member) criteria.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void updatePassword(String password, String userName) {
        Transaction transaction = null;
        try {
            Session session = SessionConfiguration.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Member member = (Member) session.get(Member.class, userName);

            member.setPassword(password);

            session.update(member);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    // public Member getMemberByEmail(String email){

    // }

}
