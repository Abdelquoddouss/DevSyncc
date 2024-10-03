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

@WebServlet("/")
public class UserServlet extends HttpServlet {

    private EntityManagerFactory emf;
    private UserRepository userRepository;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("DevSyncPU");
        userRepository = new UserRepository();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String name = req.getParameter("name");
        String prenom = req.getParameter("prenom");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userType = req.getParameter("userType");
        User user = new User(username, name, prenom, email, password, User.UserType.valueOf(userType));
        System.out.println(user);

        userRepository.addUser(user);

        resp.sendRedirect(req.getContextPath()+"index.jsp");

}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userRepository.findAll();
        System.out.println("Users retrieved: " + users);
        req.setAttribute("users", users);
        req.setAttribute("anas","anas");
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }


    @Override
    public void destroy() {
        emf.close();
    }

}
