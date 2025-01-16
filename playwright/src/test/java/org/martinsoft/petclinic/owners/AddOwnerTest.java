package org.martinsoft.petclinic.owners;

import com.microsoft.playwright.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.martinsoft.petclinic.pages.OwnerPage;
import org.martinsoft.petclinic.pages.WelcomePage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  private WelcomePage welcomePage;
  private OwnerPage ownerPage;

  @BeforeAll
  static void setupBrowser() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(0));
  }

  @BeforeEach
  void setup(TestInfo testInfo) {
    context = browser.newContext();
    page = context.newPage();

    welcomePage = new WelcomePage(page);
    ownerPage = new OwnerPage(page);

    context.tracing().start(new Tracing.StartOptions()
      .setTitle(testInfo.getDisplayName())
      .setScreenshots(true)
      .setSnapshots(true)
      .setSources(true));
  }

  @AfterEach
  void teardown(TestInfo testInfo) {
    String traceFileName = testInfo.getDisplayName().replace(" ", "_") + "-trace.zip";
    String traceFilePath = "target/playwright-traces/" + traceFileName;

    context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(traceFilePath)));
    try {
      Allure.addAttachment(traceFileName, "application/zip", Files.newInputStream(Paths.get(traceFilePath)), ".zip");
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (page != null) page.close();
    if (context != null) context.close();
  }

  @AfterAll
  static void teardownBrowser() {
    if (browser != null) browser.close();
    if (playwright != null) playwright.close();
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

  @Test
  @DisplayName("Add new owner")
  @Description("Add new owner")
  @Issue("Link to Xray here...")
  void addNewOwner() {
    welcomePage.navigateToWelcomePage();
    ownerPage.navigateToOwners();
    ownerPage.clickAddNewOwner();
    ownerPage.fillOwnerDetails("Max", "Hofer", "Rennweg 1", "Wien", "2343555");
    ownerPage.submitOwnerForm();
    assertTrue(ownerPage.isOwnerVisible("Max Hofer"));
  }

  @Test
  @DisplayName("Add new owner 1")
  @Description("Add new owner 1")
  @Issue("Link to Xray here...")
  void addNewOwner1() {
    welcomePage.navigateToWelcomePage();
    ownerPage.navigateToOwners();
    ownerPage.clickAddNewOwner();
    ownerPage.fillOwnerDetails("Moritz", "Pichler", "Rennweg 1", "Wien", "2343555");
    ownerPage.submitOwnerForm();
    assertTrue(ownerPage.isOwnerVisible("Moritz Pichler"));
  }

  @Test
  @DisplayName("Add new owner 2")
  @Description("Add new owner 2")
  @Issue("Link to Xray here...")
  void addNewOwner2() {
    welcomePage.navigateToWelcomePage();
    ownerPage.navigateToOwners();
    ownerPage.clickAddNewOwner();
    ownerPage.fillOwnerDetails("Sigmund", "Freund", "Rennweg 1", "Wien", "2343555");
    ownerPage.submitOwnerForm();
    assertThat(page.getByText("Sigmund Freund")).isVisible();
//    assertTrue(ownerPage.isOwnerVisible("Sigmund Freund"));
  }
}
