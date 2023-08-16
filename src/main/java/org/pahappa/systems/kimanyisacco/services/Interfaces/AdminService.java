package org.pahappa.systems.kimanyisacco.services.Interfaces;

import org.pahappa.systems.kimanyisacco.models.Admin;

public interface AdminService {
    boolean registerAdmin(String userName);

    Admin getAdmin(String userName);

    Admin checkUserCredentials(String userName, String password);

    boolean updateAdmin(Admin adminDetails);

    boolean changePassword(String old, String newPass, String email);
}
