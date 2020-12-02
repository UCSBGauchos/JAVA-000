package me.bob.repository;

import me.bob.entity.StudentEntity;

import java.sql.SQLException;
import java.util.List;

public interface StudentRepository {
    /**
     * 向数据库中保存一个新用户
     *
     * @param student 要保存的用户对象
     * @return 是否新增成功
     */
    Boolean add(StudentEntity student) throws SQLException;

    /**
     * 更新数据库中的一个用户
     *
     * @param student 要更新的用户对象
     * @return 是否更新成功
     */
    Boolean update(StudentEntity student) throws SQLException;

    /**
     * 删除一个指定的用户
     *
     * @param id 要删除的用户的标识
     * @return 是否删除成功
     */
    boolean delete(Long id);

    /**
     * 精确查询一个指定的用户
     *
     * @param id 要查询的用户的标识
     * @return 如果能够查询到，返回用户信息，否则返回 null
     */
    String locate(Long id);

    /**
     * 通过名称模糊查询用户
     *
     * @param name 要模糊查询的名称
     * @return 查询到的用户列表
     */
    List<StudentEntity> matchName(String name);

}
