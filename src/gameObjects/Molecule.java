package gameObjects;


public class Molecule {
	
	enum Size {BIG, SMALL};
	enum Sign {POSITIVE, NEGATIVE};
    private Size redComponentSize;
    private Sign redComponentSign;
    private Size greenComponentSize;
    private Sign greenComponentSign;
    private Size blueComponentSize;
    private Sign blueComponentSign;

    public Molecule(Size redComponentSize, Sign redComponentSign, 
            Size greenComponentSize, Sign greenComponentSign, 
            Size blueComponentSize, Sign blueComponentSign) {
    	this.redComponentSize = redComponentSize;
    	this.redComponentSign = redComponentSign;
    	this.greenComponentSize = greenComponentSize;
    	this.greenComponentSign = greenComponentSign;
    	this.blueComponentSize = blueComponentSize;
    	this.blueComponentSign = blueComponentSign;
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
}