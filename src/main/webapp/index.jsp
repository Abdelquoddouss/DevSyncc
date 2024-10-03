<%@ page import="com.devsync.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<h2>User List</h2>

<hr>

<h3>Existing Users</h3>
<table border="1">
    <thead>
    <tr>
        <th>Id</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Role</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>

    <%
        // Retrieve the user list from request attributes
        List<User> users = (List<User>) request.getAttribute("users");

        if (users != null && !users.isEmpty()) {
            // Iterate over the users list
            for (User user : users) {
    %>
    <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getPrenom() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getUserType() %></td>

        <td>
            <form action="users" method="POST" style="display:inline;">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= user.getId() %>">
                <input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this user?');">
            </form>

            <form action="users" method="GET" style="display:inline;">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="id" value="<%= user.getId() %>">
                <input type="submit" value="Edit">
            </form>

        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">No users found.</td>
    </tr>
    <%
        }
    %>

    </tbody>
</table>
<hr>

<a href="./create.jsp">Add New User</a>
</body>
</html>
