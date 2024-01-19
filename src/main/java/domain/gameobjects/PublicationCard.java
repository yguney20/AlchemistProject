package domain.gameobjects;

import java.util.ArrayList;

public class PublicationCard {
    private Player owner;
    private Theory theory;
    private int publicationCardId;
    private static ArrayList<PublicationCard> publicationCardList = new ArrayList<PublicationCard>();

    public PublicationCard(int publicationCardId,Player owner, Theory theory) {
        this.publicationCardId = publicationCardId;
        this.owner = owner;
        this.theory = theory;
        publicationCardList.add(this);
    }
    
    public static void resetPublicationList() {
        publicationCardList.clear();
    }

    public Player getOwner() {
        return owner;
    }

    public Theory getTheory() {
        return theory;
    }

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void setTheory(Theory theory) {
		this.theory = theory;
	}

    public int getPublicationId() {
        return publicationCardId;
    }
    
    public void setPublicationId(int publicationId) {
        this.publicationCardId = publicationId;
    }
    
    public Molecule getAssociatedMolecule() {
        return this.theory.getMolecule();
    }

    public IngredientCard getAssociatedIngredient() {
        return this.theory.getIngredient();
    }

    public static ArrayList<PublicationCard> getPublicationCardList() {
        return publicationCardList;
    }

    public static void setPublicationCardList(ArrayList<PublicationCard> publicationCardList) {
        PublicationCard.publicationCardList = publicationCardList;
    }
}
