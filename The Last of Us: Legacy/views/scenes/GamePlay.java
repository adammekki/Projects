package views.scenes;



import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import views.Main;
import views.buttons.GameButtons;

public class GamePlay {
	private Scene gamePlay;
	private static Image cellImg;
	private static ImageView imgView;
	private static GridPane mapCells;
	private static Hero selected = Game.heroes.get(0);
	private static GameButtons buttons[][] = new GameButtons[Game.map.length][Game.map[0].length];
	private static Text charStats = new Text();
	private static Text error;
	private static StackPane superPane = new StackPane();
	private static StackPane statsPane=new StackPane();
	private static StackPane leftpart = new StackPane();
	private static ImageView charIcon;
	private static ImageView health = new ImageView();
	private static ImageView actions;
	private static BorderPane root = new BorderPane();
	private static Image medicSpecial = new Image("medicSpecial.png");
	private static Image fighterSpecial = new Image("fighterSpecial.png");
	private static Image explorerSpecial = new Image("explorerSpecial.png");
	private static ImageView useSpecialButton;
	public static int selectedHealth;
	private static StackPane actionsPane = new StackPane();
	
	public GamePlay() {

		BackgroundImage myBI = new BackgroundImage(new Image("Gameplay Background.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
		//GridPane root = new GridPane();
		root.setBackground(new Background(myBI));
		//RowConstraints gameRow = new RowConstraints();
		//gameRow.setPrefHeight(Main.height * 0.75);

		//RowConstraints statsRow = new RowConstraints();
		//statsRow.setPrefHeight(Main.height * 0.25);

		//root.getRowConstraints().addAll(gameRow, statsRow);
		selectedHealth = selected.getCurrentHp();
		StackPane endTurnPane = new StackPane();
		Image endTurn = new Image("endTurnButton.png");
		ImageView endTurnButton = new ImageView(endTurn);
		endTurnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					Game.endTurn();
					if(selected!=null) {
						if(selectedHealth > selected.getCurrentHp()) {
							errorMessage("YOU HAVE BEEN ATTACKED BY A ZOMBIE!");
						}
						selectedHealth = selected.getCurrentHp();
					}
					checkCharacterDeath();
					checkGame();
					
				} catch (NotEnoughActionsException | InvalidTargetException e) {
					errorMessage(e.getMessage());
				}
				
			}
			
		});
		endTurnButton.setFitWidth(Main.width * 0.08);
		endTurnButton.setFitHeight(Main.height * 0.08);
		
		endTurnPane.getChildren().add(endTurnButton);
		endTurnPane.setTranslateX(Main.width * -0.05);
		endTurnPane.setTranslateY(Main.height * - 0.1);
		
		Image attack = new Image("attackButton.png");
		ImageView attackButton = new ImageView(attack);
		attackButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					selected.attack();
					selectedHealth = selected.getCurrentHp();
					checkCharacterDeath();
					checkGame();
					
				} catch (NotEnoughActionsException | InvalidTargetException e) {
					errorMessage(e.getMessage());
				}
				
			}
			
		});
		attackButton.setFitHeight(Main.width * 0.05);
		attackButton.setFitWidth(Main.width * 0.05);
		attackButton.setTranslateY(Main.height * -0.05);
		
		actionsPane.getChildren().add(attackButton);
		actionsPane.setPrefSize(Main.width * 0.05, Main.height * 0.05);
		actionsPane.setTranslateX(Main.width * 0.7);
		actionsPane.setTranslateY(Main.height * - 0.1);
		
		Image cure = new Image("curebutton.png");
		ImageView cureButton = new ImageView(cure);
		cureButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
					try {
						selected.cure();
						selectedHealth = selected.getCurrentHp();
						statsPane.getChildren().remove(charStats);
						if(selected!=null) {
							charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
									"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
						}
						else {
							charStats = new Text("Choose a character");
						}
						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						checkCharacterDeath();
						checkGame();
					} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException e) {
						errorMessage(e.getMessage());
					}
				
			}
			
		});
		cureButton.setFitWidth(Main.width * 0.05);
		cureButton.setFitHeight(Main.width * 0.05);
		cureButton.setTranslateX(Main.width * 0.06);
		cureButton.setTranslateY(Main.height * -0.05);
		actionsPane.getChildren().add(cureButton);
		//curePane.setPrefSize(Main.width * 0.05, Main.height * 0.05);
		//curePane.setTranslateX(Main.width * 0.75);
		//curePane.setTranslateY(Main.height * - 0.1);
		if(selected instanceof Fighter) {
			useSpecialButton = new ImageView(fighterSpecial);
		}
		else if(selected instanceof Medic) {
			useSpecialButton = new ImageView(medicSpecial);
		}
		if(selected instanceof Explorer) {
			useSpecialButton = new ImageView(explorerSpecial);
		}
		useSpecialButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
					try {
						selected.useSpecial();
						selectedHealth = selected.getCurrentHp();
						statsPane.getChildren().remove(charStats);
						if(selected!=null) {
							charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
									"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
						}
						else {
							charStats = new Text("Choose a character");
						}
						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						checkCharacterDeath();
						updateIcon();
						updateActions();
						updateHealth();
						setCells();
						checkGame();
					} catch (NoAvailableResourcesException | InvalidTargetException e) {
						errorMessage(e.getMessage());
					}
				
			}
			
		});
		useSpecialButton.setFitWidth(Main.width * 0.05);
		useSpecialButton.setFitHeight(Main.width * 0.05);
		useSpecialButton.setTranslateX(Main.height * 0.05);
		useSpecialButton.setTranslateY(Main.height * 0.05);
		actionsPane.getChildren().add(useSpecialButton);

		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPrefWidth((Main.width - (Main.height * 0.75))/2);

		ColumnConstraints col2 = new ColumnConstraints();
		col2.setPrefWidth(Main.height * 0.75);

		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(5);


		mapCells = new GridPane();
		mapCells.setPrefWidth(Main.height * 0.75);
		mapCells.setPrefHeight(Main.height * 0.75);
		initializeButtons();
		setCells();


		statsPane = new StackPane();
		if(selected!=null) {
			charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
					"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
		}
		else {
			charStats = new Text("Choose a character".toUpperCase());
		}
		charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
		charStats.setFill(Color.BLACK);
		statsPane.getChildren().add(charStats);
		statsPane.setTranslateY(Main.height * -0.1);
		statsPane.setTranslateX(Main.width * 0.35);

		
		//leftpart.setPrefHeight(Main.height*0.25);
		leftpart.setPrefWidth((Main.width - (Main.height * 0.75))/2);

		ImageView upArrow = new ImageView(new Image("Up Arrow.png"));
		upArrow.setTranslateY(Main.height * -0.36);
		upArrow.setTranslateX(Main.width * -0.020);
		upArrow.setFitHeight(Main.height * 0.08);
		upArrow.setFitWidth(Main.height * 0.08);
		upArrow.setOnMouseClicked(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					if(selected != null) {	
						selected.move(Direction.UP);
						if(selected!=null) {
							if(selectedHealth > selected.getCurrentHp()) {
								errorMessage("YOU STEPPED ON A TRAP!");
							}
							selectedHealth = selected.getCurrentHp();
						}
						statsPane.getChildren().remove(charStats);
						if(selected!=null) {
							charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
									"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
						}
						else {
							charStats = new Text("Choose a character");
						}
						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						checkCharacterDeath();
						checkGame();
					}
				} catch (MovementException e) {
					errorMessage(e.getMessage());
				} catch (NotEnoughActionsException e) {
					errorMessage(e.getMessage());
				}

			}

		});

		ImageView downArrow = new ImageView(new Image("Down Arrow.png"));
		downArrow.setTranslateX(Main.width * -0.020);
		downArrow.setTranslateY(Main.height * -0.2);
		downArrow.setFitHeight(Main.height * 0.08);
		downArrow.setFitWidth(Main.height * 0.08);
		downArrow.setOnMouseClicked(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					if(selected != null) {
						selected.move(Direction.DOWN);
						if(selected!=null) {
							if(selectedHealth > selected.getCurrentHp()) {
								errorMessage("YOU STEPPED ON A TRAP!");
							}
							selectedHealth = selected.getCurrentHp();
						}
						statsPane.getChildren().remove(charStats);
						if(selected!=null) {
							charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
									"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
						}
						else {
							charStats = new Text("Choose a character");
						}
						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						checkCharacterDeath();
						checkGame();
					}
				} catch (MovementException e) {
					errorMessage(e.getMessage());
				} catch (NotEnoughActionsException e) {
					errorMessage(e.getMessage());
				}
			}

		});

		ImageView rightArrow = new ImageView(new Image("Right Arrow.png"));
		rightArrow.setTranslateY(Main.height * -0.280);
		rightArrow.setTranslateX(Main.width * 0.030);
		rightArrow.setFitHeight(Main.height * 0.08);
		rightArrow.setFitWidth(Main.height * 0.08);
		rightArrow.setOnMouseClicked(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					if(selected != null) {

						selected.move(Direction.RIGHT);
						if(selected!=null) {
							if(selectedHealth > selected.getCurrentHp()) {
								errorMessage("YOU STEPPED ON A TRAP!");
							}
							selectedHealth = selected.getCurrentHp();
						}
						statsPane.getChildren().remove(charStats);
						if(selected!=null) {
							charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
									"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
						}
						else {
							charStats = new Text("Choose a character");
						}
						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						checkCharacterDeath();
						checkGame();
					}
				} catch (MovementException e) {
					errorMessage(e.getMessage());
				} catch (NotEnoughActionsException e) {
					errorMessage(e.getMessage());
				}
			}

		});

		ImageView leftArrow = new ImageView(new Image("Left Arrow.png"));
		leftArrow.setTranslateY(Main.height * -0.280);
		leftArrow.setTranslateX(Main.width * -0.070);
		leftArrow.setFitHeight(Main.height * 0.08);
		leftArrow.setFitWidth(Main.height * 0.08);
		leftArrow.setOnMouseClicked(new EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				try {
					if(selected != null) {
						selected.move(Direction.LEFT);
						if(selected!=null) {
							if(selectedHealth > selected.getCurrentHp()) {
								errorMessage("YOU STEPPED ON A TRAP!");
							}
							selectedHealth = selected.getCurrentHp();
						}
						statsPane.getChildren().remove(charStats);
						if(selected!=null) {
							charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
									"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
						}
						else {
							charStats = new Text("Choose a character");
						}
						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						checkCharacterDeath();
						checkGame();
					}
				} catch (MovementException e) {
					errorMessage(e.getMessage());
				} catch (NotEnoughActionsException e) {
					errorMessage(e.getMessage());
				}
			}

		});
		leftpart.getChildren().addAll(upArrow, downArrow, rightArrow, leftArrow);


		
		//root.add(extras, 0, 1);

		StackPane bottomPart = new StackPane();
		bottomPart.getChildren().addAll(statsPane, endTurnPane, actionsPane);
		bottomPart.setPrefHeight(Main.height * 0.15);
		bottomPart.setPrefWidth(Main.width);
		bottomPart.setTranslateX(Main.width * -0.35);
		bottomPart.setTranslateY(Main.height * 0.05);
		initializeButtons();
		
		setCells();
		
		setActions();

		updateIcon();

		updateHealth();

		updateActions();
		leftpart.setTranslateY(Main.height * -0.5);
		leftpart.setTranslateY(Main.height * 0.5);
		root.setLeft(leftpart);
		root.setCenter(mapCells);
		root.setBottom(bottomPart);
		superPane.getChildren().add(root);
		gamePlay = new Scene(superPane, Main.width, Main.height);
	}

	public Scene getGamePlay() {
		return gamePlay;
	}

	private void initializeButtons() {
		for(int i = 0; i < Game.map.length; i++) {
			for(int j = 0; j < Game.map[0].length; j++) {
				GameButtons newButton = new GameButtons(i, j);
				newButton.setPrefSize((Main.height * 0.75)/15, (Main.height * 0.75)/15);
				newButton.setPadding(new Insets(0));
				newButton.setStyle("-fx-background-color: transparent");
				mapCells.add(newButton, j, i);
				buttons[i][j] = newButton;

			}
		}
	}
	private void setCells() {
		for(int i = 0; i < Game.map.length; i++) {
			for(int j = 0; j < Game.map[0].length; j++) {
				ImageView visibilityView = new ImageView(new Image("cloud.png"));

				if(!Game.map[Game.map.length - 1 - i][j].isVisible()) {
					imgView = visibilityView;					
				}

				else if (Game.map[Game.map.length - 1 - i][j] instanceof CollectibleCell) {
					imgView = setImages(i, j);
				}

				else if (Game.map[Game.map.length - 1 - i][j] instanceof CharacterCell) {
					CharacterCell characterCell = (CharacterCell) Game.map[Game.map.length - 1 - i][j];
					if(characterCell.getCharacter() != null) {
						imgView = setImages(i, j);

					}
					else {
						cellImg = new Image("grass01.png", 0, 0, false, true);
						imgView = new ImageView(cellImg);
					}

				}
				else {
					cellImg = new Image("grass01.png", 0, 0, false, true);
					imgView = new ImageView(cellImg);
				}
				imgView.setFitHeight((Main.height * 0.75)/15);
				imgView.setFitWidth((Main.height * 0.75)/15);

				buttons[i][j].setGraphic(imgView);
			}
		}
	}

	public ImageView setImages(int i, int j) {
		if(Game.map[Game.map.length - 1 - i][j] instanceof CharacterCell) {
			CharacterCell cell1 = (CharacterCell) (Game.map[Game.map.length - 1 - i][j]);
			if(cell1.getCharacter() instanceof Zombie) {
				cellImg = new Image("Zombie.png");
			}
			else if(cell1.getCharacter() instanceof Hero){

				if(cell1.getCharacter().getName().equals("Joel Miller")) {
					cellImg = new Image("Joel Miller Char.png");
				}
				else if(cell1.getCharacter().getName().equals("Ellie Williams")) {
					cellImg = new Image("Ellie Williams Char.png");
				}
				else if(cell1.getCharacter().getName().equals("Tess")) {
					cellImg = new Image("Tess Char.png");
				}
				else if(cell1.getCharacter().getName().equals("Bill")) {
					cellImg = new Image("Bill Char.png");
				}

				else if(cell1.getCharacter().getName().equals("Riley Abel")) {
					cellImg = new Image("Riley Abel Char.png");
				}

				else if(cell1.getCharacter().getName().equals("David")) {
					cellImg = new Image("David Char.png");
				}

				else if(cell1.getCharacter().getName().equals("Henry Burell")) {
					cellImg = new Image("Henry Burell Char.png");
				}

				else{
					cellImg = new Image("Tommy Miller Char.png");
				}
			}
		}
		else if(Game.map[Game.map.length - 1 - i][j] instanceof CollectibleCell) {

			CollectibleCell cell1 = (CollectibleCell) (Game.map[Game.map.length - 1 - i][j]);
			if(cell1.getCollectible() instanceof Vaccine) {
				cellImg = new Image("Vaccine.png");
			}
			else {
				cellImg = new Image("Supplies.png");
			}
		}

		return new ImageView(cellImg);
	}

	public void updateHealth() {
		if(selected!=null) {
			leftpart.getChildren().remove(health);
			if(selected.getCurrentHp() == selected.getMaxHp()) {
				health = new ImageView(new Image("fullhealth.png"));
			}
			else if(selected.getCurrentHp() > selected.getMaxHp() * 0.8) {
				health = new ImageView(new Image("80 perc health.png"));
			}
			else if(selected.getCurrentHp() > selected.getMaxHp() * 0.6) {
				health = new ImageView(new Image("60 perc health.png"));
			}
			else if(selected.getCurrentHp() > selected.getMaxHp() * 0.4) {
				health = new ImageView(new Image("40 perc health.png"));
			}
			else if(selected.getCurrentHp() > selected.getMaxHp() * 0.2) {
				health = new ImageView(new Image("20 perc health.png"));
			}
			health.setPreserveRatio(true);
			health.setFitHeight(Main.height*0.02);
			health.setTranslateY(Main.height * -0.875);
			health.setTranslateX(Main.width * -0.01);
			leftpart.getChildren().add(health);

		}
		else {
			leftpart.getChildren().remove(health);
		}
	}

	public void updateActions() {

		if(selected!= null) {
			leftpart.getChildren().remove(actions);
			if(selected.getActionsAvailable() == 8) {
				actions = new ImageView(new Image("8 bar.png"));
			}

			else if(selected.getActionsAvailable() == 7) {
				actions = new ImageView(new Image("7 bar.png"));
			}

			else if(selected.getActionsAvailable() == 6) {
				actions = new ImageView(new Image("6 bar.png"));
			}

			else if(selected.getActionsAvailable() == 5) {
				actions = new ImageView(new Image("5 bar.png"));
			}

			else if(selected.getActionsAvailable() == 4) {
				actions = new ImageView(new Image("4 bar.png"));
			}

			else if(selected.getActionsAvailable() == 3) {
				actions = new ImageView(new Image("3 bar.png"));
			}

			else if(selected.getActionsAvailable() == 2) {
				actions = new ImageView(new Image("2 bar.png"));
			}

			else if(selected.getActionsAvailable() == 1) {
				actions = new ImageView(new Image("1 bar.png"));
			}

			else if(selected.getActionsAvailable() == 0) {
				actions = new ImageView(new Image("0 bar.png"));
			}
			actions.setPreserveRatio(true);
			actions.setFitHeight(Main.height*0.04);
			actions.setTranslateY(Main.height * -0.825);
			actions.setTranslateX(Main.width * -0.02);
			leftpart.getChildren().add(actions);
		}
		else {
			leftpart.getChildren().remove(actions);
		}
	}

	public void updateIcon() {

		if(selected!= null) {
			leftpart.getChildren().remove(charIcon);
			if(selected.getName().equals("Joel Miller")) {
				charIcon = new ImageView(new Image("Joel Icon.png"));
			}
			else if(selected.getName().equals("Ellie Williams")) {
				charIcon = new ImageView(new Image("Ellie Icon.png"));
			}
			else if(selected.getName().equals("Tess")) {
				charIcon = new ImageView(new Image("Tess Icon.png"));
			}
			else if(selected.getName().equals("David")) {
				charIcon = new ImageView(new Image("David Icon.png"));
			}
			else if(selected.getName().equals("Henry Burell")) {
				charIcon = new ImageView(new Image("Henry Icon.png"));
			}
			else if(selected.getName().equals("Bill")) {
				charIcon = new ImageView(new Image("Bill Icon.png"));
			}
			else if(selected.getName().equals("Riley Abel")) {
				charIcon = new ImageView(new Image("Riley Icon.png"));
			}
			else{
				charIcon = new ImageView(new Image("Tommy Icon.png"));
			}
			charIcon.setPreserveRatio(true);
			charIcon.setFitHeight(Main.height*0.13);
			charIcon.setTranslateX(Main.width * -0.07);
			charIcon.setTranslateY(Main.height * -0.85);
			leftpart.getChildren().add(charIcon);

		}

		else {
			leftpart.getChildren().remove(charIcon);
		}
	}

	public void setActions() {
		for(int i = 0; i < buttons.length; i++) {
			for(int j = 0; j < buttons[0].length; j++) {
				GameButtons button = new GameButtons(i,j);
				buttons[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						statsPane.getChildren().remove(charStats);
						if(Game.map[Game.map.length - 1 - button.getX()][button.getY()] instanceof CharacterCell) {
							CharacterCell characterCell = (CharacterCell) Game.map[Game.map.length - 1 - button.getX()][button.getY()];
							if(characterCell.getCharacter() instanceof Hero && selected == null) {
								selected = (Hero) characterCell.getCharacter();
								setSpecialButton();
								selectedHealth = selected.getCurrentHp();
								statsPane.getChildren().remove(charStats);
								charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
										"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
							}
							else if((characterCell.getCharacter() instanceof Hero || characterCell.getCharacter() instanceof Zombie) && selected!= null) {
								selected.setTarget(characterCell.getCharacter());
							}
							else {
								selected = null;
								selectedHealth = 0;
								charStats = new Text("Choose a character");
							}

						}else {
							selected = null;
							selectedHealth = 0;
							charStats = new Text("Choose a character");
						}

						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						updateIcon();
						updateHealth();
						updateActions();
					}
				});

			}
		}
	}
	
	public void checkGame() {
		if(Game.checkWin()) {
			Main.stage.setScene(new views.scenes.WinScreen().getWinScreen());
			Main.stage.setFullScreen(true);
			Main.stage.setResizable(false);
		}
		else if(Game.checkGameOver()) {
			Main.stage.setScene(new views.scenes.GameOverScreen().getGameOverScene());
			Main.stage.setFullScreen(true);
			Main.stage.setResizable(false);
		}
	}
	public void errorMessage(String s) {
		error = new Text(s.toUpperCase());
		error.setFont(Font.font("Pixelated", FontWeight.BOLD, FontPosture.REGULAR, Main.height * Main.width*0.00001));
		error.setFill(Color.RED);
		TranslateTransition tt = new TranslateTransition(Duration.millis(2000), error);
		tt.setByY(-0.5 * Main.height);

		FadeTransition ft = new FadeTransition(Duration.millis(2000), error);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(0);
		ft.setAutoReverse(false);
		tt.play();
		ft.play();
		superPane.getChildren().add(error);
	}
	
	public void checkCharacterDeath() {
		if(selected != null) {
			if(selected.getCurrentHp() <= 0) {
				selected.onCharacterDeath();
				selected = null;
				selectedHealth = 0;
			}
		}
		for(int i = 0; i < Game.heroes.size(); i++) {
			if(Game.heroes.get(i).getCurrentHp() <= 0) {
				Game.heroes.get(i).onCharacterDeath();
			}
		}
		for(int i = 0; i < Game.zombies.size(); i++) {
			if(Game.zombies.get(i).getCurrentHp() <= 0) {
				Game.zombies.get(i).onCharacterDeath();
				selected.setTarget(null);
			}
		}
		updateActions();
		updateIcon();
		updateHealth();
		setCells();
	}
	
	private void setSpecialButton() {
		actionsPane.getChildren().remove(useSpecialButton);
		if(selected instanceof Fighter) {
			useSpecialButton = new ImageView(fighterSpecial);
		}
		else if(selected instanceof Medic) {
			useSpecialButton = new ImageView(medicSpecial);
		}
		if(selected instanceof Explorer) {
			useSpecialButton = new ImageView(explorerSpecial);
		}
		useSpecialButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
					try {
						selected.useSpecial();
						selectedHealth = selected.getCurrentHp();
						statsPane.getChildren().remove(charStats);
						if(selected!=null) {
							charStats = new Text("Vaccines Available: ".toUpperCase() + selected.getVaccineInventory().size() + 
									"\nSupplies Available: ".toUpperCase() + selected.getSupplyInventory().size());
						}
						else {
							charStats = new Text("Choose a character");
						}
						charStats.setFont(new Font("Pixelated", Main.height * Main.width*0.000025));
						charStats.setFill(Color.BLACK);
						statsPane.getChildren().add(charStats);
						checkCharacterDeath();
						updateIcon();
						updateActions();
						updateHealth();
						setCells();
						checkGame();
					} catch (NoAvailableResourcesException | InvalidTargetException e) {
						errorMessage(e.getMessage());
					}
				
			}
			
		});
		useSpecialButton.setFitWidth(Main.width * 0.05);
		useSpecialButton.setFitHeight(Main.width * 0.05);
		useSpecialButton.setTranslateX(Main.height * 0.05);
		useSpecialButton.setTranslateY(Main.height * 0.05);
		actionsPane.getChildren().add(useSpecialButton);
	}
}
