package domain.gameobjects;
import java.util.ArrayList;

public class ValidatedTheory {
	private IngredientCard ingredient;
	private Molecule molecule;
	private static ArrayList<ValidatedTheory> validatedList = new ArrayList<ValidatedTheory>();
	public ValidatedTheory(IngredientCard ingredient, Molecule molecule) {
		
		this.ingredient = ingredient;
		this.molecule = molecule;
		validatedList.add(this);
	}
	public IngredientCard getIngredient() {
		return ingredient;
	}
	public void setIngredient(IngredientCard ingredient) {
		this.ingredient = ingredient;
	}
	public Molecule getMolecule() {
		return molecule;
	}
	public void setMolecule(Molecule molecule) {
		this.molecule = molecule;
	}
	public static ArrayList<ValidatedTheory> getFactList() {
		return validatedList;
	}
	public static void setFactList(ArrayList<ValidatedTheory> factList) {
		ValidatedTheory.validatedList = factList;
	}
	@Override
	public String toString() {
		return "ValidatedTheory [ingredient=" + ingredient + ", molecule=" + molecule + "]";
	}
	
}
