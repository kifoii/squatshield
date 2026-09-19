package com.squatshield.input;

public final class SneakShieldContext {
    private static final ThreadLocal<Boolean> SNEAK_TRIGGERED =
            ThreadLocal.withInitial(() -> false);

    private SneakShieldContext() {
    }

    public static void setSneakTriggered(boolean value) {
        SNEAK_TRIGGERED.set(value);
    }

    public static boolean isSneakTriggered() {
        return SNEAK_TRIGGERED.get();
    }
}
