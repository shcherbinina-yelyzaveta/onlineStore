package com.company.store.view;

import com.company.store.MainApp;
import com.company.store.database.OrderDAO;
import com.company.store.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CartViewController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    private Label totalPrice;

    private MainApp mainApp;

    public CartViewController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        productTable.setItems(mainApp.getCartProducts());
        totalPrice.setText(mainApp.getUser().getCart().getPrice() + " uan");
    }

    @FXML
    private void initialize() {
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());

    }

    @FXML
    private void handleDelete(){
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            OrderDAO.delete(mainApp.getUser().getCart(), productTable.getItems().get(selectedIndex).getId());
            productTable.getItems().remove(selectedIndex);
            totalPrice.setText(mainApp.getUser().getCart().getPrice() + " uan");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleBuy(){
        if(productTable.getItems()!=null){
            productTable.getItems().clear();
            OrderDAO.delete(mainApp.getUser().getCart());
            totalPrice.setText("0 uan");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Online Store");
            alert.setHeaderText(null);
            alert.setContentText("Cart is empty!");

            alert.showAndWait();
        }
    }
}
