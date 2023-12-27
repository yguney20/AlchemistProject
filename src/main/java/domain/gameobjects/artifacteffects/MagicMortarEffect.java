package domain.gameobjects.artifacteffects;

import domain.Game;
import domain.gameobjects.Player;

public class MagicMortarEffect implements  ArtifactEffect {

    @Override
    public void apply(Game game, Player player) {
        player.setMagicMortarActive(true);
    }



    
}
