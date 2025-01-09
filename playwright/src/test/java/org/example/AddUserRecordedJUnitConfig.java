package org.example;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@UsePlaywright(AddUserRecordedJUnitConfig.CustomOptions.class)
public class AddUserRecordedJUnitConfig {

  public static class CustomOptions implements OptionsFactory {
    @Override
    public Options getOptions() {
      return new Options().setBrowserName("chromium")  // firefox, webkit, chromium
        .setHeadless(false);
    }
  }

  @Test
  void addOwner(Page page) {
    page.navigate("http://localhost:4200/petclinic/welcome");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add New")).click();
    page.getByLabel("First Name").fill("Hary");
    page.getByLabel("Last Name").fill("Huberc");
    page.getByLabel("Address").fill("Landstrasse 2");
    page.getByLabel("City").fill("Wien");
    page.getByLabel("Telephone").fill("2233456");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Owner")).click();
    assertThat(page.getByText("Hary Huberc")).isVisible();
  }

  @Test
  void findOwner(Page page) {
    page.navigate("http://localhost:4200/petclinic/welcome");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Search")).click();
    page.locator("#lastName").click();
    page.locator("#lastName").fill("Davis");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Find Owner")).click();
    assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Betty Davis"))).isVisible();
    assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Harold Davis"))).isVisible();
  }
}
