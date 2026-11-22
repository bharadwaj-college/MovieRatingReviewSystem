package com.movierating.movieratingreviewsystem.servlet;

import com.movierating.movieratingreviewsystem.entity.User;
import com.movierating.movieratingreviewsystem.listener.AppContextListener;
import com.movierating.movieratingreviewsystem.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

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

        request.getRequestDispatcher(
            "/WEB-INF/views/register.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword =
            request.getParameter("confirmPassword");

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.length() < 8
                || confirmPassword == null) {

            request.setAttribute(
                "error", "Please fill all fields correctly. Password must be at least 8 characters."
            );

            doGet(request, response);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute(
                "error", "Please enter a valid email address."
            );

            doGet(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute(
                "error", "Passwords do not match."
            );

            doGet(request, response);
            return;
        }

        try {

            boolean registered =
                userService.registerUser(name, email, password);

            if (registered) {

                response.sendRedirect(
                    request.getContextPath() + "/login?registered=1"
                );

            } else {

                request.setAttribute(
                    "error", "Registration failed. This email may already be registered."
                );

                doGet(request, response);
            }

        } catch (Exception e) {

            getServletContext().log(
                "Registration error", e
            );

            request.setAttribute(
                "error", "Registration could not be completed. Please try again."
            );

            doGet(request, response);
        }
    }
}
