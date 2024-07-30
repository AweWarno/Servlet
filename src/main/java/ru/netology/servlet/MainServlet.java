package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;

  private static final String typeMethodGet = "GET";
  private static final String matchesMethodGetAllPosts = "/api/posts";
  private static final String matchesMethodGetOnePosts = "/api/posts/\\d+";
  private static final String typeMethodPost = "POST";
  private static final String matchesMethodPost = "/api/posts";
  private static final String typeMethodDelete = "DELETE";
  private static final String matchesMethodDelete = "/api/posts/\\d+";

  @Override
  public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      var path = req.getRequestURI();
      var method = req.getMethod();
      // primitive routing
      if (method.equals(typeMethodGet) && path.equals(matchesMethodGetAllPosts)) {
        controller.all(resp);
        return;
      }
      if (method.equals(typeMethodGet) && path.matches(matchesMethodGetOnePosts)) {
        // easy way
        var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.getById(id, resp);
        return;
      }
      if (method.equals(typeMethodPost) && path.equals(matchesMethodPost)) {
        controller.save(req.getReader(), resp);
        return;
      }
      if (method.equals(typeMethodDelete) && path.matches(matchesMethodDelete)) {
        // easy way
        var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
        controller.removeById(id, resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}

