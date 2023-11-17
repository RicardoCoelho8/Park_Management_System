package labdsoft.park_bo_mcs.models.park;

public enum SpotType {
    ELECTRIC_SPOT {
        @Override
        public String toString() {
            return "ELECTRIC_SPOT";
        }
    },
    GPL_SPOT {
        @Override
        public String toString() {
            return "GPL_SPOT";
        }
    },
    OTHERS_SPOT {
        @Override
        public String toString() {
            return "OTHERS_SPOT";
        }
    },
}
