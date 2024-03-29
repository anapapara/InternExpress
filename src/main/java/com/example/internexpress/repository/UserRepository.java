package com.example.internexpress.repository;

import com.example.internexpress.domain.ConvertPassword;
import com.example.internexpress.domain.User;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class UserRepository implements Repository<Long, User> {
    private final JdbcUtils jdbcUtils;

    public UserRepository(Properties props) {
        this.jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Optional<User> findOne(Long id) {
        String query = "SELECT * FROM user WHERE id=" + id + ";";
        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {

            Optional<User> f = Optional.empty();
            while (resultSet.next()) {
                Long id1 = Long.parseLong(resultSet.getString(1));
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String date = resultSet.getString(4);
                String gender = resultSet.getString(5);
                String email = resultSet.getString(6);
                String password = resultSet.getString(7);
                User user = new User(firstName, lastName, date, gender, email, password);
                user.setId(id1);
                f = Optional.of(user);
            }
            return f;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * from user");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String date = resultSet.getString(4);
                String gender = resultSet.getString(5);
                String email = resultSet.getString(6);
                String password = resultSet.getString(7);

                User user = new User(firstName, lastName, date, gender, email, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> save(User entity) {
        String salt = "";
        String hashtext = "";
        try {
            SecureRandom random = new SecureRandom();
            int randomInt = random.nextInt();
            salt = Integer.toString(randomInt);
            String p = salt + entity.getPassword();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(p.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, hash);
            hashtext = no.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        String query = "INSERT INTO user VALUES('" + entity.getId().intValue() + "','"
                + entity.getFirstName() + "','" + entity.getLastName() + "','"
                + entity.getDate() + "','" + entity.getGender() + "','"
                + entity.getEmail() + "','" + hashtext + "','" + salt + "')";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
            return Optional.of(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Long id) {
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id=? ")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new RepositoryException("User does not exist!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String query = "DELETE FROM user WHERE id=?";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE id=? ")) {

            statement.setLong(1, entity.getId());
            ResultSet rs = statement.executeQuery();
            if (!rs.next())
                throw new RepositoryException("User does not exist!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "update user set first_name=?, last_name=?, birth_date=?,email=?,password=? where id=?";

        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ConvertPassword convertPassword = new ConvertPassword();
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getDate());
            ps.setString(4, entity.getEmail());
            ps.setString(5, convertPassword.encrypt(entity.getPassword()));
            ps.setLong(6, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String email) {
        String query = "SELECT * FROM user WHERE email = ? ";
        try (Connection connection = jdbcUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id1 = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String date = resultSet.getString(4);
                String gender = resultSet.getString(5);
                String password = resultSet.getString(7);
                User user = new User(firstName, lastName, date, gender, email, password);
                user.setId(id1);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findloggedUser(String email1, String password1) {
        String query = "SELECT * FROM user WHERE email= ? ";
        try (Connection connection = jdbcUtils.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email1);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id1 = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String date = resultSet.getString(4);
                String gender = resultSet.getString(5);
                String email = resultSet.getString(6);
                String password = resultSet.getString(7);
                String salt = resultSet.getString(8);
                salt = salt + password1;
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(salt.getBytes(StandardCharsets.UTF_8));
                BigInteger no = new BigInteger(1, hash);
                String hashText = no.toString(16);

                if (hashText.equals(password)) {
                    User user = new User(firstName, lastName, date, gender, email, password);
                    user.setId(id1);
                    return Optional.of(user);
                }
            }
        } catch (SQLException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }
}


