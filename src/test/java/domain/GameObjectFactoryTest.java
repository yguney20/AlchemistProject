package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import domain.gameobjects.*;

public class GameObjectFactoryTest {
    // Instantiate the factory and mock objects for IngredientCard and Molecule
    private GameObjectFactory factory;
    private IngredientCard mockIngredient1;
    private IngredientCard mockIngredient2;
    private Molecule mockMolecule1;
    private Molecule mockMolecule2;

    @BeforeAll
    public static void setUpBeforeAll() {
        // Destroy any existing instances to ensure a clean start for each test run
        GameObjectFactory.destroyInstance();
    }

    @BeforeEach
    public void setUpBeforeEach() {
        // Initialize the factory and mocks before each test
        factory = GameObjectFactory.getInstance();
        mockMolecule1 = Mockito.mock(Molecule.class);
        mockMolecule2 = Mockito.mock(Molecule.class);
        mockIngredient1 = Mockito.mock(IngredientCard.class);
        mockIngredient2 = Mockito.mock(IngredientCard.class);

        // Configure the mock IngredientCards to return mock Molecules
        Mockito.when(mockIngredient1.getMolecule()).thenReturn(mockMolecule1);
        Mockito.when(mockIngredient2.getMolecule()).thenReturn(mockMolecule2);
    }

    @AfterEach
    public void tearDownAfterEach() {
        // Destroy the factory instance after each test to ensure isolation
        GameObjectFactory.destroyInstance();
    }

    @Test
    @DisplayName("Test Potion Creation with Neutral Outcome")
    public void testPotionCreationNeutral() {
        // Configure the molecules to simulate conditions for a neutral potion
        configureMoleculesForNeutral(mockMolecule1, mockMolecule2);
        
        // Call potionMaker and assert the expected neutral potion is created
        PotionCard potion = factory.potionMaker(mockIngredient1, mockIngredient2);
        assertNotNull(potion, "Potion should not be null for valid ingredients");
        assertEquals("Neutral", potion.getPotionType(), "Potion type should be Neutral");
    }

    @Test
    @DisplayName("Test Potion Creation with Positive Red Outcome")
    public void testPotionCreationPositiveRed() {
        // Configure the molecules to simulate conditions for a positive red potion
        configureMoleculesForPositiveRed(mockMolecule1, mockMolecule2);
        
        // Call potionMaker and assert the expected positive red potion is created
        PotionCard potion = factory.potionMaker(mockIngredient1, mockIngredient2);
        assertNotNull(potion, "Potion should not be null for valid ingredients");
        assertEquals("POSITIVE", potion.getPotionType(), "Potion type should be POSITIVE");
    }

    @Test
    @DisplayName("Test Potion Creation with Negative Blue Outcome")
    public void testPotionCreationNegativeBlue() {
        // Configure the molecules to simulate conditions for a negative blue potion
        configureMoleculesForNegativeBlue(mockMolecule1, mockMolecule2);
        
        // Call potionMaker and assert the expected negative blue potion is created
        PotionCard potion = factory.potionMaker(mockIngredient1, mockIngredient2);
        assertNotNull(potion, "Potion should not be null for valid ingredients");
        assertEquals("NEGATIVE", potion.getPotionType(), "Potion type should be NEGATIVE");
    }

    @Test
    @DisplayName("Test Potion Creation with Positive Green Outcome")
    public void testPotionCreationPositiveGreen() {
        // Configure the molecules to simulate conditions for a positive green potion
        configureMoleculesForPositiveGreen(mockMolecule1, mockMolecule2);
        
        // Call potionMaker and assert the expected positive green potion is created
        PotionCard potion = factory.potionMaker(mockIngredient1, mockIngredient2);
        assertNotNull(potion, "Potion should not be null for valid ingredients");
        assertEquals("POSITIVE", potion.getPotionType(), "Potion type should be POSITIVE");
    }

    @Test
    @DisplayName("Test Potion Creation with No Matching Outcome")
    public void testPotionCreationNoMatch() {
        // Configure the molecules to simulate conditions where no potion should be created
        configureMoleculesForNoMatch(mockMolecule1, mockMolecule2);
        
        // Call potionMaker and assert that no potion is created
        PotionCard potion = factory.potionMaker(mockIngredient1, mockIngredient2);
        assertNull(potion, "Potion should be null when no criteria match");
    }

    // Helper methods are defined below to set up different mock molecule configurations
    // for each test scenario. These methods provide detailed mock settings that
    // correspond to the specific conditions required for each type of potion.
    private void configureMoleculesForNeutral(Molecule m1, Molecule m2) {
        // Configuring for a Neutral Potion: same size, different sign for all components
        Mockito.when(m1.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getRedComponentSign()).thenReturn(Molecule.Sign.NEGATIVE);
        
        Mockito.when(m1.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getBlueComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getBlueComponentSign()).thenReturn(Molecule.Sign.NEGATIVE);
        
        Mockito.when(m1.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getGreenComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getGreenComponentSign()).thenReturn(Molecule.Sign.NEGATIVE);
    }

    // Helper method to configure the mock molecules for a positive red potion
    private void configureMoleculesForPositiveRed(Molecule m1, Molecule m2) {
        // Configuring for a Positive Red Potion: same sign, different size for red component
        Mockito.when(m1.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getRedComponentSize()).thenReturn(Molecule.Size.SMALL);
        Mockito.when(m1.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        
        // Other components do not meet any potion criteria
        Mockito.when(m1.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
    }

    // Helper method to configure the mock molecules for a negative blue potion
    private void configureMoleculesForNegativeBlue(Molecule m1, Molecule m2) {
    	// Configure molecules for a negative blue potion: same sign, different size for blue component
        Mockito.when(m1.getBlueComponentSize()).thenReturn(Molecule.Size.SMALL);
        Mockito.when(m2.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getBlueComponentSign()).thenReturn(Molecule.Sign.NEGATIVE);
        Mockito.when(m2.getBlueComponentSign()).thenReturn(Molecule.Sign.NEGATIVE);

        // Stub other methods called on the mock to avoid null returns
        Mockito.when(m1.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value
        Mockito.when(m2.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value

        Mockito.when(m1.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getGreenComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value
        Mockito.when(m2.getGreenComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value
    }

    // Helper method to configure the mock molecules for a positive green potion
    private void configureMoleculesForPositiveGreen(Molecule m1, Molecule m2) {
    	// Configure molecules for a positive green potion: same sign, different size for green component
        Mockito.when(m1.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getGreenComponentSize()).thenReturn(Molecule.Size.SMALL);
        Mockito.when(m1.getGreenComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getGreenComponentSign()).thenReturn(Molecule.Sign.POSITIVE);

        // Stub other methods called on the mock to avoid null returns
        Mockito.when(m1.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value
        Mockito.when(m2.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value

        Mockito.when(m1.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getBlueComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value
        Mockito.when(m2.getBlueComponentSign()).thenReturn(Molecule.Sign.POSITIVE); // or any non-null value
    }

    // Helper method to configure the mock molecules for a scenario with no matching outcome
    private void configureMoleculesForNoMatch(Molecule m1, Molecule m2) {
    	// Configure molecules for a no match scenario: all components have the same size and sign
        Mockito.when(m1.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getRedComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getRedComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        
        Mockito.when(m1.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getBlueComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getBlueComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getBlueComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        
        Mockito.when(m1.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m2.getGreenComponentSize()).thenReturn(Molecule.Size.BIG);
        Mockito.when(m1.getGreenComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
        Mockito.when(m2.getGreenComponentSign()).thenReturn(Molecule.Sign.POSITIVE);
    }
}