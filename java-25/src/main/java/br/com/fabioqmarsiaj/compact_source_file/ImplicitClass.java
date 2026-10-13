void main() {
    // No imports needed
    var names = List.of("Alice, Fabio, Roger");
    IO.println("Names: " + names);

    var now = LocalDateTime.now();
    IO.println("Current time " + now);

    var pi = new BigDecimal("3.1415929999999999");
    IO.println("PI: " + pi);

    var currentDIr = Path.of(".");
    IO.println("Current directory: " + currentDIr.toAbsolutePath());
}