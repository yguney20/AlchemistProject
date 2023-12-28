package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import domain.gameobjects.*;

public class GameTest {
    private Game game;
    private Player mockPlayer;
    private ArtifactCard mockCard;

    @BeforeAll
    public static void setUpBeforeAll() {
        Game.destroyInstance();
    }

    @BeforeEach
    public void setUpBeforeEach() {
        game = Game.getInstance();
        mockPlayer = Mockito.mock(Player.class);
        mockCard = Mockito.mock(ArtifactCard.class);
        

        game.setCurrentPlayer(mockPlayer);
    }

    @AfterEach
    public void tearDownAfterEach() {
        Game.destroyInstance();
    }
    
    //--------------tests for buy artifact----------------------

    @Test
    @DisplayName("Test successful purchase of an artifact card")
    public void testSuccessfulPurchase() {
        int playerGold = 10;
        int cardCost = 3;

        Mockito.when(mockPlayer.getGolds()).thenReturn(playerGold);
        Mockito.when(mockCard.getGoldValue()).thenReturn(cardCost);
        Mockito.when(mockCard.isImmadiate()).thenReturn(false);

        game.buyArtifactCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer).addArtifactCard(mockCard);
        Mockito.verify(mockPlayer).reduceGold(cardCost);
        assertTrue(game.getActionPerformed());
    }
    
    @Test
    @DisplayName("Test purchase of an artifact card with insufficient gold")
    public void testPurchaseWithInsufficientGold() {
        int playerGold = 2;
        int cardCost = 3;

        Mockito.when(mockPlayer.getGolds()).thenReturn(playerGold);
        Mockito.when(mockCard.getGoldValue()).thenReturn(cardCost);

        game.buyArtifactCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer, Mockito.never()).addArtifactCard(mockCard);
        assertFalse(game.getActionPerformed());
    }

    @Test
    @DisplayName("Test buying artifact card when action already performed")
    public void testActionAlreadyPerformed() {
        game.setActionPerformed(true);

        game.buyArtifactCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer, Mockito.never()).addArtifactCard(mockCard);
    }
    
    //--------------tests for initialize game----------------------
    
    @Test
    @DisplayName("Test initializing the game with a null player list")
    public void testInitializeGameWithNullPlayerList() {
        // Set up a null player list
        game.setPlayers(null);

        // Call the initializeGame method and expect an exception
        assertThrows(IllegalStateException.class, () -> game.initializeGame());

    }
    
    @Test
    @DisplayName("Test initializing the game with an empty ingredient deck")
    public void testInitializeGameWithEmptyIngredientDeck() {
        // Create an empty ingredient deck
        ArrayList<IngredientCard> emptyIngredientDeck = new ArrayList<>();

        // Set up the mock player list
        ArrayList<Player> mockPlayers = new ArrayList<>(Arrays.asList(
                Mockito.mock(Player.class),
                Mockito.mock(Player.class)
        ));

        game.setPlayers(mockPlayers);
        game.setIngredientDeck(emptyIngredientDeck);

        // Call the initializeGame method and expect an exception
        assertThrows(IllegalStateException.class, () -> game.initializeGame());
        
    }
    
    @Test
    @DisplayName("Test initializing the game with 4 players")
    public void testInitializeGameWithFourPlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Player mockPlayer = Mockito.mock(Player.class);
            List<IngredientCard> mockInventory = new ArrayList<>();
            Mockito.when(mockPlayer.getIngredientInventory()).thenReturn(mockInventory);
            players.add(mockPlayer);
        }
        game.setPlayers(players);

        game.initializeGame();

        for (Player player : players) {
            Mockito.verify(player, Mockito.times(1)).setGolds(10);
            // check if each player has 2 ingredient cards
            assertEquals(2, player.getIngredientInventory().size());
        }

        // check if the current player is not null
        assertNotNull(game.getCurrentPlayer());
    }

    @Test
    @DisplayName("Test initializing the game with 5 players")
    public void testInitializeGameWithFivePlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Player mockPlayer = Mockito.mock(Player.class);
            players.add(mockPlayer);
        }
        game.setPlayers(players);

     // Call the initializeGame method and expect an exception
        assertThrows(IllegalStateException.class, () -> game.initializeGame());
    }

    @Test
    @DisplayName("Test shuffling players statistically over multiple iterations")
    public void testPlayersAreShuffledStatistically() {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(Mockito.mock(Player.class));
        }
        
        game.setPlayers(players);
        
        boolean orderChanged = false;
        List<Player> originalOrder = new ArrayList<>(players);
        
        // Repeat the shuffle and check several times
        for (int i = 0; i < 100; i++) {
            game.initializeGame();
            if (!originalOrder.equals(game.getPlayers())) {
                orderChanged = true;
                break;
            }
            // Reset the players list to the original order for the next iteration
            game.setPlayers(new ArrayList<>(originalOrder));
        }

        assertTrue(orderChanged, "The order of players should change after shuffling over multiple iterations");
    }
}