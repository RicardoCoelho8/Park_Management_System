package labdsoft.park_bo_mcs.models.user;

public enum Status {
    ENABLED {
        @Override
        public String toString() {
            return "ENABLED";
        }
    },
    DISABLED {
        @Override
        public String toString() {
            return "DISABLED";
        }
    },
    BLOCKED {
        @Override
        public String toString() {
            return "BLOCKED";
        }
    }
}
