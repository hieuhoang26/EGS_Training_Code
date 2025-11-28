package org.example.simpleSer;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> userDatabase;

    public UserService() {
        this.userDatabase = new HashMap<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        userDatabase.put("1", new User("1", "John Doe", "john@example.com"));
        userDatabase.put("2", new User("2", "Jane Smith", "jane@example.com"));
    }

    public String getAllUsers() {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>Users</h1>");
        html.append("<ul>");

        for (User user : userDatabase.values()) {
            html.append("<li>").append(user.getName()).append(" - ").append(user.getEmail()).append("</li>");
        }

        html.append("</ul>");
        html.append("</body></html>");
        return html.toString();
    }

    public String getUserById(String id) {
        User user = userDatabase.get(id);
        if (user != null) {
            return "<html><body><h1>User: " + user.getName() + "</h1><p>Email: " + user.getEmail() + "</p></body></html>";
        }
        return "<html><body><h1>User not found</h1></body></html>";
    }
}
