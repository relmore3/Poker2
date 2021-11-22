/* 
 * Name: Robert Elmore
 * Class: COSC 1174
 * Assignment: Poker2
 * Due Date: 11/7/2021
 */

package applicaton;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * Create program Poker 2
 *
 */
public class Poker2 extends Application {
	
	private Card[] cards=new Card[5];//this defines the hand that is viewed
	
	private ArrayList<Integer> deckOfCards=new ArrayList<>();
	
	private int nextCardAvailableIndex=0;
	
	public static void main (String[] args) {
		launch(args);
	}
	
	@Override //start the primaty stage for the deck
	public void start(Stage primaryStage) throws Exception {
		for (int i=0; i<52; i++) {
			deckOfCards.add(i+1);
		}
		shuffle(deckOfCards);//shuffle the deck of cards
		nextCardAvailableIndex=0;
		//create card layout box
		VBox gameLayoutBox=createGameLayoutBox();

		Scene scene=new Scene(gameLayoutBox);
		primaryStage.setTitle("Poker");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	public VBox createGameLayoutBox() {//create card layout box vertically
		
		//create deal button to make it active and able to be clicked
		Button dealButton=new Button("Deal");
		dealButton.setOnMouseClicked(e ->{
			if(nextCardAvailableIndex >=52-11) {
				shuffle(deckOfCards);
				nextCardAvailableIndex=0;
				for (int i=0; i<5; i++) {
					cards[i].setCardNumber(deckOfCards.get(i));
					nextCardAvailableIndex++;
				}
			}else { 
				for (int i=0; i<5; i++) {
					cards[i].setCardNumber(deckOfCards.get(nextCardAvailableIndex));
					nextCardAvailableIndex++;
				
				}
				
			}
		});
		
		//Create draw button to make it active and able to be clicked
		Button drawButton=new Button("Draw");
		drawButton.setOnMouseClicked(e ->{
			for (int i=0; i<5; i++) {
				if(!cards[i].getHoldButton().isSelected()) {
					cards[i].getCardImageView().setVisible(false);
					cards[i].swapCard(deckOfCards.get(nextCardAvailableIndex));
					nextCardAvailableIndex++;
					
				}else {
					cards[i].getHoldButton().setSelected(false);
				}
			}
		});
		
		HBox cardLayoutBox= createCardLayoutBox();
		
		VBox gameLayoutBox=new VBox();
		gameLayoutBox.setSpacing(5);
		gameLayoutBox.getChildren().addAll(dealButton, drawButton, cardLayoutBox);
		
		return gameLayoutBox;
		
	}
	//create card layout box horizontally
	public HBox createCardLayoutBox() {
		
		for (int i=0; i<5; i++) {
			cards[i]=new Card(deckOfCards.get(i));
			nextCardAvailableIndex++;
		}
		HBox cardLayoutBox=new HBox();
		cardLayoutBox.setSpacing(5);
		
		for (int i=0; i<5; i++) {
			cardLayoutBox.getChildren().add(cards[i]);
		}
		
		return cardLayoutBox;
	}
	
	/*Generic method to randomize a list in Java*/
	public static <T> void shuffle(ArrayList<T> list) {
	        Random random = new Random();
	        int n = list.size();
	
	        /*start from the beginning of the list*/
	        for (int i = 0; i < n - 1; i++) {
	                /*get a random index `j` such that `i <= j <= n`*/
	                int j = i + random.nextInt(n - i);
	
	                /*swap element at i'th position in the list with the element at*/
	                /*randomly generated index `j`*/
	                T obj = list.get(i);
	                list.set(i, list.get(j));
	                list.set(j, obj);
	        }
	        
	        
	}
	//create the card image vertically
	public class Card extends VBox {
		private ToggleButton holdButton=new ToggleButton("Hold");
		private Image cardImage, backImage;
		private ImageView cardImageView=new ImageView();
		private ImageView backImageView=new ImageView();
		private int cardNumber=0;
		private StackPane cardPane=new StackPane();
		
		//randon card selector from 52 cards
		public Card(int cardNumber) {
			this.cardNumber=cardNumber;
			String fileName = cardNumber + ".png";
			String fullFileName = "file:" + System.getProperty("user.dir") + 
					File.separator + "card" + File.separator +  fileName;
			cardImage=new Image(fullFileName);
			cardImageView.setImage(cardImage);
			fileName="b1fv.png";
			fullFileName = "file:" + System.getProperty("user.dir") + 
					File.separator + "card" + File.separator +  fileName;
			backImage=new Image(fullFileName);
			backImageView.setImage(backImage);
			cardPane.getChildren().addAll(backImageView, cardImageView);
			getChildren().addAll(cardPane, holdButton);
			setAlignment(Pos.CENTER);
		}
	
		//getters and setters for card image and card numbers
		public Image getCardImage() {
			return cardImage;
		}
	
	
		public void setCardImage(Image cardImage) {
			this.cardImage = cardImage;
		}
	
	
		public ImageView getCardImageView() {
			return cardImageView;
		}
	
	
		public void setCardImageView(ImageView cardImageView) {
			this.cardImageView = cardImageView;
		}
	
	
		public int getCardNumber() {
			return cardNumber;
		}
	
	
		public void setCardNumber(int cardNumber) {
			this.cardNumber=cardNumber;
			String fileName = cardNumber + ".png";
			String fullFileName = "file:" + System.getProperty("user.dir") + 
					File.separator + "card" + File.separator +  fileName;
			cardImage=new Image(fullFileName);
			cardImageView.setImage(cardImage);
		}
		//method for animation for cards
		public void swapCard(int newCardNumber) {
			TranslateTransition discardTransition = new TranslateTransition(Duration.millis(500), cardPane);
			discardTransition.setToY(100 + cardPane.getLayoutY());
			discardTransition.setOnFinished(e ->{
				setCardNumber(newCardNumber);
				cardImageView.setVisible(true);
			});
			discardTransition.play();

			TranslateTransition drawTransition = new TranslateTransition(Duration.millis(1000), cardPane);
			drawTransition.setDelay(Duration.millis(500));
			drawTransition.setFromY(-200);
			drawTransition.setToY(cardPane.getLayoutY());
			drawTransition.play();
		}
		

//creating hold button for each card
		public ToggleButton getHoldButton() {
			return holdButton;
		}


		public void setHoldButton(ToggleButton holdButton) {
			this.holdButton = holdButton;
		}
		
		
		
	}

}



