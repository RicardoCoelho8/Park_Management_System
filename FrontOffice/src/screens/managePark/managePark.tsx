import React, { useState, useEffect } from "react";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { Container, Form, Table } from "react-bootstrap";
import { useUserName } from "../../store/userData/useUserName";
import {
  useGetAllParksQuery,
  useGetSpotsByParkNumberQuery,
  useChangeOperationalMutation,
} from "../../store/userData/api";
import { SpotsDetailsInput } from "../../store/userData/types";
import { ModalErrorForm } from "../../components";

export const ManageParkScreen: React.FC = () => {
  const userName = useUserName();
  const { data } = useGetAllParksQuery();
  const [parkNumber, setParkNumber] = useState("");
  var { data: spots, refetch} = useGetSpotsByParkNumberQuery(parkNumber as string);
  const [spotsCards, setSpotsCards] = useState<SpotsDetailsInput[]>([]);
  const [showModalError, setShowModalError] = useState(false);
  const [changeOperational, { error, data: newOperational }] =
  useChangeOperationalMutation();

  const handleParkSelect = (event: any) => {
    var selectedParkNumber = event.target.value.split(" ")[1];
    setParkNumber(selectedParkNumber);
  }

  useEffect(() => {
    if (data) {
      console.log("data in managePark ", data);
    }

    if(spots){
      console.log("all spots ", spots);
      const cards: SpotsDetailsInput[] = [];
      
      var sortedSpots = [...spots].sort((a, b) =>
        a.spotNumber.localeCompare(b.spotNumber)
      );

      sortedSpots.map((spot) => {
        cards.push({
          spotNumber: spot.spotNumber,
          spotType: spot.spotType,
          spotVehicleType: spot.spotVehicleType,
          floorLevel: spot.floorLevel,
          operational: spot.operational,
        });
      });
      setSpotsCards(cards);
    }

    if(parkNumber || newOperational){
      refetch();
    } else if (error) {
      setShowModalError(true);
    }
  }, [data, spots, newOperational, error]);

  var handleEnableDisable = (spotNumber: string, state: boolean) => {
    try {
      console.log("new operational state for: ", spotNumber);
      console.log(state);
      changeOperational({
        operationalData: {
          parkNumber: parkNumber,
          spotNumber: spotNumber,
          operational: state,
          managerName: userName as string
        }
      });
    } catch (error) {
      console.error("Error changing operational state:", error);
    }
  }

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
          className="bi bi-p-circle"
          style={{ fontSize: "6.25rem", color: "#005c66" }}
        />
        <Container
          style={{ height: "0.063rem", background: "#005c66", width: "100%" }}
        />
      </Container>
      <Container
        style={{
          padding: "0 1.875rem",
          color: "#005c66",
          marginTop: "0.625rem",
        }}
      >
        <form>
        <select className="form-select form-select-lg mb-3" aria-label="Large select example" onChange={handleParkSelect}>
          <option value="" selected disabled>Select a park to manage</option>
          {data?.map((park) => (
            <option value={`Park ${park}`}>Park {String(park)}</option>
          ))}
        </select>

        <div
            style={{
              overflowY: "auto",
              maxHeight: "300px", // Adjust the maximum height as needed
              textAlign: "center",
            }}
          >
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>Spot Number</th>
                  <th>Spot Type</th>
                  <th>Vehicle Type</th>
                  <th>Floor</th>
                  <th>Operational</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {spotsCards.map((spot) => (
                  <tr key={spot.spotNumber}>
                    <td>{spot.spotNumber}</td>
                    <td>{spot.spotType}</td>
                    <td>{spot.spotVehicleType}</td>
                    <td>{spot.floorLevel}</td>
                    <td>{spot.operational ? "Yes" : "No"}</td>
                    <td>
                      <Form.Check
                        type="switch"
                        id={`enable-disable-switch-${spot.spotNumber}`}
                        label=""
                        onChange={(e) =>
                          handleEnableDisable(
                            spot.spotNumber,
                            e.target.checked
                          )
                        }
                        checked={spot.operational}
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>

        </form>
      </Container>
      <ModalErrorForm
        showModal={showModalError}
        setShowModal={setShowModalError}
      />
      <BottomNavBar />
    </>
  );
};
