package domain.GameObjects;

public class IngredientCard {
	
	private int cardId;
	private String name;
    private Molecule molecule;

	public IngredientCard(int cardId, String name, Molecule molecule) {
	    this.cardId = cardId;
	    this.name = name;
	    this.molecule = molecule;
	}
	
    @Override
    public String toString() {
        return "IngredientCard{" +
                "cardId=" + cardId +
                ", name='" + name + '\'' +
                ", molecule=" + molecule +
                '}';
    }
	
	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
        this.name = name;
    }

	public Molecule getMolecule() {
		return molecule;
	}

	public void setMolecule(Molecule molecule) {
		this.molecule = molecule;
	}
	
}
