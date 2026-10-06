# QA Technical Test - UI & API Automation Framework

Single Maven project covering **Task 1 (UI automation)** against https://www.saucedemo.com and
**Task 2 (API automation)** against https://jsonplaceholder.typicode.com.

## 1. Framework overview
BDD framework: Gherkin features -> Cucumber step definitions -> Page Objects (UI) / Service layer (API).

```
src/main/java/com/framework
  config/      ConfigReader            - loads *.properties, allows -D overrides
  driver/      DriverFactory           - ThreadLocal WebDriver (Chrome/Firefox/Edge)
  pages/       BasePage, LoginPage, ProductsPage, CartPage, CheckoutPage   (Page Object Model)
  api/
    client/    ApiClient               - reusable GET/POST/PUT/DELETE + shared request spec
    services/  PostService             - business operations on /posts
    models/    Post                    - POJO (request & response handling)
    filters/   ApiLoggingFilter        - logs every request/response via Log4j2
  utils/       ScreenshotUtil
src/main/resources
  config/config.properties             - UI url, browser, headless, waits, test data
  config/api.properties                - API base url, timeouts, logging switch
  log4j2.xml
src/test/java/com/framework
  context/     TestContext             - per-scenario shared state (PicoContainer DI)
  hooks/       Hooks                   - browser start/stop, screenshot on failure
  stepdefinitions/  LoginSteps, BasketSteps, CheckoutSteps, ApiSteps
  runners/     TestRunner (UI), ApiTestRunner (API)
src/test/resources
  features/ui/   login.feature, basket.feature, checkout.feature
  features/api/  posts.feature
  extent.properties, extent-config.xml
testng.xml | testng-api.xml | testng-ui-chrome.xml
```

### Test coverage
| Task | Scenario | Feature file |
|------|----------|--------------|
| UI Test 1 | Successful login | `ui/login.feature` (plus negative / logout cases) |
| UI Test 2 | Add product to basket, verify basket count | `ui/basket.feature` |
| UI Test 3 | Login, add product, checkout, verify order completion | `ui/checkout.feature` |
| API GET | Retrieve post, verify status code and fields | `api/posts.feature` |
| API POST | Create post, verify status code and payload | `api/posts.feature` |
| API PUT | Update post, verify updated values | `api/posts.feature` |
| API DELETE | Delete post, verify status code | `api/posts.feature` |

### Design highlights
- **Page Object Model** with a `BasePage` holding explicit waits and logging; no locators or waits in steps.
- **Thread safety**: `ThreadLocal<WebDriver>` + one `TestContext` per scenario => safe parallel runs.
- **Cross-browser & parallel**: `testng.xml` runs Chrome, Firefox, Edge and API tests concurrently; scenarios inside each run in parallel too.
- **Config-driven**: URLs, browser, timeouts, credentials and test data live in properties files, overridable from the command line.
- **API layering**: `ApiClient` (HTTP) -> `PostService` (domain) -> steps (assertions); `Post` POJO for serialisation/deserialisation.
- **Error handling & reporting**: screenshot attached on UI failure, API request/response written to the report and log, meaningful assertion messages, driver always quit in `finally`.

## 2. Technology stack
Java 17, Maven, Selenium 4.25 (Selenium Manager - no manual driver setup), TestNG 7.10, Cucumber 7.20 (+ PicoContainer),
Rest Assured 5.5, Jackson, ExtentReports (Spark) via extentreports-cucumber7-adapter, Log4j2.

## 3. Setup instructions
1. Install **JDK 17+** and **Maven 3.8+** (`java -version`, `mvn -version`).
2. Install **Chrome, Firefox and/or Edge** (only the ones used in the suite you run).
3. Internet access to `saucedemo.com` and `jsonplaceholder.typicode.com` (drivers are downloaded automatically on first run).
4. `git clone <repo-url>` and `cd saucedemo-automation-framework`.

## 4. Test execution commands
```bash
mvn clean test                                          # everything: 3 browsers + API in parallel
mvn clean test -DsuiteXmlFile=testng-api.xml            # API tests only
mvn clean test -DsuiteXmlFile=testng-ui-chrome.xml      # UI on Chrome only
mvn clean test -Dcucumber.filter.tags="@smoke"          # only smoke scenarios
mvn clean test -Dcucumber.filter.tags="@checkout"       # one feature area
mvn clean test -Dheadless=true                          # headless browsers (e.g. CI)
mvn clean test -Dbase.url=https://www.saucedemo.com     # override any property
```

### Reports & logs
| Output | Location |
|--------|----------|
| Extent (Spark) report | `target/extent-report/ExtentReport.html` |
| Cucumber HTML / JSON | `target/cucumber-reports/` |
| Execution log (Log4j2) | `logs/automation.log` |

## 5. Assumptions
- Valid credentials are the public SauceDemo ones (`standard_user` / `secret_sauce`), stored in `config.properties`.
- "Basket" = SauceDemo "cart"; the product used is `Sauce Labs Backpack` (configurable: `product.name`).
- Checkout data (name / postcode) is dummy data; SauceDemo does not validate it.
- jsonplaceholder is a **mock** API: POST/PUT/DELETE return realistic responses but nothing is persisted,
  so each scenario is independent (e.g. POST returns id 101, a later GET of 101 would 404).
- Cross-browser runs need the browsers installed locally; no Selenium Grid / cloud is assumed.
- UI scenarios are independent and each starts a fresh, isolated browser session.
