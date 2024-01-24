package views.scenes;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;
import views.Main;

public class GameOverScreen {
	
	private final Scene gameOverScene;
	
	public Scene getGameOverScene() {
		return gameOverScene;
	}

	public GameOverScreen(){
		
		BackgroundImage myBI = new BackgroundImage(new Image("normaldayinNY.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		BorderPane borderpane = new BorderPane();
		StackPane root = new StackPane();
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		borderpane.setBackground(new Background(myBI));
		
		Image zombie = new Image("ZombieWin.png");
		Image zombieFlipped = new Image("Zombie Right.png");
		
		ImageView imgzombie1 = new ImageView(zombie);
		ImageView imgzombie2 = new ImageView(zombie);
		ImageView imgzombie3 = new ImageView(zombie);
		ImageView imgzombie4 = new ImageView(zombie);
		ImageView imgzombie5 = new ImageView(zombie);
		ImageView imgzombie6 = new ImageView(zombieFlipped);
		ImageView imgzombie7 = new ImageView(zombieFlipped);
		ImageView imgzombie8 = new ImageView(zombieFlipped);
		ImageView imgzombie9 = new ImageView(zombieFlipped);
		ImageView imgzombie10 = new ImageView(zombieFlipped);
		
		
		borderpane.setCenter(root);
		root.setTranslateY(300);
		
		root.getChildren().addAll(imgzombie1, imgzombie2, imgzombie3, imgzombie4, imgzombie5, imgzombie6, imgzombie7, imgzombie8, imgzombie9, imgzombie10);
		
		imgzombie1.setScaleX(0.15);
		imgzombie1.setScaleY(0.15);
		
		imgzombie2.setScaleX(0.15);
		imgzombie2.setScaleY(0.15);
		
		imgzombie3.setScaleX(0.15);
		imgzombie3.setScaleY(0.15);
		
		imgzombie4.setScaleX(0.15);
		imgzombie4.setScaleY(0.15);
		
		imgzombie5.setScaleX(0.15);
		imgzombie5.setScaleY(0.15);
		
		imgzombie6.setScaleX(0.15);
		imgzombie6.setScaleY(0.15);
		
		imgzombie7.setScaleX(0.15);
		imgzombie7.setScaleY(0.15);
		
		imgzombie8.setScaleX(0.15);
		imgzombie8.setScaleY(0.15);
		
		imgzombie9.setScaleX(0.15);
		imgzombie9.setScaleY(0.15);
		
		imgzombie10.setScaleX(0.15);
		imgzombie10.setScaleY(0.15);
		
		imgzombie1.setTranslateX(100);
		imgzombie1.setTranslateY(100);
		imgzombie2.setTranslateX(450);
		//imgzombie3.setTranslateX(-150);
		imgzombie4.setTranslateY(100);
		imgzombie4.setTranslateX(-250);
		//imgzombie5.setTranslateX(-150);
		imgzombie6.setTranslateX(300);
		imgzombie6.setTranslateY(100);
		imgzombie7.setTranslateX(150);
		imgzombie8.setTranslateX(-340);
		imgzombie8.setTranslateY(50);
		imgzombie9.setTranslateX(-450);
		imgzombie9.setTranslateY(150);
		imgzombie10.setTranslateX(-700);
		imgzombie10.setTranslateY(100);
		
		
		Text lose = new Text("GAME OVER!");
		lose.setFont(Font.font("Pixelated", FontWeight.NORMAL, FontPosture.REGULAR, Main.height * Main.width*0.000015));
		lose.setFill(Color.RED);
		lose.setTranslateX(Main.width / 2 -100);
		lose.setTranslateY(Main.height /2);
		
		lose.setScaleX(3);
		lose.setScaleY(3);
		
		FadeTransition fade = new FadeTransition();
		fade.setDuration(Duration.millis(500));
		fade.setFromValue(0);
		fade.setToValue(10);
		fade.setCycleCount(Timeline.INDEFINITE);
		fade.setAutoReverse(true);
		fade.setNode(lose);
		fade.play();
		borderpane.setTop(lose);
		
		Text exit = new Text("EXIT");
		exit.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
		exit.setFill(Color.GREY);
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
		
		borderpane.setRight(exit);
		exit.setScaleX(2);
		exit.setScaleY(2);
		exit.setTranslateX(-70);
		exit.setTranslateY(950);
		
		gameOverScene = new Scene(borderpane, screenBounds.getWidth(), screenBounds.getHeight());
		gameOverScene.setFill(Color.BLACK);
	}

}
