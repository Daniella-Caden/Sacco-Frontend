package org.pahappa.systems.kimanyisacco.services.Interfaces;

import org.pahappa.systems.kimanyisacco.constants.Status;
import org.pahappa.systems.kimanyisacco.models.Member;

import java.util.List;

public interface MemberService {

    int getJoins();


    List<Member> getJoinRequests();

    void updateStatus(String userName, Status status);

    void sendApprovalEmail(String userName, String firstName);

    void sendRejectionEmail(String userName, String firstName);

    Member getMemberByUsername(String userName);


    void updateMember(Member member);

    boolean changePassword(String oldPassword, String newPassword, String userName);

    int getMember();

    Member checkUserCredentials(String userName, String password);
}
