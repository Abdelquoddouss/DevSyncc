package com.devsync.controller;

import com.devsync.model.Tag;
import com.devsync.model.User;
import com.devsync.repository.TagRepository;
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
    private TagRepository tagRepository;


    @Override
    public void init(){
        emf= Persistence.createEntityManagerFactory("DevSyncPU");
        tagRepository = new TagRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");

        if("add".equals(action)){
            String name = req.getParameter("name");
            Tag tag = new Tag(name);
            tagRepository.addTag(tag);
            List<Tag> tags= tagRepository.findAll();
            req.setAttribute("tags",tags);
            req.getRequestDispatcher("Tag.jsp").forward(req,resp);

        }
    }



    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            String tagIdParam = req.getParameter("id");
            if (tagIdParam != null && !tagIdParam.isEmpty()) {
                Long tagId = Long.valueOf(tagIdParam);
                tagRepository.deleteTag(tagId);
                res.sendRedirect(req.getContextPath() + "/tags");
            } else {
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "tags ID is required for deletion");
            }
        }
        else {
            List<Tag> tags = tagRepository.findAll();

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
