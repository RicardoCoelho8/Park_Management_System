import React, { useEffect, useState } from "react";
import { NavBar } from "../../components/BottomNavBar/navBar";
import { useUserName } from "../../store/userData/useUserName";
import { Container } from "react-bootstrap";
import {
  TableClientUsage,
  TableClientUsageItem,
} from "../../components/TablesCustomerManager";
import {
  useAssignParkyCoinsMutation,
  useGetAllUsersQuery,
  useGetUserNumberOfVisitsQuery,
} from "../../store/userData/api";
import { GetAllUsersOutputItem } from "../../store";
import { ModalAssignParkyCoins } from "../../components/ModalAssignParkyCoins/modal";

export const HomeScreenCustomerManager: React.FC = () => {
  const [userId, setUserId] = useState("0");
  const [usersToAssignParkyCoins, setUsersToAssignParkyCoins] = useState<
    number[]
  >([]);
  const [showModalAssignParkyCoins, setShowModalAssignParkyCoins] =
    useState(false);
  const [clientUsageItems, setClientUsageItems] = useState<
    TableClientUsageItem[]
  >([]);
  const userName = useUserName();

  const { data: usersList, refetch: fetchAllUsers } = useGetAllUsersQuery();
  const { data: userNumberOfVisits, refetch: fetchUserNumberOfVisits } =
    useGetUserNumberOfVisitsQuery(userId);
  const [assignParkyCoins, { data: assignParkyCoinsData, isError }] =
    useAssignParkyCoinsMutation();

  useEffect(() => {
    console.log("usersList", usersList);
    if (usersList) {
      const items: TableClientUsageItem[] = [];
      usersList.map((user: GetAllUsersOutputItem, index) => {
        setUserId(user.id.toString());
        setTimeout(() => fetchUserNumberOfVisits(), 10);
        items.push({
          id: user.id,
          name: user.firstName + " " + user.lastName,
          numberOfVisits: userNumberOfVisits as number,
          parkyCoins: user.totalParkies,
          onClickAssignParkyCoins: () => {
            setShowModalAssignParkyCoins(true);
            setUsersToAssignParkyCoins([user.id]);
          },
        });
      });
      console.log("items", items);
      setClientUsageItems(items);
    }
  }, [usersList]);

  const handleOnClickAssignParkyCoins = (amount: number) => {
    assignParkyCoins({
      userIds: usersToAssignParkyCoins,
      amount,
      reason: "",
    });
    setTimeout(() => fetchAllUsers(), 100);
    setShowModalAssignParkyCoins(false);
    setUsersToAssignParkyCoins([]);
  };

  const handleToogleUserCheckBox = (userId: number) => {
    if (usersToAssignParkyCoins.includes(userId)) {
      setUsersToAssignParkyCoins(
        usersToAssignParkyCoins.filter((id) => id !== userId)
      );
    } else {
      const users = [...usersToAssignParkyCoins, userId];
      console.log("users", users);
      setUsersToAssignParkyCoins(users);
      console.log("usersToAssignParkyCoins", usersToAssignParkyCoins);
    }
  };

  return (
    <>
      <Container
        style={{
          width: "100%",
          display: "flex",
          alignItems: "center",
          height: "17vh",
          padding: "0 0 0 1.875rem",
          marginLeft: "15vh",
        }}
      >
        <i
          className="bi bi-person-circle"
          style={{ fontSize: "1.875rem", color: "#005c66" }}
        ></i>
        <Container
          style={{
            display: "flex",
            flexDirection: "column",
            color: "#005c66",
            padding: "1.875rem 0 0 0.625rem ",
          }}
        >
          <h5>Welcome back </h5>
          <h3>{userName}</h3>
        </Container>
      </Container>
      <TableClientUsage
        items={clientUsageItems}
        checkedUsers={usersToAssignParkyCoins}
        onClickCheckbox={handleToogleUserCheckBox}
        onClickBulkAssign={() => setShowModalAssignParkyCoins(true)}
      />
      <NavBar />
      <ModalAssignParkyCoins
        setShowModal={setShowModalAssignParkyCoins}
        showModal={showModalAssignParkyCoins}
        assign={handleOnClickAssignParkyCoins}
        onClickClose={() => setUsersToAssignParkyCoins([])}
      />
    </>
  );
};
