/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ari.dao;

import com.ari.entity.Category;
import com.ari.entity.Item;
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
public class ItemDaoImpl implements DaoService<Item> {

    @Override
    public int addData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "INSERT INTO menu(id,name,price,description,recomended,category_id) VALUES(?,?,?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, object.getId());
        ps.setString(2, object.getName());
        ps.setDouble(3, object.getPrice());
        ps.setString(4, object.getDescription());
        int value = 0;
        if (object.isRecommended() == true) {
            value = 1;
        }
        ps.setInt(5, value);
        ps.setInt(6, object.getCategory().getId());

        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int updatedData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "UPDATE menu set name=?,price=?,description=? ,recomended=?, category_id=? WHERE id=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, object.getName());
        ps.setDouble(2, object.getPrice());
        ps.setString(3, object.getDescription());
        int value = 0;
        if (object.isRecommended() == true) {
            value = 1;
        }
        ps.setInt(4, value);
        ps.setInt(5, object.getCategory().getId());
        ps.setInt(6, object.getId());
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public int deleteData(Item object) throws SQLException {
        int result = 0;
        Connection connection = ConnUtil.createConnection();
        String query
                = "DELETE FROM menu Where Id=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, object.getId());
        if (ps.executeUpdate() != 0) {
            connection.commit();
            result = 1;
        }
        return result;
    }

    @Override
    public List<Item> getAllData() throws SQLException {
        List<Item> items = new ArrayList<>();
        String query
                = "SELECT * FROM menu JOIN category  ON menu.category_id=category.id";
        Connection conn = ConnUtil.createConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt("menu.id"));
            item.setName(rs.getString("menu.name"));
            item.setPrice(rs.getDouble("menu.price"));
            item.setDescription(rs.getString("menu.description"));
            item.setRecommended(rs.getBoolean("menu.recomended"));

            Category c = new Category();
            c.setId(rs.getInt("category.id"));
            c.setName(rs.getString("category.name"));
            item.setCategory(c);

            items.add(item);
        }

        rs.close();
        ps.close();
        return items;
    }

}
