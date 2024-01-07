package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.ArrayList;

import domain.Game;
import domain.gameobjects.*;

public class GameTest {
    private Game game;
    private Player player;
    private IngredientCard selectedIngredient;

    @BeforeAll
    public static void setUpBeforeAll() {
        Game.destroyInstance();
    }

    @BeforeEach
    public void setUpBeforeEach() {
        game = Game.getInstance();
        player = new Player("Player1", "avatarPath");
        selectedIngredient = new IngredientCard(1, "Herb", null, "path1");
        game.setPlayers(new ArrayList<>());
        game.getPlayers().add(player);
        game.setCurrentPlayer(player);
        game.setActionPerformed(false);
        game.setIngredientDeck(new ArrayList<>());
    }

    @AfterEach
    public void tearDownAfterEach() {
        Game.destroyInstance();
    }

    @Test
    @DisplayName("Test successful transmutation of an ingredient")
    public void testSuccessfulTransmutation() {
        player.getIngredientInventory().add(selectedIngredient);
        game.transmuteIngredient(player, selectedIngredient);

        assertFalse(player.getIngredientInventory().contains(selectedIngredient));
        assertEquals(11, player.getGolds());
        assertTrue(game.getActionPerformed());
    }

    @Test
    @DisplayName("Test transmutation with no ingredient in inventory")
    public void testTransmutationNoIngredient() {
        game.transmuteIngredient(player, selectedIngredient);

        assertTrue(player.getIngredientInventory().isEmpty());
        assertFalse(game.getActionPerformed());
    }

    @Test
    @DisplayName("Test transmutation when action already performed")
    public void testTransmutationActionAlreadyPerformed() {
        player.getIngredientInventory().add(selectedIngredient);
        game.setActionPerformed(true);

        game.transmuteIngredient(player, selectedIngredient);

        assertTrue(player.getIngredientInventory().contains(selectedIngredient));
        assertTrue(game.getActionPerformed());
    }

    @Test
    @DisplayName("Test transmutation in the final round of the game")
    public void testTransmutationInFinalRound() {
    	
        game.setCurrentRound(3);
        game.setActionPerformed(false);
        player.getIngredientInventory().add(selectedIngredient);
        game.transmuteIngredient(player, selectedIngredient);

        assertTrue(game.getActionPerformed(), "ActionPerformed should be true if transmutation occurs in the final round.");
        assertFalse(player.getIngredientInventory().contains(selectedIngredient), "Ingredient should be removed from inventory in the final round.");
    }
    
    @Test
    @DisplayName("Test transmutation impact on multiple players")
    public void testTransmutationImpactOnMultiplePlayers() {

        Player secondPlayer = new Player("Player2", "avatarPath2");
        game.getPlayers().add(secondPlayer);

        IngredientCard commonIngredient = new IngredientCard(2, "Common Herb", null, "path2");
        player.getIngredientInventory().add(commonIngredient);
        secondPlayer.getIngredientInventory().add(commonIngredient);

        game.setActionPerformed(false);
        game.transmuteIngredient(player, commonIngredient);
        
        assertFalse(player.getIngredientInventory().contains(commonIngredient), "Ingredient should be removed from Player1's inventory.");
        assertTrue(secondPlayer.getIngredientInventory().contains(commonIngredient), "Ingredient should remain in Player2's inventory.");
        assertTrue(game.getActionPerformed(), "ActionPerformed should be true for Player1 after transmutation.");
    }

    
}
