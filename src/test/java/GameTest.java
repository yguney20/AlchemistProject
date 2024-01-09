package domain;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import domain.gameobjects.*;


import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import domain.gameobjects.*;
public class GameTest {
    private Game game;
    private Player mockPlayer;
    private IngredientCard mockIngredientcard1;

    private Molecule mockMolecule;

    @BeforeAll
    public static void setUpBeforeAll() {
        Game.destroyInstance();
    }
    @BeforeEach
    public void setUpBeforeEach() {
        game = Game.getInstance();
        mockPlayer = Mockito.mock(Player.class);
        mockIngredientcard1 = Mockito.mock(IngredientCard.class);
        mockMolecule = Mockito.mock(Molecule.class);



        game.setCurrentPlayer(mockPlayer);
    }

    @AfterEach
    public void tearDownAfterEach() {
        Game.destroyInstance();
    }
    @Test
    @DisplayName("Test  null Ingredient input")
    public void testPublishWithNullIngredientInput(){
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {

            game.publishTheory(null,mockMolecule);

        });
        assertEquals("Ingredient and molecule cannot be null.", thrown.getMessage());
    }

    @Test
    @DisplayName("Test  null molecule input")
    public void testPublishWithNullMoleculeInput(){
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {

            game.publishTheory(mockIngredientcard1,null);

        });
        assertEquals("Ingredient and molecule cannot be null.", thrown.getMessage());
    }
    @Test
    @DisplayName("Test action already performed")
    public void testActionAlreadyPerformed() {

        game.setActionPerformed(true);
        game.publishTheory(mockIngredientcard1,mockMolecule);
        assertTrue(mockPlayer.getPublicationCards().isEmpty());
        assertTrue(game.getActionPerformed());
    }




    @Test
    @DisplayName("Test theory publication before round 2")
    public void testPublishTheoryBeforeRound2() {
        Mockito.when(mockPlayer.getGolds()).thenReturn(1);
        game.setCurrentRound(1);

        game.setActionPerformed(false);

        game.publishTheory(mockIngredientcard1, mockMolecule);

        Mockito.verify(mockPlayer, Mockito.never()).increaseReputation(1);
        assertFalse(game.getActionPerformed(), "Action performed should remai false");
        }









}
