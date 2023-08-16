package org.pahappa.systems.kimanyisacco.views.registration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.pahappa.systems.kimanyisacco.models.Member;
import org.pahappa.systems.kimanyisacco.services.MemberServiceImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name = "registration")
@ViewScoped
public class Registration {
  private Date minDate;
  private Date maxDate;
  private String range;
  private Date orignaldateOfBirth;
  private boolean success;
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  public boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Date getOrignaldateOfBirth() {
    return orignaldateOfBirth;
  }

  public void setOrignaldateOfBirth(Date orignaldateOfBirth) {
    this.orignaldateOfBirth = orignaldateOfBirth;
  }

  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  public Date getMinDate() {
    return minDate;
  }

  public void setMinDate(Date minDate) {
    this.minDate = minDate;
  }

  public Date getMaxDate() {
    return maxDate;
  }

  public void setMaxDate(Date maxDate) {
    this.maxDate = maxDate;
  }

  private Member member;
  MemberServiceImpl memberServiceImpl = new MemberServiceImpl();

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public Registration() {
    this.member = new Member();
    LocalDate localMaxDate = LocalDate.now().minusYears(85);
    LocalDate localMinDate = LocalDate.now().minusYears(18);

    range = Integer.toString(localMaxDate.getYear()).concat(":").concat(Integer.toString(localMinDate.getYear()));

  }

  public static boolean isValidEmail(String email) {
    Pattern pattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  private void addFlashMessage(FacesMessage.Severity severity, String summary, String detail) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true);
    facesContext.addMessage("growl", new FacesMessage(severity, summary, detail));
  }

  public void doRegistration() throws IOException {

    if (isValidEmail(member.getEmail())) {
      System.out.println(member.getDateOfBirth());

      success = memberServiceImpl.createMember(member);

      if (success) {

        addFlashMessage(FacesMessage.SEVERITY_INFO, "Application Submitted Successfully",
            "Thank you for submitting your application. Your application has been successfully received. Please wait for an email notification regarding the approval or rejection of your application. We will get back to you as soon as possible.");

        String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        System.out.println("mybaseurl:" + context);
        FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/log-in/home.xhtml");
      }

      else {

        FacesContext.getCurrentInstance().addMessage("growl",
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email Already Exists",
                "The email you entered already exists in our system. Please enter a different email address."));
        // String context=
        // FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        // System.out.println("mybaseurl:"+context);
        // FacesContext.getCurrentInstance().getExternalContext().redirect(context+"/pages/log-in/apply.xhtml");

      }

      List<Member> result = new java.util.ArrayList<>();
      result = memberServiceImpl.getAllMember();

      
    } else {
      FacesContext.getCurrentInstance().addMessage("growl",
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Email",
              "The email you entered is invalid. Please enter a different email address."));
    }

  }

  public void redirect() throws IOException {
    String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
    System.out.println("mybaseurl:" + context);
    FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/log-in/home.xhtml");

  }
}
