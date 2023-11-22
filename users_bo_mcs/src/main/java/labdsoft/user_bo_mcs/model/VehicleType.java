package labdsoft.user_bo_mcs.model;

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
