package org.martinsoft.petclinic.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class OwnerPage {
  private final Page page;

  public OwnerPage(Page page) {
    this.page = page;
  }

  public void navigateToOwners() {
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
  }

  public void clickAddNewOwner() {
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add New")).click();
  }

  public void fillOwnerDetails(String firstName, String lastName, String address, String city, String telephone) {
    page.getByLabel("First Name").fill(firstName);
    page.getByLabel("Last Name").fill(lastName);
    page.getByLabel("Address").fill(address);
    page.getByLabel("City").fill(city);
    page.getByLabel("Telephone").fill(telephone);
  }

  public void submitOwnerForm() {
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Owner")).click();
  }

  public boolean isOwnerVisible(String fullName) {
    System.out.println("=============== fullName: " + fullName );
    return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(fullName)).isVisible();
  }
}
