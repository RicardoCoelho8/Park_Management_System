import React, { useEffect, useState } from "react";
import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import { useGetParkUsageReportQuery } from "../../store/userData/api";
export interface GraphData {
  name: string;
  value: number;
}
interface ReportParkUsageGraphProps {
  data: GraphData[];
}

export const ReportParkUsageGraph: React.FC<ReportParkUsageGraphProps> = (
  props
) => {
  const { data } = props;

  return (
    <LineChart width={500} height={300} data={data}>
      <CartesianGrid strokeDasharray="3 3" />
      <XAxis dataKey="name" />
      <YAxis />
      <Tooltip />
      <Legend />
      <Line type="monotone" dataKey="value" stroke="#8884d8" />
    </LineChart>
  );
};
