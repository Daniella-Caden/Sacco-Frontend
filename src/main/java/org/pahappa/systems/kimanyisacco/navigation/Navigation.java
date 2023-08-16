package org.pahappa.systems.kimanyisacco.navigation;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Contains the links to the different pages with in the application.
 * It is to help us navigate between the pages in the application easily.
 */
@ManagedBean(name = "navigation")
@ApplicationScoped // There should be only one instance of the class created for the entire
                   // application
public class Navigation {

    private final String dashboard = "/pages/dashboard/Dashboard.xhtml";

    private final String landing = "/pages/landing/Landing.xhtml";
    private final String home = "/pages/log-in/home.xhtml";
    private final String approve = "/pages/admin/approve.xhtml";
    private final String apply = "/pages/log-in/apply.xhtml";
    private final String about = "/pages/log-in/aboutUs.xhtml";
    private final String memberEligibility = "/pages/log-in/member.xhtml";
    private final String contactUs = "/pages/log-in/contactUs.xhtml";
    private final String logIn = "/pages/log-in/loginForm.xhtml";
    private final String history = "/pages/dashboard/history.xhtml";
    private final String notifications = "/pages/dashboard/notifications.xhtml";
    private final String profile = "/pages/dashboard/profile.xhtml";
    private final String transaction = "/pages/dashboard/transaction.xhtml";
    private final String adminDashboard = "/pages/admin/adminDashboard.xhtml";
    private final String adminReports = "/pages/admin/adminReports.xhtml";
    private final String approveWithdraw = "/pages/admin/approveWithdrawals.xhtml";
    private final String joins = "/pages/admin/joinRequests.xhtml";
    private final String memberDetails = "/pages/admin/memberDetails.xhtml";
    private final String members = "/pages/admin/members.xhtml";
    private final String withdraws = "/pages/admin/withdrawalRequests.xhtml";

    public String getApprove() {
        return approve;
    }

    public String getDashboard() {
        return dashboard;
    }

    public String getLanding() {
        return landing;
    }

    public String getHome() {
        return home;
    }

    private final int notificationsCount = 3;

    public int getNotificationsCount() {
        return notificationsCount;
    }

    public String getApply() {
        return apply;
    }

    public String getAbout() {
        return about;
    }

    public String getMemberEligibility() {
        return memberEligibility;
    }

    public String getContactUs() {
        return contactUs;
    }

    public String getLogIn() {
        return logIn;
    }

    public String getHistory() {
        return history;
    }

    public String getNotifications() {
        return notifications;
    }

    public String getProfile() {
        return profile;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getAdminDashboard() {
        return adminDashboard;
    }

    public String getAdminReports() {
        return adminReports;
    }

    public String getApproveWithdraw() {
        return approveWithdraw;
    }

    public String getJoins() {
        return joins;
    }

    public String getMemberDetails() {
        return memberDetails;
    }

    public String getMembers() {
        return members;
    }

    public String getWithdraws() {
        return withdraws;
    }
}
