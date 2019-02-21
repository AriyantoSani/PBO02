/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ari.dao;

import com.ari.entity.Category;
import com.ari.util.ConnUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author (1772046)-Ariyanto Sani
 */
public class CategoryDaoImpl implements DaoService<Category> {

    @Override
    public int addData(Category object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "INSERT INTO category(id,name) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, object.getId());
        ps.setString(2, object.getName());
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int updatedData(Category object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "UPDATE category set name= ? WHERE id= ? ";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, object.getName());
        ps.setInt(2, object.getId());
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int deleteData(Category object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "DELETE FROM category Where Id=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, object.getId());
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public List<Category> getAllData() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category";
        Connection conn = ConnUtil.createConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            categories.add(category);
        }
        rs.close();
        ps.close();
        return categories;
    }

}
