 package org.pahappa.systems.kimanyisacco.views.adminDashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import java.io.Serializable;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import org.pahappa.systems.kimanyisacco.constants.Status;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Admin;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;
import org.pahappa.systems.kimanyisacco.services.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.Interfaces.*;
import org.pahappa.systems.kimanyisacco.services.AdminServiceImpl;
// import org.pahappa.systems.kimanyisacco.views.adminDashboard.MemberComparator;

@ManagedBean(name = "admin")
@SessionScoped
public class AdminDashboard implements Serializable{
private Member member;
private Member memberResult; 
private Member memberDetails;
private Admin adminDetails;
private Transaction memberTransaction;
private List<Member> members;
private List<Transaction> transact;
private PieChartModel pieModel;
private  String oldPassword;
private String newPassword;
private String confirmPassword;
private Account memberAccount;
public String getOldPassword() {
    return oldPassword;
}

public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
}

public String getNewPassword() {
    return newPassword;
}

public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
}

public String getConfirmPassword() {
    return confirmPassword;
}

public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
}

public PieChartModel getPieModel(){
  return pieModel;
}

public void setPieModel(PieChartModel pieModel){
  this.pieModel = pieModel;
}

public Transaction getMemberTransaction() {
  return memberTransaction;
}

@PostConstruct
public void init(){
  createPieModel();
}
public void setMemberTransaction(Transaction memberTransaction) {
  this.memberTransaction = memberTransaction;
}
public List<Transaction> getTransaction() {
  return transact;
}

public void setTransact(List<Transaction> transact) {
  this.transact = transact;
}
MemberService memberService = new MemberServiceImpl();
TransactionService transactionService = new TransactionServiceImpl();
AccountService accountService = new AccountServiceImpl();
AdminService adminService = new AdminServiceImpl();
public Member getMember(){
        return member;
    }

 public void  setMember(Member member){
      this.member=member;
    }   
    
    private List<Member> result; 
    private List<Transaction> withdraws; 

    public List<Transaction> getWithdraws() {
      return withdraws;
    }

    public void setWithdraws(List<Transaction> withdrawResult) {
      this.withdraws = withdrawResult;
    }

    public List<Member> getResult() {
        return result;
    }

    public void setResult(List<Member> result) {
        this.result = result;
    }
   

    public Member getMemberResult() {
      return memberResult;
  }

  public void setMemberResult(Member memberResult) {
      this.memberResult = memberResult;
  }

  public Account getMemberAccount() {
    return memberAccount;
}

public void setMemberAccount(Account memberAccount) {
    this.memberAccount = memberAccount;
}

  public Member getMemberDetails() {
    return memberDetails;
}

public void setMemberDetails(Member memberDetails) {
    this.memberDetails = memberDetails;
}
public Admin getAdminDetails() {
  return adminDetails;
}

