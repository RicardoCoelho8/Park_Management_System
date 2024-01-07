import React, { useEffect, useState } from "react";
import { Button, Container, InputGroup, Table } from "react-bootstrap";

export interface TableClientUsageItem {
  id: number;
  name: string;
  numberOfVisits: number;
  parkyCoins: number;
  onClickAssignParkyCoins: () => void;
}

interface TableClientUsageProps {
  items: TableClientUsageItem[];
  checkedUsers: number[];
  onClickCheckbox: (id: number) => void;
  onClickBulkAssign: () => void;
}

export const TableClientUsage: React.FC<TableClientUsageProps> = (props) => {
  const { items, checkedUsers, onClickCheckbox, onClickBulkAssign } = props;
  const [ascending, setAscending] = useState(true);
  const [filteredItems, setFilteredItems] =
    useState<TableClientUsageItem[]>(items);

  useEffect(() => {
    setFilteredItems(items);
  }, [items]);

  const handleFilter = () => {
    const filteredItems = items.sort((a, b) => {
      if (ascending) {
        return a.parkyCoins - b.parkyCoins;
      } else {
        return b.parkyCoins - a.parkyCoins;
      }
    });
    setFilteredItems(filteredItems);
  };
  return (
    <>
      <Button
        style={{ margin: "0 0 10px 28.5vh", backgroundColor: "#005c66" }}
        onClick={onClickBulkAssign}
        disabled={checkedUsers.length < 2}
      >
        {" "}
        Bulk Assign
      </Button>
      <Container
        style={{
          marginLeft: "15vh",
          width: "100%",
          padding: "0",
          display: "flex",
          justifyContent: "center",
        }}
      >
        <Table style={{ width: "80%", textAlign: "center" }}>
          <thead>
            <tr>
              <th>Select</th>
              <th>Id</th>
              <th>Name</th>
              <th>Number of Visits</th>
              <th>
                {
                  <Container
                    style={{ padding: "0" }}
                    onClick={() => {
                      setAscending((prevAscending) => !prevAscending);
                      handleFilter();
                    }}
                  >
                    {"Parky Coins"}{" "}
                    {ascending ? (
                      <i
                        className="bi bi-sort-up"
                        style={{ fontSize: "20px" }}
                      />
                    ) : (
                      <i
                        className="bi bi-sort-down"
                        style={{ fontSize: "20px" }}
                      />
                    )}
                  </Container>
                }
              </th>
              <th>Assign Parky Coins</th>
            </tr>
          </thead>
          <tbody>
            {filteredItems.map((item, index) => {
              return (
                <tr>
                  <td>
                    <input
                      type="checkbox"
                      onChange={() => onClickCheckbox(item.id)}
                      checked={checkedUsers.includes(item.id)}
                    />
                  </td>
                  <td>{item.id}</td>
                  <td>{item.name}</td>
                  <td>{item.numberOfVisits}</td>
                  <td>{item.parkyCoins}</td>
                  <td>
                    <Container>
                      <i
                        className="bi bi-plus-circle-fill"
                        style={{ color: "#005c66" }}
                        onClick={item.onClickAssignParkyCoins}
                      ></i>
                    </Container>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
      </Container>
    </>
  );
};
