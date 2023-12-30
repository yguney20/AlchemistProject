package domain;

import static org.junit.jupiter.api.Assertions.*;
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

    // Black-box testing.
    // Test for Null Input
    @Test
    @DisplayName("Test buying artifact card with null input")
    public void testBuyingWithNullInput() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            game.buyArtifactCard(null, mockPlayer);
        });
        assertEquals("Card or player cannot be null.", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> {
            game.buyArtifactCard(mockCard, null);
        });
        assertEquals("Card or player cannot be null.", thrown.getMessage());
    }


    // Glass-box and Black-box testing. Checks the functionality as expected from inside and outside.
    // If the method behaves as expected when a player successfully purchases an artifact card.
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

    // Glass-box and Black-box testing combined.
    // This test checks the behavior when the player does not have enough gold
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

    // Glass-box test. This specifically tests the internal state of the Game
    // This test checks the behavior when the player already made an action
    @Test
    @DisplayName("Test buying artifact card when action already performed")
    public void testActionAlreadyPerformed() {
        game.setActionPerformed(true);

        game.buyArtifactCard(mockCard, mockPlayer);

        Mockito.verify(mockPlayer, Mockito.never()).addArtifactCard(mockCard);
    }

    // Glass-box test. This specifically tests the internal state of the Game
    // Test for Immediate-Use Card
    @Test
@DisplayName("Test successful purchase and use of an immediate card")
public void testSuccessfulPurchaseImmediate() {
    // Create a spy of the game instance
    Game spyGame = Mockito.spy(game);

    int playerGold = 10;
    int cardCost = 3;

    Mockito.when(mockPlayer.getGolds()).thenReturn(playerGold);
    Mockito.when(mockCard.getGoldValue()).thenReturn(cardCost);
    Mockito.when(mockCard.isImmadiate()).thenReturn(true);

    // Use spyGame to call buyArtifactCard
    spyGame.buyArtifactCard(mockCard, mockPlayer);

    Mockito.verify(mockPlayer).addArtifactCard(mockCard);
    Mockito.verify(mockPlayer).reduceGold(cardCost);
    Mockito.verify(spyGame).useArtifactCard(mockCard, mockPlayer); // Verify immediate use on the spy
    assertTrue(spyGame.getActionPerformed());
}

}
