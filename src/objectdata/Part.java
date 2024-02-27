package objectdata;

public class Part {
    private final int startIndex;
    private final int count;
    private Topology topology;

    public Part(int startIndex, int count, Topology topology) {
        this.startIndex = startIndex;
        this.count = count;
        this.topology = topology;
    }

    public int getCount() {
        return count;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public Topology getTopology() {
        return topology;
    }
}
