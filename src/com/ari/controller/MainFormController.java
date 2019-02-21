package com.ari.controller;

import com.ari.MainApp;
import com.ari.dao.CategoryDaoImpl;
import com.ari.dao.ItemDaoImpl;
import com.ari.entity.Category;
import com.ari.entity.Item;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author (1772046)-Ariyanto Sani
 */
public class MainFormController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private CheckBox boxRecommended;
    @FXML
    private ComboBox<Category> comboCategory;
    @FXML
    private TextField txtDescription;
    @FXML
    private TableView<Item> tbRestoran;
    @FXML
    private TableColumn<Item, String> col01;
    @FXML
    private TableColumn<Item, String> col02;
    @FXML
    private TableColumn<Item, String> col03;
    @FXML
    private TableColumn<Item, String> col04;
    @FXML
    private TableColumn<Item, String> col05;
    @FXML
    private TableColumn<Item, String> col06;
    private Stage manageStage;
    @FXML
    private BorderPane root;
    private TableColumn<Item, String> TableColumnCategory;
    private ItemDaoImpl itemDao;
    private ObservableList<Item> items;
    private CategoryDaoImpl categoryDao;
    private ObservableList<Category> categories;
    private Item selectedItem;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    private boolean valid = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Show();

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

    public void Show() {
        try {
            itemDao = new ItemDaoImpl();
            items = FXCollections.
                    observableArrayList();

            items.addAll(itemDao.getAllData());
            tbRestoran.setItems(items);
            col01.setCellValueFactory(data
                    -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.getId()));
            }
            );
            col02.setCellValueFactory(data
                    -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.
                        getCategory().getName()));
            }
            );
            col03.setCellValueFactory(data
                    -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.getName()));
            }
            );
            col04.setCellValueFactory(data
                    -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.getPrice()));
            }
            );
            col05.setCellValueFactory(data
                    -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.
                        getDescription()));
            }
            );
            col06.setCellValueFactory(data
                    -> {
                Item item = data.getValue();
                return new SimpleStringProperty(String.valueOf(item.
                        isRecommended()));
            }
            );
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public ItemDaoImpl getItemsDao() {
        if (itemDao == null) {
            itemDao = new ItemDaoImpl();
        }
        return itemDao;
    }

    @FXML
    private void tableClicked(MouseEvent event) {
        selectedItem = tbRestoran.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtId.setText(String.valueOf(selectedItem.getId()));
            txtDescription.setText(selectedItem.getDescription());
            txtName.setText(selectedItem.getName());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            boxRecommended.setSelected(true);
            if (selectedItem.isRecommended() == false) {
                boxRecommended.setSelected(false);
            }
            comboCategory.setValue(selectedItem.getCategory());
            update.setDisable(false);
            delete.setDisable(false);
        }
    }

    public ObservableList<Item> getItems() {
        try {
            items = FXCollections.observableArrayList();
            items.addAll(getItemsDao().getAllData());

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return items;
    }

    private void Check() {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        String Output = "Please fill";
        if ("".equals(txtName.getText())) {
            Output += " Name /";
        }
        if (comboCategory.getValue() == null) {
            Output += " Category";
        }
        a.setContentText(Output);
        if (!"Please fill".equals(Output)) {
            a.show();
            valid = false;
        }
    }

    @FXML
    private void saveAction(ActionEvent event) {
        Item item = new Item();
        item.setId(Integer.valueOf(txtId.getText()));
        item.setName(txtName.getText());
        item.setPrice(Double.valueOf(txtPrice.getText()));
        item.setDescription(txtDescription.getText());
        item.setRecommended(boxRecommended.isSelected());
        item.setCategory(comboCategory.getValue());
        try {
            Check();
            if (valid == true) {
                itemDao.addData(item);
            } else {
                valid = true;
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Duplicate Id");
            a.show();
        }

        getItems().clear();
        try {
            getItems().addAll(getItemsDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        Show();
    }

    @FXML
    private void resetAction(ActionEvent event) {
        txtDescription.clear();
        txtId.clear();
        txtName.clear();
        txtPrice.clear();
        boxRecommended.setSelected(false);
        comboCategory.setValue(null);
    }

    @FXML
    private void updateAction(ActionEvent event) {
        Item item = new Item();
        item.setId(Integer.valueOf(txtId.getText()));
        item.setName(txtName.getText());
        item.setPrice(Double.valueOf(txtPrice.getText()));
        item.setDescription(txtDescription.getText());
        item.setRecommended(boxRecommended.isSelected());
        item.setCategory(comboCategory.getValue());
        try {
            Check();
            if (valid == true) {
                itemDao.updatedData(item);
            } else {
                valid = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        getItems().clear();
        try {
            getItems().addAll(getItemsDao().getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(CategoryMFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        Show();
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        try {
            selectedItem = tbRestoran.getSelectionModel().getSelectedItem();
            itemDao.deleteData(selectedItem);
            items.clear();
            items.addAll(itemDao.getAllData());
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void categoryshowAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(
                    "view/CategoryMForm.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            manageStage = new Stage();
            manageStage.setScene(scene);
            manageStage.setTitle("JavaFX Stage");
            manageStage.initModality(Modality.APPLICATION_MODAL);
            manageStage.initOwner(root.getScene().getWindow());

        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        manageStage.show();
    }

    @FXML
    private void closeAction(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void aboutAction(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Created by Ariyanto Sani - 1772046");
        a.show();

    }

    @FXML
    private void comboClicked(MouseEvent event) {
        comboCategory.setItems(getCategories());
    }

}
