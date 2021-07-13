package com.bft.com.dao;

import com.bft.com.Person;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PersonsDao {
    private static final String INSERT_PERSONS_SQL = "INSERT INTO persons" + "(first_name,last_name) VALUES " + " ( ?, ?);";
    private static final String SELECT_ALL_PERSONS = "SELECT id,first_name,last_name FROM persons ORDER BY id asc";
    private static final String SELECT_PERSON_BY_ID = "SELECT id,first_name,last_name FROM persons WHERE id = ?;";
    private static final String DELETE_PERSONS_SQL = "DELETE FROM persons WHERE id = ?;";
    private static final String UPDATE_PERSONS_SQL = "UPDATE persons SET first_name = ?,last_name= ? WHERE id = ?;";


    public PersonsDao() {
    }

    public static Connection getJndiConnection() throws Exception {
        String DATASOURCE_CONTEXT = "java:/comp/env/jdbc/postgres";
        Connection result = null;
        try {
            Context initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
            if (datasource != null) {
                result = datasource.getConnection();
            } else {
                throw new Exception("Failed to lookup datasource.");
            }
        } catch (NamingException | SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void insertPerson(Person person) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = getJndiConnection();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(INSERT_PERSONS_SQL);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            assert conn != null;
            conn.rollback();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean updatePerson(Person person) throws Exception {
        boolean rowUpdated = false;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getJndiConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(UPDATE_PERSONS_SQL);
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getId());
            rowUpdated = statement.executeUpdate() > 0;
            conn.commit();
        } catch (Exception e) {
            assert conn != null;
            conn.rollback();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return rowUpdated;
    }

    public boolean deletePerson(int id) throws Exception {
        boolean rowDeleted = false;
        Connection conn = null;
        PreparedStatement statement = null;
        try {
            conn = getJndiConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(DELETE_PERSONS_SQL);
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
            conn.commit();
        } catch (Exception e) {
            assert conn != null;
            conn.rollback();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return rowDeleted;
    }

    public List<Person> selectAllPersons() throws Exception {
        List<Person> persons = new ArrayList<>();
        try (Connection conn = getJndiConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_PERSONS)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                persons.add(new Person(id, firstName, lastName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    public Person selectPersonById(int id) throws Exception {
        Person person = null;
        try (Connection conn = getJndiConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PERSON_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                int numberId = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                person = new Person(numberId,firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

}
