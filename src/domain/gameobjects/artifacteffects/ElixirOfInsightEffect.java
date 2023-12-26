package domain.gameobjects.artifacteffects;

import java.util.List;

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
        if( game.getIngredientDeck().size() >= 3) {
            
        }
    }


    
}
