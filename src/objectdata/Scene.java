package objectdata;

import java.util.List;

public class Scene {
    private final List<Solid> solids;

    public Scene(List<Solid> solids) {
        this.solids = solids;
    }

    public List<Solid> getSolids() {
        return solids;
    }
}
