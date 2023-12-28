package domain.gameobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.gameobjects.Molecule.*;
import domain.gameobjects.artifacteffects.ElixirOfInsightEffect;
import domain.gameobjects.artifacteffects.MagicMortarEffect;

public class GameObjectFactory { // Singleton Patterns
	
	private static GameObjectFactory instance;
	private static String redPositivePath;
	private static String redNegativePath;
	private static String bluePositivePath;
	private static String blueNegativePath;
	private static String greenPositivePath;
	private static String greenNegativePath;
	private static String neutralPath;

		
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
	}

    //Create a Molecule list
    public static List<Molecule> createMoleculeList() {
        List<Molecule> molecules = new ArrayList<>();

        molecules.add(new Molecule(Size.BIG, Sign.POSITIVE, Size.SMALL, Sign.POSITIVE, Size.SMALL, Sign.NEGATIVE));
        molecules.add(new Molecule(Size.SMALL, Sign.POSITIVE, Size.BIG, Sign.NEGATIVE, Size.SMALL, Sign.NEGATIVE));
        molecules.add(new Molecule(Size.SMALL, Sign.NEGATIVE, Size.BIG, Sign.POSITIVE, Size.SMALL, Sign.POSITIVE));
        molecules.add(new Molecule(Size.SMALL, Sign.NEGATIVE, Size.SMALL, Sign.POSITIVE, Size.BIG, Sign.NEGATIVE));
        molecules.add(new Molecule(Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE, Size.BIG, Sign.POSITIVE));
        molecules.add(new Molecule(Size.BIG, Sign.NEGATIVE, Size.SMALL, Sign.NEGATIVE, Size.SMALL, Sign.POSITIVE));
        molecules.add(new Molecule(Size.BIG, Sign.NEGATIVE, Size.BIG, Sign.NEGATIVE, Size.BIG, Sign.NEGATIVE));
        molecules.add(new Molecule(Size.SMALL, Sign.POSITIVE, Size.SMALL, Sign.NEGATIVE, Size.BIG, Sign.POSITIVE));

        return molecules;
    }

    /*Creates a new ingredient list for the given molecule list. 
    *Each ingredient will have a different molecule for each Game*/
    public List<IngredientCard> createIngredientDeck() {
    	List<Molecule> moleculeList = createMoleculeList();
        List<IngredientCard> ingredients = new ArrayList<>();
        Map<String, String> ingredientImages = new HashMap<>();

        ingredientImages.put("Moon Blossom", "/ui/swing/resources/images/ingredientCards/Moon Blossom.png");
        ingredientImages.put("Crystalite", "/ui/swing/resources/images/ingredientCards/Crystalite.png");
        ingredientImages.put("Shimmer Fungus", "/ui/swing/resources/images/ingredientCards/Shimmer Fungus.png");
        ingredientImages.put("Golden Mold", "/ui/swing/resources/images/ingredientCards/Golden Mald.png");
        ingredientImages.put("Starling Nectar", "/ui/swing/resources/images/ingredientCards/Starlight Nectar.png");
        ingredientImages.put("Verdant Fern", "/ui/swing/resources/images/ingredientCards/Verdant Fern.png");
        ingredientImages.put("Dandelion Root", "/ui/swing/resources/images/ingredientCards/Dandelion Root.png");
        ingredientImages.put("Dragon Powder", "/ui/swing/resources/images/ingredientCards/Dragon Powder.png");


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

        artifactDeck.add(new ArtifactCard("Elixir of Insight", 3, new ElixirOfInsightEffect(), true, false,"/ui/swing/resources/images/artifactCards/Elixir Of Insight.png" ));
        artifactDeck.add(new ArtifactCard("Magic Mortar", 3, new MagicMortarEffect(), true, true,"/ui/swing/resources/images/artifactCards/Magic Mortar.png" ));

        // Add other artifacts here

        return artifactDeck;
    }


    
    public PotionCard potionMaker(IngredientCard firstIngredient, IngredientCard secondIngredient) {
        PotionCard potionCard = null;

        if ((areSameSizeAndDifferentSign(firstIngredient.getMolecule().getRedComponentSign(), secondIngredient.getMolecule().getRedComponentSign(),
                firstIngredient.getMolecule().getRedComponentSize(), secondIngredient.getMolecule().getRedComponentSize())) 
        		&& (areSameSizeAndDifferentSign(firstIngredient.getMolecule().getBlueComponentSign(), secondIngredient.getMolecule().getBlueComponentSign(),
        				firstIngredient.getMolecule().getBlueComponentSize(), secondIngredient.getMolecule().getBlueComponentSize()))
        			&& (areSameSizeAndDifferentSign(firstIngredient.getMolecule().getGreenComponentSign(), secondIngredient.getMolecule().getGreenComponentSign(),
        	                firstIngredient.getMolecule().getGreenComponentSize(), secondIngredient.getMolecule().getGreenComponentSize()))) {
            potionCard = new PotionCard("Neutral Potion", "Neutral", "Description for Neutral Potion", neutralPath);
            
        } else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getRedComponentSign(), secondIngredient.getMolecule().getRedComponentSign(),
                firstIngredient.getMolecule().getRedComponentSize(), secondIngredient.getMolecule().getRedComponentSize())) {
            String redSignString = firstIngredient.getMolecule().getRedComponentSign().toString();
            if(redSignString.equals("POSITIVE")) {
                potionCard = new PotionCard("Red Potion", "POSITIVE", "Red Positive", redPositivePath);

            } else {
                potionCard = new PotionCard("Red Potion","NEGATIVE", "Red Negative", redNegativePath);

            }
        } else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getBlueComponentSign(), secondIngredient.getMolecule().getBlueComponentSign(),
                firstIngredient.getMolecule().getBlueComponentSize(), secondIngredient.getMolecule().getBlueComponentSize())) {
            String blueSignString = firstIngredient.getMolecule().getBlueComponentSign().toString();
            if(blueSignString.equals("POSITIVE")) {
                potionCard = new PotionCard("Blue Potion", "POSITIVE", "Blue Positive", bluePositivePath);

            } else {
                potionCard = new PotionCard("Blue Potion","NEGATIVE", "Blue Negative", blueNegativePath);
            }
            
        } else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getGreenComponentSign(), secondIngredient.getMolecule().getGreenComponentSign(),
                firstIngredient.getMolecule().getGreenComponentSize(), secondIngredient.getMolecule().getGreenComponentSize())) {
            String greenSignString = firstIngredient.getMolecule().getGreenComponentSign().toString();
            if(greenSignString.equals("POSITIVE")) {
                potionCard = new PotionCard("Green Potion", "POSITIVE", "Green Positive", greenPositivePath);

            } else {
                potionCard = new PotionCard("Green Potion","NEGATIVE", "Green Negative", greenNegativePath);
            }            
        }

        return potionCard;
    }

    private boolean areSameSizeAndDifferentSign(Molecule.Sign sign1, Molecule.Sign sign2, Molecule.Size size1, Molecule.Size size2) {
        return size1.equals(size2) && !sign1.equals(sign2);
    }

    private boolean areSameSignAndDifferentSize(Molecule.Sign sign1, Molecule.Sign sign2, Molecule.Size size1, Molecule.Size size2) {
        return sign1.equals(sign2) && !size1.equals(size2);
    }
    
}
