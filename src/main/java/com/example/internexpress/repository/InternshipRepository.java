package com.example.internexpress.repository;

import com.example.internexpress.domain.Internship;
import com.example.internexpress.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InternshipRepository implements Repository<Integer, Internship> {
    private final JdbcUtils jdbcUtils;

    public InternshipRepository(Properties props) {
        this.jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<Internship> findOne(Integer id) {
        String query = "SELECT * FROM internship_announcement i JOIN user u on i.created_by = u.id WHERE id = " + id + ";";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            Optional<Internship> internship = Optional.empty();
            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String domain = resultSet.getString("domain");
                String internshipType = resultSet.getString("internship_type");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");

                Long userId = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String date = resultSet.getString("date");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("user_type");

                User createdBy = new User(firstName, lastName, date, gender, email, password, userType);
                createdBy.setId(userId);

                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
                i.setId(id1);
                List<User> users = new ArrayList<>();
                List<User> acceptedUsers = new ArrayList<>();
                String query3 = "SELECT * FROM internship_applicants JOIN user ON user.id = internship_applicants.user_id" +
                        " WHERE internship_id=? ";
                try (PreparedStatement statement2 = connection.prepareStatement(query3)) {
                    statement2.setInt(1, id1);
                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        Long id2 = Long.parseLong(resultSet2.getString("id"));
                        String firstName1 = resultSet2.getString("first_name");
                        String lastName1 = resultSet2.getString("last_name");
                        String date1 = resultSet2.getString("date");
                        String gender1 = resultSet2.getString("gender");
                        String email1 = resultSet2.getString("email");
                        String password1 = resultSet2.getString("password");
                        String usertype = resultSet2.getString("user_type");
                        String status = resultSet2.getString("status");
                        User user = new User(firstName1, lastName1, date1, gender1, email1, password1, usertype);
                        user.setId(id2);
                        if (status.equals("Accepted")) acceptedUsers.add(user);
                        users.add(user);
                    }
                }

                i.setApplicants(users);
                i.setAcceptedUsers(acceptedUsers);

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
                     "SELECT * from internship_announcement JOIN user u on u.id = internship_announcement.created_by");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String domain = resultSet.getString("domain");
                String internshipType = resultSet.getString("internship_type");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");

                Long userId = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String date = resultSet.getString("date");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("user_type");

                User createdBy = new User(firstName, lastName, date, gender, email, password, userType);
                createdBy.setId(userId);

                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
                i.setId(id1);

                List<User> users = new ArrayList<>();
                List<User> acceptedUsers = new ArrayList<>();
                String query3 = "SELECT * FROM internship_applicants JOIN user ON user.id = internship_applicants.user_id" +
                        " WHERE internship_id=? ";
                try (PreparedStatement statement2 = connection.prepareStatement(query3)) {
                    statement2.setInt(1, id1);
                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        Long id2 = Long.parseLong(resultSet2.getString("id"));
                        String firstName1 = resultSet2.getString("first_name");
                        String lastName1 = resultSet2.getString("last_name");
                        String date1 = resultSet2.getString("date");
                        String gender1 = resultSet2.getString("gender");
                        String email1 = resultSet2.getString("email");
                        String password1 = resultSet2.getString("password");
                        String usertype = resultSet2.getString("user_type");
                        String status = resultSet2.getString("status");
                        User user = new User(firstName1, lastName1, date1, gender1, email1, password1, usertype);
                        user.setId(id2);
                        if (status.equals("Accepted")) acceptedUsers.add(user);
                        users.add(user);
                    }
                }

                i.setApplicants(users);
                i.setAcceptedUsers(acceptedUsers);

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
        String sql = "INSERT INTO internship_applicants(internship_id, user_id,status) VALUES (?,?,?)";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, entity.getId());
            ps.setLong(2, user.getId());
            ps.setString(3, "Pending");

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Internship> save(Internship entity) {
        String query = "INSERT INTO internship_announcement (id, title, duration, domain, internship_type, start_date, description, details_link, created_by) " +
                "VALUES ('" + entity.getId() + "','" + entity.getTitle() + "','" + entity.getDuration() + "','" + entity.getDomain() + "','" + entity.getInternshipType()
                + "','" + entity.getStartDate() + "','" + entity.getDescription() + "','" + entity.getDetailsLink() + "','" + entity.getCreatedBy().getId() + " ');";

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
                PreparedStatement statement = connection.prepareStatement("SELECT * from internship_announcement JOIN" +
                        " user ON user.id = internship_announcement.created_by WHERE domain=?")) {
            statement.setString(1, domain);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String internshipType = resultSet.getString("internship_type");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");

                Long userId = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String date = resultSet.getString("date");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("user_type");

                User createdBy = new User(firstName, lastName, date, gender, email, password, userType);
                createdBy.setId(userId);


                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
                i.setId(id1);

                List<User> users = new ArrayList<>();
                List<User> acceptedUsers = new ArrayList<>();
                String query3 = "SELECT * FROM internship_applicants JOIN user ON user.id = internship_applicants.user_id" +
                        " WHERE internship_id=? ";
                try (PreparedStatement statement2 = connection.prepareStatement(query3)) {
                    statement2.setInt(1, id1);
                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        Long id2 = Long.parseLong(resultSet2.getString("id"));
                        String firstName1 = resultSet2.getString("first_name");
                        String lastName1 = resultSet2.getString("last_name");
                        String date1 = resultSet2.getString("date");
                        String gender1 = resultSet2.getString("gender");
                        String email1 = resultSet2.getString("email");
                        String password1 = resultSet2.getString("password");
                        String usertype = resultSet2.getString("user_type");
                        String status = resultSet2.getString("status");
                        User user = new User(firstName1, lastName1, date1, gender1, email1, password1, usertype);
                        user.setId(id2);
                        if (status.equals("Accepted")) acceptedUsers.add(user);
                        users.add(user);
                    }
                }

                i.setApplicants(users);
                i.setAcceptedUsers(acceptedUsers);
                internships.add(i);
            }
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
                PreparedStatement statement = connection.prepareStatement("SELECT * from internship_announcement JOIN " +
                        "user ON user.id = internship_announcement.created_by WHERE created_by=?")
        ) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id1 = Integer.parseInt(resultSet.getString("id"));
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String internshipType = resultSet.getString("internship_type");
                String domain = resultSet.getString("domain");
                String startDate = resultSet.getString("start_date");
                String description = resultSet.getString("description");
                String link = resultSet.getString("details_link");

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String date = resultSet.getString("date");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("user_type");

                User createdBy = new User(firstName, lastName, date, gender, email, password, userType);
                createdBy.setId(userId);

                Internship i = new Internship(title, duration, domain, internshipType, startDate, description, link, createdBy);
                i.setId(id1);

                List<User> users = new ArrayList<>();
                List<User> acceptedUsers = new ArrayList<>();
                String query3 = "SELECT * FROM internship_applicants JOIN user ON user.id = internship_applicants.user_id" +
                        " WHERE internship_id=? ";
                try (PreparedStatement statement2 = connection.prepareStatement(query3)) {
                    statement2.setInt(1, id1);
                    ResultSet resultSet2 = statement2.executeQuery();
                    while (resultSet2.next()) {
                        Long id2 = Long.parseLong(resultSet2.getString("id"));
                        String firstName1 = resultSet2.getString("first_name");
                        String lastName1 = resultSet2.getString("last_name");
                        String date1 = resultSet2.getString("date");
                        String gender1 = resultSet2.getString("gender");
                        String email1 = resultSet2.getString("email");
                        String password1 = resultSet2.getString("password");
                        String usertype = resultSet2.getString("user_type");
                        String status = resultSet2.getString("status");
                        User user = new User(firstName1, lastName1, date1, gender1, email1, password1, usertype);
                        user.setId(id2);
                        if (status.equals("Accepted")) acceptedUsers.add(user);
                        users.add(user);
                    }
                }

                i.setApplicants(users);
                i.setAcceptedUsers(acceptedUsers);
                internships.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return internships;
    }

