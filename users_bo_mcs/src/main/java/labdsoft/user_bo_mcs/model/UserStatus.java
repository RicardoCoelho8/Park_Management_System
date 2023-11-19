package labdsoft.user_bo_mcs.model;

public enum UserStatus {
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
