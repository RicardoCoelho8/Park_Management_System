package labdsoft.payments_bo_mcs.model.vehicle;

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
