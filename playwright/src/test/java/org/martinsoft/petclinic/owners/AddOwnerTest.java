package org.martinsoft.petclinic.owners;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

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
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0));
    context = browser.newContext();
    page = context.newPage();
    context.tracing().start(new Tracing.StartOptions()
      .setScreenshots(true)
      .setSnapshots(true)
      .setSources(true));
  }

  @AfterEach
  void teardown(TestInfo testInfo) {
    if (context != null) {
      String traceFileName = testInfo.getDisplayName().replace(" ", "_") + "-trace.zip";
      context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(traceFileName)));
    }
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

  public void addOwner(String firstName, String lastName) {
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Owners")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add New")).click();
    page.getByLabel("First Name").click();
    page.getByLabel("First Name").fill(firstName);
    page.getByLabel("Last Name").click();
    page.getByLabel("Last Name").fill(lastName);
    page.getByLabel("Address").click();
    page.getByLabel("Address").fill("Rennweg 1");
    page.getByLabel("City").click();
    page.getByLabel("City").fill("Wien");
    page.getByLabel("Telephone").click();
    page.getByLabel("Telephone").fill("2343555");
    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Owner")).click();
    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(firstName + " " + lastName)).click();
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
    addOwner("Max", "Hofer");
    assertThat(page.getByText("Max Hofer")).isVisible();
  }

  @Test
  @DisplayName("Add new owner 1")
  @Description("Add new owner 1")
  @Issue("Link to Xray here...")
  void addNewOwner1() {
    navigateToPage();
    addOwner("Moritz", "Pichler");
    assertThat(page.getByText("Moritz Pichler")).isVisible();
  }

  @Test
  @DisplayName("Add new owner 2")
  @Description("Add new owner 2")
  @Issue("Link to Xray here...")
  void addNewOwner2() {
    navigateToPage();
    addOwner("Sigmund", "Freund");
    assertThat(page.getByText("Sigmund Freund")).isVisible();
  }

}
