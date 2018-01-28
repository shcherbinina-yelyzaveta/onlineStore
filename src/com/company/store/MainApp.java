package com.company.store;

import com.company.store.model.Product;
import com.company.store.view.CatalogViewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Product> products = FXCollections.observableArrayList();

    public MainApp() {
        products.add(new Product("Banana", 30));
        products.add(new Product("Apple", 15));
        products.add(new Product("Zoro", 100));
        products.add(new Product("Bananananana", 1000));
        products.add(new Product());
        products.add(new Product());
        products.add(new Product());
    }

    public ObservableList<Product> getProducts() {
        return products;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Online Store");

        initRootLayout();
        //showAuthentication();
        showProductOverview();
    }

    /**
     * Инициализирует корневой макет.
     */
    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
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
            loader.setLocation(MainApp.class.getResource("view/CatalogView.fxml"));
            primaryStage.setResizable(true);
            AnchorPane productOverview = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(productOverview);
            // Даём контроллеру доступ к главному приложению.
            CatalogViewController controller = loader.getController();
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
            loader.setLocation(MainApp.class.getResource("view/Authentication.fxml"));
            // Запрет на изменение окна
            primaryStage.setResizable(false);
            AnchorPane productOverview = loader.load();

            // Помещаем окно в центр корневого макета.
            rootLayout.setCenter(productOverview);
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
