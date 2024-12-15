package data;

import data.exceptions.geographic.InvalidGeographicCoordinateException;

final public class GeographicPoint {
    private final float latitude;
    private final float longitude;

    public GeographicPoint (float lat, float lon) throws InvalidGeographicCoordinateException {
        if (lat < -90 || lat > 90) {
            throw new InvalidGeographicCoordinateException("Latitude must be between -90 and 90: " + lat);
        }
        if (lon < -180 || lon > 180) {
            throw new InvalidGeographicCoordinateException("Longitude must be between -180 and 180: " + lon);
        }
        this.latitude = lat;
        this.longitude = lon;
    }
    public float getLatitude () { return latitude; }
    public float getLongitude () { return longitude; }
    @Override
    public boolean equals (Object o) {
        boolean eq;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicPoint gP = (GeographicPoint) o;
        eq = ((latitude == gP.latitude) && (longitude == gP.longitude));
        return eq;
    }
    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        return result;
    }
    @Override
    public String toString () {
        return "Geographic point {" + "latitude='" + latitude + '\'' +
                "longitude='" + longitude + '}';
    }

    public double CalculateDistance(GeographicPoint gP){
        double lat1Rad = Math.toRadians(this.latitude);
        double lon1Rad = Math.toRadians(this.longitude);
        double lat2Rad = Math.toRadians(gP.latitude);
        double lon2Rad = Math.toRadians(gP.longitude);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));


        return 6371 * c;

    }
}
