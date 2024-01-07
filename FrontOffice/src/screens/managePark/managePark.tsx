import React, { useState, useEffect } from "react";
import { Container, Form, Table, Button } from "react-bootstrap";
import { useUserName } from "../../store/userData/useUserName";
import {
    useGetAllParksQuery,
    useGetSpotsByParkNumberQuery,
    useGetThresholdsByParkNumberQuery,
    useChangeOperationalMutation,
    useChangeThresholdsMutation,
    useGetPriceTableEntriesByParkNumberQuery,
    useChangePriceTableMutation,
    useGetOvernightConfigByParkNumberQuery,
    useChangeOvernightEnableMutation,
    useChangeOvernightFeeMutation,    
} from "../../store/userData/api";
import {
  SpotsDetailsInput,
  ThresholdCost,
  PriceTableEntry,
} from "../../store/userData/types";
import { ModalErrorForm } from "../../components";
import { NavBar } from "../../components/BottomNavBar/navBar";

export const ManageParkScreen: React.FC = () => {
        const userName = useUserName();
        const {data} = useGetAllParksQuery();
        const [parkNumber, setParkNumber] = useState("");
        var {data: spots, refetch: refetchSpots} = useGetSpotsByParkNumberQuery(parkNumber as string);
        var {data: thresholds, refetch: refecthThresholds} = useGetThresholdsByParkNumberQuery(parkNumber as string);
        var {data: overnightConfigs, refetch: refetchOvernightConfigs} = useGetOvernightConfigByParkNumberQuery(parkNumber as string);
        const [thresholdsHour, setThresholdsHour] = useState<number>(-1);
        const [thresholdsMin, setThresholdsMin] = useState<number>(-1);
        const [overnightEnabled, setOvernightEnabled] = useState<boolean>(false);
        const [overnightFee, setOvernightFee] = useState<number>(-1);
        const [spotsCards, setSpotsCards] = useState<SpotsDetailsInput[]>([]);
        const [showModalError, setShowModalError] = useState(false);
        const [changeOperational, {error: errorOperational, data: newOperational}] =
            useChangeOperationalMutation();
        const [changeOvernightEnable, {error: errorOvernightEnable, data: newOvernightEnable}] =
            useChangeOvernightEnableMutation();
        const [changeOvernightFee, {error: errorOvernightFee, data: newOvernightFee}] =
            useChangeOvernightFeeMutation();
        const [changeThresholds, {error: errorThresholds, data: newThresholds}] =
            useChangeThresholdsMutation();

  var { data: priceTable, refetch: refecthTableEntries } =
    useGetPriceTableEntriesByParkNumberQuery(parkNumber as string);
  const [priceTableEntries, setPriceTableEntries] = useState<PriceTableEntry[]>(
    []
  );
  const [changePriceTable, { data: entries }] = useChangePriceTableMutation();

  const handleParkSelect = (event: any) => {
    var selectedParkNumber = event.target.value.split(" ")[1];
    setParkNumber(selectedParkNumber);
  };

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

            if (overnightConfigs) {
                console.log("overnightConfigs ", overnightConfigs);
                setOvernightEnabled(overnightConfigs.enabled);
                setOvernightFee(overnightConfigs.overnightFee);
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
                refetchOvernightConfigs();
            } else if (newOperational) {
                refetchSpots();
            } else if (newThresholds) {
                refecthThresholds();
                refecthTableEntries();
            } else if(newOvernightEnable || newOvernightFee){
                refetchOvernightConfigs();
            }else if (errorOperational || errorThresholds || errorOvernightEnable || errorOvernightFee) {
                setShowModalError(true);
            }

        }, [data, spots, newOperational, newThresholds, errorOperational, errorThresholds, priceTable, overnightConfigs, newOvernightEnable, newOvernightFee, errorOvernightEnable, errorOvernightFee]);

        var handleEnableDisableOvernightConfig = (state: boolean) => {
            try {
                console.log(state);
                changeOvernightEnable({
                    overnightEnable: {
                        parkNumber: parkNumber,
                        status: state
                    },
                });
            } catch (error) {
                console.error("Error enabling/disabling overnight fee:", error);
            }
        }

        var handleChangeOvernightFee = (newprice: number) => {
            try {
                console.log(newprice);
                changeOvernightFee({
                    overnightPrice: {
                        parkNumber: parkNumber,
                        price: newprice
                    },
                });
            } catch (error) {
                console.error("Error changing overnight fee price:", error);
            }
        }

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

        const convertToNumeric = (timeString: string) => {
            const [hours, minutes] = timeString.split(':').map(Number);
            return hours * 60 + minutes;
        };

        const isConflict = (start1: string, end1: string, start2: string, end2: string) => {
            // Convert time strings to numerical representations
            const start1Numeric: number = convertToNumeric(start1);
            const end1Numeric: number = convertToNumeric(end1);
            const start2Numeric: number = convertToNumeric(start2);
            const end2Numeric: number = convertToNumeric(end2);

            //22 05 04 23

            if (start1Numeric >= 720 && end1Numeric <= 720) {
                if (start2Numeric >= 720 && end2Numeric <= 720) {
                    if (start2Numeric >= end1Numeric && start2Numeric <= start1Numeric) {
                        return false;
                    }
                    if (end2Numeric >= end1Numeric && end2Numeric <= start1Numeric) {
                        return false;
                    }
                    return true
                } else{
                    if (start2Numeric < start1Numeric && start2Numeric < end1Numeric) {
                        return true;
                    }
                    if (end2Numeric > start1Numeric && end2Numeric < end1Numeric+ (24*60)) {
                        return true;
                    }
                }
            } else {

                    if (start2Numeric > start1Numeric && start2Numeric < end1Numeric) {
                        return true;
                    }
                    if (end2Numeric > start1Numeric && end2Numeric < end1Numeric) {
                        return true;
                    }
            }
        }


        const handleChangeTimePeriods = () => {
            try {
                console.log("new operational state for: " + priceTableEntries);

                let periods = priceTableEntries.map((entry) => {
                        return {
                            periodStart: entry.periodStart,
                            periodEnd: entry.periodEnd
                        }
                    }
                );

                for (let i = 0; i < periods.length; i++) {
                    for (let j = i + 1; j < periods.length; j++) {
                        const period1 = periods[i];
                        const period2 = periods[j];

                        const start1 = period1.periodStart;
                        const end1 = period1.periodEnd;
                        const start2 = period2.periodStart;
                        const end2 = period2.periodEnd;

                        // Check for conflicts between the two periods
                        if (isConflict(start1, end1, start2, end2)) {
                            alert("Time periods cannot overlap!");
                            return true; // Conflicts found
                        }
                    }
                }

                changePriceTable({
                    priceTableData: priceTableEntries,
                    parkNumber: parkNumber
                });

            } catch
                (error) {
                console.error("Error changing parky thresholds:", error);
            }
        };

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
                        style={{fontSize: "6.25rem", color: "#005c66"}}
                    />
                    <Container
                        style={{height: "0.063rem", background: "#005c66", width: "100%"}}
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
                        <select className="form-select form-select-lg mb-3" aria-label="Large select example"
                                onChange={handleParkSelect}>
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
                        <br/>
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
                                                    onChange={(e) => {
                                                        entry.periodStart = e.target.value
                                                    }}
                                                />
                                            </td>
                                            <td>
                                                <input
                                                    id={`period_end_${index}`}
                                                    type="string"
                                                    className="form-control"
                                                    defaultValue={entry.periodEnd}
                                                    onBlur={() => compareDatesStartEnd(index)}
                                                    onChange={(e) => {
                                                        entry.periodEnd = e.target.value
                                                    }}

                                                />
                                            </td>
                                            <td>
                                                <Button type="button"
                                                        onClick={() => handleDeleteRowPriceTable(index)}>Delete</Button>
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
                                                                    onChange={(e) => {
                                                                        e.target.value < 0 ? e.target.value = 0 : e.target.value = Number.parseInt(e.target.value);
                                                                        threshold.thresholdMinutes = e.target.value
                                                                    }}
                                                                />
                                                            </td>
                                                            <td>
                                                                <input
                                                                    type="number"
                                                                    className="form-control"
                                                                    defaultValue={threshold.costPerMinuteAutomobiles}
                                                                    onChange={(e) => {
                                                                        e.target.value < 0 ? e.target.value = 0 : e.target.value = Number.parseFloat(e.target.value);
                                                                        threshold.costPerMinuteAutomobiles = e.target.value
                                                                    }}

                                                                />
                                                            </td>
                                                            <td>
                                                                <input
                                                                    type="number"
                                                                    className="form-control"
                                                                    defaultValue={threshold.costPerMinuteMotorcycles}
                                                                    onChange={(e) => {
                                                                        e.target.value < 0 ? e.target.value = 0 : e.target.value = Number.parseFloat(e.target.value)
                                                                        threshold.costPerMinuteMotorcycles = e.target.value
                                                                    }}

                                                                />
                                                            </td>
                                                            <td>
                                                                <Button type="button"
                                                                        onClick={() => handleDeleteRowPriceTableThresholdCost(index, i)}>Delete</Button>
                                                            </td>
                                                        </tr>
                                                    ))}
                                                    <tr>
                                                        <td colSpan={4}>
                                                            <Button type="button"
                                                                    onClick={() => addRowPriceTableThresholdCost(index)}>New
                                                                Threshold Cost</Button>
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
                        <Button onClick={() => handleChangeTimePeriods()} style={{width: "100%"}}>
                            Change Time Peridos
                        </Button>
                        <br/>
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
                        <Button
                            onClick={() => handleChangeThresholds(Number(`${thresholdsHour}`), Number(`${thresholdsMin}`))}
                            style={{width: "100%"}}>
                            Change Thresholds
                        </Button>
                        <br/>
                        <h3>Overnight fee config</h3>
                        <div className="input-group mb-3">
                            <Form.Check
                                type="switch"
                                id={`enable-disable-switch-overnightfee`}
                                label=""
                                onChange={(e) =>
                                    handleEnableDisableOvernightConfig(
                                        e.target.checked
                                    )
                                }
                                checked={overnightEnabled}
                            />
                            <input
                                id={`input-overnightprice`}
                                type="number"
                                className="form-control"
                                placeholder={overnightFee == -1 ? "Overnight Price" : `${overnightFee}`}
                                onChange={(e) => setOvernightFee(e.target.value === '' ? -1 : Number(e.target.value))}
                                hidden={!overnightEnabled}
                            />
                        </div>
                        <Button
                            onClick={() => handleChangeOvernightFee(overnightFee)}
                            style={{width: "100%"}}>
                            Change Overnight fee
                        </Button>
                        <br/>
                        <br/>
                        <br/>
                        <br/>
                        <br/>
                        <br/>
                    </form>
                </Container>
                <ModalErrorForm
                    showModal={showModalError}
                    setShowModal={setShowModalError}
                />
                <BottomNavBar/>
            </>
        );
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
            costPerMinuteMotorcycles: cost.costPerMinuteMotorcycles,
          });
        });

        entries.push({
          parkId: entry.parkId,
          periodStart: entry.periodStart,
          periodEnd: entry.periodEnd,
          thresholds: thresholdsCosts,
        });
      });

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
  }, [
    data,
    spots,
    newOperational,
    newThresholds,
    errorOperational,
    errorThresholds,
  ]);

  var handleEnableDisable = (spotNumber: string, state: boolean) => {
    try {
      console.log("new operational state for: ", spotNumber);
      console.log(state);
      changeOperational({
        operationalData: {
          parkNumber: parkNumber,
          spotNumber: spotNumber,
          operational: state,
          managerName: userName as string,
        },
      });
    } catch (error) {
      console.error("Error changing operational state:", error);
    }
  };

  var handleChangeThresholds = (inputHour: number, inputMinute: number) => {
    try {
      console.log(
        "new operational state for: " +
          inputHour +
          " perHour and " +
          inputMinute +
          " perMin"
      );
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
  };

  const convertToNumeric = (timeString: string) => {
    const [hours, minutes] = timeString.split(":").map(Number);
    return hours * 60 + minutes;
  };

  const isConflict = (
    start1: string,
    end1: string,
    start2: string,
    end2: string
  ) => {
    // Convert time strings to numerical representations
    const start1Numeric = convertToNumeric(start1);
    const end1Numeric = convertToNumeric(end1);
    const start2Numeric = convertToNumeric(start2);
    const end2Numeric = convertToNumeric(end2);

    // Check for conflicts

    if (start1Numeric <= end1Numeric && start2Numeric <= end2Numeric) {
      // Both periods are in the same day
      return end1Numeric > start2Numeric && start1Numeric < end2Numeric;
    } else if (start1Numeric > end1Numeric && start2Numeric > end2Numeric) {
      // Both periods are in the same day (after midnight)
      return end1Numeric > start2Numeric || start1Numeric < end2Numeric;
    } else {
      // One period spans across midnight
      return true;
    }
  };

  const handleChangeTimePeriods = () => {
    try {
      console.log("new operational state for: " + priceTableEntries);

      let periods = priceTableEntries.map((entry) => {
        return {
          periodStart: entry.periodStart,
          periodEnd: entry.periodEnd,
        };
      });

      for (let i = 0; i < periods.length; i++) {
        for (let j = i + 1; j < periods.length; j++) {
          const period1 = periods[i];
          const period2 = periods[j];

          const start1 = period1.periodStart;
          const end1 = period1.periodEnd;
          const start2 = period2.periodStart;
          const end2 = period2.periodEnd;

          // Check for conflicts between the two periods
          if (isConflict(start1, end1, start2, end2)) {
            alert("Time periods cannot overlap!");
            return true; // Conflicts found
          }
        }
      }

      changePriceTable({
        priceTableData: priceTableEntries,
        parkNumber: parkNumber,
      });
    } catch (error) {
      console.error("Error changing parky thresholds:", error);
    }
  };

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

  var handleDeleteRowPriceTableThresholdCost = (
    indexPriceTable: number,
    indexCost: number
  ) => {
    try {
      console.log(
        "Removing entryPriceTable, cost: " + indexPriceTable + ", " + indexCost
      );

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
        thresholds: [],
      });

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
        costPerMinuteMotorcycles: "0",
      });

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
          <select
            className="form-select form-select-lg mb-3"
            aria-label="Large select example"
            onChange={handleParkSelect}
          >
            <option value="" selected disabled>
              Select a park to manage
            </option>
            {data
              ?.map(Number)
              .sort((a, b) => a - b)
              .map((park) => (
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
                          handleEnableDisable(spot.spotNumber, e.target.checked)
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
                          onChange={(e) => {
                            entry.periodStart = e.target.value;
                          }}
                        />
                      </td>
                      <td>
                        <input
                          id={`period_end_${index}`}
                          type="string"
                          className="form-control"
                          defaultValue={entry.periodEnd}
                          onBlur={() => compareDatesStartEnd(index)}
                          onChange={(e) => {
                            entry.periodEnd = e.target.value;
                          }}
                        />
                      </td>
                      <td>
                        <Button
                          type="button"
                          onClick={() => handleDeleteRowPriceTable(index)}
                        >
                          Delete
                        </Button>
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
                            {entry.thresholds.map(
                              (threshold: ThresholdCost, i) => (
                                <tr key={i}>
                                  <td>
                                    <input
                                      type="number"
                                      className="form-control"
                                      defaultValue={threshold.thresholdMinutes}
                                      onChange={(e) => {
                                        e.target.value < 0
                                          ? (e.target.value = 0)
                                          : (e.target.value = Number.parseInt(
                                              e.target.value
                                            ));
                                        threshold.thresholdMinutes =
                                          e.target.value;
                                      }}
                                    />
                                  </td>
                                  <td>
                                    <input
                                      type="number"
                                      className="form-control"
                                      defaultValue={
                                        threshold.costPerMinuteAutomobiles
                                      }
                                      onChange={(e) => {
                                        e.target.value < 0
                                          ? (e.target.value = 0)
                                          : (e.target.value = Number.parseFloat(
                                              e.target.value
                                            ));
                                        threshold.costPerMinuteAutomobiles =
                                          e.target.value;
                                      }}
                                    />
                                  </td>
                                  <td>
                                    <input
                                      type="number"
                                      className="form-control"
                                      defaultValue={
                                        threshold.costPerMinuteMotorcycles
                                      }
                                      onChange={(e) => {
                                        e.target.value < 0
                                          ? (e.target.value = 0)
                                          : (e.target.value = Number.parseFloat(
                                              e.target.value
                                            ));
                                        threshold.costPerMinuteMotorcycles =
                                          e.target.value;
                                      }}
                                    />
                                  </td>
                                  <td>
                                    <Button
                                      type="button"
                                      onClick={() =>
                                        handleDeleteRowPriceTableThresholdCost(
                                          index,
                                          i
                                        )
                                      }
                                    >
                                      Delete
                                    </Button>
                                  </td>
                                </tr>
                              )
                            )}
                            <tr>
                              <td colSpan={4}>
                                <Button
                                  type="button"
                                  onClick={() =>
                                    addRowPriceTableThresholdCost(index)
                                  }
                                >
                                  New Threshold Cost
                                </Button>
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
                    <Button type="button" onClick={() => addRowPriceTable()}>
                      New Time Period
                    </Button>
                  </td>
                </tr>
              </tbody>
            </Table>
          </div>
          <Button
            onClick={() => handleChangeTimePeriods()}
            style={{ width: "100%" }}
          >
            Change Time Peridos
          </Button>
          <br />
          <h3>Thresholds</h3>
          <div className="input-group mb-3">
            <span className="input-group-text">
              <i className="bi bi-coin"></i>/H
            </span>
            <input
              id={`input-${thresholdsHour}`}
              type="number"
              className="form-control"
              placeholder={
                thresholdsHour == -1 ? "Per hour" : `${thresholdsHour}`
              }
              onChange={(e) =>
                setThresholdsHour(
                  e.target.value === "" ? -1 : Number(e.target.value)
                )
              }
            />
            <span className="input-group-text">
              <i className="bi bi-coin"></i>/M
            </span>
            <input
              id={`input-${thresholdsMin}`}
              type="number"
              className="form-control"
              placeholder={
                thresholdsMin == -1 ? "Per minute" : `${thresholdsMin}`
              }
              onChange={(e) =>
                setThresholdsMin(
                  e.target.value === "" ? -1 : Number(e.target.value)
                )
              }
            />
          </div>
          <Button
            onClick={() =>
              handleChangeThresholds(
                Number(`${thresholdsHour}`),
                Number(`${thresholdsMin}`)
              )
            }
            style={{ width: "100%" }}
          >
            Change Thresholds
          </Button>
        </form>
      </Container>
      <ModalErrorForm
        showModal={showModalError}
        setShowModal={setShowModalError}
      />
      <NavBar />
    </>
  );
};
