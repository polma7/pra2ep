package data;

public final class StationID {
    private final String id;

    public StationID(String id){
        this.id  = id;
    }
    public String getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object o){
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationID statID = (StationID) o;
        eq = statID.getId().equals(this.id);
        return eq;
    }

    @Override
    public String toString () {
        return "Station ID { ID:" + id + " }";
    }
}
