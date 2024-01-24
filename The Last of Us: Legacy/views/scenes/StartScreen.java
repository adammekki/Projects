package views.scenes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;
import views.Main;

public class StartScreen {

	private final Scene startScreen;
	
	public StartScreen() {
		BackgroundImage myBI = new BackgroundImage(new Image("TheLastOfUsLegacyTitleScreen.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		StackPane root = new StackPane();
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		root.setBackground(new Background(myBI));
		
		
		Text start = new Text("PRESS ANY BUTTON");
		start.setFont(Font.font("Pixelated", FontWeight.NORMAL, FontPosture.REGULAR, Main.height * Main.width*0.000015));
		start.setFill(Color.WHITE);
		start.setTranslateX(Main.width * -0.3);
		start.setTranslateY(Main.height * 0.2);
		
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(2000));
		fade.setFromValue(0);
		fade.setToValue(10);
		fade.setCycleCount(Timeline.INDEFINITE);
		fade.setAutoReverse(true);
		fade.setNode(start);
		fade.play();
		
		 
		root.getChildren().add(start);
		startScreen = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
		startScreen.setFill(Color.BLACK);
		startScreen.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent arg0) {
				Main.fadeOut(root);
				
				Main.stage.setScene(new views.scenes.GameMenu().getGameMenu());
				Main.stage.setResizable(false);
				Main.stage.setFullScreen(true);
			}
			
		});
		startScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
				Main.fadeOut(root);
				
				Main.stage.setScene(new views.scenes.GameMenu().getGameMenu());;
				Main.stage.setResizable(false);
				Main.stage.setFullScreen(true);
			}
			
		});

	}

	public Scene getStartScreen() {
		return startScreen;
	}
	
	
}
