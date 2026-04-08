package com.ecommerce.automation.config;

import java.lang.reflect.Field;
import java.util.Properties;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigReaderTest {
    private static final String TEST_TRIM_KEY = "unit.test.trimmed.value";
    private static final String TEST_INVALID_INT_KEY = "unit.test.invalid.int";

    @Test
    public void shouldReturnConfiguredBooleanAndIntegerValues() {
        Assert.assertFalse(ConfigReader.getBoolean("headless"));
        Assert.assertEquals(ConfigReader.getInt("implicit.wait.seconds"), 5);
    }

    @Test
    public void shouldTrimPropertyValuesBeforeReturningThem() throws Exception {
        Properties properties = getProperties();
        properties.setProperty(TEST_TRIM_KEY, "  trimmed-value  ");

        try {
            Assert.assertEquals(ConfigReader.get(TEST_TRIM_KEY), "trimmed-value");
        } finally {
            properties.remove(TEST_TRIM_KEY);
        }
    }

    @Test
    public void shouldThrowForMissingKey() {
        try {
            ConfigReader.get("missing.config.key");
            Assert.fail("Expected IllegalArgumentException for a missing config key.");
        } catch (IllegalArgumentException expected) {
            Assert.assertTrue(expected.getMessage().contains("Missing config key"));
        }
    }

    @Test
    public void shouldThrowForInvalidIntegerValue() throws Exception {
        Properties properties = getProperties();
        properties.setProperty(TEST_INVALID_INT_KEY, "not-a-number");

        try {
            try {
                ConfigReader.getInt(TEST_INVALID_INT_KEY);
                Assert.fail("Expected NumberFormatException for an invalid integer value.");
            } catch (NumberFormatException expected) {
                Assert.assertTrue(true);
            }
        } finally {
            properties.remove(TEST_INVALID_INT_KEY);
        }
    }

    private static Properties getProperties() throws Exception {
        Field field = ConfigReader.class.getDeclaredField("PROPERTIES");
        field.setAccessible(true);
        return (Properties) field.get(null);
    }
}