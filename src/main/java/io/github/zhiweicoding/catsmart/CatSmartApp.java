package io.github.zhiweicoding.catsmart;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.MFXResourcesLoader;
import io.github.zhiweicoding.catsmart.controllers.DemoController;
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
		CSSFX.start();

		FXMLLoader loader = new FXMLLoader(MFXResourcesLoader.loadURL("fxml/Demo.fxml"));
		loader.setControllerFactory(c -> new DemoController(primaryStage));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MaterialFX Demo");
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch();
	}
}