//    @Override
//    public List<User> findAllApplicants(long id) {
//        List<User> applicantsList = new ArrayList<>();
//        //String query = "SELECT * FROM internship_applicants WHERE internship_id = " + id + ";";
//        String query = "SELECT u.id, u.first_name, u.last_name, u.date, u.gender, u.email, " +
//                " u.interested_areas, u.graduated_from " +
//                "FROM user u " +
//                "JOIN internship_applicants ia ON u.id = ia.user_id " +
//                "WHERE ia.internship_id = ?";
//        try (
//                Connection connection = jdbcUtils.getConnection();
//                PreparedStatement statement = connection.prepareStatement(query);
//                statement.setLong(9, id);
//                ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                Integer id1 = Integer.parseInt(resultSet.getString("internship_id"));
//                Long userId = resultSet.getLong(1);
//                String firstName = resultSet.getString(2);
//                String lastName = resultSet.getString(3);
//                String date = resultSet.getString(4);
//                String gender = resultSet.getString(5);
//                String email = resultSet.getString(6);
//                String interestedAreas = resultSet.getString(7);
//                String graduatedFrom = resultSet.getString(8);
//
//                User user = new User(firstName, lastName, date, gender, email, "", "Student");
//                user.setGraduatedFrom(graduatedFrom);
//                user.setInterestedAreas(Arrays.stream(interestedAreas.split(",")).toList());
//                applicantsList.add(user);
//            }

    //            Optional<Internship> internship = Optional.empty();
//            while (resultSet.next()) {
//                Integer id1 = Integer.parseInt(resultSet.getString("internship_id"));
//                //Integer id2 = Integer.parseInt(resultSet.getString("user_id"));
//                String status = resultSet.getString("status");
//
//                User user = null;
//                String query2 = "SELECT * FROM user WHERE id=?";
//                try (PreparedStatement statement1 = connection.prepareStatement(query2);
//                     ResultSet resultSet1 = statement1.executeQuery()) {
//                    statement1.setInt(1, id2);
//
//                    while (resultSet1.next()) {
//                        Long userId = resultSet.getLong(1);
//                        String firstName = resultSet.getString(2);
//                        String lastName = resultSet.getString(3);
//                        String date = resultSet.getString(4);
//                        String gender = resultSet.getString(5);
//                        String email = resultSet.getString(6);
//                        String password = resultSet.getString(7);
//                        String userType = resultSet.getString(8);
//
//                        user = new User(firstName, lastName, date, gender, email, password, userType);
//                        user.setId(userId);
//                    }
//
//                }
//                applicantsList.add(user);
//                //applicantsList = Optional.of();
//            }
//            return applicantsList;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        //return Optional.empty();
//        return null;
//    }
    @Override
    public String getApplianceStatus(Long userId, Integer internshipId) {
        String status = "";
        String sql = "SELECT * FROM internship_applicants WHERE user_id=? AND internship_id=?";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setLong(1, userId);
            ps.setInt(2, internshipId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next())
                status = resultSet.getString("status");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public void changeStatus(Internship entity, User user, String status) {
        String sql = "UPDATE internship_applicants SET status=? WHERE internship_id=? AND user_id=?";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, status);
            ps.setInt(2, entity.getId());
            ps.setLong(3, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


