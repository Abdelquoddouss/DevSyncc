package com.devsync.controller;

import com.devsync.model.User;
import com.devsync.repository.UserRepository;
import com.devsync.service.UserService;
import com.devsync.util.PasswordUtil;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private EntityManagerFactory emf;
    private UserService userService;

    @Override
    public void init(){
        emf = Persistence.createEntityManagerFactory("DevSyncPU");
        userService = new UserService(emf);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        // Debug statements
        System.out.println("Email entered: " + email);
        System.out.println("Password entered: " + password);

        // Retrieve user by email
        User user = userService.findByEmail(email);

        if (user != null) {
            System.out.println("User found: " + user.getEmail());
            System.out.println("Password in database: " + user.getPassword());

            if (PasswordUtil.checkPassword(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if (user.getUserType() == User.UserType.MANAGER) {
                    response.sendRedirect("/users");
                } else {
                    response.sendRedirect("/tasks");
                }
            } else {
                System.out.println("Password does not match.");
                request.setAttribute("errorMessage", "Invalid email or password");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            System.out.println("No user found with that email.");
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }




}
