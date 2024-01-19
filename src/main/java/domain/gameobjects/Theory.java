package domain.gameobjects;

import java.util.ArrayList;

public class Theory {
	private IngredientCard ingredient;
	private Molecule molecule;
	private static ArrayList<Theory> theoryList = new ArrayList<Theory>();
	private int theoryId;

	
	public Theory(int theoryId,IngredientCard ingredient, Molecule molecule) {
		this.ingredient = ingredient;
		this.molecule = molecule;
		this.theoryId = theoryId;
		theoryList.add(this);
		
	}
	
    public static void resetTheoryList() {
        theoryList.clear();
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

	public static ArrayList<Theory> getTheoryList() {
		return theoryList;
	}

	public static void setTheoryList(ArrayList<Theory> theoryList) {
		Theory.theoryList = theoryList;
	}
	
	public int getTheoryId() {
		return theoryId;
	}

	public void setTheoryId(int theoryId){
		this.theoryId = theoryId;
	}
}
