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
            mainApp.showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product in the table.");
            }
    }

    @FXML
    private void handleBuy(){
        if(productTable.getItems()!=null){
            productTable.getItems().clear();
            OrderDAO.delete(mainApp.getUser().getCart());
            totalPrice.setText("0 uan");
            mainApp.showAlert(Alert.AlertType.INFORMATION, "Online Store",
                    "Stay in touch! Our manager will contact you.");
        } else {
            mainApp.showAlert(Alert.AlertType.ERROR, "Online Store", "Cart is empty!");
        }
    }
}
