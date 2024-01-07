import React, { useState, useEffect } from "react";
import { Container } from "react-bootstrap";
import { useUserId } from "../../store/userData/useUserId";
import { useUserName } from "../../store/userData/useUserName";
import { useUserEmail } from "../../store/userData/useUserEmail";
import {
  useChangePaymentMutation,
  useGetUserPaymentMethodQuery,
} from "../../store/userData/api";
import { ModalErrorForm } from "../../components";
import { PaymentMethodDropdown } from "../../components/PaymentMethodDropdown/paymentDropdown";
import { NavBar } from "../../components/BottomNavBar/navBar";

export const AccountScreen: React.FC = () => {
  const userId = useUserId();
  const userName = useUserName();
  const userEmail = useUserEmail();
  const { data, refetch } = useGetUserPaymentMethodQuery(userId as string);
  const [paymentMethod, setPaymentMethod] = useState("");
  const [showModalError, setShowModalError] = useState(false);
  const [changePayment, { error, data: newPayment }] =
    useChangePaymentMutation();

  useEffect(() => {
    if (data) {
      setPaymentMethod(data.paymentMethod);
    }
    if (error) {
      setShowModalError(true);
    } else if (newPayment) {
      refetch();
    }
  }, [newPayment, error, data]);

  const handlePaymentMethodSelect = (value: string) => {
    try {
      console.log("NEW SELECTED PAYMENT", value);
      changePayment({
        paymentData: {
          paymentMethod: value,
        },
        userId: userId as string,
      });
    } catch (error) {
      console.error("Error updating payment method:", error);
    }
  };

  return (
    <>
      <Container
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <i
          className="bi bi-person-circle"
          style={{ fontSize: "6.25rem", color: "#005c66" }}
        />
        <h2 style={{ color: "#005c66" }}>{userName}</h2>
        <Container
          style={{ height: "0.063rem", background: "#005c66", width: "100%" }}
        />
      </Container>
      {/* Form */}
      <Container
        style={{
          padding: "0 1.875rem",
          color: "#005c66",
          marginTop: "0.625rem",
        }}
      >
        <p style={{ margin: "0 0 0.313rem 0 " }}>Email</p>
        <h5>{userEmail}</h5>
        <Container
          style={{ height: "0.063rem", background: "#005c66", width: "100%" }}
        />
        <p style={{ margin: "0 0 0.313rem 0 " }}>Payment method </p>
        <h5>{paymentMethod} </h5>

        <PaymentMethodDropdown onSelect={handlePaymentMethodSelect} />
      </Container>
      <ModalErrorForm
        showModal={showModalError}
        setShowModal={setShowModalError}
      />
      <NavBar />
    </>
  );
};
