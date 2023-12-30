package domain.gameobjects.artifacteffects;

import java.util.Random;

import domain.Game;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;

public class MagicMortarEffect implements  ArtifactEffect {

    @Override
    public void apply(Game game, Player player) {
        player.setMagicMortarActive(true);
    }

    @Override
    public void applyOnMakeExperiment(Player player, IngredientCard firstCard, IngredientCard secondCard) {
         Random random = new Random();
                    boolean keepFirstCard = random.nextBoolean(); // Randomly returns true or false
                    if (keepFirstCard) {
                         player.getIngredientInventory().remove(secondCard);
                    } else {
                         player.getIngredientInventory().remove(firstCard);
                    }
    }
}
