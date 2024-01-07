import React, { useEffect, useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import { NavBar } from "../../components/BottomNavBar/navBar";
import { useUserId } from "../../store/userData/useUserId";
import {
  useChangeUserParkyCoinsWalletFlagMutation,
  useGetUserParkyCoinsFlagQuery,
  useGetUserParkyWalletQuery,
} from "../../store/userData/api";
import { UserParkyWalletOutput } from "../../store";
import "../../utils/styles.css";

export const PaymentScreen: React.FC = () => {
  const userId = useUserId();
  const { data } = useGetUserParkyWalletQuery(userId as string);
  const { data: userFlag, refetch: refetchFlag } =
    useGetUserParkyCoinsFlagQuery(userId as string);
  const [
    changeUserParkyCoinsWalletFlag,
    { data: changeUserParkyCoinsWalletFlagData },
  ] = useChangeUserParkyCoinsWalletFlagMutation();
  const [userParkyWallet, setUserParkyWallet] = useState<UserParkyWalletOutput>(
    data as UserParkyWalletOutput
  );
  const [userParkyCoinsFlag, setUserParkyCoinsFlag] = useState(false);
  useEffect(() => {
    if (data !== undefined && userFlag !== undefined) {
      console.log("data", userFlag);
      setUserParkyWallet(data);
      setUserParkyCoinsFlag(userFlag);
    }
  }, [data]);

  const transformTimestamp = (inputDate: string) => {
    const date = new Date(inputDate);
    const options: Intl.DateTimeFormatOptions = {
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
    };
    return date.toLocaleDateString(undefined, options);
  };

  const handleOnChangeUserParkyCoinsWalletFlag = () => {
    changeUserParkyCoinsWalletFlag({
      customerID: userId as string,
      parkyFlag: !userParkyCoinsFlag,
    });
    refetchFlag();
    setUserParkyCoinsFlag(!userParkyCoinsFlag);
  };
  return (
    <>
      <Container style={{ paddingTop: "20%" }}>
        <Container
          style={{
            display: "flex",
            flexDirection: "column",
            justifyContent: "space-around",
            height: "30vh",
            backgroundColor: "#005c66",
            borderRadius: "1rem",
            boxShadow:
              "rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset",
          }}
        >
          <Container style={{ display: "flex", alignItems: "center" }}>
            <Button
              style={{
                backgroundColor: "white",
                color: "#005c66",
                borderColor: "#005c66",
                width: "50%",
              }}
            >
              {" "}
              Use Parky Coins{" "}
            </Button>
            <Form.Check
              type="switch"
              style={{ margin: "0 0 0 10px" }}
              checked={userParkyCoinsFlag}
              onChange={handleOnChangeUserParkyCoinsWalletFlag}
            ></Form.Check>
          </Container>
          <Container style={{ color: "white" }}>
            <h6>Current balance</h6>
            <h2>
              {userParkyWallet &&
                `${userParkyWallet.parkies} parky coins available`}
            </h2>
          </Container>
        </Container>
      </Container>
      <Container>
        <h1 style={{ color: "#005c66", margin: "10px 0 10px 10px " }}>
          Movements
        </h1>
        {userParkyWallet && (
          <Container
            className="scroll-container"
            style={{
              height: "40vh",
              overflow: "auto",
            }}
          >
            {userParkyWallet.parkyEvents.map((movement) => {
              const signal = movement.amount > 0 ? "+" : "";
              return (
                <Container
                  style={{
                    display: "flex",
                    flexDirection: "row",
                    justifyContent: "space-between",
                    overflow: "auto",
                  }}
                >
                  <p
                    style={{ color: signal === "+" ? "green" : " red" }}
                  >{`${signal} ${movement.amount}`}</p>
                  <p>{transformTimestamp(movement.transactionTime)}</p>
                </Container>
              );
            })}
          </Container>
        )}
      </Container>

      <NavBar />
    </>
  );
};
