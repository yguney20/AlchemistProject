 package domain.GameObjects;

 import java.util.ArrayList;
 import java.util.List;

 public class Player {
     private String nickname;
     private String avatar; // This can be a path to the avatar image file 
     private int golds;
     private List<IngredientCard> ingredientInventory;
     private List<PotionCard> potionInventory;
     private List<ArtifactCard> artifactCards;
     private int sicknessLevel;
     private int reputationPoints;
     private double score;

     public Player(String nickname, String avatar) {
         this.nickname = nickname;
         this.avatar = avatar;
         this.golds = 10; 
         this.ingredientInventory = new ArrayList<>();
         this.potionInventory = new ArrayList<>();
         this.artifactCards = new ArrayList<>();
         this.sicknessLevel = 0; 
         this.reputationPoints = 0; 
     }

     public void increaseSickness(int x) {
    	 this.sicknessLevel += x;
     }
     
     public void increaseGold(int x) {
    	 this.golds += x;
     }
     
     public void reduceGold(int x) {
    	 this.golds -= x;
     }
     
     public void increaseReputation(int x) {
    	 this.reputationPoints += x;
     }
     
     public void reduceReputation(int x) {
    	 this.reputationPoints -= x;
     }
     
     public double calculateFinalScore() {
    	 int goldsForScore = this.golds;
         goldsForScore += 2*artifactCards.size();
         score = 10*this.getReputationPoints();
         score += goldsForScore/3;
         return score;
     }
     
     public List<ArtifactCard> getArtifactCards() {
		return artifactCards;
	}

	public void setArtifactCards(List<ArtifactCard> artifactCards) {
		this.artifactCards = artifactCards;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setIngredientInventory(List<IngredientCard> ingredientInventory) {
		this.ingredientInventory = ingredientInventory;
	}

	public void setPotionInventory(List<PotionCard> potionInventory) {
		this.potionInventory = potionInventory;
	}

	public String getNickname() {
         return nickname;
     }

     public String getAvatar() {
         return avatar;
     }

     public int getGolds() {
         return golds;
     }

     public void setGolds(int golds) {
         this.golds = golds;
     }

     public List<IngredientCard> getIngredientInventory() {
         return ingredientInventory;
     }

     public List<PotionCard> getPotionInventory() {
         return potionInventory;
     }

     public int getSicknessLevel() {
         return sicknessLevel;
     }

     public void setSicknessLevel(int sicknessLevel) {
         this.sicknessLevel = sicknessLevel;
     }

     public int getReputationPoints() {
         return reputationPoints;
     }

     public void setReputationPoints(int reputationPoints) {
         this.reputationPoints = reputationPoints;
     }
 }

