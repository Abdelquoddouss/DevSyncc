package com.devsync.controller;

import com.devsync.model.Tag;
import com.devsync.model.User;
import com.devsync.repository.TagRepository;
import com.devsync.services.TagService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/tags")
public class TagServlet extends HttpServlet {
    private EntityManagerFactory emf;
    private TagService tagService;

    @Override
    public void init() {
        emf = Persistence.createEntityManagerFactory("DevSyncPU");
        tagService = new TagService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String name = req.getParameter("name");
            Tag tag = new Tag(name);
            tagService.addTag(tag);
            List<Tag> tags = tagService.findAllTags();
            req.setAttribute("tags", tags);
            req.getRequestDispatcher("Tag.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            String tagIdParam = req.getParameter("id");
            if (tagIdParam != null && !tagIdParam.isEmpty()) {
                Long tagId = Long.valueOf(tagIdParam);
                tagService.deleteTag(tagId);
                res.sendRedirect(req.getContextPath() + "/tags");
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tag ID is required for deletion");
            }
        } else {
            List<Tag> tags = tagService.findAllTags();
            req.setAttribute("tags", tags);
            req.getRequestDispatcher("Tag.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }


}
