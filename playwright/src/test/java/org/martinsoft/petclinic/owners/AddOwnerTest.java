package org.martinsoft.petclinic.owners;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("Epic: Angular WebUI petclinic")
@Feature("Feature: OWNERS")
@Story("Story: Add owner")
@Owner("martin.ibersperger@gmx.at")
@Link(name = "OpenAPI Documentation", url = "https://github.com/spring-petclinic/spring-petclinic-angular")
public class AddOwnerTest {
  private static Playwright playwright;
  private static Browser browser;
  private BrowserContext context;
  private Page page;

  @BeforeAll
  static void setupBrowser() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0));

  }

  @BeforeEach
  void setup() {
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
  }

  @AfterAll
  static void teardownBrowser() {
    if (browser != null) {
      browser.close();
    }
    if (playwright != null) {
      playwright.close();
    }

    try {
      // Run the PowerShell command to restart the Docker container
      String command = "powershell.exe docker restart petclinic-backend";

      // Using ProcessBuilder to execute the command
      ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
      Process process = processBuilder.start();

      // Wait for the command to complete
      int exitCode = process.waitFor();
      if (exitCode == 0) {
        System.out.println("Docker container restarted successfully.");
      } else {
        System.out.println("Error restarting Docker container. Exit code: " + exitCode);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
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
