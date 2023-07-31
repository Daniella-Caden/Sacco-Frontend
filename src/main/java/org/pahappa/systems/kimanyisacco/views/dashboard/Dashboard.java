package org.pahappa.systems.kimanyisacco.views.dashboard;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.pahappa.systems.kimanyisacco.models.Transactions;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;

import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.MemberImpl;
import org.pahappa.systems.kimanyisacco.services.TransactionsImpl;
@SessionScoped
@ManagedBean(name = "dashboard")
public class Dashboard {
    private Transactions trans = new Transactions();
    TransactionsImpl transImpl = new TransactionsImpl();
    MemberImpl memberImpl = new MemberImpl();
   private  List<Transactions> result;
    private  List<Transactions> history;
    private Members memberDetails;
    private Members updatedDetails;
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

    private boolean telephoneEditable = false;
      public boolean isTelephoneEditable() {
        return telephoneEditable;
    }

    public void setTelephoneEditable(boolean telephoneEditable) {
        this.telephoneEditable = telephoneEditable;
    }

    private boolean location = false;
    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
       this.location = location;
    }

    private boolean employmentDetailsEditable = false;
    public boolean isEmploymentDetailsEditable() {
        return employmentDetailsEditable;
    }

    public void setEmploymentDetailsEditable(boolean employmentDetailsEditable) {
        this.employmentDetailsEditable = employmentDetailsEditable;
    }

    private boolean incomeDetailsEditable = false;
    public boolean isIncomeDetailsEditable() {
        return incomeDetailsEditable;
    }

    public void setIncomeDetailsEditable(boolean incomeDetailsEditable) {
        this.incomeDetailsEditable = incomeDetailsEditable;
    }

    private boolean refereesEditable = false;

    public boolean isRefereesEditable() {
        return refereesEditable;
    }

    public void setRefereesEditable(boolean refereesEditable) {
        this.refereesEditable = refereesEditable;
    }

    public Members getUpdatedDetails() {
        return updatedDetails;
    }

    public void setUpdatedDetails(Members updatedDetails) {
        this.updatedDetails = updatedDetails;
    }

    public Members getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(Members memberDetails) {
        this.memberDetails = memberDetails;
    }

    public List<Transactions> getHistory() {
        return history;
    }

    public void setHistory(List<Transactions> history) {
        this.history = history;
    }

    private Date systemTime;

public List<Transactions> getResult() {
        
        return result;
    }

    public void setResult(List<Transactions> result) {
        this.result = result;
    }
    public Dashboard() {
        this.systemTime = new Date();
        this.trans=new Transactions();
        this.updatedDetails= new Members();
       
    }
    public Transactions getTrans(){
        return trans;
    }

 public void  setTrans(Transactions trans){
      this.trans=trans;
    }  


    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }


    public void showSession(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
ExternalContext externalContext = facesContext.getExternalContext();
HttpSession session = (HttpSession) externalContext.getSession(false);

// Retrieve the user's email from the session
String userEmail = (String) session.getAttribute("userName");
System.out.println(userEmail);
    }


    public void doTransaction() throws IOException {

       
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        
        // Retrieve the user's email from the session
        String userEmail = (String) session.getAttribute("userName");
    
        // Retrieve the associated Members entity from the database using the userEmail
        Members m = memberImpl.getMemberByUsername(userEmail);
        
    if(trans.getAmount()>0){
        // Make sure the member exists before proceeding
        if (m != null) {
            LocalDate localDateToStore = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = localDateToStore.format(dateFormatter);
            
            trans.setMember(m); // Set the associated Members entity
            trans.setCreatedOn(formattedDate);
            
            // if (trans.getTransactionType().equals("withdraw")) {
            //     trans.setStatus("PENDING");
            // } else {
            //     trans.setStatus("APPROVED");
            // }
            
           boolean check = transImpl.createTransaction(trans);
           System.out.println("CHECK IS "+check);

           if(check){
    
            String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            System.out.println("mybaseurl:" + context);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/dashboard/Dashboard.xhtml");}
            else{
                
                FacesContext.getCurrentInstance().addMessage("growl",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Withdraw Request Failed",
                    " You are requesting to withdraw an amount that is more than your account balance")); 
            }
        } else {
            
        }
    }
    else{
        FacesContext.getCurrentInstance().addMessage("growl",
        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Withdraw Request Failed",
            "Please enter a valid value"));
    }
    }

    public void viewProfile() throws IOException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(false);
        
        // Retrieve the user's email from the session
     String userEmail = (String) session.getAttribute("userName");

    memberDetails = memberImpl.getMemberByUsername(userEmail); 
String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            System.out.println("mybaseurl:" + context);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/dashboard/profile.xhtml");


        
    }


    public void updateMember()throws IOException{
        
     
        memberImpl.updateMember(memberDetails);

         String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:"+context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/dashboard/Dashboard.xhtml"); 

        
    }


    public void viewNotifications() throws IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");

        result = transImpl.getNotifications(userEmail);

        for(Transactions trans:result){
        System.out.println(trans.getNotifications());}


 String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            System.out.println("mybaseurl:" + context);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/dashboard/notifications.xhtml");

    }
    public void doWithdraw(Transactions trans) throws IOException{
        System.out.println(trans.getAmount());
        transImpl.updateWithdraw(trans);
        String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:"+context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/dashboard/Dashboard.xhtml");   
    
      } 

  public List<Transactions> viewTransactions()throws IOException{
 FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");

        return transImpl.getHistory(userEmail);

        //  String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        // System.out.println("mybaseurl:"+context);
        // FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/dashboard/history.xhtml");   
    


  }    

  public void changePassword() throws IOException{

     FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");
    memberImpl.changePassword(oldPassword,newPassword,confirmPassword,userEmail);
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Password changes saved successfully!", null);
        FacesContext.getCurrentInstance().addMessage(null, message);
        String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:"+context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/dashboard/profile.xhtml");  

  }
    
    

}
