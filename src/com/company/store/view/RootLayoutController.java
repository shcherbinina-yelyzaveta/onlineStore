package com.company.store.view;

import com.company.store.MainApp;
import com.company.store.database.OrderDAO;
import com.company.store.model.Category;
import com.company.store.model.Product;
import com.company.store.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

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

    @FXML
    private void handleSignOut(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Online Store");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure?");
        alert.getDialogPane().setPrefWidth(250);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            mainApp.initAuthenticationLayout();
            mainApp.setUser(null);
            mainApp.showAuthentication();
        }
    }

    @FXML
    private void handleChangePassword(){
        TextInputDialog dialog = new TextInputDialog(null);
        dialog.setTitle("Online Store");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your new password:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            mainApp.getUser().setPassword(result.get());
            User.userDAO.update(mainApp.getUser());
        }
    }

    private void viewCatalog(String name) {
        if(mainApp.isCart()){
            mainApp.showProductOverview();
        }
        catalog_id = Category.categoryDAO.findIdByName(name);
        mainApp.setProducts(Product.productDAO.findAllByCatalogId(catalog_id));
    }
}
