import React, { useState, useEffect } from "react";
import { BottomNavBar } from "../../components/BottomNavBar/bottomNavBar";
import { Container, Form, Table, Button } from "react-bootstrap";
import { useUserName } from "../../store/userData/useUserName";
import {
  useGetAllParksQuery,
  useGetSpotsByParkNumberQuery,
  useGetThresholdsByParkNumberQuery,
  useChangeOperationalMutation,
  useChangeThresholdsMutation,
} from "../../store/userData/api";
import { SpotsDetailsInput } from "../../store/userData/types";
import { ModalErrorForm } from "../../components";

export const ManageParkScreen: React.FC = () => {
  const userName = useUserName();
  const { data } = useGetAllParksQuery();
  const [parkNumber, setParkNumber] = useState("");
  var { data: spots, refetch: refetchSpots} = useGetSpotsByParkNumberQuery(parkNumber as string);
  var { data: thresholds, refetch: refecthThresholds} = useGetThresholdsByParkNumberQuery(parkNumber as string);
  const [thresholdsHour, setThresholdsHour] = useState<number>(-1);
  const [thresholdsMin, setThresholdsMin] = useState<number>(-1);
  const [spotsCards, setSpotsCards] = useState<SpotsDetailsInput[]>([]);
  const [showModalError, setShowModalError] = useState(false);
  const [changeOperational, { error: errorOperational, data: newOperational }] =
  useChangeOperationalMutation();
  const [changeThresholds, { error: errorThresholds, data: newThresholds }] =
  useChangeThresholdsMutation();

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

    if(thresholds){
      console.log("thresholds ", thresholds);
      setThresholdsHour(thresholds.parkiesPerHour);
      setThresholdsMin(thresholds.parkiesPerMinute);
    }

    if(parkNumber){
      refetchSpots();
      refecthThresholds();
    }else if(newOperational){
      refetchSpots();
    }else if(newThresholds){
      refecthThresholds();
    }else if(errorOperational || errorThresholds){
      setShowModalError(true);
    }

  }, [data, spots, newOperational, newThresholds, errorOperational, errorThresholds]);

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

  var handleChangeThresholds = (inputHour: number, inputMinute: number) => {
    try {
      console.log("new operational state for: " + inputHour + " perHour and " + inputMinute + " perMin");
      changeThresholds({
        thresholdsData: {
          parkiesPerHour: inputHour,
          parkiesPerMinute: inputMinute,
        },
        parkNumber: parkNumber,
      });

      setThresholdsHour(-1);
      setThresholdsMin(-1);
    } catch (error) {
      console.error("Error changing parky thresholds:", error);
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
          {data?.map(Number).sort((a, b) => a - b).map((park) => (
            <option value={`Park ${park}`}>Park {String(park)}</option>
          ))}
        </select>

        <div
            style={{
              overflowY: "auto",
              maxHeight: "300px",
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
          <br />
          <h3>Thresholds</h3>
          <div className="input-group mb-3">
            <span className="input-group-text"><i className="bi bi-coin"></i>/H</span>
            <input 
              id={`input-${thresholdsHour}`} 
              type="number" 
              className="form-control" 
              placeholder={thresholdsHour == -1 ? "Per hour" : `${thresholdsHour}`}
              onChange={(e) => setThresholdsHour(e.target.value === '' ? -1 : Number(e.target.value))}
              />
            <span className="input-group-text"><i className="bi bi-coin"></i>/M</span>
            <input 
              id={`input-${thresholdsMin}`} 
              type="number" 
              className="form-control" 
              placeholder={thresholdsMin == -1 ? "Per minute" : `${thresholdsMin}`}
              onChange={(e) => setThresholdsMin(e.target.value === '' ? -1 : Number(e.target.value))}
              />
          </div>
          <Button onClick={() => handleChangeThresholds(Number(`${thresholdsHour}`), Number(`${thresholdsMin}`))} style={{ width: "100%" }}>
            Change Thresholds
          </Button>
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
