package domain.GameObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import domain.GameObjects.*;
import domain.GameObjects.Molecule.*;

public class GameObjectFactory { // Singleton Patterns
	
	private static GameObjectFactory instance;
		
    private GameObjectFactory() {
    }

    // Singleton Methods
    public static GameObjectFactory getInstance() {
        if (GameObjectFactory.instance == null) {
            GameObjectFactory.instance = new GameObjectFactory();
        }
        return GameObjectFactory.instance;
    }
    
    public static void destroyInstance() {
        GameObjectFactory.instance = null;
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
    public static List<IngredientCard> createIngredientDeck() {
    	List<Molecule> moleculeList = createMoleculeList();
        List<IngredientCard> ingredients = new ArrayList<>();

        List<String> ingredientNames = new ArrayList<> (List.of("Moon Blossom", "Crystalite", "Shimmer Fungus", "Golden Mold",
                "Ruby Crystal", "Verdant Fern", "Dandelion Root", "Dragon Powder"));

        Collections.shuffle(ingredientNames);

        for (int i = 0; i < ingredientNames.size(); i++) {
            for (int j = 0; j < 3; j++) { //3 of each ingredient
                Molecule molecule = moleculeList.get(i);
                String ingredientName = ingredientNames.get(i);
                ingredients.add(new IngredientCard((i * 3) + j + 1, ingredientName, molecule));
            }
        }

        Collections.shuffle(ingredients);
        
        return ingredients;

    }
    
    public PotionCard potionMaker(IngredientCard firstIngredient, IngredientCard secondIngredient) {
        PotionCard potionCard = null;

        if ((areSameSizeAndDifferentSign(firstIngredient.getMolecule().getRedComponentSign(), secondIngredient.getMolecule().getRedComponentSign(),
                firstIngredient.getMolecule().getRedComponentSize(), secondIngredient.getMolecule().getRedComponentSize())) 
        		&& (areSameSizeAndDifferentSign(firstIngredient.getMolecule().getBlueComponentSign(), secondIngredient.getMolecule().getBlueComponentSign(),
        				firstIngredient.getMolecule().getBlueComponentSize(), secondIngredient.getMolecule().getBlueComponentSize()))
        			&& (areSameSizeAndDifferentSign(firstIngredient.getMolecule().getGreenComponentSign(), secondIngredient.getMolecule().getGreenComponentSign(),
        	                firstIngredient.getMolecule().getGreenComponentSize(), secondIngredient.getMolecule().getGreenComponentSize()))) {
            potionCard = new PotionCard("Neutral Potion", "Clear", "Description for Neutral Potion");
            
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
