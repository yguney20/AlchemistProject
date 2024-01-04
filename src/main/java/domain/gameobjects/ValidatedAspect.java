package domain.gameobjects;
import java.util.ArrayList;

public class ValidatedAspect {
    private IngredientCard ingredient;
    private Molecule.Component validatedComponent;
    private Molecule.Sign validatedSign;
    private static ArrayList<ValidatedAspect> validatedList = new ArrayList<ValidatedAspect>();

    public ValidatedAspect(IngredientCard ingredient, Molecule.Component validatedComponent, Molecule.Sign validatedSign) {
        this.ingredient = ingredient;
        this.validatedComponent = validatedComponent;
        this.validatedSign = validatedSign;
        validatedList.add(this);
    }

    public IngredientCard getIngredient() {
        return ingredient;
    }

    public Molecule.Component getValidatedComponent() {
        return validatedComponent;
    }

    public Molecule.Sign getValidatedSign() {
        return validatedSign;
    }

    public static ArrayList<ValidatedAspect> getValidatedList() {
        return validatedList;
    }

    @Override
    public String toString() {
        return "ValidatedAspect [ingredient=" + ingredient + ", validatedComponent=" + validatedComponent + ", validatedSign=" + validatedSign + "]";
    }
}
