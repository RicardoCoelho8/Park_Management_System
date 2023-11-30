import React, { useState, useEffect } from "react";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { Button, Container, Form, Image, Dropdown } from "react-bootstrap";
import { useUserId } from "../../store/userData/useUserId";
import { useUserName } from "../../store/userData/useUserName";
import { useUserEmail } from "../../store/userData/useUserEmail";
import { useUserRole } from "../../store/userData/useUserRole";
import { useChangePaymentMutation, useGetUserPaymentMethodQuery } from "../../store/userData/api";
import { person } from "../../images";
import { PaymentMethod } from "../../utils/types";
import { ModalErrorForm } from "../../components";

export const AccountScreen: React.FC = () => {
  const userId = useUserId();
  const userName = useUserName();
  const userEmail = useUserEmail();
  const userRole = useUserRole();
  const { data, refetch } = useGetUserPaymentMethodQuery(userId as string);
  const [showModalError, setShowModalError] = useState(false);
  const [changePayment, { error, data: newPayment }] =
    useChangePaymentMutation();

  useEffect(() => {
    if (error) {
      setShowModalError(true);
    } else if (newPayment) {
      refetch();
    }
  }, [newPayment, error, data]);

  const getUserPaymentMethod = () => {
    return data?.paymentMethod || PaymentMethod.not_defined;
  };
  var userPaymentMethod = getUserPaymentMethod();

  const [showDropdown, setShowDropdown] = useState(false);

  var handleDropdownToggle = () => {
    setShowDropdown(!showDropdown);
  };

  const handlePaymentMethodSelect = (selectedPaymentMethod: string | null) => {
    if (selectedPaymentMethod !== null) {
      try {
        changePayment({
          paymentData: {
            paymentMethod: selectedPaymentMethod,
          },
          userId: userId as string,
        });
      } catch (error) {
        console.error("Error updating payment method:", error);
      }
    }
    setShowDropdown(false);
  };

  const paymentMethodOptions = Object.values(PaymentMethod) as string[];

  return (
    <>
      <Container style={{ padding: "20px" }}>
        <Container style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
          <Image src={person} fluid style={{ width: "100%", maxWidth: "150px", borderRadius: "50%" }} />
          <h3>{userName}</h3>
          <p>{userEmail}</p>
          <p>Role: {userRole}</p>
          <p>Payment method: {userPaymentMethod}</p>
        </Container>
        {/* Form */}
        <Container>
          <Form style={{ display: "flex", flexDirection: "column" }}>
            <Button style={{ marginTop: "0.313rem" }} onClick={handleDropdownToggle}>
              Change payment method
            </Button>
            <Dropdown show={showDropdown} onSelect={handlePaymentMethodSelect} style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
              <Dropdown.Menu>
                {paymentMethodOptions.map((method, index) => (
                  <Dropdown.Item key={index} eventKey={method}>
                    {method}
                  </Dropdown.Item>
                ))}
              </Dropdown.Menu>
            </Dropdown>
          </Form>
        </Container>
      </Container>
      <ModalErrorForm
        showModal={showModalError}
        setShowModal={setShowModalError}
      />
      <BottomNavBar />
    </>
  );
};
