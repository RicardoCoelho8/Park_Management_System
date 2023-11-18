import React, { useState } from "react";
import { logo } from "../../images";
import { Button, Container, Form, Image } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { UserDataInput, setUserData } from "../../store";
import { usePostUserDataMutation } from "../../store/userData/api";
import { Link, useNavigate } from "react-router-dom";

export const LoginScreen: React.FC = () => {
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [postUserData, { data, error, isLoading }] = usePostUserDataMutation();
  const navigate = useNavigate();

  const handleOnClickSubmit = () => {
    dispatch(setUserData({ email, password }));

    setEmail("");
    setPassword("");
    navigate("/register");
  };

  return (
    /* Logo
    review image drag 
    */
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
          <Button variant="primary" onClick={handleOnClickSubmit}>
            Sign in
          </Button>
        </Form>
      </Container>
      <Container
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          marginRight: "5px",
        }}
      >
        <p style={{ marginRight: "0.313rem" }}> Not registered yet? </p>
        <Link to={"/register"}>Register Here!</Link>
      </Container>
    </Container>
  );
};
