package labdsoft.payments_bo_mcs.model.vehicle;

public enum VehicleType {
    ELECTRIC {
        @Override
        public String toString() {
            return "ELECTRIC";
        }
    },
    FUEL {
        @Override
        public String toString() {
            return "FUEL";
        }
    }
}
