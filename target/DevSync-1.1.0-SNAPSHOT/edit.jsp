<%@ page import="com.devsync.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }
        form { background-color: #ffffff; padding: 20px; border: 1px solid #ddd; border-radius: 10px; max-width: 400px; margin: 0 auto; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); }
        label { display: block; margin-bottom: 10px; font-weight: bold; color: #333; }
        input[type="text"], input[type="email"], input[type="password"], select { width: 100%; padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 5px; }
        button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
<form action="users" method="POST">
    <!-- Hidden action field for updating -->
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${user.id}">

    <label >Nom</label>
    <input type="text" name="name" value="${user.name}" required>

    <label >Prénom</label>
    <input type="text" name="prenom" value="${user.prenom}" required>

    <label >Email</label>
    <input type="email" name="email" value="${user.email}" required>

    <label >Mot de passe</label>
    <input type="password" name="password" value="${user.password}" required>

    <label >Type d'utilisateur</label>
    <select name="userType">
        <option value="USER" ${user.userType == 'USER' ? 'selected' : ''}>User</option>
        <option value="MANAGER" ${user.userType == 'MANAGER' ? 'selected' : ''}>Manager</option>
    </select>

    <button type="submit">Mettre à jour</button>


</form>
</body>
</html>
