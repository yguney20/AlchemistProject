package domain.gameobjects;

public class PublicationCard {
    private Player owner;
    private Theory theory;

    public PublicationCard(Player owner, Theory theory) {
        this.owner = owner;
        this.theory = theory;
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

}
