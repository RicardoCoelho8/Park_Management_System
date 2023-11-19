package labdsoft.user_bo_mcs.model;

public enum PaymentMethod {
    CREDIT {
        @Override
        public String toString() {
            return "CREDIT_CARD";
        }
    },
    DEBIT {
        @Override
        public String toString() {
            return "DEBIT_CARD";
        }
    },
    PAYPAL {
        @Override
        public String toString() {
            return "PAYPAL";
        }
    },
    NOT_DEFINED {
        @Override
        public String toString() {
            return "NOT_DEFINED";
        }
    }
}
