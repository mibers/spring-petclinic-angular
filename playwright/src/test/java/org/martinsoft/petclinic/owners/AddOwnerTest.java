package org.martinsoft.petclinic.owners;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("Epic: Angular WebUI petclinic")
@Feature("Feature: OWNERS")
@Story("Story: Add owner")
@Owner("martin.ibersperger@gmx.at")
@Link(name = "OpenAPI Documentation", url = "https://github.com/spring-petclinic/spring-petclinic-angular")
public class AddOwnerTest {
  private Playwright playwright;
  private Browser browser;
  private BrowserContext context;
  private Page page;

  @BeforeEach
  void setup() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1));
    context = browser.newContext();
    page = context.newPage();
  }

  @AfterEach
  void teardown() {
    if (page != null) {
      page.close();
    }
    if (context != null) {
      context.close();
    }
    if (browser != null) {
      browser.close();
    }
    if (playwright != null) {
      playwright.close();
    }
  }

  public void navigateToPage() {
    page.navigate("http://localhost:4200/petclinic/welcome");
  }

  public void addOwner() {
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

  public void listOwner() {
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Search")).click();
  }

  @Test
  @DisplayName("Add new owner")
  @Description("Add new owner")
  @Issue("Link to Xray here...")
  void addNewOwner() {
    navigateToPage();
    addOwner();
    assertThat(page.getByText("Max Hofer")).isVisible();
  }

}
