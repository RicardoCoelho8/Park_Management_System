import React from "react";
import { Container } from "react-bootstrap";
import "../../utils/styles.css";

export interface HistoryCarouselDetails {
  parkId: number;
  date: string;
  durationHours: number;
  durationMinutes: number;
  price: number;
}

interface HistoryCarouselProps {
  historyDetails: HistoryCarouselDetails[];
}

export const HistoryCarousel: React.FC<HistoryCarouselProps> = (props) => {
  const { historyDetails } = props;
  return (
    <Container
      className="scroll-container"
      style={{
        borderRadius: "1rem",
        background:
          "linear-gradient(180deg, rgba(0, 212, 255, 0.8) 0%, rgba(20, 100, 120, 0.6) 40%, rgba(12, 57, 80, 0.4) 80%, rgba(12, 57, 80, 0.2) 100%)",
        boxShadow:
          "rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset",
        color: "white",
        margin: "1.25rem 0 0 0",
        height: "28vh",
        overflow: "auto",
        position: "relative",
      }}
    >
      <h1
        style={{
          padding: "0.625rem 0 0 0.625rem",
        }}
      >
        History
      </h1>
      {historyDetails.map((historyDetail) => {
        const isLastItem =
          historyDetails.indexOf(historyDetail) === historyDetails.length - 1;
        return (
          <>
            <Container
              style={{
                display: "flex",
                justifyContent: "space-between",
                padding: "0 0.625rem",
              }}
            >
              <p>{`Park ${historyDetail.parkId}`}</p>
              <p>{`${historyDetail.date} ${historyDetail.durationHours} hour(s) and ${historyDetail.durationMinutes} min`}</p>
              <p>{`${historyDetail.price}€`}</p>
            </Container>
            {!isLastItem && (
              <Container
                style={{
                  background: "white",
                  height: "0.063rem",
                  width: "90%",
                  marginBottom: "0.313rem",
                }}
              />
            )}
          </>
        );
      })}
    </Container>
  );
};
