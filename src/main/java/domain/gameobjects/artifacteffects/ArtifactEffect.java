package domain.gameobjects.artifacteffects;

import domain.Game;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;

/*
* Artifect Effect were implmented with Strategy Pattern
* Each has different effect and integration with different parts of game
* Scalability: Different methods can be added here.
*/ 

public interface ArtifactEffect {
    void apply(Game game, Player player);
    void applyOnMakeExperiment(Player player, IngredientCard firstCard, IngredientCard secondCard);
    void applyOnDebunkTheory(Player player );

}

