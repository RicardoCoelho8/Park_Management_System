package labdsoft.user_bo_mcs.model;

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
