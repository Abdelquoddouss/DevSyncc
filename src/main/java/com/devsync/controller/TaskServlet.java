package com.devsync.controller;

import com.devsync.enums.TaskStatus;
import com.devsync.model.Tag;
import com.devsync.model.Task;
import com.devsync.model.User;
import com.devsync.repository.TagRepository;
import com.devsync.repository.TaskRepository;
import com.devsync.repository.UserRepository;
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

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;

    @Override
    public void init() {
        taskRepository = new TaskRepository();
        userRepository = new UserRepository();
        tagRepository = new TagRepository();
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

            // Assign the task to the user
            if (currentUser.getUserType() == User.UserType.MANAGER) {
                Long userId = Long.valueOf(req.getParameter("userId"));
                User assignedUser = userRepository.findById(userId);
                task.setUser(assignedUser);
            } else {
                task.setUser(currentUser);
            }


            String[] selectedTagIds = req.getParameterValues("tags[]");
            if (selectedTagIds != null) {
                List<Tag> selectedTags = new ArrayList<>();
                for (String tagIdStr : selectedTagIds) {
                    Long tagId = Long.valueOf(tagIdStr);
                    Tag tag = tagRepository.findById(tagId);
                    if (tag != null) {
                        selectedTags.add(tag);
                    }
                }
                task.setTags( selectedTags);
            }

            // Add the task to the repository
            taskRepository.addTask(task);

            // Fetch and display all tasks
            List<Task> tasks = taskRepository.findAll();
            req.setAttribute("tasks", tasks);

            if (currentUser.getUserType() == User.UserType.MANAGER) {
                // Show the task list for managers
                List<User> users = userRepository.findAll();
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

                taskRepository.updateTaskStatus(taskId, newStatus);

                List<Task> tasks = taskRepository.findAll();
                req.setAttribute("tasks", tasks);

                if (currentUser.getUserType() == User.UserType.MANAGER) {
                    List<User> users = userRepository.findAll();
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
            Long taskId = Long.valueOf(req.getParameter("taskId"));
            Task task = taskRepository.findById(taskId); // Fetch the task to check ownership

            HttpSession session = req.getSession();
            User currentUser = (User) session.getAttribute("user");

            if (currentUser == null) {
                resp.sendRedirect("login.jsp");
                return;
            }

            if (currentUser.getUserType() == User.UserType.MANAGER) {
                taskRepository.deleteTask(taskId);
            } else if (task.getUser().getId().equals(currentUser.getId())) {
                taskRepository.deleteTask(taskId);
            } else {
                // Set an error message if the user is not authorized to delete
                req.setAttribute("error", "Vous n'êtes pas autorisé à supprimer cette tâche.");
            }

            // Redirect to the task list
            resp.sendRedirect(req.getContextPath() + "/tasks");
        } else {
            // Existing code for other actions
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                // Redirect to login page if the user is not authenticated
                resp.sendRedirect("login.jsp");
                return;
            }

            // Set currentUser as a request attribute
            req.setAttribute("currentUser", user);

            List<Task> tasks = taskRepository.findAll();
            List<Task> userTasks = taskRepository.findTasksByUser(user.getId());
            List<User> users = userRepository.findAll();
            List<Tag> tags = tagRepository.findAll();

            req.setAttribute("users", users);
            req.setAttribute("tags", tags);

            if (user.getUserType() == User.UserType.USER) {
                req.setAttribute("tasks", userTasks);
                req.getRequestDispatcher("ViewUser.jsp").forward(req, resp);
            } else {
                req.setAttribute("tasks", tasks);
                req.setAttribute("users", users);
                req.getRequestDispatcher("Task.jsp").forward(req, resp);
            }
        }
    }
}