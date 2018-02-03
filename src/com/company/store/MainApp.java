package com.company.store;

import com.company.store.model.Product;
import com.company.store.model.User;
import com.company.store.view.AuthenticationController;
import com.company.store.view.CartViewController;
import com.company.store.view.CatalogViewController;
import com.company.store.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private ObservableList<Product> cartProducts = FXCollections.observableArrayList();
    private User user;
    private boolean isCart;

    public MainApp() {
        setProducts(Product.productDAO.findAll());
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
    }

    public ObservableList<Product> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<Product> cartProducts) {
        this.cartProducts.addAll(cartProducts);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isCart() {
        return isCart;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Online Store");
        this.primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));

        initAuthenticationLayout();
        showAuthentication();
    }

    public void initAuthenticationLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/AuthenticationLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом макете сведения об продуктах.
     */
    public void showProductOverview() {
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/CatalogView.fxml"));
            AnchorPane productOverview = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(productOverview);
            isCart = false;
            // Даём контроллеру доступ к главному приложению.
            CatalogViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCartOverview(){
        try {
            // Загружаем сведения об адресатах.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/CartView.fxml"));
            AnchorPane cartOverview = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(cartOverview);
            isCart = true;
            // Даём контроллеру доступ к главному приложению.
            CartViewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает в корневом пакете окно входа
     */
    public void showAuthentication() {
        try {
            // Загружаем окно.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/Authentication.fxml"));
            AnchorPane productOverview = loader.load();

            // Помещаем окно в центр корневого макета.
            rootLayout.setCenter(productOverview);
            AuthenticationController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает главную сцену.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
