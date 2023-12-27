package domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import domain.Game;
import domain.gameobjects.ArtifactCard;
import domain.gameobjects.Player;

public class GameTest {
    private Game game;
    private Player mockPlayer;
    private ArtifactCard mockCard;

    @BeforeAll
    public void setUpBeforeAll() {
        // Initialization that applies to all tests, if necessary
        Game.resetInstance();
    }

    @BeforeEach
    public void setUpBeforeEach() {
        game = Game.getInstance();
        mockPlayer = Mockito.mock(Player.class);
        mockCard = Mockito.mock(ArtifactCard.class);
    }

    @AfterEach
    public void tearDownAfterEach() {
        // Cleanup after each test, if necessary
    }

    @AfterAll
    public void tearDownAfterAll() {
        // Final cleanup after all tests, if necessary
    }

    // Test methods structured similar to the provided examples
    // ...

    // Example test case
    @Test
    @DisplayName("Test successful purchase of an artifact card")
    public void testSuccessfulPurchase() {
        // Your test implementation
    }

    @Test
    @DisplayName("Test successful purchase of a non-immediate artifact card")
    public void testSuccessfulPurchaseNonImmediate() {
        // Arrange
        Mockito.when(mockPlayer.getGolds()).thenReturn(100);
        Mockito.when(mockCard.getGoldValue()).thenReturn(50);
        Mockito.when(mockCard.isImmadiate()).thenReturn(false);
        Mockito.when(game.getCurrentPlayer()).thenReturn(mockPlayer);

        // Act
        game.buyArtifactCard(mockCard, mockPlayer);

        // Assert
        Mockito.verify(mockPlayer).addArtifactCard(mockCard);
        Mockito.verify(mockPlayer).reduceGold(50);
        assertFalse(game.getActionPerformed());
    }

    // Test for successful purchase and use of an immediate card
    @Test
    @DisplayName("Test successful purchase and use of an immediate card")
    public void testSuccessfulPurchaseImmediate() {
        // Arrange
        Mockito.when(mockPlayer.getGolds()).thenReturn(100);
        Mockito.when(mockCard.getGoldValue()).thenReturn(50);
        Mockito.when(mockCard.isImmadiate()).thenReturn(true);
        Mockito.when(game.getCurrentPlayer()).thenReturn(mockPlayer);

        // Act
        game.buyArtifactCard(mockCard, mockPlayer);

        // Assert
        Mockito.verify(game).useArtifactCard(mockCard, mockPlayer);
        Mockito.verify(mockPlayer).reduceGold(50);
        assertTrue(game.getActionPerformed());
    }

    // Test for player having insufficient gold
    @Test
    @DisplayName("Test buying artifact card with insufficient gold")
    public void testInsufficientGold() {
        // Arrange
        Mockito.when(mockPlayer.getGolds()).thenReturn(30);
        Mockito.when(mockCard.getGoldValue()).thenReturn(50);
        Mockito.when(game.getCurrentPlayer()).thenReturn(mockPlayer);

        // Act and Assert
        // Expecting some method to capture the notification
    }

    // Test for action already performed
    @Test
    @DisplayName("Test buying artifact card when action already performed")
    public void testActionAlreadyPerformed() {
        // Arrange
        game.setActionPerformed(true);
        Mockito.when(game.getCurrentPlayer()).thenReturn(mockPlayer);

        // Act and Assert
        // Expecting some method to capture the notification
    }

    // Test for handling IllegalStateException
    @Test
    @DisplayName("Test handling of IllegalStateException for one-time use cards")
    public void testIllegalStateExceptionHandling() {
        // Arrange
        Mockito.doThrow(new IllegalStateException("Card already used")).when(mockCard).applyEffect(game, mockPlayer);
        Mockito.when(mockCard.isImmadiate()).thenReturn(true);

        // Act and Assert
        // Expecting some method to handle the exception
    }
    
}
