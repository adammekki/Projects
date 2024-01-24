package views;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{

	public static Stage stage;
	
	public static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static final int width = gd.getDisplayMode().getWidth();
	public static final int height = gd.getDisplayMode().getHeight();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setTitle("The Last of Us - Legacy");
		stage.setScene(new views.scenes.StartScreen().getStartScreen());
		stage.setResizable(false);
		stage.setFullScreen(true);
		stage.show();
	}

	public static void fadeOut(Node root) {
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(2000));
		fade.setFromValue(10);
		fade.setToValue(0);
		fade.setCycleCount(1);
		fade.setAutoReverse(false);
		fade.setNode(root);
		fade.play();
	}
	
	public static void fadeIn(Node root) {
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(2000));
		fade.setFromValue(0);
		fade.setToValue(10);
		fade.setCycleCount(1);
		fade.setAutoReverse(false);
		fade.setNode(root);
		fade.play();
	}
}