public void setAdminDetails(Admin adminDetails) {
  this.adminDetails = adminDetails;
}
 
    public AdminDashboard(){
        this.member=new Member();
        this.memberResult=new Member();
        this.memberDetails = new Member();
        this.memberTransaction=new Transaction();
        this.memberAccount= new Account();
        this.adminDetails = new Admin();
        createPieModel();
        
    
    }
    private void createPieModel() {
      pieModel = new PieChartModel();
      ChartData data = new ChartData();

      PieChartDataSet dataSet = new PieChartDataSet();
      List<Number> values = new ArrayList<>();
      values.add(transactionService.getWithdrawType());
      values.add(transactionService.getDepositType());
     
      dataSet.setData(values);

      List<String> bgColors = new ArrayList<>();
      bgColors.add("rgb(54, 162, 235)");
      bgColors.add("rgb(255, 99, 132)");
      
      dataSet.setBackgroundColor(bgColors);

      data.addChartDataSet(dataSet);
      List<String> labels = new ArrayList<>();
      labels.add("Withdraws");
      labels.add("Deposits");
      
      data.setLabels(labels);

      pieModel.setData(data);
  }

  public List<Member> viewJoin() throws IOException{
    
    return memberService.getJoinRequests();
    
  }
  
  

   private void addFlashMessage(FacesMessage.Severity severity, String summary, String detail) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true);
    facesContext.addMessage("growl", new FacesMessage(severity, summary, detail));
  }
  public void Approve(String userName,String firstName) throws IOException{
    System.out.println(userName);
    memberService.updateStatus(userName,Status.APPROVED);
    memberService.sendApprovalEmail(userName,firstName);
    addFlashMessage(FacesMessage.SEVERITY_INFO, "Member Approved and Notified","The Memberhip application for the approved member has been successfully processed. An email notification has been sent to the member with further details");
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");   

  }

  public void Reject(String userName,String firstName) throws IOException{
    System.out.println(userName);
    memberService.updateStatus(userName,Status.REJECTED);
    memberService.sendRejectionEmail(userName,firstName);
    addFlashMessage(FacesMessage.SEVERITY_INFO, "Member Status.REJECTED","The Memberhip application for the Status.REJECTED member has been processed. An email notification has been sent to the member informing them about the rejection");
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");   

  }

   String userEmail;
    public void adminProfile() throws IOException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(false);
        
        // Retrieve the user's email from the session
    userEmail = (String) session.getAttribute("userName");

    adminDetails = adminService.getAdmin("admin@kimwanyi.com");
    System.out.println(adminDetails.getEmail());
String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            System.out.println("mybaseurl:" + context);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/admin/profile.xhtml");


        
    }

    public void adminPassword() throws IOException{
      FacesContext facesContext = FacesContext.getCurrentInstance();
         ExternalContext externalContext = facesContext.getExternalContext();
         HttpSession session = (HttpSession) externalContext.getSession(false);
         String userEmail = (String) session.getAttribute("userName");
         System.out.println(oldPassword);
        System.out.println(newPassword);
        System.out.println(confirmPassword);

        if(newPassword.equals(confirmPassword)){
     boolean passwordChangeSuccess = adminService.changePassword(oldPassword,newPassword,"admin@kimwanyi.com");
    System.out.println(passwordChangeSuccess);
     if(passwordChangeSuccess){

      addFlashMessage(FacesMessage.SEVERITY_INFO, "","Your password has been successfully updated.");
      String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
         System.out.println("mybaseurl:"+context);
         FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/profile.xhtml");
         }

         else {
          addFlashMessage(FacesMessage.SEVERITY_ERROR, "","You entered a wrong old password");
         }
        }

        else{
          addFlashMessage(FacesMessage.SEVERITY_ERROR, "","The new password and confirmation password do not match. ");
        }
   }

  public List<Transaction> viewRequests() throws IOException{
    
   return transactionService.getWithdrawalRequests();
    
    
      
  }
public double getAccountBalance(String userName){
  Account account= transactionService.getAccountBalance(userName);
  return account.getAccountBalance();
}
  public void viewWithdrawal(Transaction memberTransaction) throws IOException{
    this.memberTransaction = memberTransaction;
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/approveWithdrawals.xhtml");

  }

  public void updateAdmin() throws IOException{
  boolean updateProfileSuccess = adminService.updateAdmin(adminDetails);

  if(updateProfileSuccess){
    addFlashMessage(FacesMessage.SEVERITY_INFO, "","Your profile has been successfully updated.");
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
   System.out.println("mybaseurl:"+context);
   FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");}
  }

  public void approveWithdrawal(String userName,long id,String decision) throws IOException{
    System.out.println(userName);
    transactionService.updateStatus(id,decision);
    if(decision.equals("APPROVE")){
     addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Approved","The withdrawal request has been successfully approved.");}
     else{
       addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Status.REJECTED","The withdrawal request has been Status.REJECTED.");
     }
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");   

  }

public List<Account> viewMember(){

  return accountService.getAccounts();
}


public void memberView(Member memberResult) throws IOException{
  this.memberResult=memberResult;
  
  String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
  System.out.println("mybaseurl:"+context);
  FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/memberDetails.xhtml");
}


public List<Transaction> viewTransactions(String userName){
  return transactionService.getHistory(userName);
}



  






 }






 