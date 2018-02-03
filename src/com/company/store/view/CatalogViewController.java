package com.company.store.view;

import com.company.store.MainApp;
import com.company.store.database.OrderDAO;
import com.company.store.model.Cart;
import com.company.store.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CatalogViewController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> valueColumn;

    @FXML
    private Label productNameLabel;
    @FXML
    private Label catalogsLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label valueLabel;

    private MainApp mainApp;

    public CatalogViewController() {
    }

    @FXML
    private void initialize() {
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        valueColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        showProductDetails(null);

        // Слушаем изменения выбора, и при изменении отображаем
        // дополнительную информацию об адресате.
        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        productTable.setItems(mainApp.getProducts());
    }

    private void showProductDetails(Product product) {
        if (product != null) {
            productNameLabel.setText(product.getProductName());
            catalogsLabel.setText(product.getCategory().getCategoryName());
            descriptionLabel.setText(product.getDescription());
            valueLabel.setText(Double.toString(product.getPrice()) + " uan");
        } else {
            productNameLabel.setText("");
            catalogsLabel.setText("");
            descriptionLabel.setText("");
            valueLabel.setText("0 uan");
        }
    }

    @FXML
    private void handleBuy(){
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            OrderDAO.create(mainApp.getUser().getCart().getId(), productTable.getItems().get(selectedIndex).getId());
            Cart.cartDAO.update(mainApp.getUser().getCart());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Product Selected");
            alert.setContentText("Please select a product in the table.");

            alert.showAndWait();
        }
    }
}
