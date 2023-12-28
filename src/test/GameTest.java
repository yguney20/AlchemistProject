package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

import domain.Game;
import domain.gameobjects.*;

//Glass-Box Testing 

public class GameTest {
    private Game game;
    private Player player;
    private List<IngredientCard> ingredientDeck;

    @BeforeAll
    public static void setUpBeforeAll() {
        Game.destroyInstance();
    }

    @BeforeEach
    public void setUpBeforeEach() {
        game = Game.getInstance();
        player = new Player("Player1", "avatarPath");
        ingredientDeck = new ArrayList<>();

        game.setPlayers(new ArrayList<>());
        game.getPlayers().add(player);
        game.setCurrentPlayer(player);
        game.setIngredientDeck(ingredientDeck);
    }

    @AfterEach
    public void tearDownAfterEach() {
        Game.destroyInstance();
    }
    
    
    @Test
    @DisplayName("Test successful foraging for an ingredient")
    public void testSuccessfulForaging() {
    	IngredientCard foragedCard = new IngredientCard(1, "Herb", null, "path1");
    	ingredientDeck.add(foragedCard);
    	game.setActionPerformed(false);

    	game.forageForIngredient(player);

    	assertTrue(ingredientDeck.isEmpty()); // The deck should be empty after foraging
    	assertTrue(player.getIngredientInventory().contains(foragedCard)); // The player's inventory should contain the foraged card
    	assertTrue(game.getActionPerformed()); // The action performed flag should be true

    }
    

    @Test
    @DisplayName("Test foraging with empty ingredient deck")
    public void testForagingWithEmptyDeck() {
        game.setActionPerformed(false);

        game.forageForIngredient(player);

        assertTrue(player.getIngredientInventory().isEmpty());
        assertFalse(game.getActionPerformed());
    }


    @Test
    @DisplayName("Test foraging when action already performed")
    public void testForagingActionAlreadyPerformed() {
        ingredientDeck.add(new IngredientCard(2, "Root", null, "path2"));
        game.setActionPerformed(true);

        game.forageForIngredient(player);

        assertTrue(player.getIngredientInventory().isEmpty());
        assertTrue(game.getActionPerformed());
    }
    
    
    @Test
    @DisplayName("Test foraging for an ingredient after the game has ended")
    public void testForagingAfterGameEnd() {
        game.endGame();
        assertTrue(game.isGameOver());
        ingredientDeck.add(new IngredientCard(1, "Herb", null, "path1"));
        game.forageForIngredient(player);

        // Assert: The player's inventory should not change as the game has ended
        assertTrue(player.getIngredientInventory().isEmpty(), "Player's inventory should remain empty after the game has ended");

        // Assert: The actionPerformed flag should not be set as no actions should occur after the game ends
        assertFalse(game.getActionPerformed(), "ActionPerformed should be false as the game has ended");
    }


    @Test
    @DisplayName("Test foraging with an empty deck and no prior action")
    public void testForagingWithEmptyDeckNoAction() {
        ingredientDeck.clear();
        game.setActionPerformed(false);
        game.forageForIngredient(player);

        // Assert that the player's inventory is still empty
        assertTrue(player.getIngredientInventory().isEmpty(), "Player's inventory should remain empty when foraging with an empty deck.");

        // Assert that no action was performed since the deck was empty
        assertFalse(game.getActionPerformed(), "ActionPerformed should remain false when foraging with an empty deck.");
    }


}
