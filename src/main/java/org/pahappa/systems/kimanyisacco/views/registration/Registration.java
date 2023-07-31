package org.pahappa.systems.kimanyisacco.views.registration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.pahappa.systems.kimanyisacco.controllers.HyperLinks;
import org.pahappa.systems.kimanyisacco.models.Members;
import org.pahappa.systems.kimanyisacco.services.MemberImpl;
import org.pahappa.systems.kimanyisacco.services.UserImpl;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "registration")
@ViewScoped
public class Registration {
  private Date minDate;
  private Date maxDate;
  private String range;
  private Date orignaldateOfBirth;
  private boolean success;

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

  private Members member;
  MemberImpl memberImpl = new MemberImpl();

  public Members getMember() {
    return member;
  }

  public void setMember(Members member) {
    this.member = member;
  }

  public Registration() {
    this.member = new Members();
    LocalDate localMaxDate = LocalDate.now().minusYears(85);
    LocalDate localMinDate = LocalDate.now().minusYears(18);

    range = Integer.toString(localMaxDate.getYear()).concat(":").concat(Integer.toString(localMinDate.getYear()));

  }

  private void addFlashMessage(FacesMessage.Severity severity, String summary, String detail) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Flash flash = facesContext.getExternalContext().getFlash();
    flash.setKeepMessages(true);
    facesContext.addMessage("growl", new FacesMessage(severity, summary, detail));
  }

  public void doRegistration() throws IOException {

    System.out.println(member.getLastName());
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    member.setDateOfBirth(sdf.format(orignaldateOfBirth));

  success = memberImpl.createMember(member);

    if (success) {

       addFlashMessage(FacesMessage.SEVERITY_INFO, "Registration Successful","Congratulations! You have been successfully registered.");
      // FacesContext.getCurrentInstance().addMessage("growl",
      //     new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Successful",
      //         "Please enter a different email address."));
      // PrimeFaces.current().ajax().update("form:growl");
    
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

    List<Members> result = new java.util.ArrayList<>();
    result = memberImpl.getAllMembers();

    for (Members member : result) {
      System.out.println(member.getAccountBalance());
    }
  }
  public void redirect() throws IOException{
          String context = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
      System.out.println("mybaseurl:" + context);
      FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/pages/log-in/home.xhtml");

  }
}
