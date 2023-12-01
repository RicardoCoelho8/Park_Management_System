import React, { useState } from "react";
import { Dropdown, Form, InputGroup } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import { PaymentMethod } from "../../utils/types";

interface PaymentMethodDropdownProps {
  onSelect: (value: string) => void;
}

export const PaymentMethodDropdown: React.FC<PaymentMethodDropdownProps> = (
  props
) => {
  const { onSelect } = props;
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState("");
  const [showDropdown, setShowDropdown] = useState(false);

  const toggleDropdown = () => {
    setShowDropdown(!showDropdown);
  };

  const handleSelect = (eventKey: string) => {
    setSelectedPaymentMethod(eventKey);
    setShowDropdown(false);
    onSelect(eventKey);
  };

  return (
    <Dropdown>
      <InputGroup className="mb-3">
        <Form.Control
          placeholder="Payment Method"
          aria-describedby="basic-addon2"
          readOnly
          value={selectedPaymentMethod || ""}
        />
        <InputGroup.Text
          id="basic-addon2"
          style={{
            background:
              "linear-gradient(180deg, rgba(0, 212, 255, 0.8) 0%, rgba(20, 100, 120, 0.6) 40%, rgba(12, 57, 80, 0.4) 80%, rgba(12, 57, 80, 0.2) 100%)",
          }}
          onClick={toggleDropdown}
        >
          <i
            className="bi bi-caret-down-fill"
            style={{
              transform: showDropdown ? "rotate(180deg)" : "",
              transition: "transform 0.3s ease-in-out",
              color: "white",
            }}
          ></i>
        </InputGroup.Text>
      </InputGroup>
      <Dropdown.Menu
        show={showDropdown}
        style={{
          display: showDropdown ? "block" : "none",
          width: "100%",
          top: "100%",
          backgroundColor: "#f9f9f9",
          boxShadow: "0px 8px 16px 0px rgba(0,0,0,0.2)",
          zIndex: 1,
        }}
      >
        {Object.values(PaymentMethod).map((method) => (
          <Dropdown.Item
            key={method}
            active={selectedPaymentMethod === method}
            onClick={() => handleSelect(method)}
          >
            {method}
          </Dropdown.Item>
        ))}
      </Dropdown.Menu>
    </Dropdown>
  );
};
