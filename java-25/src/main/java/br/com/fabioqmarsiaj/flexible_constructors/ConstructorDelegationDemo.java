package br.com.fabioqmarsiaj.flexible_constructors;

public class ConstructorDelegationDemo {
    void main() {

        Connection conn = new Connection("https://api.example.com");
        IO.println(conn.toString());

        Connection conn2 = new Connection("http://localhost:8080");
        IO.println(conn2.toString());

        Connection conn3 = new Connection("http://localhost:abc");
        IO.println(conn3.toString());
    }

    static class Connection {
        private final String host;
        private final int port;
        private final boolean secure;

        Connection(String host, int port, boolean secure) {
            if (host == null || host.isBlank()) {
                throw new IllegalArgumentException("Host is required");
            }
            if (port < 1 || port > 65535) {
                throw new IllegalArgumentException("Invalid port: " + port);
            }
            this.host = host;
            this.port = port;
            this.secure = secure;
        }

        Connection(String url) {
            boolean isSecure = url.startsWith("https://");
            String stripped = url.replaceFirst("https?://", "");
            String[] parts = stripped.split(":");
            String parsedHost = parts[0];
            int parsedPort;

            try {
                parsedPort = parts.length > 1 ? Integer.parseInt(parts[1]) : (isSecure ? 443 : 80);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Caught invalid port in URL: " + url, e);
            }

            this(parsedHost, parsedPort, isSecure);
        }

        @Override
        public String toString() {
            return (secure ? "https" : "http") + "://" + host + ":" + port;
        }
    }
}
