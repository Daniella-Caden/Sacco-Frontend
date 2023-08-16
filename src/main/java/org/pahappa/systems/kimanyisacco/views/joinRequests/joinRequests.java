package org.pahappa.systems.kimanyisacco.views.joinRequests;

import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.Interfaces.MemberService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.List;

@SessionScoped
@ManagedBean(name = "joins")
public class joinRequests {
    MemberService memberService = new MemberServiceImpl();
    public List<Member> viewJoin() throws IOException {

        return memberService.getJoinRequests();

    }
}
