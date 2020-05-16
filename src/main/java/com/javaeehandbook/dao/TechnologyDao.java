package com.javaeehandbook.dao;

import com.javaeehandbook.bean.JavaEETechnology;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TechnologyDao {
    private static final Logger log;
    private static Database database;

    static {
        log = LogManager.getLogger(TechnologyDao.class);
        try {
            database = new Database();
        } catch (Exception e) {
            database = null;
            log.error(e.getMessage());
        }
    }

    public static List<JavaEETechnology> getAllTechnologies() throws SQLException {
        String SQL;
        ResultSet rs;
        List<JavaEETechnology> list = new ArrayList<JavaEETechnology>();
        SQL = "SELECT *\n" +
                "FROM java_technologies INNER JOIN used_versions\n" +
                "ON java_technologies.versions = used_versions.used_versions_id;";

        rs = database.getConnection().createStatement().executeQuery(SQL);

        while (rs.next()) {
            list.add(fromResultSetToObject(rs));
        }

        return list;
    }

    public static int addTechnology(JavaEETechnology technology) throws SQLException {
        String SQL;
        PreparedStatement preparedStatement;

        SQL = "INSERT INTO used_versions(java_4, java_5, java_6, java_7, java_8) \n" +
                "VALUES(?, ?, ?, ?, ?)\n" +
                "ON DUPLICATE KEY UPDATE java_4=?, java_5=?, java_6=?, java_7=?, java_8=?;";
        preparedStatement = database.getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, technology.getVersionForJava4());
        preparedStatement.setString(2, technology.getVersionForJava5());
        preparedStatement.setString(3, technology.getVersionForJava6());
        preparedStatement.setString(4, technology.getVersionForJava7());
        preparedStatement.setString(5, technology.getVersionForJava8());
        preparedStatement.setString(6, technology.getVersionForJava4());
        preparedStatement.setString(7, technology.getVersionForJava5());
        preparedStatement.setString(8, technology.getVersionForJava6());
        preparedStatement.setString(9, technology.getVersionForJava7());
        preparedStatement.setString(10, technology.getVersionForJava8());
        int status = preparedStatement.executeUpdate();

        if (status > 0) {
            SQL = "INSERT INTO java_technologies(tech_name, versions, description)\n" +
                    "VALUES(?,\n" +
                    "(SELECT used_versions_id\n" +
                    "FROM used_versions\n" +
                    "WHERE java_4=? AND java_5=? AND java_6=? AND java_7=? AND java_8=?), ?)\n" +
                    "ON DUPLICATE KEY UPDATE tech_name=?;";
            preparedStatement = database.getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, technology.getName());
            preparedStatement.setString(2, technology.getVersionForJava4());
            preparedStatement.setString(3, technology.getVersionForJava5());
            preparedStatement.setString(4, technology.getVersionForJava6());
            preparedStatement.setString(5, technology.getVersionForJava7());
            preparedStatement.setString(6, technology.getVersionForJava8());
            preparedStatement.setString(7, technology.getDescription());
            preparedStatement.setString(8, technology.getName());
            status = preparedStatement.executeUpdate();
        }

        return status;
    }

    public static void deleteTechnology(JavaEETechnology technology) throws SQLException {
        String SQL;
        PreparedStatement preparedStatement;
        SQL = "DELETE FROM java_technologies\n" +
                "WHERE tech_id=?;";
        preparedStatement = database.getConnection().prepareStatement(SQL);
        preparedStatement.setInt(1, technology.getId());
        preparedStatement.executeUpdate();
    }

    public static void updateTechnology(JavaEETechnology technology) throws SQLException {
        String SQL;
        PreparedStatement preparedStatement;

        SQL = "INSERT INTO used_versions(java_4, java_5, java_6, java_7, java_8) \n" +
                "VALUES(?, ?, ?, ?, ?)\n" +
                "ON DUPLICATE KEY UPDATE java_4=?, java_5=?, java_6=?, java_7=?, java_8=?;";
        preparedStatement = database.getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, technology.getVersionForJava4());
        preparedStatement.setString(2, technology.getVersionForJava5());
        preparedStatement.setString(3, technology.getVersionForJava6());
        preparedStatement.setString(4, technology.getVersionForJava7());
        preparedStatement.setString(5, technology.getVersionForJava8());
        preparedStatement.setString(6, technology.getVersionForJava4());
        preparedStatement.setString(7, technology.getVersionForJava5());
        preparedStatement.setString(8, technology.getVersionForJava6());
        preparedStatement.setString(9, technology.getVersionForJava7());
        preparedStatement.setString(10, technology.getVersionForJava8());
        preparedStatement.executeUpdate();

        SQL = "UPDATE java_technologies\n" +
                "SET tech_name=?, versions=(SELECT used_versions_id\n" +
                "FROM used_versions\n" +
                "WHERE java_4=? AND java_5=? AND java_6=? AND java_7=? AND java_8=?),\n" +
                "description=?\n" +
                "WHERE tech_id=?;";
        preparedStatement = database.getConnection().prepareStatement(SQL);
        preparedStatement.setString(1, technology.getName());
        preparedStatement.setString(2, technology.getVersionForJava4());
        preparedStatement.setString(3, technology.getVersionForJava5());
        preparedStatement.setString(4, technology.getVersionForJava6());
        preparedStatement.setString(5, technology.getVersionForJava7());
        preparedStatement.setString(6, technology.getVersionForJava8());
        preparedStatement.setString(7, technology.getDescription());
        preparedStatement.setInt(8, technology.getId());
        preparedStatement.executeUpdate();
    }

    public static JavaEETechnology getTechnologyById(Integer id) throws SQLException {
        String SQL;
        ResultSet rs;

        SQL = "SELECT *\n" +
                "FROM java_technologies INNER JOIN used_versions\n" +
                "ON java_technologies.versions = used_versions.used_versions_id\n" +
                "WHERE java_technologies.tech_id = ?;";

        PreparedStatement statement = database.getConnection().prepareStatement(SQL);
        statement.setInt(1, id);
        rs = statement.executeQuery();
        rs.next();

        return fromResultSetToObject(rs);
    }

    private static JavaEETechnology fromResultSetToObject(ResultSet rs) throws SQLException {
        JavaEETechnology technology = new JavaEETechnology();
        technology.setId(rs.getInt("tech_id"));
        technology.setName(rs.getString("tech_name"));
        technology.setVersionForJava4(rs.getString("java_4"));
        technology.setVersionForJava5(rs.getString("java_5"));
        technology.setVersionForJava6(rs.getString("java_6"));
        technology.setVersionForJava7(rs.getString("java_7"));
        technology.setVersionForJava8(rs.getString("java_8"));
        technology.setDescription(rs.getString("description"));

        return technology;
    }
}