package labdsoft.park_bo_mcs.models.user;

public enum VehicleType {
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
