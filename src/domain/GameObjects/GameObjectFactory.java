package domain.GameObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import domain.GameObjects.*;
import domain.GameObjects.Molecule.*;

public class GameObjectFactory {

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

    /*Creates a new ingredient list for the given molecule. 
    *Each Game ingredients have different moleluce */
    public static List<IngredientCard> createIngredientDeck() {
        List<IngredientCard> ingredients = new ArrayList<>();

        List<String> ingredientNames = new ArrayList<> (List.of("Moon Blossom", "Crystalite", "Shimmer Fungus", "Golden Mold",
                "Ruby Crystal", "Verdant Fern", "Dandelion Root", "Dragon Powder"));

        Collections.shuffle(ingredientNames);

        for (int i = 0; i < ingredientNames.size(); i++) {
            for (int j = 0; j < 3; j++) { //3 of each ingredient
                Molecule molecule = createMoleculeList().get(i);
                String ingredientName = ingredientNames.get(i);
                ingredients.add(new IngredientCard((i * 3) + j + 1, ingredientName, molecule));
            }
        }

        Collections.shuffle(ingredients);
        
        return ingredients;

    }
    
}
