import React, { useEffect, useState } from "react";
import { logo } from "../../images";
import { Button, Container, Form, Image } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { setUserData } from "../../store";
import { useLoginMutation } from "../../store/userData/api";
import { Link, useNavigate } from "react-router-dom";
import { ModalErrorForm } from "../../components";
import { decodeJwt, storeUserInLocalStorage } from "../../utils/jwtUtils";
import { useUserIsAuth } from "../../store/userData/useUserIsAuth";

export const LoginScreen: React.FC = () => {
  const dispatch = useDispatch();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [loginUser, { data, error }] = useLoginMutation();
  const navigate = useNavigate();
  const innerWidth = window.innerWidth;

  useEffect(() => {
    if (data) {
      console.log("Login sucessful", data);
      const decodedToken = decodeJwt(data.token);
      storeUserInLocalStorage(data.token, decodedToken.sub, decodedToken.role);
      dispatch(
        setUserData({
          userId: decodedToken.sub,
          userRole: decodedToken.role,
          email,
          name: data.name,
          isAuth: true,
        })
      );
      setEmail("");
      setPassword("");
      navigate("/home");
    } else if (error) {
      setShowModal(true);
      console.log("Login error", error);
    }
  }, [data, error]);

  const handleOnClickSubmit = () => {
    loginUser({ email, password });
  };

  return (
    /* Logo
    review image drag 
    */
    <>
      <Container
        style={{
          padding: "20px",
          display: "flex",
          flexDirection: innerWidth > 1024 ? "row" : "column",
          alignItems: "center",
        }}
      >
        <Container style={{ height: "auto" }}>
          <Image src={logo} fluid style={{ width: "100%" }} />
        </Container>
        {/* Form */}
        <Container>
          <Form>
            <Form.Group controlId="formBasicEmail">
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

            <Form.Group controlId="formBasicPassword">
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
              style={{ marginTop: "0.313rem", width: "100%" }}
            >
              Sign in
            </Button>
          </Form>
        </Container>
        {innerWidth < 1024 && (
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
        )}
      </Container>
      <ModalErrorForm showModal={showModal} setShowModal={setShowModal} />
    </>
  );
};
