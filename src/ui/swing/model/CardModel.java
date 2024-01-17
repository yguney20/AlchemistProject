package ui.swing.model;

import javax.swing.Icon;

public class CardModel {
	
	 private Icon icon;
	 private String title;
	 private String description;

	public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CardModel(Icon icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

}

/**
* @Author -- H. Sarp Vulas
*/
