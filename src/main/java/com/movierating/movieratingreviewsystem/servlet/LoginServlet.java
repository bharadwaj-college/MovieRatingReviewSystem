package com.movierating.movieratingreviewsystem.servlet;

import com.movierating.movieratingreviewsystem.entity.User;
import com.movierating.movieratingreviewsystem.listener.AppContextListener;
import com.movierating.movieratingreviewsystem.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {

        AnnotationConfigApplicationContext context =
            (AnnotationConfigApplicationContext)
            getServletContext().getAttribute(
                AppContextListener.SPRING_CONTEXT
            );

        if (context == null) {
            throw new ServletException("Spring context not available.");
        }

        userService = context.getBean(UserService.class);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if ("1".equals(request.getParameter("registered"))) {
            request.setAttribute(
                "success", "Registration successful! Please log in."
            );
        }

        request.getRequestDispatcher(
            "/WEB-INF/views/login.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()) {

            request.setAttribute(
                "error", "Please enter your email and password."
            );

            doGet(request, response);
            return;
        }

        try {

            User user = userService.authenticateUser(email, password);

            if (user != null) {

                HttpSession oldSession = request.getSession(false);

                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session = request.getSession(true);

                session.setAttribute("loggedInUser", user.getUserId());
                session.setAttribute("userName", user.getName());
                session.setMaxInactiveInterval(30 * 60);

                response.sendRedirect(
                    request.getContextPath() + "/movies"
                );

            } else {

                request.setAttribute(
                    "error", "Invalid email or password."
                );

                doGet(request, response);
            }

        } catch (Exception e) {

            getServletContext().log("Login error", e);

            request.setAttribute(
                "error", "Login could not be completed. Please try again."
            );

            doGet(request, response);
        }
    }
}
