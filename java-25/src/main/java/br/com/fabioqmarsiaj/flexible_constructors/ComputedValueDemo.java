package br.com.fabioqmarsiaj.flexible_constructors;

public class ComputedValueDemo {

    static class Account {
        private final String id;
        private final String displayName;

        Account(String id, String displayName){
            this.id = id;
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getId() {
            return id;
        }
    }

    static class UserAccount extends Account {
        private final String email;

        UserAccount(String firstName, String lastName, String email) {
            // Before Java 25 we couldn't validate parameters before 'super' call
            if(firstName == null || lastName == null) {
                throw new IllegalArgumentException("First and last name are required.");
            }
            String id = email.split("@")[0].toLowerCase();
            String displayName = firstName + " " + lastName;

            super(id, displayName);
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }

    void main() {
        var user = new UserAccount("Alice", "Smith", "alice.smith@example.com");
        IO.println("ID: " + user.getId());
        IO.println("Display Name: " + user.getDisplayName());
        IO.println("Email: " + user.getEmail());
    }
}
