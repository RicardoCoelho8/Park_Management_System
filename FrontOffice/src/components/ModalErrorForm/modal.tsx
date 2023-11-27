import React from "react";
import { Button, Modal } from "react-bootstrap";

interface ModalErrorFormProps {
  showModal: boolean;
  setShowModal: (modal: boolean) => void;
}

export const ModalErrorForm: React.FC<ModalErrorFormProps> = (props) => {
  const { showModal, setShowModal } = props;

  return (
    <>
      <Modal
        show={showModal}
        size="lg"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <Modal.Body>
          <h4>Error</h4>
          <p>There seems to be an error, please try again later.</p>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowModal(false)}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
