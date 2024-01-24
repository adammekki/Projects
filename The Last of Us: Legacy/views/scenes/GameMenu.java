package views.scenes;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import views.Main;

public class GameMenu {
	private final Scene gameMenu;
	
	public GameMenu(){
		
		BackgroundImage myBI = new BackgroundImage(new Image("MenuScreenBackground.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		StackPane root = new StackPane();
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		root.setBackground(new Background(myBI));
		
		Text play = new Text("PLAY");
		play.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
		play.setFill(Color.GREY);
		play.setTranslateX(Main.width * -0.3);
		play.setTranslateY(Main.height * 0.1);
		play.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				play.setFill(Color.WHITE);
			}
			
		});
		
		play.setOnMouseExited(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent arg0) {
				play.setFill(Color.GREY);
			}
		});
		
		play.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Main.fadeOut(root);
				
				Main.stage.setScene(new views.scenes.CharacterSelection().getCharacterSelection());
				Main.stage.setResizable(false);
				Main.stage.setFullScreen(true);			}
			
		});
		
		Text exit = new Text("EXIT");
		exit.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
		exit.setFill(Color.GREY);
		exit.setTranslateX(Main.width * -0.3);
		exit.setTranslateY(Main.height * 0.2);
		exit.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				exit.setFill(Color.WHITE);
			}
			
		});
		
		exit.setOnMouseExited(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent arg0) {
				exit.setFill(Color.GREY);
			}
		});
		
		exit.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				Main.fadeOut(root);
				
				Platform.exit();			
			}
			
		});
		
		root.getChildren().addAll(play, exit);
		
		gameMenu = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
		
	}
	
	public Scene getGameMenu() {
		return gameMenu;
	}
}
