import React, { useState } from "react";
import { Button, Form, InputGroup, Modal } from "react-bootstrap";

interface ModalAddNewVehicleProps {
  showModal: boolean;
  setShowModal: (modal: boolean) => void;
  assign: (amount: number) => void;
  onClickClose: () => void;
}

export const ModalAssignParkyCoins: React.FC<ModalAddNewVehicleProps> = (
  props
) => {
  const { showModal, setShowModal, assign, onClickClose } = props;
  const [isAmountValid, setIsAmountValid] = useState(true);
  const [amount, setAmount] = useState(0);

  const handleOnChange = (e: any) => {
    const value = e.target.value;
    setIsAmountValid(!isNaN(value) && value !== "");
    setAmount(value);
  };

  return (
    <>
      <Modal
        show={showModal}
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <Modal.Body>
          <h4>Assign Parky Coins</h4>
          <p>Insert Amount</p>
          <InputGroup>
            <Form.Control
              value={amount}
              onChange={handleOnChange}
              isInvalid={!isAmountValid}
            />
          </InputGroup>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => {
              setShowModal(false);
              onClickClose();
            }}
          >
            Close
          </Button>
          <Button
            variant="primary"
            onClick={() => {
              assign(amount);
              setAmount(0);
            }}
            disabled={amount <= 0 ? true : !isAmountValid}
          >
            Assign
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
