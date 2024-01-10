package com.example.internexpress.repository;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class InternshipRepository implements Repository<Integer, Internship> {
    private final JdbcUtils jdbcUtils;

    public InternshipRepository(Properties props) {
        this.jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Internship> findOne(Integer id) {
        String query = "SELECT * FROM internship_announcement WHERE id = " + id + ";";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            Optional<Internship> internship = Optional.empty();
            while (resultSet.next()) {
                Integer id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String domain = resultSet.getString("domain");
                String internshipType = resultSet.getString("internship_type");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");
                Integer createdById = Integer.parseInt(resultSet.getString("created_by"));

                String query2 = "SELECT * FROM user WHERE id=?";
                PreparedStatement statement1 = connection.prepareStatement(query2);
                statement1.setInt(1, createdById);
                ResultSet resultSet1 = statement1.executeQuery();
                User createdBy = null;
                while (resultSet1.next()) {
                    Long userId = resultSet.getLong(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    String date = resultSet.getString(4);
                    String gender = resultSet.getString(5);
                    String email = resultSet.getString(6);
                    String password = resultSet.getString(7);
                    String userType = resultSet.getString(8);

                    createdBy = new User(firstName, lastName, date, gender, email, password, userType);
                    createdBy.setId(userId);
                }


                Internship i = new Internship(title,duration,domain,internshipType,startDate,description,link, createdBy);
                i.setId(id1);
                internship = Optional.of(i);
            }
            return internship;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public ArrayList<Internship> findAll() {
        return null;
    }


    @Override
    public Optional<Internship> save(Internship entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Internship> delete(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Optional<Internship> update(Internship entity) {
        return Optional.empty();
    }

}


