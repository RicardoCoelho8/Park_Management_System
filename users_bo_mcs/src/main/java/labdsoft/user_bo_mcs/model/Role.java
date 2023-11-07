package labdsoft.user_bo_mcs.model;

public enum Role {
    SUPERVISOR {
        @Override
        public String toString() {
            return "SUPERVISOR";
        }
    },
    CUSTOMER {
        @Override
        public String toString() {
            return "CUSTOMER";
        }
    },
    CUSTOMER_MANAGER{
        @Override
        public String toString() {
            return "CUSTOMER_MANAGER";
        }
    },
    PARK_MANAGER {
        @Override
        public String toString() {
            return "PARK_MANAGER";
        }
    }
}
