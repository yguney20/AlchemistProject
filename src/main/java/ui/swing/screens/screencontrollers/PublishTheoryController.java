package ui.swing.screens.screencontrollers;

import domain.controllers.GameController;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Molecule;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;

public class PublishTheoryController {

    private IngredientCard selectedIngredient;
    private Molecule selectedMolecule;

    List<IngredientCard> ingredientCards = IngredientCard.getIngredientList();
    List<Molecule> molecules = Molecule.getMoleculeList();

    private GameController gameController = GameController.getInstance();

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView ingredient1;
    @FXML
    private ImageView ingredient2;
    @FXML
    private ImageView ingredient3;
    @FXML
    private ImageView ingredient4;
    @FXML
    private ImageView ingredient5;
    @FXML
    private ImageView ingredient6;
    @FXML
    private ImageView ingredient7;
    @FXML
    private ImageView ingredient8;

    @FXML
    private ImageView molecule1;
    @FXML
    private ImageView molecule2;
    @FXML
    private ImageView molecule3;
    @FXML
    private ImageView molecule4;
    @FXML
    private ImageView molecule5;
    @FXML
    private ImageView molecule6;
    @FXML
    private ImageView molecule7;
    @FXML
    private ImageView molecule8;

    @FXML
    private Label publishButton;

    private void resetAllIngredients() {
        setIngredientImage(ingredient1, "Crystalite.png");
        setIngredientImage(ingredient2, "Dandelion_Root.png");
        setIngredientImage(ingredient3, "Dragon_powder.png");
        setIngredientImage(ingredient4, "Golden_Mald.png");
        setIngredientImage(ingredient5, "Moon_Blossom.png");
        setIngredientImage(ingredient6, "Shimmer_Fungus.png");
        setIngredientImage(ingredient7, "Starlight_Nectar.png");
        setIngredientImage(ingredient8, "Verdant_Fern.png");
    }

    private void resetAllMolecules() {
        setMoleculeImage(molecule1, "molecule1.png");
        setMoleculeImage(molecule2, "molecule2.png");
        setMoleculeImage(molecule3, "molecule3.png");
        setMoleculeImage(molecule4, "molecule4.png");
        setMoleculeImage(molecule5, "molecule5.png");
        setMoleculeImage(molecule6, "molecule6.png");
        setMoleculeImage(molecule7, "molecule7.png");
        setMoleculeImage(molecule8, "molecule8.png");
    }

    private void setIngredientImage(ImageView ingredient, String imageName) {
        Image image = new Image("/images/ingredientCards/" + imageName);
        ingredient.setImage(image);
    }

    private void setMoleculeImage(ImageView molecule, String imageName) {
        Image image = new Image("/images/molecules/" + imageName);
        molecule.setImage(image);
    }


    @FXML
    public void ingredient1MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Crystalite.gif");
        resetAllIngredients();
        ingredient1.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Crystalite".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }
    }

    @FXML
    public void ingredient2MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Dandelion_Root.gif");
        resetAllIngredients();
        ingredient2.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Dandelion Root".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }

    }
    @FXML
    public void ingredient3MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Dragon_powder.gif");
        resetAllIngredients();
        ingredient3.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Dragon Powder".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }

    }
    @FXML
    public void ingredient4MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Golden_Mald.gif");
        resetAllIngredients();
        ingredient4.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Golden Mold".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }
    }
    @FXML
    public void ingredient5MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Moon_Blossom.gif");
        resetAllIngredients();
        ingredient5.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Moon Blossom".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }
    }
    @FXML
    public void ingredient6MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Shimmer_Fungus.gif");
        resetAllIngredients();
        ingredient6.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Shimmer Fungus".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }
    }
    @FXML
    public void ingredient7MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Starlight_Nectar.gif");
        resetAllIngredients();
        ingredient7.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Starlight Nectar".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }
    }

    @FXML
    public void ingredient8MousePressed(MouseEvent event) {
        Image image = new Image("/images/ingredientCards/Verdant_Fern.gif");
        resetAllIngredients();
        ingredient8.setImage(image);

        selectedIngredient = null;
        for (IngredientCard card : ingredientCards) {
            if ("Verdant Fern".equals(card.getName())) {
                selectedIngredient = card;
                break;
            }
        }
    }

    @FXML
    public void molecule1MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule1Selected.png");
        resetAllMolecules();
        molecule1.setImage(image);

        selectedMolecule = null;
        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule1.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void molecule2MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule2Selected.png");
        resetAllMolecules();
        molecule2.setImage(image);
        selectedMolecule = null;

        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule2.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void molecule3MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule3Selected.png");
        resetAllMolecules();
        molecule3.setImage(image);

        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule3.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void molecule4MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule4Selected.png");
        resetAllMolecules();
        molecule4.setImage(image);

        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule4.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void molecule5MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule5Selected.png");
        resetAllMolecules();
        molecule5.setImage(image);

        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule5.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void molecule6MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule6Selected.png");
        resetAllMolecules();
        molecule6.setImage(image);

        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule6.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void molecule7MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule7Selected.png");
        resetAllMolecules();
        molecule7.setImage(image);

        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule7.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void molecule8MousePressed(MouseEvent event) {
        Image image = new Image("/images/molecules/molecule8Selected.png");
        resetAllMolecules();
        molecule8.setImage(image);

        for (Molecule molecule : molecules) {
            if ("/images/molecules/molecule8.png".equals(molecule.getImagePath())) {
                selectedMolecule = molecule;
                break;
            }
        }
    }

    @FXML
    public void publishButtonMousePressed(MouseEvent event) {

        if(selectedIngredient == null || selectedMolecule == null) {
            System.out.println("null ingredient or molecule.");
        } else {
            int playerId = gameController.getCurrentPlayer().getPlayerId();
            int ingredientId = selectedIngredient.getCardId();
            int moleculeId = selectedMolecule.getMoleculeId();
            gameController.publishTheory(playerId,ingredientId, moleculeId);

        }

    }

    @FXML
    public void exitButtonPressed(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        // Close the stage
        stage.close();
    }

}
