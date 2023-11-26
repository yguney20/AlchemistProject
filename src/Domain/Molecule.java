package Domain;

public class Molecule {
    private String color;
    private String sign;
    private int size;

    public Molecule(String color, String sign, int size) {
        this.color = color;
        this.sign = sign;
        this.size = size;
    }

    public void setColor(String color) {
		this.color = color;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setSize(int size) {
		this.size = size;
	}

	// Getters for color, sign, and size
    public String getColor() {
        return color;
    }

    public String getSign() {
        return sign;
    }

    public int getSize() {
        return size;
    }
}

