void main() throws InterruptedException {
    IO.println("=== Basic ThreadLocal Usage ===");
    basicUsage();

    IO.println("=== Problem 1: Forgetting to Remove ===");
    memoryLeakDemo();

    IO.println("=== Problem 2: Mutable State ===");
    mutableStateDemo();

    IO.println("=== Problem 3: Virtual Threads ===");
    virtualThreadDemo();
}

void basicUsage(){
    ThreadLocal<String> currentUser = new ThreadLocal<>();
    currentUser.set("Bob");
    IO.println("Current user: " + currentUser.get());

    processRequest(currentUser);
    currentUser.remove();

    IO.println("After remove: " + currentUser.get());
}

void processRequest(ThreadLocal<String> currentUser) {
    IO.println("Processing request for: " + currentUser.get());

}

void memoryLeakDemo() {
    ThreadLocal<String> leakyLocal = new ThreadLocal<>();
    leakyLocal.set("Sensitive data");

    IO.println("Set value: " + leakyLocal.get());

    IO.println("Ops... Forgot to call remove()!");
    IO.println("Value still there: " + leakyLocal.get());

    leakyLocal.remove();

}

void mutableStateDemo(){
    ThreadLocal<String> userContext = new ThreadLocal<>();
    userContext.set("Bob");
    IO.println("Before: " + userContext.get());

    sneakyModify(userContext);

    IO.println("After sneaky modify: " + userContext.get());

    userContext.remove();

}

void sneakyModify(ThreadLocal<String> tl) {
    tl.set("CHANGED BY ANOTHER METHOD");
    IO.println("Modified valued to: " + tl.get());
}

void virtualThreadDemo() throws InterruptedException{
    var inheritableUser = new InheritableThreadLocal<String>();
    inheritableUser.set("Bob");

    IO.println("Parent thread set user to: " + inheritableUser.get());
    IO.println("Each virtual thread get its own COPY of this value: ");
    Thread[] threads = new Thread[5];

    for(int i = 0; i < threads.length; i++) {
        final int id = i;
        threads[i] = Thread.ofVirtual().start(() -> {
            String user = inheritableUser.get();
            IO.println("   Virtual Thread " + id + " sees: " + user);
        });
    }

    for(Thread t : threads) {
        t.join();
    }

    inheritableUser.remove();
    IO.println("With million of virtual threads, that's a million copies!");
    IO.println("Scoped values solve all these problems.");
}