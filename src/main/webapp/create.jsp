<%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 02/10/2024
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add User</title>
    <style>
        body {
            font-family: BlinkMacSystemFont, -apple-system, "Segoe UI", "Roboto", "Oxygen", "Ubuntu", "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", "Helvetica", "Arial", sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-top: 10px;
            color: #666;
        }

        input, select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #6c7ae0;
            color: white;
            border: none;
            padding: 10px;
            margin-top: 20px;
            cursor: pointer;
            border-radius: 4px;
        }

        input[type="submit"]:hover {
            background-color: #5a66d1;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>Add User</h2>
    <form id="userForm" action="users" method="POST">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>

        <label for="name">First Name:</label>
        <input type="text" id="name" name="name" required>

        <label for="prenom">Last Name:</label>
        <input type="text" id="prenom" name="prenom" required>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>

        <label for="userType">User Type:</label>
        <select id="userType" name="userType" required>
            <option value="USER">USER</option>
            <option value="MANAGER">MANAGER</option>
        </select>

        <input type="submit" value="Add User">
    </form>
</div>
</body>
</html>
