package data;

public final class VehicleID {
    private final String id;

    public VehicleID(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    @Override
    public boolean equals(Object o ){
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleID vId = (VehicleID) o;
        eq = vId.getId().equals(this.id);
        return eq;
    }

    @Override
    public String toString(){
        return "VehicleID { " + this.id + " }";
    }
}
