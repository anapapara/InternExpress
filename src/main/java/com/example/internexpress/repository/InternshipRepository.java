package com.example.internexpress.repository;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                int createdById = Integer.parseInt(resultSet.getString("created_by"));

                User createdBy = null;
                String query2 = "SELECT * FROM user WHERE id=?";
                try (PreparedStatement statement1 = connection.prepareStatement(query2);
                     ResultSet resultSet1 = statement1.executeQuery()) {
                    statement1.setInt(1, createdById);

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

                }
                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
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
        ArrayList<Internship> internships = new ArrayList<>();
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * from internship_announcement");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String domain = resultSet.getString("domain");
                String internshipType = resultSet.getString("internship_type");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");
                int createdById = Integer.parseInt(resultSet.getString("created_by"));

                User createdBy = null;
                String query2 = "SELECT * FROM user WHERE id=?";
                try (PreparedStatement statement1 = connection.prepareStatement(query2);
                     ResultSet resultSet1 = statement1.executeQuery()) {
                    statement1.setInt(1, createdById);

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

                }

                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
                i.setId(id1);
                internships.add(i);
            }
            return internships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internships;
    }


    @Override
    public void saveAppliance(Internship entity, User user) {
        String sql = "INSERT INTO internship_applicants(internship_id, user_id) VALUES (?,?)";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, entity.getId());
            ps.setLong(2, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Internship> save(Internship entity) {
        String query = "INSERT INTO internship_announcement (id, title, duration, domain, internship_type, start_date, description, details_link, created_by) " +
                "VALUES (" + entity.getId() + ", " + entity.getTitle() + ", " + entity.getDuration() + ", " + entity.getDomain() + ", " + entity.getInternshipType()
                + ", " + entity.getStartDate() + ", " + entity.getDescription() + ", " + entity.getDetailsLink() + ", " + entity.getCreatedBy().getId() + " );";

        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.execute();
            return Optional.of(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Internship> delete(Integer id) {
        String query1 = "SELECT * FROM internship_announcement WHERE id=? ";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query1)
        ) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new RepositoryException("Internship does not exist!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "DELETE FROM internship_announcement WHERE id=?";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Internship> update(Internship entity) {
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id=? ")
        ) {
            statement.setInt(1, entity.getId());
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new RepositoryException("Internship does not exist!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "update internship_announcement set title=?, duration=?, domain=?, internship_type=?, start_date=?, description=?, details_link=? where id=?";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDuration());
            ps.setString(3, entity.getDomain());
            ps.setString(4, entity.getInternshipType());
            ps.setString(5, entity.getStartDate());
            ps.setString(6, entity.getDescription());
            ps.setString(7, entity.getDetailsLink());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(entity);
    }

    @Override
    public List<Internship> findByDomain(String domain) {
        ArrayList<Internship> internships = new ArrayList<>();
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * from internship_announcement WHERE domain=?")
        ) {
            statement.setString(1, domain);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String internshipType = resultSet.getString("internship_type");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");
                int createdById = Integer.parseInt(resultSet.getString("created_by"));

                User createdBy = null;
                String query2 = "SELECT * FROM user WHERE id=?";
                try (PreparedStatement statement1 = connection.prepareStatement(query2);
                     ResultSet resultSet1 = statement1.executeQuery()) {
                    statement1.setInt(1, createdById);

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

                }

                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
                i.setId(id1);
                internships.add(i);
            }
            return internships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internships;
    }

    @Override
    public List<Internship> findByCreator(Long userId) {
        ArrayList<Internship> internships = new ArrayList<>();
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * from internship_announcement WHERE created_by=?")
        ) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String internshipType = resultSet.getString("internship_type");
                String domain = resultSet.getString("domain");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");
                int createdById = Integer.parseInt(resultSet.getString("created_by"));

                User createdBy = null;
                String query2 = "SELECT * FROM user WHERE id=?";
                try (PreparedStatement statement1 = connection.prepareStatement(query2);
                     ResultSet resultSet1 = statement1.executeQuery()) {
                    statement1.setInt(1, createdById);

                    while (resultSet1.next()) {
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

                }

                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
                i.setId(id1);
                internships.add(i);
            }
            return internships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internships;
    }
}


