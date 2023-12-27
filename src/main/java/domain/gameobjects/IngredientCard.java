package domain.gameobjects;

public class IngredientCard {
	
	private int cardId;
	private String name;
    private Molecule molecule;
	private String imagePath;

	public IngredientCard(int cardId, String name, Molecule molecule, String imagePath) {
	    this.cardId = cardId;
	    this.name = name;
	    this.molecule = molecule;
		this.imagePath = imagePath;
	}
	
    @Override
    public String toString() {
        return "IngredientCard{" +
                "\n\tcardId=" + cardId +
                "\n\tname='" + name +
                "\n\tmolecule=" + molecule +
				"\n\timagePath='" + imagePath +
                "\n}";
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
