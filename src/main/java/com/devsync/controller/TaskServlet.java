package com.devsync.controller;

import com.devsync.enums.TaskStatus;
import com.devsync.model.Tag;
import com.devsync.model.Task;
import com.devsync.model.User;
import com.devsync.repository.TagRepository;
import com.devsync.repository.TaskRepository;
import com.devsync.repository.UserRepository;
import com.devsync.services.TagService;
import com.devsync.services.TaskService;
import com.devsync.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;
    private UserService userService;
    private TagService tagService; // Declare TagService

    @Override
    public void init() {
        taskService = new TaskService();
        userService = new UserService(); // Initialize UserService if needed
        tagService = new TagService(); // Initialize TagService
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            resp.sendRedirect("/login");
            return;
        }

        req.setAttribute("currentUser", currentUser);
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            LocalDate creationDate = LocalDate.now();
            LocalDate dueDate = LocalDate.parse(req.getParameter("dueDate"));
            TaskStatus status = TaskStatus.NOT_STARTED;

            // Fix date validation
            if (dueDate.isBefore(creationDate.plusDays(3))) {
                req.setAttribute("error", "La date d'échéance doit être supérieure à 3 jours à partir de la date de création.");
                req.getRequestDispatcher("tasks.jsp").forward(req, resp);
                return;
            }

            // Create a new task
            Task task = new Task();
            task.setTitle(title);
            task.setDesctiption(description);
            task.setCreationDate(creationDate);
            task.setDueDate(dueDate);
            task.setStatus(status);

            String[] selectedTagIds = req.getParameterValues("tags[]");
            taskService.addTask(task, currentUser, selectedTagIds);

            // Fetch and display all tasks
            List<Task> tasks = taskService.findAllTasks();
            req.setAttribute("tasks", tasks);

            if (currentUser.getUserType() == User.UserType.MANAGER) {
                // Show the task list for managers
                List<User> users = userService.findAllUsers(); // Changed to userService
                req.setAttribute("users", users);
                req.getRequestDispatcher("Task.jsp").forward(req, resp);
            } else {
                // Show the task list for regular users
                req.getRequestDispatcher("ViewUser.jsp").forward(req, resp);
            }
        }

        if ("updateStatus".equals(action)) {
            Long taskId = Long.valueOf(req.getParameter("taskId"));
            String newStatusStr = req.getParameter("status");

            try {
                TaskStatus newStatus = TaskStatus.valueOf(newStatusStr);
                taskService.updateTaskStatus(taskId, newStatus);

                List<Task> tasks = taskService.findAllTasks();
                req.setAttribute("tasks", tasks);

                if (currentUser.getUserType() == User.UserType.MANAGER) {
                    List<User> users = userService.findAllUsers(); // Changed to userService
                    req.setAttribute("users", users);
                    req.getRequestDispatcher("Task.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("ViewUser.jsp").forward(req, resp);
                }
            } catch (IllegalArgumentException e) {
                req.setAttribute("error", "Le statut fourni est invalide.");
                req.getRequestDispatcher("tasks.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            Long taskId = Long.valueOf(req.getParameter("id"));
            taskService.deleteTask(taskId);
            resp.sendRedirect(req.getContextPath() + "/tasks");
        } else {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                // Redirige vers la page de connexion si l'utilisateur n'est pas authentifié
                resp.sendRedirect("login.jsp");
                return;
            }

            // Définir currentUser comme attribut de requête
            req.setAttribute("currentUser", user);

            List<Task> tasks = taskService.findAllTasks();
            List<Task> userTasks = taskService.findTasksByUser(user.getId());
            List<User> users = userService.findAllUsers(); // Changed to userService
            List<Tag> tags = tagService.findAllTags(); // Use TagService to get tags

            req.setAttribute("users", users);
            req.setAttribute("tags", tags);

            if (user.getUserType() == User.UserType.USER) {
                req.setAttribute("tasks", userTasks);
                req.getRequestDispatcher("ViewUser.jsp").forward(req, resp);
            } else {
                req.setAttribute("tasks", tasks);
                req.getRequestDispatcher("Task.jsp").forward(req, resp);
            }
        }
    }
}