package domain.gameobjects.artifacteffects;

import domain.Game;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;

public class PrintingPressEffect implements ArtifactEffect {
    
    @Override
    public void apply(Game game, Player player ) {
        // Logic for Printing Press - allows publishing a theory without paying gold
        player.setPrintingPressActive(true);
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

