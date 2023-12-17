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
  useGetPriceTableEntriesByParkNumberQuery
} from "../../store/userData/api";
import { SpotsDetailsInput, ThresholdCost, PriceTableEntry } from "../../store/userData/types";
import { ModalErrorForm } from "../../components";

export const ManageParkScreen: React.FC = () => {
  const userName = useUserName();
  const { data } = useGetAllParksQuery();
  const [parkNumber, setParkNumber] = useState("");
  var { data: spots, refetch: refetchSpots } = useGetSpotsByParkNumberQuery(parkNumber as string);
  var { data: thresholds, refetch: refecthThresholds } = useGetThresholdsByParkNumberQuery(parkNumber as string);
  const [thresholdsHour, setThresholdsHour] = useState<number>(-1);
  const [thresholdsMin, setThresholdsMin] = useState<number>(-1);
  const [spotsCards, setSpotsCards] = useState<SpotsDetailsInput[]>([]);
  const [showModalError, setShowModalError] = useState(false);
  const [changeOperational, { error: errorOperational, data: newOperational }] =
    useChangeOperationalMutation();
  const [changeThresholds, { error: errorThresholds, data: newThresholds }] =
    useChangeThresholdsMutation();

  var { data: priceTable, refetch: refecthTableEntries } = useGetPriceTableEntriesByParkNumberQuery(parkNumber as string);
  const [priceTableEntries, setPriceTableEntries] = useState<PriceTableEntry[]>([]);

  const handleParkSelect = (event: any) => {
    var selectedParkNumber = event.target.value.split(" ")[1];
    setParkNumber(selectedParkNumber);
  }

  useEffect(() => {
    if (data) {
      console.log("data in managePark ", data);
    }

    if (spots) {
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

    if (thresholds) {
      console.log("thresholds ", thresholds);
      setThresholdsHour(thresholds.parkiesPerHour);
      setThresholdsMin(thresholds.parkiesPerMinute);
    }

    if (priceTable) {
      console.log("Price Table Entries ", priceTable);

      const entries: PriceTableEntry[] = [];

      var thresholdsCosts: ThresholdCost[] = [];

      priceTable.map((entry) => {
        thresholdsCosts = [];

        entry.thresholds.map((cost) => {
          thresholdsCosts.push({
            thresholdMinutes: cost.thresholdMinutes,
            costPerMinuteAutomobiles: cost.costPerMinuteAutomobiles,
            costPerMinuteMotorcycles: cost.costPerMinuteMotorcycles
          })
        })

        entries.push({
          parkId: entry.parkId,
          periodStart: entry.periodStart,
          periodEnd: entry.periodEnd,
          thresholds: thresholdsCosts
        })
      })

      setPriceTableEntries(entries);
    }

    if (parkNumber) {
      refetchSpots();
      refecthTableEntries();
      refecthThresholds();
    } else if (newOperational) {
      refetchSpots();
    } else if (newThresholds) {
      refecthThresholds();
      refecthTableEntries();
    } else if (errorOperational || errorThresholds) {
      setShowModalError(true);
    }

  }, [data, spots, newOperational, newThresholds, , errorOperational, errorThresholds]);

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

  var handleDeleteRowPriceTable = (index: number) => {
    try {
      console.log("Removing entry: " + index);
      const updatedEntries = [...priceTableEntries];
      updatedEntries.splice(index, 1);
      setPriceTableEntries(updatedEntries);
    } catch (error) {
      console.error("Error deleting Entry:", error);
    }
  };

  var handleDeleteRowPriceTableThresholdCost = (indexPriceTable: number, indexCost: number) => {
    try {
      console.log("Removing entryPriceTable, cost: " + indexPriceTable + ", " + indexCost);

      const updatedEntries = [...priceTableEntries];
      updatedEntries[indexPriceTable].thresholds.splice(indexCost, 1);

      setPriceTableEntries(updatedEntries);
    } catch (error) {
      console.error("Error deleting EntryCost:", error);
    }
  };

  var addRowPriceTable = () => {
    try {
      console.log("Adding new entry");

      var updatedEntries = [...priceTableEntries];

      updatedEntries.push({
        parkId: parkNumber,
        periodStart: "00:00",
        periodEnd: "00:00",
        thresholds: []
      })

      setPriceTableEntries(updatedEntries);
    } catch (error) {
      console.error("Error adding Entry:", error);
    }
  };

  var addRowPriceTableThresholdCost = (indexPriceTable: number) => {
    try {
      console.log("Adding new entryCost");

      const updatedEntries = [...priceTableEntries];
      updatedEntries[indexPriceTable].thresholds.push({
        thresholdMinutes: "0",
        costPerMinuteAutomobiles: "0",
        costPerMinuteMotorcycles: "0"
      })

      setPriceTableEntries(updatedEntries);
    } catch (error) {
      console.error("Error adding EntryCost:", error);
    }
  };

  var compareDatesStartEnd = (index: number) => {
    var start = document.getElementById("period_start_" + index)!.value!;
    var end = document.getElementById("period_end_" + index)!.value!;

    var isValidInput = /^([01]\d|2[0-3]):([0-5]\d)$/.test(start);

    if (!isValidInput) {
      document.getElementById("period_start_" + index)!.value = "";
      document.getElementById("period_end_" + index)!.value = "";
      return;
    }

    var isValidInput = /^([01]\d|2[0-3]):([0-5]\d)$/.test(end);

    if (!isValidInput) {
      document.getElementById("period_end_" + index)!.value = "";
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
          <h3>Time Periods</h3>
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
                  <th>Period Start</th>
                  <th>Period End</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {priceTableEntries.map((entry, index) => (
                  <React.Fragment key={index}>
                    <tr>
                      <td>
                        <input
                          id={`period_start_${index}`}
                          type="string"
                          className="form-control"
                          defaultValue={entry.periodStart}
                          onBlur={() => compareDatesStartEnd(index)}
                        />
                      </td>
                      <td>
                        <input
                          id={`period_end_${index}`}
                          type="string"
                          className="form-control"
                          defaultValue={entry.periodEnd}
                          onBlur={() => compareDatesStartEnd(index)}
                        />
                      </td>
                      <td>
                        <Button type="button" onClick={() => handleDeleteRowPriceTable(index)}>Delete</Button>
                      </td>
                    </tr>
                    <tr>
                      <td colSpan={3}>
                        <Table>
                          <thead>
                            <tr>
                              <th>Threshold Minutes</th>
                              <th>Automobiles</th>
                              <th>Motorcycles</th>
                              <th></th>
                            </tr>
                          </thead>
                          <tbody>
                            {entry.thresholds.map((threshold: ThresholdCost, i) => (
                              <tr key={i}>
                                <td>
                                  <input
                                    type="number"
                                    className="form-control"
                                    defaultValue={threshold.thresholdMinutes}
                                    onChange={(e) => e.target.value < 0 ? e.target.value = 0 : e.target.value = Number.parseInt(e.target.value)}
                                  />
                                </td>
                                <td>
                                  <input
                                    type="number"
                                    className="form-control"
                                    defaultValue={threshold.costPerMinuteAutomobiles}
                                    onChange={(e) => e.target.value < 0 ? e.target.value = 0 : e.target.value = Number.parseFloat(e.target.value)}

                                  />
                                </td>
                                <td>
                                  <input
                                    type="number"
                                    className="form-control"
                                    defaultValue={threshold.costPerMinuteMotorcycles}
                                    onChange={(e) => e.target.value < 0 ? e.target.value = 0 : e.target.value = Number.parseFloat(e.target.value)}

                                  />
                                </td>
                                <td>
                                  <Button type="button" onClick={() => handleDeleteRowPriceTableThresholdCost(index, i)}>Delete</Button>
                                </td>
                              </tr>
                            ))}
                            <tr>
                              <td colSpan={4}>
                                <Button type="button" onClick={() => addRowPriceTableThresholdCost(index)}>New Threshold Cost</Button>
                              </td>
                            </tr>
                          </tbody>
                        </Table>
                      </td>
                    </tr>
                  </React.Fragment>
                ))}
                <tr>
                  <td colSpan={3}>
                    <Button type="button" onClick={() => addRowPriceTable()}>New Time Period</Button>
                  </td>
                </tr>
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
