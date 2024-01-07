package domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import domain.gameobjects.*;
import domain.gameobjects.Molecule.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Overview: GameObjectFactory is a singleton class responsible for creating various game objects,
 * including players, molecules, ingredient cards, artifact cards, and potions.
 *
 * Abstract Function: A factory for creating game objects, ensuring consistent and centralized object creation.
 *
 * Representation Invariant: 'instance' is either null (not yet created) or a valid GameObjectFactory object.
 */
public class GameObjectFactoryClassTest {

    private GameObjectFactory factory;

    @BeforeEach
    public void setUp() {
        // Destroy the existing instance to ensure a fresh start for each test
        GameObjectFactory.destroyInstance();
        factory = GameObjectFactory.getInstance();
    }

    private boolean repOk() {
        // This method checks the representation invariant.
        return factory == null || factory instanceof GameObjectFactory;
    }

    @Test
    public void testRepOk() {
        assertTrue(repOk(), "Representation Invariant should hold");
    }

    @Test
    public void testSingleton() {
        GameObjectFactory anotherInstance = GameObjectFactory.getInstance();
        assertSame(factory, anotherInstance, "Singleton instances should be the same");
    }

    @Test
    public void testCreateMoleculeList() {
        List<Molecule> molecules = factory.createMoleculeList();
        assertNotNull(molecules, "Molecule list should not be null");
        assertFalse(molecules.isEmpty(), "Molecule list should not be empty");
        assertEquals(8, molecules.size(), "Molecule list should have a specific number of elements");
    }

    @Test
    public void testCreateIngredientDeck() {
        List<IngredientCard> deck = factory.createIngredientDeck();
        assertNotNull(deck, "Ingredient deck should not be null");
        assertFalse(deck.isEmpty(), "Ingredient deck should not be empty");
        assertEquals(24, deck.size(), "Ingredient deck should have a specific number of elements");
        assertUniqueIngredientCards(deck);
    }

    private void assertUniqueIngredientCards(List<IngredientCard> deck) {
        Set<IngredientCard> uniqueCards = new HashSet<>(deck);
        assertEquals(deck.size(), uniqueCards.size(), "All Ingredient Cards should be unique");
    }

    @Test
    public void testCreateArtifactDeck() {
        List<ArtifactCard> deck = factory.createArtifactDeck();
        assertNotNull(deck, "Artifact deck should not be null");
        assertFalse(deck.isEmpty(), "Artifact deck should not be empty");
    }

    @Test
    public void testPotionMakerWithValidIngredients() {
        // Create mock ingredients with valid molecule combinations for potion making
        IngredientCard ingredient1 = Mockito.mock(IngredientCard.class);
        IngredientCard ingredient2 = Mockito.mock(IngredientCard.class);

        // Stub the behavior of getMolecule() to return specific Molecule objects
        Molecule molecule1 = new Molecule(Size.BIG, Sign.POSITIVE, Size.SMALL, Sign.POSITIVE, Size.SMALL, Sign.NEGATIVE);
        Molecule molecule2 = new Molecule(Size.SMALL, Sign.POSITIVE, Size.BIG, Sign.NEGATIVE, Size.SMALL, Sign.NEGATIVE);
        when(ingredient1.getMolecule()).thenReturn(molecule1);
        when(ingredient2.getMolecule()).thenReturn(molecule2);

        PotionCard potion = factory.potionMaker(ingredient1, ingredient2);
        assertNotNull(potion, "Potion should not be null");
    }

    @Test
    public void testPotionMakerWithInvalidIngredients() {
        // Create mock ingredients with invalid molecule combinations
        IngredientCard ingredient1 = Mockito.mock(IngredientCard.class);
        IngredientCard ingredient2 = Mockito.mock(IngredientCard.class);

        // Stubbing behavior of getMolecule() to return specific Molecule objects
        Molecule molecule1 = new Molecule(Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE);
        Molecule molecule2 = new Molecule(Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE);
        when(ingredient1.getMolecule()).thenReturn(molecule1);
        when(ingredient2.getMolecule()).thenReturn(molecule2);

        PotionCard potion = factory.potionMaker(ingredient1, ingredient2);
        assertNull(potion, "Potion should be null for invalid ingredients");
    }

}
