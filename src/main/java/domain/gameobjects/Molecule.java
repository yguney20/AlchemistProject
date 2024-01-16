package domain.gameobjects;

import java.util.ArrayList;

public class Molecule {
	private int moleculeId;
	public enum Size {BIG, SMALL};
	public enum Sign {POSITIVE, NEGATIVE};
    public enum Component { RED, GREEN, BLUE }
    private Size redComponentSize;
    private Sign redComponentSign;
    private Size greenComponentSize;
    private Sign greenComponentSign;
    private Size blueComponentSize;
    private Sign blueComponentSign;
	private String imagePath;
	private static ArrayList<Molecule> moleculeList = new ArrayList<Molecule>();


    public Molecule(int moleculeId,Size redComponentSize, Sign redComponentSign, 
            Size greenComponentSize, Sign greenComponentSign, 
            Size blueComponentSize, Sign blueComponentSign, String imagePath) {
    	this.redComponentSize = redComponentSize;
    	this.redComponentSign = redComponentSign;
    	this.greenComponentSize = greenComponentSize;
    	this.greenComponentSign = greenComponentSign;
    	this.blueComponentSize = blueComponentSize;
    	this.blueComponentSign = blueComponentSign;
    	this.imagePath = imagePath;
        this.moleculeId = moleculeId;
    	moleculeList.add(this);
    }

    
    public static ArrayList<Molecule> getMoleculeList() {
		return moleculeList;
	}


	public static void setMoleculeList(ArrayList<Molecule> moleculeList) {
		Molecule.moleculeList = moleculeList;
	}


	@Override
    public String toString() {
        return "Molecule{" +
                "redComponentSize=" + redComponentSize +
                ", redComponentSign=" + redComponentSign +
                ", greenComponentSize=" + greenComponentSize +
                ", greenComponentSign=" + greenComponentSign +
                ", blueComponentSize=" + blueComponentSize +
                ", blueComponentSign=" + blueComponentSign +
                '}';
    }

    public int getMoleculeId() {
        return moleculeId;
    }
    
    public void setMoleculeId(int moleculeId) {
        this.moleculeId = moleculeId;
    }
    
    public Size getRedComponentSize() {
    	return redComponentSize;
    }

    public Sign getRedComponentSign() {
    	return redComponentSign;
    }

    public Size getGreenComponentSize() {
    	return greenComponentSize;
    }

    public Sign getGreenComponentSign() {
    	return greenComponentSign;
    }

    public Size getBlueComponentSize() {
    	return blueComponentSize;
    }

    public Sign getBlueComponentSign() {
    	return blueComponentSign;
    }

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

    public Sign getComponentSign(Component component) {
        switch (component) {
            case RED:
                return this.getRedComponentSign();
            case GREEN:
                return this.getGreenComponentSign();
            case BLUE:
                return this.getBlueComponentSign();
            default:
                throw new IllegalArgumentException("Invalid component type");
        }
    }
    
    public Size getComponentSize(Component component) {
        switch (component) {
            case RED:
                return this.getRedComponentSize();
            case GREEN:
                return this.getGreenComponentSize();
            case BLUE:
                return this.getBlueComponentSize();
            default:
                throw new IllegalArgumentException("Invalid component type");
        }
    }


    public boolean compareComponent(Molecule otherMolecule, Component component) {
        switch (component) {
            case RED:
                return this.redComponentSize == otherMolecule.redComponentSize
                        && this.redComponentSign == otherMolecule.redComponentSign;
            case GREEN:
                return this.greenComponentSize == otherMolecule.greenComponentSize
                        && this.greenComponentSign == otherMolecule.greenComponentSign;
            case BLUE:
                return this.blueComponentSize == otherMolecule.blueComponentSize
                        && this.blueComponentSign == otherMolecule.blueComponentSign;
            default:
                throw new IllegalArgumentException("Invalid component type");
        }
    }
  
}