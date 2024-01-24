package views.scenes;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
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

public class WinScreen {

	private final Scene winScreen;
	
	public WinScreen(){
	BackgroundImage myBI = new BackgroundImage(new Image("normaldayinNY.png", 0, 0, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(1.0, 1.0, true, true, false, false));
	BorderPane borderpane = new BorderPane();
	StackPane root = new StackPane();
	Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	borderpane.setBackground(new Background(myBI));
	
	Image bill = new Image("BillWin.png");
	ImageView imgbill = new ImageView(bill);
	
	Image david = new Image("David Left.png");
	ImageView imgdavid = new ImageView(david);
	
	Image ellie = new Image("Ellie Williams Left.png");
	ImageView imgellie = new ImageView(ellie);
	
	Image henry = new Image("Henry BurellWin.png");
	ImageView imghenry = new ImageView(henry);
	
	Image joel = new Image("Joel MillerWin.png");
	ImageView imgjoel = new ImageView(joel);
	
	Image riley = new Image("Riley AbelWin.png");
	ImageView imgriley = new ImageView(riley);
	
	Image tess = new Image("TessWin.png");
	ImageView imgtess = new ImageView(tess);
	
	Image tommy = new Image("Tommy Miller Left.png");
	ImageView imgtommy = new ImageView(tommy);
	

	borderpane.setCenter(root);
	root.setTranslateY(300);
	
	root.getChildren().add(imgbill);
	root.getChildren().add(imgdavid);
	root.getChildren().add(imgellie);
	root.getChildren().add(imghenry);
	root.getChildren().add(imgjoel);
	root.getChildren().add(imgriley);
	root.getChildren().add(imgtess);
	root.getChildren().add(imgtommy);
	
	TranslateTransition translateTransitionBill = new TranslateTransition(Duration.seconds(1), imgbill);
	translateTransitionBill.setToY(-50); // Set the Y translation distance
	translateTransitionBill.setAutoReverse(true); // Make the animation reverse
	translateTransitionBill.setCycleCount(Animation.INDEFINITE); // Repeat the animation indefinitely
	translateTransitionBill.play(); // Start the animation
	
	TranslateTransition translateTransitionRiley = new TranslateTransition(Duration.seconds(0.5), imgriley);
	translateTransitionRiley.setToX(-20);
	translateTransitionRiley.setAutoReverse(true);
	translateTransitionRiley.setCycleCount(Animation.INDEFINITE);
	translateTransitionRiley.play();
	
	imgbill.setTranslateX(-320);
	imgdavid.setTranslateX(450);
	imghenry.setTranslateX(-150);
	imgriley.setTranslateY(100);
	imgriley.setTranslateX(-250);
	imgtess.setTranslateX(-450);
	imgtommy.setTranslateX(300);
	imgtommy.setTranslateY(100);
	imgellie.setTranslateX(150);
	
	
	
	
	RotateTransition rotateTransitionBill = new RotateTransition(Duration.seconds(1), imgbill);
	rotateTransitionBill.setByAngle(-45); // Set the rotation angle
	rotateTransitionBill.setAutoReverse(true);
	rotateTransitionBill.setCycleCount(Animation.INDEFINITE); // Repeat the animation indefinitely
	rotateTransitionBill.play(); // Start the animation
	
	/*imgbill.setScaleX(2);
	imgbill.setScaleY(2);*/

	
	RotateTransition rotateTransitionDavid = new RotateTransition(Duration.seconds(2), imgdavid);
	rotateTransitionDavid.setByAngle(360); // Set the rotation angle
	rotateTransitionDavid.setCycleCount(Animation.INDEFINITE); // Repeat the animation indefinitely
	rotateTransitionDavid.play(); // Start the animation
	
	TranslateTransition translateTransitionDavid = new TranslateTransition(Duration.seconds(1),imgdavid);
	translateTransitionDavid.setByY(-40);
	translateTransitionDavid.setAutoReverse(true);
	translateTransitionDavid.setCycleCount(Animation.INDEFINITE);
	translateTransitionDavid.play();

	
	Text win = new Text("YOU WIN!");
	win.setFont(Font.font("Pixelated", FontWeight.NORMAL, FontPosture.REGULAR, Main.height * Main.width*0.000015));
	win.setFill(Color.GOLD);
	win.setTranslateX(Main.width / 2 -100);
	win.setTranslateY(Main.height /2);
	
	win.setScaleX(3);
	win.setScaleY(3);
	
	FadeTransition fade = new FadeTransition();
	fade.setDuration(Duration.millis(500));
	fade.setFromValue(0);
	fade.setToValue(10);
	fade.setCycleCount(Timeline.INDEFINITE);
	fade.setAutoReverse(true);
	fade.setNode(win);
	fade.play();
	borderpane.setTop(win);
	
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
	
	winScreen = new Scene(borderpane, screenBounds.getWidth(), screenBounds.getHeight());
	winScreen.setFill(Color.BLACK);
	
	}
	
	

	public Scene getWinScreen(){
		return this.winScreen;
	}

}
