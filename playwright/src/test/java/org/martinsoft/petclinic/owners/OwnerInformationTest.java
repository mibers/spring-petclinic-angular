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
@Story("Story: Owner information")
@Owner("martin.ibersperger@gmx.at")
@Link(name = "OpenAPI Documentation", url = "https://github.com/spring-petclinic/spring-petclinic-angular")
public class OwnerInformationTest {
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

  public void getSingleOwnerInformation() {
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Search")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("George Franklin")).click();
    assertThat(page.locator("b")).containsText("George Franklin");
    page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("6085551023")).click();
    assertThat(page.locator("app-owner-detail")).containsText("Madison");
    assertThat(page.locator("app-owner-detail")).containsText("6085551023");
  }

  @Test
  @DisplayName("Get owner information")
  @Description("Get owner information")
  @Issue("Link to Xray here...")
  public void getOwnerInformation() {
    navigateToPage();
    getSingleOwnerInformation();
    assertThat(page.locator("app-owner-detail")).containsText("Madison");
    assertThat(page.locator("app-owner-detail")).containsText("6085551023");
  }

}
