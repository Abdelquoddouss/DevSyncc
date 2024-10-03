<%@ page import="com.devsync.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <style>
        /* Style général pour le corps de la page */
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            padding: 20px;
        }

        /* Style pour le conteneur du formulaire */
        form {
            background-color: #ffffff;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            max-width: 400px;
            margin: 0 auto;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        /* Style pour les étiquettes et les champs de formulaire */
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
            color: #333;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 12px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s ease;
            width: 100%;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        /* Style pour les liens */
        a {
            display: inline-block;
            margin-top: 20px;
            text-decoration: none;
            color: white;
            background-color: #008CBA;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
            text-align: center;
        }

        a:hover {
            background-color: #007BB5;
        }

        /* Centrer le texte d'en-tête */
        h2 {
            text-align: center;
            color: #333;
        }

    </style>
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