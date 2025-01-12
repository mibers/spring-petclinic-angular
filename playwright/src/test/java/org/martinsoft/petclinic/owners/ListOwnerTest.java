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
@Story("Story: List owner")
@Owner("martin.ibersperger@gmx.at")
@Link(name = "OpenAPI Documentation", url = "https://github.com/spring-petclinic/spring-petclinic-angular")
public class ListOwnerTest {
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

  public void listOwner() {
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Search")).click();
  }

  @Test
  @DisplayName("List all existing owners")
  @Description("List all existing owners")
  @Issue("Link to Xray here...")
  public void listAllOwner() {
    navigateToPage();
    listOwner();
    assertThat(page.getByText("George Franklin")).isVisible();

  }

}
