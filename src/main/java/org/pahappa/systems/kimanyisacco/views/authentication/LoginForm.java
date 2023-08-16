package org.pahappa.systems.kimanyisacco.views.authentication;

import java.io.IOException;
import java.text.DecimalFormat;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ExternalContext;

import javax.servlet.http.HttpSession;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.models.Transaction;
import org.pahappa.systems.kimanyisacco.models.Admin;
import org.pahappa.systems.kimanyisacco.services.AccountServiceImpl;
import org.pahappa.systems.kimanyisacco.services.AdminServiceImpl;
import org.pahappa.systems.kimanyisacco.services.Interfaces.AccountService;
import org.pahappa.systems.kimanyisacco.services.Interfaces.AdminService;
import org.pahappa.systems.kimanyisacco.services.Interfaces.MemberService;
import org.pahappa.systems.kimanyisacco.services.Interfaces.TransactionService;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;
import org.pahappa.systems.kimanyisacco.services.TransactionServiceImpl;
import org.pahappa.systems.kimanyisacco.models.Account;

@ManagedBean(name = "loginForm")
@SessionScoped
public class LoginForm {
  private Admin adminObject;
  private Member member;
  private Member memberResult;
  private String formattedBalance;
  private String formattedTransactionType;
  private Transaction recentTransaction;
  private int totalDeposits;
  private int totalWithdraws;

  private int withdraws;
  private int joins;
  private int memberNumber;
  private Member m;
  private Admin adminResult;



  private double totalFunds;

  public int getWithdraws() {
    return withdraws;
  }

  public void setWithdraws(int withdraws) {
    this.withdraws = withdraws;
  }

  public int getJoins() {
    return joins;
  }

  public void setJoins(int joins) {
    this.joins = joins;
  }

  public int getMemberNumber() {
    return memberNumber;
  }

  public void setMemberNumber(int memberNumber) {
    this.memberNumber = memberNumber;
  }

  MemberService memberService = new MemberServiceImpl();
  TransactionService transactionService = new TransactionServiceImpl();
  AdminService adminService = new AdminServiceImpl();
  AccountService accountService = new AccountServiceImpl();

  public String getFormattedBalance() {
    return formattedBalance;
  }

  public void setFormattedBalance(String formattedBalance) {
    this.formattedBalance = formattedBalance;
  }

  public String getFormattedTransactionType() {
    return formattedTransactionType;
  }

  public void setFormattedTransactionType(String formattedTransactionType) {
    this.formattedBalance = formattedTransactionType;
  }

  public Member getMemberResult() {
    return memberResult;
  }

  public void setMemberResult(Member memberResult) {
    this.memberResult = memberResult;
  }

  public Admin getAdminResult() {
    return adminResult;
  }

  public void setAdminResult(Admin adminResult) {
    this.adminResult = adminResult;
  }

  public Transaction getRecentTransaction() {
    return recentTransaction;
  }

  public void setRecentTransaction(Transaction recentTransaction) {
    this.recentTransaction = recentTransaction;
  }

  private double accountBalance;

  public double getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(double accountBalance) {
    this.accountBalance = accountBalance;
  }

  public Member getM() {
    return m;
  }

  public void setM(Member m) {
    this.m = m;
  }

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public LoginForm() {

    this.member = new Member();
    this.memberResult = new Member();
    this.recentTransaction = new Transaction();
    this.m = new Member();
    this.adminObject = new Admin();

  }

  public void init() {
    this.member = new Member();
  }

