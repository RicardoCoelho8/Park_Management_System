import { LatLngExpression } from "leaflet";
import React, { useEffect } from "react";
import { useMap } from "react-leaflet";

interface ChangeViewProps {
  center: LatLngExpression;
  zoom: number;
}

export const ChangeMapCenter: React.FC<ChangeViewProps> = (props) => {
  const { center, zoom } = props;
  const map = useMap();
  useEffect(() => {
    map.setView(center, zoom);
  }, [center, zoom, map]);

  return null; // Doesn't render anything
};
