package org.pahappa.systems.kimanyisacco.views.Transactions;

import org.pahappa.systems.kimanyisacco.constants.SaccoConstants;
import org.pahappa.systems.kimanyisacco.constants.TransactionType;
import org.pahappa.systems.kimanyisacco.models.Member;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;




import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;
import org.pahappa.systems.kimanyisacco.views.Notifications.Notifications;


@SessionScoped
    @ManagedBean(name = "transaction")
    public class Transactions {
        private Transaction trans = new Transaction();
    MemberServiceImpl memberServiceImpl = new MemberServiceImpl();
    TransactionService transactionService = new TransactionServiceImpl();
        public Transaction getTrans() {
            return trans;
        }

        public void setTrans(Transaction trans) {
            this.trans = trans;
        }
        public void doTransaction() throws IOException {
            if (trans.getAmount() > SaccoConstants.MIN_TRANSACTION_AMOUNT) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ExternalContext externalContext = facesContext.getExternalContext();
                HttpSession session = (HttpSession) externalContext.getSession(false);

                // Retrieve the user's email from the session
                String userName = (String) session.getAttribute("userName");

                // Retrieve the associated Member entity from the database using the userEmail
                Member m = memberServiceImpl.getMemberByUsername(userName);
                // Make sure the member exists before proceeding
                if (m != null) {
                    org.pahappa.systems.kimanyisacco.models.Transaction t = transactionService.getPending(userName);
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

                                Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "Deposit Successful",
                                        "You have successfully desposited UGX" + trans.getAmount());
                            } else {
                                Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "Withdrawal Request Submitted",
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
    }


