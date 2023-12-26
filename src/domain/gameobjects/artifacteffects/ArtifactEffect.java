package domain.gameobjects.artifacteffects;

import domain.Game;
import domain.gameobjects.Player;

public interface ArtifactEffect {
    void apply(Game game, Player player);
}

