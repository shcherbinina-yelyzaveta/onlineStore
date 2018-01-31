package com.company.store.view;

import com.company.store.MainApp;
import com.company.store.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AuthenticationController {
    @FXML
    private TextField login;
    @FXML
    private TextField loginNew;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordNew;
    @FXML
    private PasswordField confirmPassword;

    private MainApp mainApp;
    private User user;

    public AuthenticationController() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleLogIn(){
        Integer id = User.userDAO.authentication(login.getText(), password.getText());
        if(id!=null){
            user = User.userDAO.findEntityById(id);
            mainApp.showProductOverview();
        } else {
            errorView("Incorrect login or password");
        }
    }

    @FXML
    private void handleSignUp(){
      if(isCorrect()){
          user = new User(loginNew.getText(), email.getText(), passwordNew.getText());
          mainApp.showAuthentication();
      }
    }

    private boolean isCorrect(){
        String errorMessage = "";
        if(!passwordNew.getText().equals(confirmPassword.getText())){
            errorMessage+="Passwords are not equal!\n";
        }
        if(loginNew.getText().length()==0 || login.getText().length()>=20){
            errorMessage+="Login is too short or too long!\n";
        }
        if(!email.getText().endsWith("@gmail.com")){
            errorMessage+="It supports only Google Mail";
        }

        if(errorMessage.length()==0){
            return true;
        } else {
            errorView(errorMessage);
            return false;
        }
    }

    private void errorView(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}