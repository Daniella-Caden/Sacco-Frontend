package org.pahappa.systems.kimanyisacco.views.Profile;

import org.pahappa.systems.kimanyisacco.models.Admin;
import org.pahappa.systems.kimanyisacco.services.AdminServiceImpl;
import org.pahappa.systems.kimanyisacco.services.Interfaces.AdminService;
import org.pahappa.systems.kimanyisacco.views.Notifications.Notifications;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ManagedBean(name = "adminProfile")
@SessionScoped
public class AdminProfile {
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
    AdminService adminService = new AdminServiceImpl();
    private Admin adminDetails;
    public Admin getAdminDetails() {
        return adminDetails;
    }

    public void setAdminDetails(Admin adminDetails) {
        this.adminDetails = adminDetails;
    }

    public void adminProfile() throws IOException {
        adminDetails = adminService.getAdmin("admin@kimwanyi.com");
        System.out.println(adminDetails.getEmail());
        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/admin/profile.xhtml");



    }

    public void adminPassword() throws IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");
        System.out.println(oldPassword);
        System.out.println(newPassword);
        System.out.println(confirmPassword);

        if(newPassword.equals(confirmPassword)){
            boolean passwordChangeSuccess = adminService.changePassword(oldPassword,newPassword,"admin@kimwanyi.com");
            System.out.println(passwordChangeSuccess);
            if(passwordChangeSuccess){

               Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "","Your password has been successfully updated.");
                String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                System.out.println("mybaseurl:"+context);
                FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/profile.xhtml");
            }

            else {
                Notifications.addFlashMessage(FacesMessage.SEVERITY_ERROR, "","You entered a wrong old password");
            }
        }

        else{
            Notifications.addFlashMessage(FacesMessage.SEVERITY_ERROR, "","The new password and confirmation password do not match. ");
        }
    }

    public void updateAdmin() throws IOException{
        boolean updateProfileSuccess = adminService.updateAdmin(adminDetails);

        if(updateProfileSuccess){
            Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "","Your profile has been successfully updated.");
            String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            System.out.println("mybaseurl:"+context);
            FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");}
    }
}
