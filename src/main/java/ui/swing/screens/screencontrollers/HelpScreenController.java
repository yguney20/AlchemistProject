package ui.swing.screens.screencontrollers;

import javax.swing.JFrame;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ui.swing.screens.scenes.HelpScreen;

public class HelpScreenController {

    @FXML
    private VBox qaVBox;
    private JFrame helpScreen;

    @FXML
    public void initialize() {
        // Example of adding a question and answer
    	addQuestionAndAnswer("Q: How can I gather magical ingredients for my experiments?\n", 
                "A: Navigate to the Player Dashboard and use the \"Forage for Ingredient\" option to acquire magical components for your alchemical endeavors.");
        addQuestionAndAnswer("Q: What's the process for creating potions in the game?\n", 
                "A: Utilize the \"Brew Potions\" feature on the Player Dashboard to combine collected ingredients and concoct powerful potions.");
        addQuestionAndAnswer("Q: How can I unravel the mysteries of magical ingredients?\n" ,
                "A: Engage in experiments by selecting the \"Conduct Experiments\" option on the Player Dashboard to gain insights into the properties of different components.");
        addQuestionAndAnswer("Q: How can I share my discoveries with other alchemists?\n" ,
                "A: Share your findings by choosing the \"Publish Theories\" option on the Player Dashboard to contribute to the collective knowledge of the alchemical community.");
        addQuestionAndAnswer("Q: What's the significance of reputation points in the game?\n" ,
                "A: Reputation points serve as a measure of your standing in the alchemical world. Successfully brewing potions, and publishing theories will elevate your reputation.");
        addQuestionAndAnswer("Q: What are Artifact Cards, and how do they impact the game?\n" ,
                "A: You can find detailed explanations below, one by one, and discover the rest in-game.");
        addQuestionAndAnswer("Q: How are reputation points calculated in the game?\n" ,
                "A: Multiply your reputation points by 10 to determine your score points. The higher your reputation, the greater your score.");
        addQuestionAndAnswer("Q: Can you explain the effects of the selected Artifact card Koc Cure?\n" ,
                "- Koc Cure: Grants an immediate effect of obtaining 1 healing potion when selected.\n");
        addQuestionAndAnswer("Q: Can you explain the effects of the selected Artifact card Rumeli Selector?\n" ,
        		"- Rumeli Selector: Allows you to choose the ingredient you want when performing the action 'Forage for Ingredient.' This effect can be used one time.\n");
        addQuestionAndAnswer("Q: Can you explain the effects of the selected Artifact card Dean's Honor?\n" ,
        		"- Dean's Honor: Provides an immediate effect of gaining 1 reputation point upon selection.\n");
        addQuestionAndAnswer("Q: Can you explain the effects of the selected Artifact card Vehbi's Vision?\n" ,
        		"- Vehbi's Vision: Grants the ability to see the ingredients of the next potion the other player makes. This effect is a one-time use.\n");
        addQuestionAndAnswer("Q: Can you explain the effects of the selected Artifact card Random Ingredient Saver?\n" ,
        		"- Random Ingredient Saver: When making a potion, instead of discarding both ingredients used, only 1 is randomly chosen to be discarded (once per round)");
        addQuestionAndAnswer("Q: What's the primary objective of the game?\n" ,
                "A: Goal is to uncover the secrets of magical ingredients, create powerful potions, publishing theories, and earning reputation points.");
        addQuestionAndAnswer("Q: How do I maximize my score in the game?\n" ,
                "A: Engage in diverse alchemical activities, from collecting ingredients to brewing potions and publishing theories. Use Artifact Cards for bonus points at the end.");
    }

    private void addQuestionAndAnswer(String question, String answer) {
        Button questionButton = new Button(question);
        Label answerLabel = new Label(answer);
        answerLabel.setVisible(false);

        questionButton.setOnAction(e -> answerLabel.setVisible(!answerLabel.isVisible()));

        qaVBox.getChildren().addAll(questionButton, answerLabel);
    }
    
    public void setHelpScreenFrame(HelpScreen helpScreen) {
		// TODO Auto-generated method stub
		this.helpScreen = helpScreen;
	}

    @FXML
    private void handleBackAction() {
    	if (helpScreen != null) {
    		helpScreen.dispose(); // Close the JFrame
        }
    }
}