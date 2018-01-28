package com.company.store.view;

import com.company.store.MainApp;
import com.company.store.model.Product;
import javafx.fxml.FXML;
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
}
