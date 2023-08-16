package org.pahappa.systems.kimanyisacco.services.Interfaces;

import org.pahappa.systems.kimanyisacco.models.Account;

import java.util.List;

public interface AccountService {
     List<Account> getAccounts();

     double getTotalFunds();
}
