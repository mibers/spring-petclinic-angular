package org.martinsoft.petclinic.pages;

import com.microsoft.playwright.Page;

public class WelcomePage {
  private final Page page;

  public WelcomePage(Page page) {
    this.page = page;
  }

  public void navigateToWelcomePage() {
    page.navigate("http://localhost:4200/petclinic/welcome");
  }
}
