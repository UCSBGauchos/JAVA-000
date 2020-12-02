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
    Boolean add(StudentEntity student);


    /**
     * 精确查询一个指定的用户
     *
     * @param id 要查询的用户的标识
     * @return 如果能够查询到，返回用户信息，否则返回 null
     */
    String locate(Long id);


    Boolean addWithRouteMap(StudentEntity student);

    String locateWithRouteMap(Long id);

}
