package org.example;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

@UsePlaywright
public class AddUserRecordedJUnit {
  @Test
  void test(Page page) {
    page.navigate("http://localhost:4200/petclinic/welcome");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add New")).click();
    page.getByLabel("First Name").click();
    page.getByLabel("First Name").fill("Moritz");
    page.getByLabel("Last Name").click();
    page.getByLabel("Last Name").fill("Huber");
    page.getByLabel("Address").click();
    page.getByLabel("Address").fill("Landstrasse 2");
    page.getByLabel("City").click();
    page.getByLabel("City").fill("Wien");
    page.getByLabel("City").press("Tab");
    page.getByLabel("Telephone").fill("2233456");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Owner")).click();
  }
}
