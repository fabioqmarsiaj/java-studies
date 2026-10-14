//void main() {
//    // Before Java 25: manual unboxing ---
//    Object value = 42;
//    if(value instanceof Integer) {
//        int i = (Integer)value;
//        IO.println("Old way: " + (i * 2));
//    }
//
//    // After Java 25: primitive type patterns ---
//    if (value instanceof int i) {
//        IO.println("New way: " + (i*2));
//    }
//
//}
//
