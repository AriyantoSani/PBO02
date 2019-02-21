/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ari.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author (1772046)-Ariyanto Sani
 */
public interface DaoService<T> {

    int addData(T object) throws SQLException;

    int updatedData(T object) throws SQLException;

    int deleteData(T object) throws SQLException;

    List<T> getAllData() throws SQLException;
}
