package domain.gameobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.gameobjects.Molecule.*;
import domain.gameobjects.artifacteffects.*;


public class GameObjectFactory { // Singleton Patterns
	
	private static GameObjectFactory instance;

    // Counters for unique IDs
    private int playerCounter = 1;
    private static int moleculeCounter = 1;
    private int artifactCounter = 1;
    private int potionCounter = 1;
	
	private static String redPositivePath = "/images/potions/RedPlusPotion";
	private static String redNegativePath = "/images/potions/RedMinusPotion";
	private static String bluePositivePath = "/images/potions/BluePlusPotion";
	private static String blueNegativePath = "/images/potions/BlueMinusPotion";
	private static String greenPositivePath = "/images/potions/GreenPlusPotion";
	private static String greenNegativePath = "/images/potions/GreenMinusPotion";
	private static String neutralPath = "/images/potions/NeutralPotion";
	
	private static String molecule1image = "/images/molecules/molecule1.png";
	private static String molecule2image = "/images/molecules/molecule2.png";
	private static String molecule3image = "/images/molecules/molecule3.png";
	private static String molecule4image = "/images/molecules/molecule4.png";
	private static String molecule5image = "/images/molecules/molecule5.png";
	private static String molecule6image = "/images/molecules/molecule6.png";
	private static String molecule7image = "/images/molecules/molecule7.png";
	private static String molecule8image = "/images/molecules/molecule8.png";


		
    //constructor should be private in Singleton
    private GameObjectFactory() {
    }

    // Singleton Methods
    public static GameObjectFactory getInstance() {
        if (instance == null) {
            instance = new GameObjectFactory();
        }
        return instance;
    }
    
    public static void destroyInstance() {
        instance = null;
    }
    
	//Create a player
	public void createPlayer(String nickname, String avatar) {
		Player player = new Player(nickname, avatar);
        player.setPlayerId(playerCounter++);
	}

    //Create a Molecule list
    public static List<Molecule> createMoleculeList() {
        List<Molecule> molecules = new ArrayList<>();

        molecules.add(new Molecule(moleculeCounter++,Size.BIG, Sign.POSITIVE, Size.SMALL, Sign.POSITIVE, Size.SMALL, Sign.NEGATIVE, molecule4image ));
        molecules.add(new Molecule(moleculeCounter++,Size.SMALL, Sign.POSITIVE, Size.BIG, Sign.NEGATIVE, Size.SMALL, Sign.NEGATIVE, molecule3image));
        molecules.add(new Molecule(moleculeCounter++,Size.SMALL, Sign.NEGATIVE, Size.BIG, Sign.POSITIVE, Size.SMALL, Sign.POSITIVE, molecule7image));
        molecules.add(new Molecule(moleculeCounter++,Size.SMALL, Sign.NEGATIVE, Size.SMALL, Sign.POSITIVE, Size.BIG, Sign.NEGATIVE, molecule5image));
        molecules.add(new Molecule(moleculeCounter++,Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE, molecule1image));
        molecules.add(new Molecule(moleculeCounter++,Size.BIG, Sign.NEGATIVE, Size.SMALL, Sign.NEGATIVE, Size.SMALL, Sign.POSITIVE, molecule6image));
        molecules.add(new Molecule(moleculeCounter++,Size.BIG, Sign.NEGATIVE, Size.BIG, Sign.NEGATIVE, Size.BIG, Sign.NEGATIVE, molecule2image));
        molecules.add(new Molecule(moleculeCounter++,Size.SMALL, Sign.POSITIVE, Size.SMALL, Sign.NEGATIVE, Size.BIG, Sign.POSITIVE, molecule8image));

        return molecules;
    }
    
    public Map<String, String> createIngredientNameAndPathList(){
    	
        Map<String, String> ingredientImages = new HashMap<>();

        ingredientImages.put("Moon Blossom", "/images/ingredientCards/Moon Blossom.png");
        ingredientImages.put("Crystalite", "/images/ingredientCards/Crystalite.png");
        ingredientImages.put("Shimmer Fungus", "/images/ingredientCards/Shimmer Fungus.png");
        ingredientImages.put("Golden Mold", "/images/ingredientCards/Golden Mald.png");
        ingredientImages.put("Starling Nectar", "/images/ingredientCards/Starlight Nectar.png");
        ingredientImages.put("Verdant Fern", "/images/ingredientCards/Verdant Fern.png");
        ingredientImages.put("Dandelion Root", "/images/ingredientCards/Dandelion Root.png");
        ingredientImages.put("Dragon Powder", "/images/ingredientCards/Dragon Powder.png");
        
        return ingredientImages;
    }

    /*Creates a new ingredient list for the given molecule list. 
    *Each ingredient will have a different molecule for each Game*/
    public List<IngredientCard> createIngredientDeck() {
    	List<Molecule> moleculeList = createMoleculeList();
        List<IngredientCard> ingredients = new ArrayList<>();
        Map<String, String> ingredientImages = createIngredientNameAndPathList();

        List<String> ingredientNames = new ArrayList<> (List.of("Moon Blossom", "Crystalite", "Shimmer Fungus", "Golden Mold",
                "Starlight Nectar", "Verdant Fern", "Dandelion Root", "Dragon Powder"));

        Collections.shuffle(ingredientNames);

        for (int i = 0; i < ingredientNames.size(); i++) {
            String ingredientName = ingredientImages.keySet().toArray(new String[0])[i];
            String imagePath = ingredientImages.get(ingredientName);

            for (int j = 0; j < 3; j++) { //3 of each ingredient
                Molecule molecule = moleculeList.get(i);
                ingredients.add(new IngredientCard((i * 3) + j + 1, ingredientName, molecule, imagePath));
            }
        }
        

        Collections.shuffle(ingredients);
        
        return ingredients;

    }

