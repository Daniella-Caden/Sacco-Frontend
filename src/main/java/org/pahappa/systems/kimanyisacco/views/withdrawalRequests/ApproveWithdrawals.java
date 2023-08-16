package org.pahappa.systems.kimanyisacco.views.withdrawalRequests;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;
import org.pahappa.systems.kimanyisacco.views.Notifications.Notifications;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.io.IOException;

@SessionScoped
@ManagedBean(name = "approveWithdraws")
public class ApproveWithdrawals {
    TransactionService transactionService = new TransactionServiceImpl();
    private Transaction memberTransaction;
    public Transaction getMemberTransaction() {
        return memberTransaction;
    }
    public void setMemberTransaction(Transaction memberTransaction) {
        this.memberTransaction = memberTransaction;
    }

    public void WithdrawalRequests(){
        this.memberTransaction=new Transaction();
    }


    public void approveWithdrawal(String userName,long id,String decision) throws IOException {
        System.out.println(userName);
        transactionService.updateStatus(id,decision);
        if(decision.equals("APPROVE")){
            Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Approved","The withdrawal request has been successfully approved.");}
        else{
            Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Status.REJECTED","The withdrawal request has been Status.REJECTED.");
        }
        String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:"+context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");

    }

    public double getAccountBalance(String userName){
        Account account= transactionService.getAccountBalance(userName);
        return account.getAccountBalance();
    }

    public void ApproveWithdrawals(){
        this.memberTransaction=new Transaction();
    }



}
