 package org.pahappa.systems.kimanyisacco.views.adminDashboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.SortOrder;
import org.primefaces.model.SortMeta;
import java.util.Collections;
import java.io.Serializable;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import org.pahappa.systems.kimanyisacco.controllers.HyperLinks;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.models.Transactions;
import org.pahappa.systems.kimanyisacco.services.MemberImpl;
import org.pahappa.systems.kimanyisacco.services.TransactionsImpl;
import org.pahappa.systems.kimanyisacco.services.UserImpl;
// import org.pahappa.systems.kimanyisacco.views.adminDashboard.MemberComparator;

@ManagedBean(name = "admin")
@SessionScoped
public class AdminDashboard implements Serializable{
private Members member;
private Members memberResult; 
private Members memberDetails;
private Transactions memberTransaction;
private List<Members> members;
private List<Transactions> transact;
private PieChartModel pieModel;
private  String oldPassword;
private String newPassword;
private String confirmPassword;
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

public Transactions getMemberTransaction() {
  return memberTransaction;
}

@PostConstruct
public void init(){
  createPieModel();
}
public void setMemberTransactions(Transactions memberTransactions) {
  this.memberTransaction = memberTransaction;
}
public List<Transactions> getTransactions() {
  return transact;
}

public void setTransact(List<Transactions> transact) {
  this.transact = transact;
}
MemberImpl memberImpl = new MemberImpl();
TransactionsImpl transImpl = new TransactionsImpl();
public Members getMember(){
        return member;
    }

 public void  setMembers(Members member){
      this.member=member;
    }   
    // Add this field to hold the result
    private List<Members> result; 
    private List<Transactions> withdraws;// Add this property

    public List<Transactions> getWithdraws() {
      return withdraws;
    }

    public void setWithdraws(List<Transactions> withdrawResult) {
      this.withdraws = withdrawResult;
    }

    public List<Members> getResult() {
        return result;
    }

    public void setResult(List<Members> result) {
        this.result = result;
    }
   

    public Members getMemberResult() {
      return memberResult;
  }

  public void setMemberResult(Members MemberResult) {
      this.memberResult = memberResult;
  }
  public Members getMemberDetails() {
    return memberDetails;
}

public void setMemberDetails(Members memberDetails) {
    this.memberDetails = memberDetails;
}

 
    public AdminDashboard(){
        this.member=new Members();
        this.memberResult=new Members();
        this.memberDetails = new Members();
        this.memberTransaction=new Transactions();
        createPieModel();
        
    
    }
    private void createPieModel() {
      pieModel = new PieChartModel();
      ChartData data = new ChartData();

      PieChartDataSet dataSet = new PieChartDataSet();
      List<Number> values = new ArrayList<>();
      values.add(transImpl.getWithdrawType());
      values.add(transImpl.getDepositType());
     
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

  public List<Members> viewJoin() throws IOException{
    
    return memberImpl.getJoinRequests(); 
    
  }
  
  
  public void doApprove(Members memberResult)throws IOException{
    this.memberResult = memberResult;
    
    System.out.println("Name"+ memberResult.getFirstName());
    System.out.println("Name"+ memberResult.getStatus());

    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/approve.xhtml");
  }

   private void addFlashMessage(FacesMessage.Severity severity, String summary, String detail) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true);
    facesContext.addMessage("growl", new FacesMessage(severity, summary, detail));
  }
  public void Approve(String userName,String firstName) throws IOException{
    System.out.println(userName);
    memberImpl.updateStatus(userName,"APPROVED");
    memberImpl.sendApprovalEmail(userName,firstName);
    addFlashMessage(FacesMessage.SEVERITY_INFO, "Member Approved and Notified","The membership application for the approved member has been successfully processed. An email notification has been sent to the member with further details");
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");   

  }

  public void Reject(String userName,String firstName) throws IOException{
    System.out.println(userName);
    memberImpl.updateStatus(userName,"REJECTED");
    memberImpl.sendRejectionEmail(userName,firstName);
    addFlashMessage(FacesMessage.SEVERITY_INFO, "Member Rejected","The membership application for the rejected member has been processed. An email notification has been sent to the member informing them about the rejection");
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

    memberDetails = memberImpl.getMemberByUsername(userEmail); 
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
      memberImpl.changePassword(oldPassword,newPassword,confirmPassword,"admin@kimwanyi.com");
     FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password changes saved successfully!", null);
         FacesContext.getCurrentInstance().addMessage(null, message);
       String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
         System.out.println("mybaseurl:"+context);
         FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/profile.xhtml"); 
   }

  public List<Transactions> viewRequests() throws IOException{
    
   return transImpl.getWithdrawalRequests();
    
    
    // String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    // System.out.println("mybaseurl:"+context);
    // FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/withdrawalRequests.xhtml");

// for(Transactions t:withdraws){
//     System.out.println(t.getMember().getFirstName());
//      System.out.println(t.getCreatedOn());

// }   
  }

  public void viewWithdrawal(Transactions memberTransaction) throws IOException{
    this.memberTransaction = memberTransaction;
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/approveWithdrawals.xhtml");

  }

  public void updateAdmin() throws IOException{
    memberImpl.updateAdmin(memberDetails);

    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
   System.out.println("mybaseurl:"+context);
   FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");
  }

  public void approveWithdrawal(String userName,int id,String decision) throws IOException{
    System.out.println(userName);
    transImpl.updateStatus(id,decision);
    if(decision.equals("APPROVE")){
     addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Approved","The withdrawal request has been successfully approved.");}
     else{
       addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Rejected","The withdrawal request has been rejected.");
     }
    String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:"+context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");   

  }

public List<Members> viewMembers(){
if(members==null){
  members= memberImpl.getMember();}
 return members;
}

public void memberView(Members memberResult) throws IOException{
  this.memberResult=memberResult;
  
  String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
  System.out.println("mybaseurl:"+context);
  FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/memberDetails.xhtml");
}


public List<Transactions> viewTransactions(String userName){
  return transImpl.getHistory(userName);
}



  






 }






 