package domain.gameobjects;
import java.util.List;

public class PotionCard {
			private int potionId;
			private String potionName;
		    private String potionType;		    
		    private String description;
		    private String imagePath;
		    
		    public PotionCard(int potionId,String potionName, String potionType, String description, String imagePath) {
		        this.potionName = potionName;
		        this.potionType = potionType;		        
		        this.description = description;
		        this.imagePath = imagePath;
		        this.potionId = potionId;

		    }
		    public int getPotionId() {
				return potionId;
			}
			
			public void setPotionId(int potionId) {
				this.potionId = potionId;
			}
		    
		    public String getImagePath() {
				return imagePath;
			}


			public void setImagePath(String imagePath) {
				this.imagePath = imagePath;
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






