package gameObjects;
import java.util.List;

public class PotionCard {
	

	

	
		 	private String potionName;
		    private String potionType;
		    private List<IngredientCard> ingredients;
		    private int points;
		    private String description;
		    
		    public PotionCard(String potionName, String potionType, List<IngredientCard> ingredients, int points, String description) {
		        this.potionName = potionName;
		        this.potionType = potionType;
		        this.ingredients = ingredients;
		        this.points = points;
		        this.description = description;
		    }
		    
		    
		    public String getPotionName() {
		        return potionName;
		    }

		    public void setPotionName(String potionName) {
		        this.potionName = potionName;
		    }

		    public String getPotionType() {
		        return potionType;
		    }

		    public void setPotionType(String potionType) {
		        this.potionType = potionType;
		    }

		    public List<IngredientCard> getIngredients() {
		        return ingredients;
		    }

		    public void setIngredients(List<IngredientCard> ingredients) {
		        this.ingredients = ingredients;
		    }

		    public int getPoints() {
		        return points;
		    }

		    public void setPoints(int points) {
		        this.points = points;
		    }

		    public String getDescription() {
		        return description;
		    }

		    public void setDescription(String description) {
		        this.description = description;
		    }
	}






