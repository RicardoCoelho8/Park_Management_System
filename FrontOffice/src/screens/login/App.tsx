import React, { useState } from "react";
import { logo } from "../../images";
import { Button, Container, Form, Image } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { setUserData } from "../../store";

export const App: React.FC = () => {
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleOnClickSubmit = () => {
    console.log("App.tsx", email, password);
    dispatch(setUserData({ email, password }));
    setEmail("");
    setPassword("");
  };

  return (
    /* Logo
    review image drag 
    */
    <Container style={{ padding: "20px" }}>
      <Container style={{ display: "flex", height: "auto" }} draggable="false">
        <Image src={logo} fluid style={{ width: "100%" }} draggable="false" />
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
    </Container>
  );
};
