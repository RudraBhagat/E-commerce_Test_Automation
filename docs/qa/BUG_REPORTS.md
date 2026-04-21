# Bug Reports

All bugs below are derived from the latest `mvn clean test` execution and Surefire evidence.

## BUG-001: Checkout completion fails because Finish button is not found

- ID: BUG-001
- Title: NoSuchElementException on `#finish` during happy-path checkout
- Severity: High
- Priority: P1
- Module: Checkout
- Environment: Windows 11, Chrome 147.0.7727.102, Selenium 4.20.0, Java 25.0.2
- Affected test: `CartCheckoutTests.addToCartAndCheckoutShouldCompleteSuccessfully`

### Steps to Reproduce
1. Run `mvn clean test`.
2. Execute checkout flow with valid login and valid checkout data.
3. Observe failure when attempting to finish checkout.

### Expected Result
The test clicks the Finish button and confirms order completion message.

### Actual Result
`org.openqa.selenium.NoSuchElementException` for selector `#finish`.

### Evidence
- Report file: `target/surefire-reports/TestSuite.txt`
- Stack trace location: `com.ecommerce.automation.pages.CheckoutPage.finishCheckout(CheckoutPage.java:35)`

### Suspected Root Cause
The test tries to click the Finish button before the checkout overview step is confirmed as loaded, or locator/wait logic is too brittle for page transition timing.

### Suggested Fix
- Add explicit wait for checkout overview title or Finish button visibility/clickability before click.
- Add page-state assertion after `continueCheckout()` to ensure navigation reached overview page.

## BUG-002: Missing postal code validation test fails because error banner is not found

- ID: BUG-002
- Title: NoSuchElementException on checkout error banner locator during missing postal code validation
- Severity: High
- Priority: P1
- Module: Checkout Validation
- Environment: Windows 11, Chrome 147.0.7727.102, Selenium 4.20.0, Java 25.0.2
- Affected test: `InvalidInputTests.checkoutWithMissingPostalCodeShouldShowValidationError`

### Steps to Reproduce
1. Run `mvn clean test`.
2. Log in with valid user.
3. Add product and move to checkout info form.
4. Submit form without postal code.

### Expected Result
Error banner appears with text containing `Postal Code is required`.

### Actual Result
`org.openqa.selenium.NoSuchElementException` for locator `h3[data-test='error']`.

### Evidence
- Report file: `target/surefire-reports/TestSuite.txt`
- Stack trace location: `com.ecommerce.automation.pages.CheckoutPage.getErrorMessage(CheckoutPage.java:43)`

### Suspected Root Cause
Immediate element lookup without synchronization on a dynamic UI state after form submission, or flow does not consistently remain on expected page before error retrieval.

### Suggested Fix
- Wait explicitly for error banner visibility before reading text.
- Add assertion for current URL/page title to confirm step context before reading error element.

## Summary Snapshot
- Total executed: 7
- Passed: 5
- Failed: 2
- Blocked flows: checkout completion and checkout validation
- Recommendation: address BUG-001 and BUG-002 first, then rerun smoke and regression packs.
