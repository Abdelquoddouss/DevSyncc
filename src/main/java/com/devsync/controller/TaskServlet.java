package com.devsync.controller;

import com.devsync.enums.TaskStatus;
import com.devsync.model.Task;
import com.devsync.model.User;
import com.devsync.repository.TaskRepository;
import com.devsync.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private TaskRepository taskRepository;
    private UserRepository userRepository; // Nouveau pour accéder aux utilisateurs

    @Override
    public void init() {
        taskRepository = new TaskRepository();
        userRepository = new UserRepository(); // Initialiser le repository des utilisateurs
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            LocalDate creationDate = LocalDate.now();
            LocalDate dueDate = LocalDate.parse(req.getParameter("dueDate"));
            Long userId = Long.valueOf(req.getParameter("userId"));
            TaskStatus status = TaskStatus.NOT_STARTED; // Statut par défaut

            // Créer une nouvelle tâche
            Task task = new Task();
            task.setTitle(title);
            task.setDesctiption(description);
            task.setCreationDate(creationDate);
            task.setDueDate(dueDate);
            task.setStatus(status);

            // Assigner l'utilisateur à la tâche
            User assignedUser = userRepository.findById(userId);
            task.setUser(assignedUser);

            taskRepository.addTask(task);

            // Récupérer toutes les tâches et les renvoyer à la vue
            List<Task> tasks = taskRepository.findAll();
            req.setAttribute("tasks", tasks);
            req.getRequestDispatcher("Task.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            Long taskId = Long.valueOf(req.getParameter("id"));
            taskRepository.deleteTask(taskId);
            resp.sendRedirect(req.getContextPath() + "/tasks");
        } else {
            // Récupérer toutes les tâches et les utilisateurs pour les afficher
            List<Task> tasks = taskRepository.findAll();
            List<User> users = userRepository.findAll(); // Récupérer les utilisateurs pour les assigner

            req.setAttribute("tasks", tasks);
            req.setAttribute("users", users); // Passer les utilisateurs à la vue
            req.getRequestDispatcher("Task.jsp").forward(req, resp);
        }
    }
}
