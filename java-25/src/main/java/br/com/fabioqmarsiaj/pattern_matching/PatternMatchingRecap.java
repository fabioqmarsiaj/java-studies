void main() {
    // --- Type pattern in instance of ---
    Object greeting = "Hello, Java 25!";

    // Before: manual instance of + cast
    if (greeting instanceof String) {
        String s = (String) greeting;
        IO.println("Old way: " + s.toUpperCase());
    }
    // After: type pattern (Java 16+)
    if (greeting instanceof String s) {
        IO.println("New way: " + s.toUpperCase());
    }

    // --- Switch with patterns ---
    Object[] values = {"Java", 42, 3.14, true, null};

    for(Object val: values) {
        String description = describeValue(val);
        IO.println(description);
    }

    // --- Record patterns ---
    record Point(int x, int y){}

    Object point = new Point(10, 20);

    if (point instanceof Point(int x, int y)){
        IO.println("Point coordinates: x= " + x + ", y= " + y);
    }

    // Switch with record patterns
    Object origin = new Point(0, 0);
    Object offCenter = new Point(5, 3);

    for (Object p: new Object[]{origin, offCenter}){
        String label = switch (p) {
            case Point(int x, int y) when x == 0 && y == 0 -> "Origin";
            case Point(int x, int y) -> "Point(" + x + ", " + y + ")";
            default -> "Not a Point";
        };
        IO.println(label);
    }
}

String describeValue(Object obj) {
    return switch(obj){
        case String s when s.length() > 10 -> "Long string: " + s;
        case String s                      -> "String: " + s;
        case Integer i                     -> "Integer: " + i;
        case Double d                      -> "Double: " + d;
        case Boolean b                     -> "Boolean: " + b;
        case null                          -> "null value";
        default                            -> "Other: " + obj;
    };
}