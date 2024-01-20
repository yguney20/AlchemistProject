package domain.gameobjects.artifacteffects;

import domain.Game;
import domain.gameobjects.IngredientCard;
import domain.gameobjects.Player;

public class PhilosophersCompassEffect implements ArtifactEffect{

    @Override
    public void apply(Game game, Player player) {
        player.setPhilosopherCompassEffect(true);
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


