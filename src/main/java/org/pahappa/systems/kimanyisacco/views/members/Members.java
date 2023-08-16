package org.pahappa.systems.kimanyisacco.views.members;

import org.pahappa.systems.kimanyisacco.models.Account;
import org.pahappa.systems.kimanyisacco.services.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.Interfaces.AccountService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name = "members")
@SessionScoped
public class Members {
    private Account memberAccount;
    AccountService accountService = new AccountServiceImpl();
    public Account getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(Account memberAccount) {
        this.memberAccount = memberAccount;
    }
    public List<Account> viewMember(){

        return accountService.getAccounts();
    }

    public Members(){
        this.memberAccount= new Account();
    }
}
