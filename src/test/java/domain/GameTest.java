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
}