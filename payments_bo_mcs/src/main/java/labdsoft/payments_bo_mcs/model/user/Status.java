package labdsoft.payments_bo_mcs.model.user;

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
