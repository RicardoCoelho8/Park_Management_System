package labdsoft.user_bo_mcs.model;

public enum VehicleType {
    ELECTRIC {
        @Override
        public String toString() {
            return "ELECTRIC";
        }
    },
    GPL {
        @Override
        public String toString() {
            return "GPL";
        }
    },
    OTHERS {
        @Override
        public String toString() {
            return "OTHERS";
        }
    },
}
