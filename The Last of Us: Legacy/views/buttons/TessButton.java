package views.buttons;

import java.io.IOException;

import engine.Game;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import views.Main;
import views.scenes.CharacterSelection;

public class TessButton {
	private Button tess;
	
	public TessButton() {
		tess = new Button();
		
		Image tessImg = new Image("TessTransparent.png");
		ImageView tessView = new ImageView(tessImg);
		tessView.setFitHeight(Main.height * 0.05);
		tessView.setPreserveRatio(true);
		tess.setPrefHeight(Main.height * 0.05);
		tess.setPrefWidth(tessView.getFitWidth());
		tess.setGraphic(tessView);
		tess.setBackground(null);
		
		tess.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				BackgroundImage myBI = new BackgroundImage(new Image("TessSelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
				CharacterSelection.root.setBackground(new Background(myBI));
				
			}
		});
		
		tess.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(CharacterSelection.selected != null) {
					if(CharacterSelection.selected.getName().equals("Joel Miller")) {
						BackgroundImage myBI = new BackgroundImage(new Image("JoelSelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
					else if(CharacterSelection.selected.getName().equals("Ellie Williams")) {
						BackgroundImage myBI = new BackgroundImage(new Image("EllieSelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
					else if(CharacterSelection.selected.getName().equals("Bill")) {
						BackgroundImage myBI = new BackgroundImage(new Image("BillSelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
					else if(CharacterSelection.selected.getName().equals("Tess")) {
						BackgroundImage myBI = new BackgroundImage(new Image("TessSelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
					else if(CharacterSelection.selected.getName().equals("Henry Burell")) {
						BackgroundImage myBI = new BackgroundImage(new Image("HenrySelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
					else if(CharacterSelection.selected.getName().equals("Riley Abel")) {
						BackgroundImage myBI = new BackgroundImage(new Image("RileySelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
					else if(CharacterSelection.selected.getName().equals("Tommy Miller")) {
						BackgroundImage myBI = new BackgroundImage(new Image("TommySelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
					else if(CharacterSelection.selected.getName().equals("Henry")) {
						BackgroundImage myBI = new BackgroundImage(new Image("HenrySelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
						CharacterSelection.root.setBackground(new Background(myBI));
					}
				}
				else{
					BackgroundImage myBI = new BackgroundImage(new Image("BackgroundBlur.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
					CharacterSelection.root.setBackground(new Background(myBI));
				}
				
			}
			
		});
		
		tess.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				BackgroundImage myBI = new BackgroundImage(new Image("TessSelection.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
				CharacterSelection.root.setBackground(new Background(myBI));
				CharacterSelection.selected = Game.availableHeroes.get(2);
			}
			
		});
	}
	
	public Button getTess() {
		return tess;
	}
}
