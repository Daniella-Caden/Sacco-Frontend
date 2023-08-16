package org.pahappa.systems.kimanyisacco.views.Profile;

import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.views.Notifications.Notifications;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@SessionScoped
@ManagedBean(name = "memberProfile")
public class MemberProfile {
    MemberServiceImpl memberServiceImpl = new MemberServiceImpl();
    private Member memberDetails = new Member();
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
    public Member getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(Member memberDetails) {
        this.memberDetails = memberDetails;
    }
    public void viewProfile() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);

        // Retrieve the user's email from the session
      String  userEmail = (String) session.getAttribute("userName");

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

    public void changePassword() throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(false);
        String userEmail = (String) session.getAttribute("userName");

        if (newPassword.equals(confirmPassword)) {
            boolean passwordChangeSuccess = memberServiceImpl.changePassword(oldPassword, newPassword, userEmail);
            if (passwordChangeSuccess) {
                Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "", "Your password has been successfully updated.");
                String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                System.out.println("mybaseurl:" + context);
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(context + "/pages/dashboard/profile.xhtml");
            }

            else {
                Notifications.addFlashMessage(FacesMessage.SEVERITY_ERROR, "", "You entered a wrong old password");
            }
        }

        else {
            Notifications.addFlashMessage(FacesMessage.SEVERITY_ERROR, "",
                    "The new password and confirmation password do not match. ");
        }
    }
}
