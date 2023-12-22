package domain.gameobjects;
import java.util.List;

public class PotionCard {
	

			private String potionName;
		    private String potionType;

		    		    
		    private String description;
		    
		    public PotionCard(String potionName, String potionType, String description) {
		        this.potionName = potionName;
		        this.potionType = potionType;
		        
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

		    public String getDescription() {
		        return description;
		    }

		    public void setDescription(String description) {
		        this.description = description;
		    }
		    
		 	@Override
		 	public String toString() {
		 		return "PotionCard [potionName=" + potionName + ", potionType=" + potionType + ", description=" + description
				+ "]";
	}
	}






