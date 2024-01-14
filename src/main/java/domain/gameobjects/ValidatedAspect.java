package domain.gameobjects;
import java.util.ArrayList;

public class ValidatedAspect {
    private int validatedAspectId;
    private IngredientCard ingredient;
    private Molecule.Component validatedComponent;
    private Molecule.Sign validatedSign;
    private Molecule.Size validatedSize;
    private static ArrayList<ValidatedAspect> validatedList = new ArrayList<ValidatedAspect>();

    public ValidatedAspect(int validatedAspectId,IngredientCard ingredient, Molecule.Component validatedComponent, Molecule.Sign validatedSign, Molecule.Size validatedSize) {
        this.ingredient = ingredient;
        this.validatedComponent = validatedComponent;
        this.validatedSign = validatedSign;
        this.validatedAspectId =  validatedAspectId;
        this.validatedSize = validatedSize;
        validatedList.add(this);
    }

    public int getValidatedAspectId(){
        return validatedAspectId;
    }

    public void setValidatedAspectId(int validatedAspectId){
        this.validatedAspectId = validatedAspectId;
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
		return "True Aspect: ingredient = " + ingredient.getName()
				+ ", component = " + validatedComponent + ", sign = " + validatedSign + ", size = " + validatedSize;
	}
}
