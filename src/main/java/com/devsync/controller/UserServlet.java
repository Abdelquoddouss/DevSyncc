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
            Long userId = Long.valueOf(req.getParameter("id"));
            String username = req.getParameter("username");
            String name = req.getParameter("name");
            String prenom = req.getParameter("prenom");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String userType = req.getParameter("userType");

            User user = new User(name, prenom, email, password, User.UserType.valueOf(userType));
            user.setId(userId);
            userRepository.updateUser(user);

            List<User> users = userRepository.findAll();
            req.setAttribute("users", users);

            req.getRequestDispatcher("TableUser.jsp").forward(req, resp);

        } else if ("delete".equals(action)) {
            Long userId = Long.valueOf(req.getParameter("id"));
            userRepository.deleteUser(userId);

            resp.sendRedirect(req.getContextPath()+"/");

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
        } else {
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