    // Create Artifacts
    public List<ArtifactCard> createArtifactDeck() {
        List<ArtifactCard> artifactDeck = new ArrayList<>();

        artifactDeck.add(new ArtifactCard(artifactCounter++, "Elixir of Insight", 3, new ElixirOfInsightEffect(), true, false,"/images/artifactCards/Elixir Of Insight.png" ));
        artifactDeck.add(new ArtifactCard(artifactCounter++, "Magic Mortar", 3, new MagicMortarEffect(), true, false,"/images/artifactCards/Magic Mortar.png" ));
        artifactDeck.add((new ArtifactCard(artifactCounter++, "Printing Press", 3, new PrintingPressEffect(), false, false,"/images/artifactCards/Printing Press.png" )));
        artifactDeck.add((new ArtifactCard(artifactCounter++, "Wisdom Idol", 3, new WisdomIdolEffect(), true, false,"/images/artifactCards/Wisdom Idol.png" )));
        // Add other artifacts here

        return artifactDeck;
    }


    
    public PotionCard potionMaker(IngredientCard firstIngredient, IngredientCard secondIngredient) {
        // Initialize potionCard as null
        PotionCard potionCard = null;

        // Check if the ingredients can create a Neutral Potion
        if ((areSameSizeAndDifferentSign(firstIngredient.getMolecule().getRedComponentSign(), secondIngredient.getMolecule().getRedComponentSign(),
                firstIngredient.getMolecule().getRedComponentSize(), secondIngredient.getMolecule().getRedComponentSize())) 
        		&& (areSameSizeAndDifferentSign(firstIngredient.getMolecule().getBlueComponentSign(), secondIngredient.getMolecule().getBlueComponentSign(),
        				firstIngredient.getMolecule().getBlueComponentSize(), secondIngredient.getMolecule().getBlueComponentSize()))
        					&& (areSameSizeAndDifferentSign(firstIngredient.getMolecule().getGreenComponentSign(), secondIngredient.getMolecule().getGreenComponentSign(),
                	                firstIngredient.getMolecule().getGreenComponentSize(), secondIngredient.getMolecule().getGreenComponentSize()))) {
            // Create a Neutral Potion
            potionCard = new PotionCard(potionCounter++,"Neutral Potion", "Neutral", "Description for Neutral Potion", neutralPath);
        } 
        // Check if the ingredients can create a Red Potion
        else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getRedComponentSign(), secondIngredient.getMolecule().getRedComponentSign(),
                firstIngredient.getMolecule().getRedComponentSize(), secondIngredient.getMolecule().getRedComponentSize())) {
            String redSignString = firstIngredient.getMolecule().getRedComponentSign().toString();
            // Check if the Red Potion is positive or negative
            if(redSignString.equals("POSITIVE")) {
                potionCard = new PotionCard(potionCounter++,"Red Potion", "POSITIVE", "Red Positive", redPositivePath);
            } else {
                potionCard = new PotionCard(potionCounter++,"Red Potion","NEGATIVE", "Red Negative", redNegativePath);
            }
        } 
        // Check if the ingredients can create a Blue Potion
        else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getBlueComponentSign(), secondIngredient.getMolecule().getBlueComponentSign(),
                firstIngredient.getMolecule().getBlueComponentSize(), secondIngredient.getMolecule().getBlueComponentSize())) {
            String blueSignString = firstIngredient.getMolecule().getBlueComponentSign().toString();
            // Check if the Blue Potion is positive or negative
            if(blueSignString.equals("POSITIVE")) {
                potionCard = new PotionCard(potionCounter++,"Blue Potion", "POSITIVE", "Blue Positive", bluePositivePath);
            } else {
                potionCard = new PotionCard(potionCounter++,"Blue Potion","NEGATIVE", "Blue Negative", blueNegativePath);
            }
        } 
        // Check if the ingredients can create a Green Potion
        else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getGreenComponentSign(), secondIngredient.getMolecule().getGreenComponentSign(),
                firstIngredient.getMolecule().getGreenComponentSize(), secondIngredient.getMolecule().getGreenComponentSize())) {
            String greenSignString = firstIngredient.getMolecule().getGreenComponentSign().toString();
            // Check if the Green Potion is positive or negative
            if(greenSignString.equals("POSITIVE")) {
                potionCard = new PotionCard(potionCounter++,"Green Potion", "POSITIVE", "Green Positive", greenPositivePath);
            } else {
                potionCard = new PotionCard(potionCounter++,"Green Potion","NEGATIVE", "Green Negative", greenNegativePath);
            }            
        }

        // Return the created potionCard (can be null if no match is found)
        return potionCard;
    }


    private boolean areSameSizeAndDifferentSign(Molecule.Sign sign1, Molecule.Sign sign2, Molecule.Size size1, Molecule.Size size2) {
        return size1.equals(size2) && !sign1.equals(sign2);
    }

    private boolean areSameSignAndDifferentSize(Molecule.Sign sign1, Molecule.Sign sign2, Molecule.Size size1, Molecule.Size size2) {
        return sign1.equals(sign2) && !size1.equals(size2);
    }
    
}
