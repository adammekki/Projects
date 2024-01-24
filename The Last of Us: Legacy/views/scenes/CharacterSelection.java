package views.scenes;

import java.io.IOException;

import engine.Game;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.characters.Hero;
import views.Main;

public class CharacterSelection {
	public final Scene characterSelection;
	public static Hero selected;
	public static StackPane root = new StackPane();
	public CharacterSelection() {
		try {
			Game.loadHeroes("C:\\Users\\Dell\\Downloads\\Team24\\TheLastOfUsLegacy\\Heroes.csv");
		} catch (IOException e) {
			errorMessage(e.getMessage());
		}
		BackgroundImage myBI = new BackgroundImage(new Image("BackgroundBlur.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		root.setBackground(new Background(myBI));
		
		HBox leaf = new HBox();
		leaf.getChildren().add(new views.buttons.JoelMillerButton().getJoelMiller());
		leaf.getChildren().add(new views.buttons.EllieWilliamsButton().getEllieWilliams());
		leaf.getChildren().add(new views.buttons.TessButton().getTess());
		leaf.getChildren().add(new views.buttons.RileyAbelButton().getRileyAbel());
		leaf.getChildren().add(new views.buttons.TommyMillerButton().getTommyMiller());
		leaf.getChildren().add(new views.buttons.BillButton().getBill());
		leaf.getChildren().add(new views.buttons.DavidButton().getDavid());
		leaf.getChildren().add(new views.buttons.HenryBurellButton().getHenryBurell());
		leaf.setTranslateX(Main.width * 0.37);
		leaf.setTranslateY(Main.height * 0.83);
		root.getChildren().add(leaf);
		
		Button select = new Button();
		Image img = new Image("selectButton.png");
		ImageView selectView = new ImageView(img);
		selectView.setFitHeight(Main.height * 0.05);
		selectView.setPreserveRatio(true);
		select.setGraphic(selectView);
		select.setBackground(null);
		select.setPrefSize(selectView.getFitWidth(), selectView.getFitHeight());
		select.setTranslateY(Main.height * 0.42);
		select.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(selected != null) {
					Game.startGame(selected);
					Main.stage.setScene(new views.scenes.GamePlay().getGamePlay());
					Main.stage.setFullScreen(true);
					Main.stage.setResizable(false);
				}
				
			}
			
		});
		root.getChildren().add(select);
		
		characterSelection = new Scene(root, Main.width, Main.height);
		Main.stage.setScene(characterSelection);
		Main.stage.show();
		Main.stage.setResizable(false);
		Main.stage.setFullScreen(true);
	}

	public Scene getCharacterSelection() {
		return characterSelection;
	}
	
	public void errorMessage(String s) {
		Text error = new Text(s.toUpperCase());
		error.setFont(Font.font("Pixelated", FontWeight.BOLD, FontPosture.REGULAR, Main.height * Main.width*0.00001));
		error.setFill(Color.RED);
		//TranslateTransition tt = new TranslateTransition(Duration.millis(2000), error);
		//tt.setByY(-0.5 * Main.height);

		//FadeTransition ft = new FadeTransition(Duration.millis(2000), error);
		//ft.setFromValue(1.0);
		//ft.setToValue(0);
		//ft.setCycleCount(0);
		//ft.setAutoReverse(false);
		//tt.play();
		//ft.play();
		root.getChildren().add(error);
	}
	
}
