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
    
}
