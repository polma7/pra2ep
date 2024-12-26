package data;

import data.exceptions.serviceId.InvalidServiceIdFormatException;
import data.exceptions.serviceId.NullServiceIdException;


public final class ServiceID {
    private final String id;
    private static final String ID_PATTERN = "^[A-Z]{2}-\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";

    public ServiceID(String id) throws NullServiceIdException, InvalidServiceIdFormatException {
        if (id == null || id.isEmpty()) {
            throw new NullServiceIdException("Service ID cannot be null or empty.");
        }
        if (!id.matches(ID_PATTERN)) {
            throw new InvalidServiceIdFormatException("Service ID must match the format: 2 letters, hyphen, and LocalDateTime (yyyy-MM-ddTHH:mm:ss).");
        }
        this.id  = id;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceID srID = (ServiceID) o;
        return srID.getId().equals(this.id);
    }

    @Override
    public String toString() {
        return "Service ID { ID:" + id + " }";
    }
}
