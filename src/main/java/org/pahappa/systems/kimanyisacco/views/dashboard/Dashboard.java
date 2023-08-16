package org.pahappa.systems.kimanyisacco.views.dashboard;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

import org.pahappa.systems.kimanyisacco.models.Transaction;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;

import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;

@SessionScoped
@ManagedBean(name = "dashboard")
public class Dashboard {
    private Transaction trans = new Transaction();
    TransactionService transactionService = new TransactionServiceImpl();
    MemberServiceImpl memberServiceImpl = new MemberServiceImpl();
    private List<Transaction> result;
    private List<Transaction> history;
    private Member memberDetails;
    private Member updatedDetails;
    private String oldPassword;
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

    public Member getUpdatedDetails() {
        return updatedDetails;
    }

    public void setUpdatedDetails(Member updatedDetails) {
        this.updatedDetails = updatedDetails;
    }

    public Member getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(Member memberDetails) {
        this.memberDetails = memberDetails;
    }

    public List<Transaction> getHistory() {
        return history;
    }

    public void setHistory(List<Transaction> history) {
        this.history = history;
    }

    private Date systemTime;

    public List<Transaction> getResult() {

        return result;
    }

    public void setResult(List<Transaction> result) {
        this.result = result;
    }

    public Dashboard() {
        this.systemTime = new Date();
        this.trans = new Transaction();
        this.updatedDetails = new Member();

    }

    public Transaction getTrans() {
        return trans;
    }

    public void setTrans(Transaction trans) {
        this.trans = trans;
    }

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }

    public void showSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        // Retrieve the user's email from the session
        String userEmail = (String) session.getAttribute("userName");
        System.out.println(userEmail);
    }

    public void doTransaction() throws IOException {
        if (trans.getAmount() > 499) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpSession session = (HttpSession) externalContext.getSession(false);

            // Retrieve the user's email from the session
            String userName = (String) session.getAttribute("userName");

            // Retrieve the associated Member entity from the database using the userEmail
            Member m = memberServiceImpl.getMemberByUsername(userName);
            // Make sure the member exists before proceeding
            if (m != null) {
                Transaction t = transactionService.getPending(userName);
                // System.out.println(t.getAmount());
                if ((trans.getTransactionType() == TransactionType.WITHDRAW && (t == null))
                        || trans.getTransactionType() == TransactionType.DEPOSIT) {
                    LocalDate localDateToStore = LocalDate.now();
                    // DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    // String formattedDate = localDateToStore.format(dateFormatter);

                    trans.setMember(m); // Set the associated Member entity
                    trans.setCreatedOn(localDateToStore);

                    boolean check = transactionService.createTransaction(trans);
                    System.out.println("CHECK IS " + check);

                    if (check) {

                        if (trans.getTransactionType() == TransactionType.DEPOSIT) {

                            addFlashMessage(FacesMessage.SEVERITY_INFO, "Deposit Successful",
                                    "You have successfully desposited UGX" + trans.getAmount());
                        } else {
                            addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Submitted",
                                    "Please wait for the request to be approved. You will receive a notification once the request is processed.");

                        }
                        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                        System.out.println("mybaseurl:" + context);
                        FacesContext.getCurrentInstance().getExternalContext()
                                .redirect(context + "/pages/dashboard/Dashboard.xhtml");
                    }

                    else {
                        FacesContext.getCurrentInstance().addMessage("growl",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Withdraw Request Failed",
                                        " You are requesting to withdraw an amount that is more than your account balance"));
                    }

                } else {

                    FacesContext.getCurrentInstance().addMessage("growl",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Withdraw Request Failed",
                                    " You still have a pending withdraw request"));

                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Transaction failed",
                            "Please an amount of UGX 500 and above"));
        }
    }

    private void addFlashMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = facesContext.getExternalContext().getFlash();
        flash.setKeepMessages(true);
        facesContext.addMessage("growl", new FacesMessage(severity, summary, detail));
    }

    String userEmail;

    public void viewProfile() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        // Retrieve the user's email from the session
        userEmail = (String) session.getAttribute("userName");

        memberDetails = memberServiceImpl.getMemberByUsername(userEmail);
        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/dashboard/profile.xhtml");

    }

    public void updateMember() throws IOException {

        memberServiceImpl.updateMember(memberDetails);

        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/dashboard/Dashboard.xhtml");

    }

    public void viewNotifications() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");

        result = transactionService.getNotifications(userEmail);

        for (Transaction trans : result) {
            System.out.println(trans.getNotifications());
        }

        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext()
                .redirect(context + "/pages/dashboard/notifications.xhtml");

    }

    public void doWithdraw(Transaction trans) throws IOException {
        System.out.println(trans.getAmount());
        transactionService.updateWithdraw(trans);
        addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Successful",
                "You have successfully withdrawn UGX" + trans.getAmount());
        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/dashboard/Dashboard.xhtml");

    }

    public List<Transaction> viewTransaction() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");

        return transactionService.getHistory(userEmail);

        // String context=
        // FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        // System.out.println("mybaseurl:"+context);
        // FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/dashboard/history.xhtml");

    }

    public void changePassword() throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");

        if (newPassword.equals(confirmPassword)) {
            boolean passwordChangeSuccess = memberServiceImpl.changePassword(oldPassword, newPassword, userEmail);
            if (passwordChangeSuccess) {
                addFlashMessage(FacesMessage.SEVERITY_INFO, "", "Your password has been successfully updated.");
                String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                System.out.println("mybaseurl:" + context);
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(context + "/pages/dashboard/profile.xhtml");
            }

            else {
                addFlashMessage(FacesMessage.SEVERITY_ERROR, "", "You entered a wrong old password");
            }
        }

        else {
            addFlashMessage(FacesMessage.SEVERITY_ERROR, "",
                    "The new password and confirmation password do not match. ");
        }
    }

}
