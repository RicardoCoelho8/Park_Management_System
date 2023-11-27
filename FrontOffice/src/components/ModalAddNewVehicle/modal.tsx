import React, { useState } from "react";
import { Button, Form, InputGroup, Modal } from "react-bootstrap";
import { validateLicensePlate } from "../../utils/functions";

interface ModalAddNewVehicleProps {
  showModal: boolean;
  setShowModal: (modal: boolean) => void;
  addVehicle: (licensePlateNumber: string) => void;
}

export const ModalAddNewVehicle: React.FC<ModalAddNewVehicleProps> = (
  props
) => {
  const { showModal, setShowModal, addVehicle } = props;
  const [isLicensePlateValid, setIsLicensePlateValid] = useState(true);
  const [licensePlateNumber, setLicensePlateNumber] = useState("");

  const handleOnChange = (e: any) => {
    const value = e.target.value;
    setIsLicensePlateValid(validateLicensePlate(value));
    setLicensePlateNumber(value);
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
          <h4>Add New Vehicle</h4>
          <p>Insert License Plate</p>
          <InputGroup>
            <Form.Control
              placeholder="AA-00-AA"
              value={licensePlateNumber}
              onChange={handleOnChange}
              isInvalid={!isLicensePlateValid}
            />
          </InputGroup>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Close
          </Button>
          <Button
            variant="primary"
            onClick={() => addVehicle(licensePlateNumber)}
            disabled={licensePlateNumber === "" ? true : !isLicensePlateValid}
          >
            Add Vehicle
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
