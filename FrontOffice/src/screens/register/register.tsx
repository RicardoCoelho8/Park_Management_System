import React, { useEffect, useState } from "react";
import { Button, Container, Form, Image } from "react-bootstrap";
import { usePostUserDataMutation } from "../../store/userData/api";
import { logo } from "../../images";
import { ModalErrorForm } from "../../components";
import { useNavigate } from "react-router-dom";

enum VehicleType {
  electric = "ELECTRIC",
  gpl = "GPL",
  others = "OTHERS",
}

export const RegisterScreen: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [paymentMethod, setPaymentMethod] = useState("");
  const [nif, setNif] = useState("");
  const [licensePlateNumber, setLicensePlateNumber] = useState("");
  const [isLicensePlateValid, setIsLicensePlateValid] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [postUserData, { error, isSuccess }] = usePostUserDataMutation();
  const navigate = useNavigate();

  useEffect(() => {
    if (error) {
      setShowModal(true);
    }
    if (isSuccess) {
      navigate("/");
    }
  }, [error, isSuccess, navigate]);

  const getRandomVehicleType = (): VehicleType => {
    const types = Object.values(VehicleType);
    const randomIndex = Math.floor(Math.random() * types.length);
    return types[randomIndex] as VehicleType;
  };

  const handleLicensePlateNumberOnChange = (e: any) => {
    const regex =
      /^([A-Z0-9]{2}-\d{2}-\d{2}|[A-Z0-9]{2}-\d{2}-[A-Z0-9]{2}|[A-Z0-9]{2}-\d{2}-\d{1}-[A-Z0-9]{1})$/;
    const value = e.target.value;
    const isValid = regex.test(value);
    setIsLicensePlateValid(isValid);
    setLicensePlateNumber(value);
  };

  const handleButtonOnClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    const vehicleType = getRandomVehicleType();

    try {
      postUserData({
        email,
        password,
        firstName,
        lastName,
        paymentMethod,
        nif,
        licensePlateNumber,
        vehicleType,
      });
    } catch (error) {
      console.log("Error Registration", error);
    }
  };

  return (
    <>
      <Container style={{ padding: "20px" }}>
        {/* Logo */}
        <Container style={{ display: "flex", height: "auto" }}>
          <Image src={logo} fluid style={{ width: "100%" }} />
        </Container>
        {/* Form */}
        <Container>
          <Form style={{ display: "flex", flexDirection: "column" }}>
            <Form.Group
              controlId="formBasicEmail"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <Form.Label style={{ margin: "0 0 5px 0" }}>
                Email address
              </Form.Label>
              <Form.Control
                style={{
                  borderRadius: "16px",
                  borderWidth: "1px",
                  margin: "0 0 5px 0",
                }}
                type="email"
                placeholder="example@example.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </Form.Group>

            <Form.Group
              controlId="formBasicPassword"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <Form.Label style={{ margin: "0 0 5px 0" }}>Password</Form.Label>
              <Form.Control
                style={{
                  borderRadius: "16px",
                  borderWidth: "1px",
                  margin: "0 0 5px 0",
                }}
                type="password"
                placeholder="**********"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </Form.Group>
            <Form.Group
              controlId="formBasicFirstName"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <Form.Label style={{ margin: "0 0 5px 0" }}>
                First Name
              </Form.Label>
              <Form.Control
                style={{
                  borderRadius: "16px",
                  borderWidth: "1px",
                  margin: "0 0 5px 0",
                }}
                type="firstName"
                placeholder="First Name"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
            </Form.Group>
            <Form.Group
              controlId="formBasicLastName"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <Form.Label style={{ margin: "0 0 5px 0" }}>Last Name</Form.Label>
              <Form.Control
                style={{
                  borderRadius: "16px",
                  borderWidth: "1px",
                  margin: "0 0 5px 0",
                }}
                type="lastName"
                placeholder="Last Name"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </Form.Group>
            <Form.Group
              controlId="formBasicPaymentMethod"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <Form.Label style={{ margin: "0 0 5px 0" }}>
                Payment Method
              </Form.Label>
              <Form.Control
                style={{
                  borderRadius: "16px",
                  borderWidth: "1px",
                  margin: "0 0 5px 0",
                }}
                type="paymentMethod"
                placeholder="Payment Method"
                value={paymentMethod}
                onChange={(e) => setPaymentMethod(e.target.value)}
              />
            </Form.Group>
            <Form.Group
              controlId="formBasicNIF"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <Form.Label style={{ margin: "0 0 5px 0" }}>NIF</Form.Label>
              <Form.Control
                style={{
                  borderRadius: "16px",
                  borderWidth: "1px",
                  margin: "0 0 5px 0",
                }}
                type="NIF"
                placeholder="NIF"
                value={nif}
                onChange={(e) => setNif(e.target.value)}
              />
            </Form.Group>
            <Form.Group
              controlId="formBasicLicensePlateNumber"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <Form.Label style={{ margin: "0 0 5px 0" }}>
                License Plate Number{" "}
              </Form.Label>
              <Form.Control
                style={{
                  borderRadius: "16px",
                  borderWidth: "1px",
                  margin: "0 0 5px 0",
                }}
                type="licensePlateNumber"
                placeholder="License Plate Number"
                value={licensePlateNumber}
                onChange={handleLicensePlateNumberOnChange}
                isInvalid={!isLicensePlateValid}
              />
              <Form.Control.Feedback type="invalid">
                Invalid license plate number
              </Form.Control.Feedback>
            </Form.Group>
          </Form>
          <Button
            variant="primary"
            onClick={handleButtonOnClick}
            style={{ marginTop: "0.313rem", width: "100%" }}
          >
            Sign up
          </Button>
        </Container>
      </Container>
      {/* MODAL */}
      <ModalErrorForm showModal={showModal} setShowModal={setShowModal} />
    </>
  );
};
