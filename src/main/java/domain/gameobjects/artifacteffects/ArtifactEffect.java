package domain.gameobjects.artifacteffects;

import domain.Game;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;
import domain.gameobjects.PotionCard;

public interface ArtifactEffect {
    void apply(Game game, Player player);
     void applyOnMakeExperiment(Player player, IngredientCard firstCard, IngredientCard secondCard);
}

