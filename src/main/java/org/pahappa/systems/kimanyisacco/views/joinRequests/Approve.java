package org.pahappa.systems.kimanyisacco.views.joinRequests;

import org.pahappa.systems.kimanyisacco.constants.Status;
import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.Interfaces.MemberService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.views.Notifications.Notifications;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.io.IOException;

@SessionScoped
@ManagedBean(name = "approve")
public class Approve {
    MemberService memberService = new MemberServiceImpl();
    public Member getMemberResult() {
        return memberResult;
    }

    public void setMemberResult(Member memberResult) {
        this.memberResult = memberResult;
    }

    private Member memberResult;

    public Approve(){

        this.memberResult=new Member();
    }


    public void Approve(String userName,String firstName) throws IOException {
        System.out.println(userName);
        memberService.updateStatus(userName, Status.APPROVED);
        memberService.sendApprovalEmail(userName,firstName);
        Notifications.addFlashMessage(FacesMessage.SEVERITY_INFO, "Member Approved and Notified","The Memberhip application for the approved member has been successfully processed. An email notification has been sent to the member with further details");
        String context= FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:"+context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/admin/adminDashboard.xhtml");

    }
}
