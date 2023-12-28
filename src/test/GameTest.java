package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;

import domain.Game;
import domain.gameobjects.*;

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
    
    //Successful Foraging: Tests if a player successfully forages for an ingredient when the deck is not empty and no action has been performed.
    @Test
    @DisplayName("Test successful foraging for an ingredient")
    public void testSuccessfulForaging() {
        ingredientDeck.add(new IngredientCard(1, "Herb", null, "path1"));
        game.setActionPerformed(false);

        game.forageForIngredient(player);

        assertFalse(ingredientDeck.isEmpty());
        assertTrue(player.getIngredientInventory().contains(ingredientDeck.get(0)));
        assertTrue(game.getActionPerformed());
    }
    
    // Empty Ingredient Deck: Verifies the method's behavior when the ingredient deck is empty.
    @Test
    @DisplayName("Test foraging with empty ingredient deck")
    public void testForagingWithEmptyDeck() {
        game.setActionPerformed(false);

        game.forageForIngredient(player);

        assertTrue(player.getIngredientInventory().isEmpty());
        assertFalse(game.getActionPerformed());
    }

    // Action Already Performed: Checks if the method prevents foraging when an action has already been performed in the turn.
    @Test
    @DisplayName("Test foraging when action already performed")
    public void testForagingActionAlreadyPerformed() {
        ingredientDeck.add(new IngredientCard(2, "Root", null, "path2"));
        game.setActionPerformed(true);

        game.forageForIngredient(player);

        assertTrue(player.getIngredientInventory().isEmpty());
        assertTrue(game.getActionPerformed());
    }
    
    //Full Inventory: Tests the scenario where the player's inventory is full.
    @Test
    @DisplayName("Test foraging with player having full inventory")
    public void testForagingWithFullInventory() {
        for (int i = 0; i < 10; i++) {
            player.getIngredientInventory().add(new IngredientCard(i, "Ingredient" + i, null, "path" + i));
        }
        ingredientDeck.add(new IngredientCard(10, "New Herb", null, "path10"));
        game.setActionPerformed(false);

        game.forageForIngredient(player);

        assertFalse(player.getIngredientInventory().contains(ingredientDeck.get(0)));
        assertFalse(game.getActionPerformed());
    }
    
    //Game Pause: Checks if foraging is prevented when the game is paused.
    @Test
    @DisplayName("Test foraging for ingredient during game pause")
    public void testForagingDuringGamePause() {
        ingredientDeck.add(new IngredientCard(3, "Flower", null, "path3"));
        game.setActionPerformed(false);
        game.pauseGame();

        game.forageForIngredient(player);

        assertTrue(player.getIngredientInventory().isEmpty());
        assertFalse(game.getActionPerformed());
    }
}
