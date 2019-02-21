package com.ari.controller;

import com.ari.dao.CategoryDaoImpl;
import com.ari.entity.Category;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author (1772046)-Ariyanto Sani
 */
public class CategoryMFormController implements Initializable {

    @FXML
    private TextField TextfieldID;
    @FXML
    private TextField TextfieldName;
    @FXML
    private TableView<Category> tbProperty;
    @FXML
    private TableColumn<Category, String> TableColumnID;
    @FXML
    private TableColumn<Category, String> TableColumnCategory;
    private CategoryDaoImpl categoryDao;
    private ObservableList<Category> categories;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Show();
    }

    public void Show() {
        try {
            categoryDao = new CategoryDaoImpl();
            categories = FXCollections.
                    observableArrayList();

            categories.addAll(categoryDao.getAllData());
            tbProperty.setItems(categories);

            TableColumnID.setCellValueFactory(data
                    -> {
                Category category = data.getValue();
                return new SimpleStringProperty(String.valueOf(category.getId()));
            }
            );
            TableColumnCategory.setCellValueFactory(data
                    -> {
                Category category = data.getValue();
                return new SimpleStringProperty(String.valueOf(category.
                        getName()));
            }
            );
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public CategoryDaoImpl getCategoriesDao() {
        if (categoryDao == null) {
            categoryDao = new CategoryDaoImpl();
        }
        return categoryDao;
    }

    public ObservableList<Category> getCategories() {
        try {
            categories = FXCollections.observableArrayList();
            categories.addAll(getCategoriesDao().getAllData());

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @FXML
    private void onClickSave(ActionEvent event) {
        Category category = new Category();
        category.setId(Integer.valueOf(TextfieldID.getText()));
        category.setName(TextfieldName.getText().trim());
        try {
            categoryDao.addData(category);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Duplicate ID Category");
            a.show();
        }

        getCategories().clear();
        try {
            getCategories().addAll(getCategoriesDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        Show();

    }

}
