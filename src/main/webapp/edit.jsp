<%@ page import="com.devsync.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h2>Edit User</h2>

<%
    User user = (User) request.getAttribute("user");
    if (user == null) {
%>
<p>Error: User not found.</p>
<a href="${pageContext.request.contextPath}/">Back to User List</a>
<%
} else {
%>

<form action="${pageContext.request.contextPath}/" method="POST">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= user.getId() %>">

    <label>Username:</label>
    <input type="text" name="username" value="<%= user.getUsername() %>" required><br>

    <label>First Name:</label>
    <input type="text" name="name" value="<%= user.getName() %>" required><br>

    <label>Last Name:</label>
    <input type="text" name="prenom" value="<%= user.getPrenom() %>" required><br>

    <label>Email:</label>
    <input type="email" name="email" value="<%= user.getEmail() %>" required><br>

    <label>Password:</label>
    <input type="password" name="password" value="<%= user.getPassword() %>" required><br>

    <label>Role:</label>
    <select name="userType">
        <option value="MANAGER" <%= user.getUserType() == User.UserType.MANAGER ? "selected" : "" %>>Manager</option>
        <option value="USER" <%= user.getUserType() == User.UserType.USER ? "selected" : "" %>>User</option>
    </select><br>

    <input type="submit" value="Update">
</form>

<a href="${pageContext.request.contextPath}/">Back to User List</a>

<%
    }
%>

</body>
</html>