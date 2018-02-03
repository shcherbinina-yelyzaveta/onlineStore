package com.company.store.view;

import com.company.store.MainApp;
import com.company.store.database.OrderDAO;
import com.company.store.model.Category;
import com.company.store.model.Product;
import javafx.fxml.FXML;

public class RootLayoutController {
    private MainApp mainApp;
    private Integer catalog_id;

    public RootLayoutController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleWomen() {
        viewCatalog("women's clothing");
    }

    @FXML
    private void handleMen() {
        viewCatalog("men's clothing");
    }

    @FXML
    private void handleKids() {
        viewCatalog("kid's clothing");
    }

    @FXML
    private void handleFruits() {
        viewCatalog("fruits");
    }

    @FXML
    private void handleVegetables() {
        viewCatalog("vegetables");
    }

    @FXML
    private void handleDrinks() {
        viewCatalog("drinks");
    }

    @FXML
    private void handleSweets() {
        viewCatalog("sweets");
    }

    @FXML
    private void handleAll() {
        if(mainApp.isCart()){
            mainApp.showProductOverview();
        }
        mainApp.setProducts(Product.productDAO.findAll());
    }

    @FXML
    private void handleOther() {
        viewCatalog("other");
    }

    @FXML
    private void handleCart(){
        mainApp.showCartOverview();
    }

    private void viewCatalog(String name) {
        if(mainApp.isCart()){
            mainApp.showProductOverview();
        }
        catalog_id = Category.categoryDAO.findIdByName(name);
        mainApp.setProducts(Product.productDAO.findAllByCatalogId(catalog_id));
    }
}
