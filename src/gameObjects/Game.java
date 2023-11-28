package gameObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gameObjects.Molecule.Sign;
import gameObjects.Molecule.Size;

public class Game {
	
    private List<Player> players;
    private List<Molecule> moleculeList;
    private List<IngredientCard> ingredientDeck;
	private List<ArtifactCard> artifactDeck;
    private int currentRound;
    private Player currentPlayer;
    
    
    public Game(List<Player> players) {
        this.players = players;
        this.moleculeList = createMoleculeList();
        this.ingredientDeck = createIngredientDeck();
        this.artifactDeck = new ArrayList<>();
        this.currentRound = 1; 

    }
    
    public void initializeGame() {
    	
    	Collections.shuffle(players);
    	 
    	for (Player p : players) {
    		p.setGolds(10);
    		IngredientCard i1= drawIngredientCard();
    		IngredientCard i2= drawIngredientCard();
    		p.getIngredientInventory().add(i1);
    		p.getIngredientInventory().add(i2);
    	}
    	
        currentPlayer = players.get(0);
    	
    }
    
    public IngredientCard drawIngredientCard(){
            return ingredientDeck.remove(0); // Remove and return the top card
    }
    

    
    private List<Molecule> createMoleculeList() {
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

    private List<IngredientCard> createIngredientDeck() {
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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

    public List<IngredientCard> getIngredientDeck() {
		return ingredientDeck;
	}
    
	public void setIngredientDeck(List<IngredientCard> ingredientDeck) {
		this.ingredientDeck = ingredientDeck;
	}

	public List<ArtifactCard> getArtifactDeck() {
		return artifactDeck;
	}

	public void setArtifactDeck(List<ArtifactCard> artifactDeck) {
		this.artifactDeck = artifactDeck;
	}

	public List<Molecule> getMoleculeList() {
		return moleculeList;
	}

	public void setMoleculeList(List<Molecule> moleculeList) {
		this.moleculeList = moleculeList;
	}

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


}
