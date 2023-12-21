package domain.gameobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import domain.gameobjects.Molecule.*;
import domain.gameobjects.artifacteffects.ElixirOfInsightEffect;

public class GameObjectFactory { // Singleton Patterns
	
	private static GameObjectFactory instance;
		
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

        ingredientImages.put("Moon Blossom", "/UI/Swing/Images/IngredientCards/Moon Blossom.png");
        ingredientImages.put("Crystalite", "/UI/Swing/Images/IngredientCards/Crystalite.png");
        ingredientImages.put("Shimmer Fungus", "/UI/Swing/Images/IngredientCards/Shimmer Fungus.png");
        ingredientImages.put("Golden Mold", "/UI/Swing/Images/IngredientCards/Golden Mald.png");
        ingredientImages.put("Starling Nectar", "/UI/Swing/Images/IngredientCards/Starlight Nectar.png");
        ingredientImages.put("Verdant Fern", "/UI/Swing/Images/IngredientCards/Verdant Fern.png");
        ingredientImages.put("Dandelion Root", "/UI/Swing/Images/IngredientCards/Dandelion Root.png");
        ingredientImages.put("Dragon Powder", "/UI/Swing/Images/IngredientCards/Dragon Powder.png");


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

        artifactDeck.add(new ArtifactCard("Elixir of Insight", 3, new ElixirOfInsightEffect(), true, false,"/UI/Swing/Images/ArtifactCards/Elixir Of Insight.png" ));
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
            potionCard = new PotionCard("Neutral Potion", "Neutral", "Description for Neutral Potion");
            
        } else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getRedComponentSign(), secondIngredient.getMolecule().getRedComponentSign(),
                firstIngredient.getMolecule().getRedComponentSize(), secondIngredient.getMolecule().getRedComponentSize())) {
            String redSignString = firstIngredient.getMolecule().getRedComponentSign().toString();
            potionCard = new PotionCard("Red Potion", redSignString, "Description for Red Potion");
        } else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getBlueComponentSign(), secondIngredient.getMolecule().getBlueComponentSign(),
                firstIngredient.getMolecule().getBlueComponentSize(), secondIngredient.getMolecule().getBlueComponentSize())) {
            String blueSignString = firstIngredient.getMolecule().getBlueComponentSign().toString();
            potionCard = new PotionCard("Blue Potion", blueSignString, "Description for Blue Potion");
        } else if (areSameSignAndDifferentSize(firstIngredient.getMolecule().getGreenComponentSign(), secondIngredient.getMolecule().getGreenComponentSign(),
                firstIngredient.getMolecule().getGreenComponentSize(), secondIngredient.getMolecule().getGreenComponentSize())) {
            String greenSignString = firstIngredient.getMolecule().getGreenComponentSign().toString();
            potionCard = new PotionCard("Green Potion", greenSignString, "Description for Green Potion");
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
