package org.pahappa.systems.kimanyisacco.services;




import java.io.UnsupportedEncodingException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.daos.AddMember;
import org.pahappa.systems.kimanyisacco.daos.AddUser;
import org.pahappa.systems.kimanyisacco.models.Members;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;



public class MemberImpl  {
   
   AddMember memberDAO = new AddMember();
    
    public boolean createMember(Members member) {


        if (member.getPassword() == null || member.getPassword().trim().isEmpty()) {
            String hashedPassword = hashPassword(member.getEmail());
            // If password is empty or null, set it to the generated ID
            member.setPassword(hashedPassword);
        }

        if (member.getUserName() == null || member.getUserName().trim().isEmpty()) {
            // If password is empty or null, set it to the generated ID
            member.setUserName(member.getEmail());
        }

        if (member.getStatus() == null || member.getStatus().trim().isEmpty()) {
            // If password is empty or null, set it to the generated ID
            member.setStatus("PENDING");
        }

        member.setAccountBalance(0);
        
      return  memberDAO.save(member);

        
      
    }
public static String hashPassword(String plainPassword) {
        // Generate a salt and hash the password
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(plainPassword, salt);
        return hashedPassword;
    }


    public List<Members> getAllMembers(){
        return memberDAO.getAllMembers();
    }

public List<Members> getJoinRequests(){
        return memberDAO.getJoinRequests();
    }

    public void sendApprovalEmail(String recipientEmail,String firstName) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        // Set up the session with the authentication details
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("caden.wwdd@gmail.com", "tlipzljibdhzptke");
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("caden.wwdd@gmail.com","Kimwanyi SACCO"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Kimwanyi SACCO Membership Approval");
            message.setText( "Dear "+ firstName + ",\n\n" +
            "We are delighted to inform you that your membership application to Kimwanyi SACCO has been approved!\n\n" +
            "Here are your login credentials:\n\n" +
            "Username: " + recipientEmail + "\n" +
            "Temporary Password: " + recipientEmail + "\n\n" +
            "Please use the provided credentials to log in to your account. For security purposes, we recommend that you change your password after your first login.\n\n" +
            "Thank you for joining Kimwanyi SACCO. We look forward to serving you.\n\n" +
            "Best regards,\n" +
            "Kimwanyi SACCO Team");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        }
    }
public boolean registerAdmin(String userName){
     String hashedPassword = hashPassword(userName);
            // If password is empty or null, set it to the generated ID
    Members adminMember = new Members();
    adminMember.setPassword(hashedPassword); adminMember.setUserName(userName);
     adminMember.setLocation(userName);
adminMember.setDateOfBirth(userName);
adminMember.setFirstName(userName);
adminMember.setLastName(userName);
adminMember.setEmail(userName);
adminMember.setStatus(userName);
adminMember.setAccountBalance(0);
adminMember.setGender(userName);
adminMember.setTelephoneContact(userName);

adminMember.setCurrentEmployment(userName);
adminMember.setEmployerName(userName);
adminMember.setEmployerPhoneNumber(userName);
adminMember.setJobPosition(userName);
adminMember.setMonthlySalary(0);
adminMember.setSourcesOfIncome(userName);
adminMember.setRefereeName(userName);
adminMember.setRefereePhoneNumber(userName);
adminMember.setRefereeJobPosition(userName);


    return memberDAO.registerAdmin(adminMember);
}
    public void sendRejectionEmail(String recipientEmail,String firstName) {
        // Configure the email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        // Set up the session with the authentication details
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("caden.wwdd@gmail.com", "tlipzljibdhzptke");
            }
        });

        try {
            // Create a new message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("caden.wwdd@gmail.com","Kimwanyi SACCO"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Kimwanyi SACCO Membership Approval");
            message.setText( "Dear "+ firstName + ",\n\n" +
            "We regret to inform you that your membership application to Kimwanyi SACCO has been declined!\n\n Kimwanyi SACCO Team");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Handle the exception if the email sending fails
        }
    }
    
    public void updateAdmin(Members memberDetails){
        memberDAO.updateMember(memberDetails);

    }


public Members getMemberByUsername(String userName){
    return memberDAO.getMemberByUsername(userName);
}
 public void updateStatus(String userName,String status){
    memberDAO.updateStatus(userName,status);
 }  

 public void updateMember(Members updatedDetails){
    memberDAO.updateMember(updatedDetails);
 }  
 
 public Members checkUserCredentials(String userName,String password){
    Members memberByCredentials = memberDAO.getMemberByCredentials(userName);

    if(memberByCredentials!=null){
    String storedPassword = memberByCredentials.getPassword();
    boolean passwordMatches = BCrypt.checkpw(password, storedPassword);
  
    if(passwordMatches){
       return memberByCredentials;
    }


    
}


return null;
   
 }

 public void changePassword(String old,String newPass,String confirm,String userName){

    Members passwordCheck = memberDAO.getMemberByCredentials(userName);
String storedPassword = passwordCheck.getPassword();
    boolean passwordMatches = BCrypt.checkpw(old, storedPassword);
    if(passwordMatches){
        if(newPass.equals(confirm))
        {
             String hashedPassword = hashPassword(newPass);
            // If password is empty or null, set it to the generated ID
            memberDAO.updatePassword(hashedPassword,userName);
        }
        else{
            System.out.println("Different");
        }
        System.out.println("Done");
    }


 }

 
 
 public int getJoins(){
return (memberDAO.getJoinRequests()).size();
 }
    
public int getMembers(){

    return (memberDAO.getMembers()).size();

}

public List<Members> getMember(){
    return memberDAO.getMembers();
}

}
