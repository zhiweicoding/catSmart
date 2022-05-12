package io.github.zhiweicoding.catsmart;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.zhiweicoding.catsmart.controllers.CatSmartController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CatSmartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoadFontSupport.loadFont();
        CSSFX.start();

        FXMLLoader loader = new FXMLLoader(SelfXMlLoader.loadURL("fxml/CatSmartApp.fxml"));
        loader.setControllerFactory(c -> new CatSmartController(primaryStage));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("智能后台管理");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
