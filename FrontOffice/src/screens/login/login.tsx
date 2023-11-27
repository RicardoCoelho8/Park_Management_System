import React, { useEffect, useState } from "react";
import { logo } from "../../images";
import { Button, Container, Form, Image } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { setUserData } from "../../store";
import { useLoginMutation } from "../../store/userData/api";
import { Link, useNavigate } from "react-router-dom";
import { ModalErrorForm } from "../../components";
import { decodeJwt, storeUserInLocalStorage } from "../../utils/jwtUtils";

export const LoginScreen: React.FC = () => {
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [loginUser, { data, error }] = useLoginMutation();
  const navigate = useNavigate();

  useEffect(() => {
    if (data) {
      console.log("Login sucessful", data);
      const decodedToken = decodeJwt(data.token);
      storeUserInLocalStorage(data.token, decodedToken.sub, decodedToken.role);
      dispatch(
        setUserData({ userId: decodedToken.sub, userRole: decodedToken.role })
      );
      navigate("/home");
    } else if (error) {
      setShowModal(true);
      console.log("Login error", error);
    }
  }, [data, error]);

  const handleOnClickSubmit = () => {
    loginUser({ email, password });
    setEmail("");
    setPassword("");
  };

  return (
    /* Logo
    review image drag 
    */
    <>
      <Container style={{ padding: "20px" }}>
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
            <Button
              variant="primary"
              onClick={handleOnClickSubmit}
              style={{ marginTop: "0.313rem" }}
            >
              Sign in
            </Button>
          </Form>
        </Container>
        <Container
          style={{
            display: "flex",
            justifyContent: "center",
            marginTop: "0.625rem",
          }}
        >
          <p style={{ marginRight: "0.313rem" }}> Not registered yet? </p>
          <Link to={"/register"}>Register Here!</Link>
        </Container>
      </Container>
      <ModalErrorForm showModal={showModal} setShowModal={setShowModal} />
    </>
  );
};
