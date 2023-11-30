package domain.GameObjects;

public class ArtifactCard {
	
    private String name;
    private int goldValue;

    public ArtifactCard(String name, int goldValue) {
        this.name = name;
        this.goldValue = goldValue;
    }

    public String getName() {
        return name;
    }

    public int getGoldValue() {
        return goldValue;
    }

}
