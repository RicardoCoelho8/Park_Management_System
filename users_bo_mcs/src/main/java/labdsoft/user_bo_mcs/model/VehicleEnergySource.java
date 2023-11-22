package labdsoft.user_bo_mcs.model;

public enum VehicleEnergySource {
    FUEL {
        @Override
        public String toString() {
            return "FUEL";
        }
    },
    GPL {
        @Override
        public String toString() {
            return "GPL";
        }
    },
    ELECTRIC {
        @Override
        public String toString() {
            return "ELECTRIC";
        }
    },
    PLUG_IN_HYBRID {
        @Override
        public String toString() {
            return "PLUG_IN_HYBRID";
        }
    },
}
