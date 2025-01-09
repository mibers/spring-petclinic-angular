package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class AddUserRecordedLibrary {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
      BrowserContext context = browser.newContext();
      Page page = context.newPage();
      page.navigate("http://localhost:4200/petclinic/welcome");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add New")).click();
      page.getByLabel("First Name").click();
      page.getByLabel("First Name").fill("Max");
      page.getByLabel("Last Name").click();
      page.getByLabel("Last Name").fill("Hofer");
      page.getByLabel("Address").click();
      page.getByLabel("Address").fill("Rennweg 1");
      page.getByLabel("City").click();
      page.getByLabel("City").fill("Wien");
      page.getByLabel("Telephone").click();
      page.getByLabel("Telephone").fill("2343555");
      page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Owner")).click();
      page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Max Hofer")).click();
    }
  }
}
