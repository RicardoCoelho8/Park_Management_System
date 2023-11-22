package labdsoft.park_bo_mcs.models.park;

public enum SpotVehicleType {
    AUTOMOBILE {
        @Override
        public String toString() {
            return "AUTOMOBILE";
        }
    },
    MOTORCYCLE {
        @Override
        public String toString() {
            return "MOTORCYCLE";
        }
    },
}
