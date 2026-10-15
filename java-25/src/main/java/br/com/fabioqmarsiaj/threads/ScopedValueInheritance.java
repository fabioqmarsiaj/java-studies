static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();
static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

void main() throws InterruptedException {
    IO.println("=== Unstructured Threads ===");
    unstructuredThreadDemo();

    IO.println("\n=== Nested Rebinding ===");
    nestedRebinding();

    IO.println("\n=== Multiple Scoped Values ===");
    multipleScopedValues();
}

void unstructuredThreadDemo() throws InterruptedException {
    ScopedValue.where(CURRENT_USER, "Bob").run(() -> {
        IO.println("Parent: " + CURRENT_USER.get());

        // Unstructured virtual thread do NOT inherit scoped values
        Thread thread = Thread.ofVirtual().start(() -> {
            IO.println("   Virtual thread bound? " + CURRENT_USER.isBound());
            IO.println("   Virtual thread orElse: " + CURRENT_USER.orElse("NOT INHERITED"));
        });

        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        IO.println("Parent still sees: " + CURRENT_USER.get());
    });
}

void nestedRebinding() {
    ScopedValue.where(CURRENT_USER, "Bob").run(() -> {
        IO.println("Outer scope: " + CURRENT_USER.get());

        // Rebinding in nested scope - shadows the outer value
        ScopedValue.where(CURRENT_USER, "Admin").run(() -> {
            IO.println("Inner scope: " + CURRENT_USER.get());
        });

        // Original value restored
        IO.println("Back to outer: " + CURRENT_USER.get());
    });
}

void multipleScopedValues() {
    ScopedValue.where(CURRENT_USER, "Bob")
            .where(REQUEST_ID, "REQ-42")
            .run(() -> {
                IO.println("User: " + CURRENT_USER.get());
                IO.println("Request ID: " + REQUEST_ID.get());
            });
}