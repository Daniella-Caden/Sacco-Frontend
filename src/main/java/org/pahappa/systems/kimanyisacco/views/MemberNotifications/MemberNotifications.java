package org.pahappa.systems.kimanyisacco.views.MemberNotifications;

import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@SessionScoped
@ManagedBean(name = "notifications")
public class MemberNotifications {
    TransactionService transactionService = new TransactionServiceImpl();
    private List<Transaction> result;
    public List<Transaction> getResult() {

        return result;
    }

    public void setResult(List<Transaction> result) {
        this.result = result;
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
}
