# E-commerce UI Automation Framework

End-to-end and unit-style test automation framework for the Sauce Demo application, built with Java, Selenium WebDriver, TestNG, and Maven.

## Quick Start (First-Time Contributors)

1. Verify prerequisites:

```bash
java -version
mvn -version
```

2. Review defaults in `src/test/resources/config.properties` (browser, headless mode, credentials).

3. Run the default TestNG suite:

```bash
mvn clean test
```

4. Open reports after execution:

- `reports/ExtentReport_yyyyMMdd_HHmmss.html`
- `target/surefire-reports/`

5. Optional: run deterministic unit-style tests only:

```bash
mvn "-Dtest=ConfigReaderTest,ScreenshotUtilTest,ExtentTestManagerTest,ExtentManagerTest,BaseTestTest,DriverFactoryTest,LoginPageTest,ProductsPageTest,CartPageTest,CheckoutPageTest" test
```

## Overview

This project follows a Page Object Model (POM) approach and includes:

- Functional UI test coverage for login, cart, and checkout flows
- Negative-path validations for login and checkout input handling
- Extent HTML reporting with screenshot capture support
- Additional deterministic unit-style tests for page/base utility behavior

Target application: [Sauce Demo](https://www.saucedemo.com/)

## Tech Stack

- Java (project target: 17, compatible with newer JDKs)
- Maven
- Selenium WebDriver
- TestNG
- WebDriverManager
- Extent Reports

## Project Layout

```text
Project_1
|- pom.xml
|- testng.xml
|- README.md
|- reports/
|- src/
|  |- test/
|  |  |- java/com/ecommerce/automation/
|  |  |  |- base/
|  |  |  |- config/
|  |  |  |- listeners/
|  |  |  |- pages/
|  |  |  |- support/
|  |  |  |- tests/
|  |  |  |- utils/
|  |  |- resources/config.properties
```

## Prerequisites

1. Java 17 or higher installed
2. Maven 3.8+ installed
3. Chrome, Edge, or Firefox installed locally (for UI tests)
4. Internet access to run against Sauce Demo

## Setup

1. Open a terminal in the project root.
2. Verify Java and Maven:

```bash
java -version
mvn -version
```

3. Review test configuration in `src/test/resources/config.properties`.

## Configuration

The main runtime settings are in `src/test/resources/config.properties`:

- `base.url`
- `browser` (`chrome`, `firefox`, `edge`)
- `headless` (`true` or `false`)
- `implicit.wait.seconds`
- `explicit.wait.seconds`
- test credentials and checkout test data

## Running Tests

### Run full default suite

This runs the TestNG suite defined in `testng.xml`:

```bash
mvn clean test
```

### Run a specific TestNG class

```bash
mvn -Dtest=LoginTests test
```

### Run generated unit-style tests only

```bash
mvn "-Dtest=ConfigReaderTest,ScreenshotUtilTest,ExtentTestManagerTest,ExtentManagerTest,BaseTestTest,DriverFactoryTest,LoginPageTest,ProductsPageTest,CartPageTest,CheckoutPageTest" test
```

## Reporting

After execution, Extent reports are generated in:

- `reports/ExtentReport_yyyyMMdd_HHmmss.html`
- `reports/screenshots/*.png` (captured on failure when available)

TestNG/Surefire artifacts are available in:

- `target/surefire-reports/`

## Current Functional Coverage

- Valid login flow
- Invalid login input handling
- Add-to-cart and checkout flow
- Checkout validation for missing required fields

## Troubleshooting

- If browser startup fails, verify local browser installation and rerun.
- If WebDriver mismatch warnings appear, update browser and rerun tests.
- If Selenium CDP warnings appear for newer Chrome versions, tests may still pass; they are warnings unless failures occur.
- If a UI test is flaky due to timing or environment, rerun once and inspect `target/surefire-reports` plus Extent report details.

## Contribution Notes

- Keep page interactions in page classes under `pages/`.
- Keep test flow assertions in `tests/` or dedicated test classes.
- Prefer deterministic tests for unit-style coverage and avoid adding network/browser dependency when not required.
