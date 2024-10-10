package com.devsync.controller;

import com.devsync.model.User;
import com.devsync.repository.UserRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private EntityManagerFactory emf;
    private UserRepository userRepository;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("DevSyncPU");
        userRepository = new UserRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String name = req.getParameter("name");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String userType = req.getParameter("userType");

            User user = new User(name, prenom, email, password, User.UserType.valueOf(userType));
            userRepository.addUser(user);

            List<User> users = userRepository.findAll();
            req.setAttribute("users", users);
            req.getRequestDispatcher("TableUser.jsp").forward(req, resp);
        }
        else if ("update".equals(action)) {
            // Mise à jour de l'utilisateur
            Long userId = Long.valueOf(req.getParameter("id"));
            String name = req.getParameter("name");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String userType = req.getParameter("userType");

            // Rechercher l'utilisateur existant
            User existingUser = userRepository.findById(userId);
            if (existingUser != null) {
                // Mettre à jour les champs
                existingUser.setName(name);
                existingUser.setPrenom(prenom);
                existingUser.setEmail(email);
                existingUser.setPassword(password);
                existingUser.setUserType(User.UserType.valueOf(userType));

                userRepository.updateUser(existingUser);

                // Redirection après mise à jour
                resp.sendRedirect(req.getContextPath() + "/users");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action non supportée.");
        }
    }



        @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("edit".equals(action)) {
            String userIdParam = req.getParameter("id");
            if (userIdParam != null && !userIdParam.isEmpty()) {
                Long userId = Long.valueOf(userIdParam);
                User user = userRepository.findById(userId);
                if (user != null) {
                    req.setAttribute("user", user);
                    req.getRequestDispatcher("/edit.jsp").forward(req, res);
                } else {
                    res.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                }
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            }
        }
        else if ("delete".equals(action)) {
            String userIdParam = req.getParameter("id");
            if (userIdParam != null && !userIdParam.isEmpty()) {
                Long userId = Long.valueOf(userIdParam);
                userRepository.deleteUser(userId);
                res.sendRedirect(req.getContextPath() + "/users");
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required for deletion");
            }
        }
        else {
            List<User> users = userRepository.findAll();

            req.setAttribute("users", users);

            req.getRequestDispatcher("TableUser.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
