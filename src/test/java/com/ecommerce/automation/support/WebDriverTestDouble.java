package com.ecommerce.automation.support;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class WebDriverTestDouble {
    private WebDriverTestDouble() {
    }

    public static Context newContext() {
        return new Context();
    }

    public static final class Context {
        private final Map<String, ElementState> elements = new LinkedHashMap<>();
        private final AtomicInteger quitCount = new AtomicInteger();
        private final WebDriver driver;

        private Context() {
            driver = createDriverProxy();
        }

        public Context register(By locator, String text) {
            elements.put(locator.toString(), new ElementState(text));
            return this;
        }

        public WebDriver driver() {
            return driver;
        }

        public ElementState element(By locator) {
            return elements.get(locator.toString());
        }

        public int quitCount() {
            return quitCount.get();
        }

        private WebDriver createDriverProxy() {
            InvocationHandler handler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) {
                    String methodName = method.getName();
                    if ("findElement".equals(methodName)) {
                        By locator = (By) args[0];
                        ElementState elementState = elements.computeIfAbsent(locator.toString(), ignored -> new ElementState(""));
                        return createElementProxy(elementState);
                    }
                    if ("quit".equals(methodName)) {
                        quitCount.incrementAndGet();
                        return null;
                    }
                    if ("toString".equals(methodName)) {
                        return "WebDriverTestDouble";
                    }
                    return defaultValue(method.getReturnType());
                }
            };

            return (WebDriver) Proxy.newProxyInstance(
                    WebDriverTestDouble.class.getClassLoader(),
                    new Class<?>[]{WebDriver.class},
                    handler);
        }

        private WebElement createElementProxy(ElementState elementState) {
            InvocationHandler handler = new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) {
                    String methodName = method.getName();
                    if ("clear".equals(methodName)) {
                        elementState.clearCount++;
                        return null;
                    }
                    if ("sendKeys".equals(methodName)) {
                        if (args != null && args.length > 0 && args[0] instanceof CharSequence[]) {
                            CharSequence[] values = (CharSequence[]) args[0];
                            elementState.sentKeys.add(joinValues(values));
                        }
                        return null;
                    }
                    if ("click".equals(methodName)) {
                        elementState.clickCount++;
                        return null;
                    }
                    if ("getText".equals(methodName)) {
                        return elementState.text;
                    }
                    if ("toString".equals(methodName)) {
                        return elementState.toString();
                    }
                    if ("isDisplayed".equals(methodName)) {
                        return true;
                    }
                    return defaultValue(method.getReturnType());
                }
            };

            return (WebElement) Proxy.newProxyInstance(
                    WebDriverTestDouble.class.getClassLoader(),
                    new Class<?>[]{WebElement.class},
                    handler);
        }

        private static String joinValues(CharSequence[] values) {
            StringBuilder builder = new StringBuilder();
            for (CharSequence value : values) {
                builder.append(value);
            }
            return builder.toString();
        }

        private static Object defaultValue(Class<?> returnType) {
            if (!returnType.isPrimitive()) {
                return null;
            }
            if (Boolean.TYPE.equals(returnType)) {
                return false;
            }
            if (Byte.TYPE.equals(returnType)) {
                return (byte) 0;
            }
            if (Short.TYPE.equals(returnType)) {
                return (short) 0;
            }
            if (Integer.TYPE.equals(returnType)) {
                return 0;
            }
            if (Long.TYPE.equals(returnType)) {
                return 0L;
            }
            if (Float.TYPE.equals(returnType)) {
                return 0.0f;
            }
            if (Double.TYPE.equals(returnType)) {
                return 0.0d;
            }
            if (Character.TYPE.equals(returnType)) {
                return '\0';
            }
            return null;
        }
    }

    public static final class ElementState {
        private final String text;
        private final List<String> sentKeys = new ArrayList<>();
        private int clearCount;
        private int clickCount;

        private ElementState(String text) {
            this.text = Objects.requireNonNullElse(text, "");
        }

        public int getClearCount() {
            return clearCount;
        }

        public int getClickCount() {
            return clickCount;
        }

        public List<String> getSentKeys() {
            return sentKeys;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return "ElementState{text='" + text + "'}";
        }
    }
}