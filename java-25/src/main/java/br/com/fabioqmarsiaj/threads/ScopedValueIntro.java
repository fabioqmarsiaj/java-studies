static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();

void main() {
    IO.println("=== Basic Scoped Value ===");
    basicBinding();

    IO.println("\n=== Safe Access ===");
    safeAccess();

    IO.println("\n=== Immutability ===");
    immutabilityDemo();
}

void basicBinding() {
    ScopedValue.where(CURRENT_USER, "Bob").run(() -> {
        IO.println("Current User: " + CURRENT_USER.get());
        processRequest();
    });
    // Value automatically unbounded - no remove() needed
    IO.println("After scope: bound = " + CURRENT_USER.isBound());
}

void processRequest() {
    // Access without param passing
    IO.println("Processing request for: " + CURRENT_USER.get());
}

void safeAccess() {
    // Outside any binding - isBound() returns false
    IO.println("Is bound: " + CURRENT_USER.isBound());
    IO.println("With orElse: " + CURRENT_USER.orElse("Anonymous"));

    // .get() outside a binding throws NoSuchElementException
    try {
        String user = CURRENT_USER.get();
    } catch (NoSuchElementException e) {
        IO.println("get() threw: " + e.getClass().getSimpleName());
    }
}

void immutabilityDemo() {
    ScopedValue.where(CURRENT_USER, "Bob").run(() -> {
        IO.println("Current User: " + CURRENT_USER.get());
        // No set() method exists - cannot change the value
        // ScopedValue API only has: get(), orElse(), isBound()
        IO.println("No set() method - value is immutable withing scope");
        tryToModify();
        IO.println("the value still: " + CURRENT_USER.get());
    });
}

void tryToModify() {
    CURRENT_USER.orElse("Hacker");
    IO.println("   trying to modify to: " + CURRENT_USER.get());
    // CURRENT_USER.set("hacker") - doesn't compile.
    IO.println("   tryToModify cannot change the value - no set() method");
}