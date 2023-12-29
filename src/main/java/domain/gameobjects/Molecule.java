package domain.gameobjects;

import java.util.ArrayList;

public class Molecule {
	
	public enum Size {BIG, SMALL};
	public enum Sign {POSITIVE, NEGATIVE};
    private Size redComponentSize;
    private Sign redComponentSign;
    private Size greenComponentSize;
    private Sign greenComponentSign;
    private Size blueComponentSize;
    private Sign blueComponentSign;
	private String imagePath;
	private static ArrayList<Molecule> moleculeList = new ArrayList<Molecule>();


    public Molecule(Size redComponentSize, Sign redComponentSign, 
            Size greenComponentSize, Sign greenComponentSign, 
            Size blueComponentSize, Sign blueComponentSign, String imagePath) {
    	this.redComponentSize = redComponentSize;
    	this.redComponentSign = redComponentSign;
    	this.greenComponentSize = greenComponentSize;
    	this.greenComponentSign = greenComponentSign;
    	this.blueComponentSize = blueComponentSize;
    	this.blueComponentSign = blueComponentSign;
    	this.imagePath = imagePath;
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
    
    
}