package org.pahappa.systems.kimanyisacco.services;

import org.pahappa.systems.kimanyisacco.models.Admin;
import org.pahappa.systems.kimanyisacco.daos.AdminDAO;
import org.mindrot.jbcrypt.BCrypt;
import org.pahappa.systems.kimanyisacco.services.Interfaces.AdminService;

public class AdminServiceImpl implements AdminService {
    AdminDAO adminDAO = new AdminDAO();

    public boolean registerAdmin(String userName){
        Admin admin = new Admin();
        String hashedPassword = hashPassword(userName);
        admin.setPassword(hashedPassword);
        admin.setEmail(userName);
       return adminDAO.registerAdmin(admin);
    }

    public static String hashPassword(String plainPassword) {
        // Generate a salt and hash the password
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(plainPassword, salt);
        return hashedPassword;
    }

    public Admin getAdmin(String userName){
        return adminDAO.getAdmin(userName);
    }

    
    public Admin checkUserCredentials(String userName, String password) {
        Admin adminByCredentials = adminDAO.getAdminByCredentials(userName);

        if (adminByCredentials != null) {
            String storedPassword = adminByCredentials.getPassword();
            boolean passwordMatches = BCrypt.checkpw(password, storedPassword);

            if (passwordMatches) {
                return adminByCredentials;
            }

        }

        return null;

    }

    public boolean updateAdmin(Admin adminDetails) {
      boolean  updateProfileSuccess = true;
        adminDAO.updateAdmin(adminDetails);
        return updateProfileSuccess;

    }

    public boolean changePassword(String old, String newPass,String email) {

        boolean passwordChangeSuccess = false;
        Admin passwordCheck = adminDAO.getAdmin(email);
        String storedPassword = passwordCheck.getPassword();
        boolean passwordMatches = BCrypt.checkpw(old, storedPassword);
        if (passwordMatches) {
            
            String hashedPassword = hashPassword(newPass);
                // If password is empty or null, set it to the generated ID
            adminDAO.updatePassword(hashedPassword,email);
            passwordChangeSuccess=true;

            
            
            
        }

        return passwordChangeSuccess;

    }
}