  public void doLogin() throws IOException {

    boolean admin = false;
    System.out.println(member.getUserName());

    if (member.getUserName().equals("admin@kimwanyi.com")) {

       adminObject = adminService.getAdmin("admin@kimwanyi.com");

      if (adminObject == null) {
        adminService.registerAdmin("admin@kimwanyi.com");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);

        session.setAttribute("userName", member.getUserName());
        withdraws = transactionService.getWithdraws();
        joins = memberService.getJoins();
        memberNumber = memberService.getMember();
        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/admin/adminDashboard.xhtml");
      }

      else {
        adminResult = adminService.checkUserCredentials(member.getUserName(), member.getPassword());
        if (adminResult != null) {
          FacesContext facesContext = FacesContext.getCurrentInstance();
          ExternalContext externalContext = facesContext.getExternalContext();
          HttpSession session = (HttpSession) externalContext.getSession(true);

          session.setAttribute("userName", member.getUserName());
          withdraws = transactionService.getWithdraws();
          joins = memberService.getJoins();
          memberNumber = memberService.getMember();
          totalFunds = accountService.getTotalFunds();
          totalDeposits=transactionService.getTotalDeposits();
          totalWithdraws=transactionService.getTotalWithdraws();

          System.out.println(withdraws + " " + joins + " " + memberNumber);
          String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
          System.out.println("mybaseurl:" + context);
          FacesContext.getCurrentInstance().getExternalContext()
              .redirect(context + "/pages/admin/adminDashboard.xhtml");

        } else {
          FacesContext.getCurrentInstance().addMessage("growl",
              new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed",
                  "The username or password you entered is incorrect. Please try again"));
        }

      }

    } else {
      memberResult = memberService.checkUserCredentials(member.getUserName(), member.getPassword());
      if (memberResult != null) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);

        session.setAttribute("userName", member.getUserName());

        recentTransaction = transactionService.getRecent(memberResult.getUserName());
        Account account = transactionService.getAccountBalance(memberResult.getUserName());

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        formattedBalance = decimalFormat.format(account.getAccountBalance());
        formattedTransactionType = decimalFormat.format(recentTransaction.getAmount());

        System.out.println(formattedBalance);

        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/dashboard/Dashboard.xhtml");
      }

      else {
        FacesContext.getCurrentInstance().addMessage("growl",
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed",
                "The username or password you entered is incorrect. Please try again"));
      }
    }
  }

  public void adminDisplay() {

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(false);

    adminResult = adminService.checkUserCredentials(member.getUserName(), member.getPassword());
    // Retrieve the user's email from the session
    String userEmail = (String) session.getAttribute("userName");
    withdraws = transactionService.getWithdraws();
    joins = memberService.getJoins();
    memberNumber = memberService.getMember();
    totalFunds = accountService.getTotalFunds();
    totalDeposits=transactionService.getTotalDeposits();
    totalWithdraws=transactionService.getTotalWithdraws();

  }

  public void memberDisplay() {

    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(false);

    // Retrieve the user's email from the session
    String userEmail = (String) session.getAttribute("userName");

    m = memberService.getMemberByUsername(userEmail);
    Account account = transactionService.getAccountBalance(m.getUserName());
    recentTransaction = transactionService.getRecent(m.getUserName());

    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    formattedBalance = decimalFormat.format(account.getAccountBalance());
    formattedTransactionType = decimalFormat.format(recentTransaction.getAmount());

  }

  public void logOut() throws IOException {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(true);

    if (session != null) {
      session.invalidate();
      FacesContext.getCurrentInstance().addMessage("growl",
          new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout Successful",
              ""));

      String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
      System.out.println("mybaseurl:" + context);
      FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/log-in/home.xhtml");
    }

  }


  public void checkSessionValidity() throws IOException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpSession session = (HttpSession) externalContext.getSession(false);

    if (session == null || session.getAttribute("userName") == null) {
      String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
      System.out.println("mybaseurl:" + context);
      FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/log-in/loginForm.xhtml");

    }
  }

  public int getTotalDeposits() {
    return totalDeposits;
  }

  public void setTotalDeposits(int totalDeposits) {
    this.totalDeposits = totalDeposits;
  }

  public int getTotalWithdraws() {
    return totalWithdraws;
  }

  public void setTotalWithdraws(int totalWithdraws) {
    this.totalWithdraws = totalWithdraws;
  }

  public double getTotalFunds() {
    return totalFunds;
  }

  public void setTotalFunds(double totalFunds) {
    this.totalFunds = totalFunds;
  }
}
