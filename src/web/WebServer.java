package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServer {

    private static HttpServer server;
    private static final int PORT = 8080;

    public static void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
            server.createContext("/", (HttpHandler) new FileHandler());
            server.setExecutor(null);
            server.start();

            System.out.println("\n========================================");
            System.out.println("   WEB SERVER STARTED SUCCESSFULLY");
            System.out.println("========================================");
            System.out.println("Server running at: http://localhost:" + PORT);
            System.out.println("========================================\n");

        } catch (IOException e) {
            System.out.println("Failed to start web server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Web server stopped.");
        }
    }

    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();

            if (requestPath.equals("/")) {
                requestPath = "/index.html";
            }


            InputStream inputStream = getClass().getResourceAsStream("/webapp" + requestPath);

            if (inputStream != null) {
                byte[] fileBytes = inputStream.readAllBytes();
                String contentType = getContentType(requestPath);

                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, fileBytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(fileBytes);
                os.close();
                inputStream.close();

                System.out.println("Served: " + requestPath);
            } else {
                String response = "404 - File Not Found: " + requestPath;
                exchange.sendResponseHeaders(404, response.length());

                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

                System.out.println("Not found: " + requestPath);
            }
        }

        private String getContentType(String filePath) {
            if (filePath.endsWith(".html")) return "text/html; charset=UTF-8";
            if (filePath.endsWith(".css")) return "text/css; charset=UTF-8";
            if (filePath.endsWith(".js")) return "application/javascript; charset=UTF-8";
            if (filePath.endsWith(".json")) return "application/json; charset=UTF-8";
            if (filePath.endsWith(".png")) return "image/png";
            if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
            if (filePath.endsWith(".svg")) return "image/svg+xml";
            return "text/plain; charset=UTF-8";
        }
    }
}