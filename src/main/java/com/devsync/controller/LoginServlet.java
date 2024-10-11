package com.devsync.controller;

import com.devsync.model.User;
import com.devsync.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException{
        userRepository = new UserRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validation des entrées
        if (email == null || email.isEmpty()) {
            request.setAttribute("errorMessage", "Please enter a valid email address.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        if (password == null || password.isEmpty() || password.length() < 6) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        User user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if (user.getUserType() == User.UserType.MANAGER) {
                response.sendRedirect("/users");
            } else {
                response.sendRedirect("/tasks");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }






}
