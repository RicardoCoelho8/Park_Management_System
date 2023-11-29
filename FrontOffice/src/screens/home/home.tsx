import React, { useEffect, useState } from "react";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { MenuCard } from "../../components/MenuCard/menuCard";
import { Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import {
  HistoryCarousel,
  HistoryCarouselDetails,
} from "../../components/HistoryCarousel/historyCarrousel";
import { useUserId } from "../../store/userData/useUserId";
import { useGetUserParkingHistoryQuery } from "../../store/userData/api";
import { useUserEmail } from "../../store/userData/useUserEmail";

export const HomeScreen: React.FC = () => {
  const navigate = useNavigate();
  const userId = useUserId();
  const userEmail = useUserEmail();
  const { data } = useGetUserParkingHistoryQuery(userId as string);
  const [historyCards, setHistoryCards] = useState<HistoryCarouselDetails[]>(
    []
  );

  useEffect(() => {
    if (data) {
      const cards: HistoryCarouselDetails[] = [];
      data.map((history) => {
        const inputDate = new Date(history.endTime);
        const month = new Intl.DateTimeFormat("en", { month: "short" }).format(
          inputDate
        );
        const day = new Intl.DateTimeFormat("en", { day: "numeric" }).format(
          inputDate
        );
        cards.push({
          parkId: history.parkId,
          date: `${day} ${month}`,
          durationHours: history.hoursBetweenEntranceExit,
          durationMinutes: history.minutesBetweenEntranceExit,
          price: history.price,
        });
      });
      setHistoryCards(cards);
    }
  }, [data]);
  const menuCards: MenuCard[] = [
    {
      title: "Vechiles",
      subtitle: "View All Your Vechiles Here",
      icon: "bi bi-car-front",
      onClick: () => navigate("/addVechile"),
    },
    {
      title: "Payments",
      subtitle: "View All Your Transactions Here",
      icon: "bi bi-credit-card-2-back",
      onClick: () => navigate("/addVechile"),
    },
    {
      title: "Vechiles",
      subtitle: "View All Your Vechiles Here",
      icon: "bi bi-car-front",
      onClick: () => navigate("/addVechile"),
    },
  ];
  return (
    <>
      <Container
        style={{
          width: "100%",
          display: "flex",
          alignItems: "center",
          height: "17vh",
          padding: "0 0 0 1.875rem",
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
          <h3>{userEmail}</h3>
        </Container>
      </Container>
      <Container style={{ padding: "0 0.625rem" }}>
        <MenuCard cards={menuCards} />
        <HistoryCarousel historyDetails={historyCards} />
      </Container>

      <BottomNavBar />
    </>
  );
};
