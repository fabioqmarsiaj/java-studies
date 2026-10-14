package br.com.fabioqmarsiaj.flexible_constructors;

public class ConstructorDelegationDemo {
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
            String stripped = url.replace("https?://", "");
            String[] parts = stripped.split(":");
            String parsedHost = parts[0];
            int parsedPort = parts.length > 1 ? Integer.parseInt(parts[1]) : (isSecure ? 443 : 80);

            this(parsedHost, parsedPort, isSecure);
        }

        @Override
        public String toString() {
            return (secure ? "https" : "http") + "://" + host + ":" + port;
        }
    }

    void main() {

    }
}
