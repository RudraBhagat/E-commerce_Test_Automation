# Test Case Catalog

## Coverage Matrix

| ID | Scenario | Type | Priority | Preconditions | Test Data | Expected Result | Automation Mapping |
|---|---|---|---|---|---|---|---|
| TC-LOGIN-001 | Valid login | Functional | P1 | App reachable | standard_user / secret_sauce | User lands on Products page | LoginTests.validLoginShouldNavigateToProductsPage |
| TC-LOGIN-002 | Empty username and password | Negative | P1 | Login page open | '' / '' | Error contains 'Username is required' | InvalidInputTests.invalidLoginShouldShowErrorMessage |
| TC-LOGIN-003 | Empty password | Negative | P1 | Login page open | standard_user / '' | Error contains 'Password is required' | InvalidInputTests.invalidLoginShouldShowErrorMessage |
| TC-LOGIN-004 | Invalid credentials | Negative | P1 | Login page open | invalid_user / invalid_password | Error contains 'do not match' | InvalidInputTests.invalidLoginShouldShowErrorMessage |
| TC-LOGIN-005 | Locked user login | Negative | P1 | Login page open | locked_out_user / secret_sauce | Error contains 'locked out' | InvalidInputTests.invalidLoginShouldShowErrorMessage |
| TC-CART-001 | Add backpack to cart | Functional | P1 | Logged in | Backpack product | Cart badge becomes 1 | CartCheckoutTests.addToCartAndCheckoutShouldCompleteSuccessfully |
| TC-CART-002 | Open cart and validate selected item | Functional | P1 | One item in cart | Sauce Labs Backpack | Cart item title matches selected item | CartCheckoutTests.addToCartAndCheckoutShouldCompleteSuccessfully |
| TC-CHK-001 | Complete checkout (happy path) | Functional | P1 | Item present in cart | John / Doe / 10001 | Order completion message shown | CartCheckoutTests.addToCartAndCheckoutShouldCompleteSuccessfully |
| TC-CHK-002 | Missing postal code | Validation | P1 | Checkout info page open | John / Doe / '' | Error contains 'Postal Code is required' | InvalidInputTests.checkoutWithMissingPostalCodeShouldShowValidationError |
| TC-PO-001 | Checkout page enters values and clicks continue | Unit-style | P2 | Test double context ready | John / Doe / 10001 | clear/sendKeys/click called correctly | CheckoutPageTest.shouldPopulateCheckoutFormAndClickContinue |
| TC-PO-002 | Checkout page supports multi-attempt input correction | Unit-style | P2 | Test double context ready | First attempt then corrected attempt | Input fields are cleared and repopulated for each attempt | CheckoutPageTest.shouldClearAndRepopulateFieldsAcrossMultipleAttempts |
| TC-PO-003 | Checkout page reads success and error labels | Unit-style | P2 | Test double context ready | Success and error labels seeded | Expected message text returned | CheckoutPageTest.shouldClickFinishAndReadCompletionAndValidationMessages |
| TC-PO-004 | Products page exposes title and cart badge | Unit-style | P3 | Test double context ready | Products / 1 | Page title and badge text retrieved | ProductsPageTest.shouldReadTheProductsTitleAndCartBadge |
| TC-PO-005 | Products page supports empty cart badge state | Unit-style | P3 | Test double context ready | Badge text empty | Empty badge text returned without exception | ProductsPageTest.shouldReturnEmptyBadgeWhenCartCountNotVisible |

## Regression Pack Recommendation
- Tier 1 (Smoke): TC-LOGIN-001, TC-CART-001, TC-CHK-001
- Tier 2 (Core Regression): Tier 1 + TC-LOGIN-002/003/004/005 + TC-CHK-002
- Tier 3 (Architecture Safety Net): all TC-PO-* deterministic tests

## Traceability
- Authentication: TC-LOGIN-001..005
- Cart and product selection: TC-CART-001..002
- Checkout business flow and validations: TC-CHK-001..002
- Page object and framework behavior: TC-PO-001..005
