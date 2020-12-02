package me.bob.repository;

import me.bob.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {


    @Autowired
    DataSource masterDataSource;

    @Autowired
    DataSource slaveDataSource;

    @Override
    public Boolean add(StudentEntity student) {
        String sql = "insert into t1 values (?, ?) ";
        try (Connection connection = masterDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean update(StudentEntity student) throws SQLException {
        return true;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public String locate(Long id) {
        String sql = "select name from t1 where id = ? ";
        try (Connection connection = slaveDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentEntity> matchName(String name) {
        return null;
    }
}
