package com.bft.com;

import com.bft.com.dao.PersonsDao;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;


//использование DataSource и JNDI для получения данных из БД

@WebServlet(urlPatterns = {"/ServletJndi/*"}, name = "ServletJndi")
public class ServletJndi extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PersonsDao personsDao;

    public ServletJndi() {
    }

    @Override
    public void init() {
       this.personsDao = new PersonsDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/new":
                showNewForm(request, response);
                break;
            case "/insert":
                try {
                    insertPerson(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "/update":
                try {
                    updatePerson(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "/edit":
                try {
                    showEditForm(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "/delete":
                try {
                    deletePerson(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    personList(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    private void personList (HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Person> personList = personsDao.selectAllPersons();
        Gson gson = new Gson();
        String personJSON = gson.toJson(personList);
        PrintWriter printWriter = response.getWriter();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        printWriter.write(personJSON);
        printWriter.close();
        RequestDispatcher dispatcher = request.getRequestDispatcher("person-list.jsp");
        dispatcher.forward(request,response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("person-form.jsp");
        dispatcher.forward(request, response);
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        Person existPerson = personsDao.selectPersonById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("person-form.jsp");
        request.setAttribute("person",existPerson);
        dispatcher.forward(request, response);
    }

    private void insertPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String first_name = request.getParameter("firstName");
        String last_name = request.getParameter("lastName");
        if (request.getParameter("id") == null) {
            Person newPerson = new Person(first_name, last_name);
            personsDao.insertPerson(newPerson);
            response.sendRedirect("person-list.jsp");
        }
    }
    private void updatePerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            String first_name = request.getParameter("firstName");
            String last_name = request.getParameter("lastName");
            Person person = new Person(id, first_name, last_name);
            personsDao.updatePerson(person);
            response.sendRedirect("person-list.jsp");
        }
    }

    private void deletePerson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        personsDao.deletePerson(id);
        response.sendRedirect("person-list.jsp");
    }
}
