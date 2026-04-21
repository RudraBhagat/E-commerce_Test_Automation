# E-commerce Test Strategy

## 1. Objective
This strategy defines how quality is validated for the Sauce Demo UI automation project. The goal is to provide a reliable, interview-ready demonstration of:
- Test design thinking
- Automation architecture skills
- Defect discovery and reporting discipline
- Practical quality metrics

## 2. Scope
In scope:
- Login flows (positive and negative)
- Product discovery and cart behavior
- Checkout flow and checkout validations
- Framework utilities (config, driver lifecycle, reporting hooks)

Out of scope:
- API contract testing (no direct API layer in this project)
- Performance and load testing
- Security penetration testing

## 3. Test Levels
- Unit-style tests: deterministic tests with `WebDriverTestDouble` for page objects and utility classes.
- Functional UI tests: Selenium + TestNG flow tests against Sauce Demo.
- Smoke subset: login + add to cart + checkout happy path.
- Regression subset: smoke + negative login + checkout form validations.

## 4. Test Design Techniques
- Equivalence partitioning: valid and invalid credential classes.
- Boundary/value coverage: empty required checkout fields.
- Risk-based prioritization: checkout and authentication are highest severity user journeys.
- Data-driven testing: invalid login scenarios via TestNG `@DataProvider`.

## 5. Entry and Exit Criteria
Entry criteria:
- Build compiles with Maven.
- Browser and driver are available.
- `config.properties` values are valid.

Exit criteria (per run):
- Smoke pass rate >= 95% on stable environment.
- No open `Critical` defects.
- All `High` defects have workaround or fix plan.
- Test report generated in `reports/` and `target/surefire-reports/`.

## 6. Defect Severity and Priority
Severity:
- Critical: complete blocker for checkout/login core path.
- High: key flow broken with partial workaround.
- Medium: validation or UX issue with moderate impact.
- Low: cosmetic or non-blocking issue.

Priority:
- P1: fix before release/demo.
- P2: fix in next iteration.
- P3: backlog improvement.

## 7. Environment Strategy
- Browser: Chrome (default), with optional Firefox/Edge.
- Execution: local machine via Maven.
- Config: `src/test/resources/config.properties`.
- Reporting: Extent Report + Surefire reports.

## 8. CI Recommendation (Campus Portfolio Enhancement)
- Trigger smoke suite on each push.
- Trigger full regression nightly.
- Archive `reports/` and `target/surefire-reports/` as artifacts.
- Publish defect trend and pass-rate trend weekly.

## 9. Risks and Mitigation
- Locator instability risk: add resilient selectors and explicit waits around page transitions.
- Data dependency risk: keep test users and test data immutable.
- Flaky UI timing: centralize wait strategy and avoid blind immediate lookups.

## 10. Quality Metrics to Track
- Pass rate by suite
- Defect density by module
- Defect leakage (escaped issues)
- Mean time to detect and mean time to resolve
- Flakiness index (rerun-dependent failures)
