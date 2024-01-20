package domain.gameobjects.artifacteffects;

import domain.Game;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;

/**
 * ElixirOfInsightEffect is an implementation of the ArtifactEffect interface.
 * This effect allows a player to view and rearrange the top three cards of the ingredient deck.
 */


public class ElixirOfInsightEffect implements ArtifactEffect {

    @Override
    public void apply(Game game, Player player) {
        // Since this usage comes by UI. This part is empty
    }

    @Override
    public void applyOnMakeExperiment(Player player, IngredientCard firstCard, IngredientCard secondCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyOnMakeExperiment'");
    }

    @Override
    public void applyOnDebunkTheory(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyOnDebunkTheory'");
    }


    
}
