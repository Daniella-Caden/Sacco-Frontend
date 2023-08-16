package org.pahappa.systems.kimanyisacco.views.History;

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
@ManagedBean(name = "history")
public class History {
    TransactionService transactionService = new TransactionServiceImpl();
    public List<Transaction> viewTransaction() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");

        return transactionService.getHistory(userEmail);


    }
}
