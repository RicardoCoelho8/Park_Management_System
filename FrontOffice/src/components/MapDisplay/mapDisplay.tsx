import React, { useEffect } from "react";
import { MapContainer, TileLayer, Marker, Popup, useMap } from "react-leaflet";
import icon from "leaflet/dist/images/marker-icon.png";
import iconShadow from "leaflet/dist/images/marker-shadow.png";
import L, { LatLngExpression, map } from "leaflet";
import { parkIcon as parkIconSvg } from "../../images";
import { ChangeMapCenter } from "./changeMapCenter";

let defaultIcon = L.icon({
  iconUrl: icon,
  shadowUrl: iconShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});

let parkIcon = L.icon({
  iconUrl: parkIconSvg,
  shadowUrl: iconShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41],
});

export interface MapMarker {
  latitude: number;
  longitude: number;
  popupText: string;
}

interface MapDisplayProps {
  markers: MapMarker[];
  center: LatLngExpression;
  userMarker: MapMarker;
}

L.Marker.prototype.options.icon = defaultIcon;

export const MapDisplay: React.FC<MapDisplayProps> = (props) => {
  const { markers, center, userMarker } = props;
  const zoom = 16;

  const [mapCenter, setMapCenter] = React.useState<LatLngExpression>(center);

  useEffect(() => {
    if (markers.length !== 0) {
      setTimeout(() => {
        //Refocus from user to nearest park
        setMapCenter([markers[0].latitude, markers[0].longitude]);
      }, 3000);
    }
  }, [markers]);

  return (
    <MapContainer
      center={mapCenter}
      zoom={zoom}
      style={{ height: "90vh", width: "100%" }}
    >
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
      <ChangeMapCenter center={mapCenter} zoom={zoom} />
      <Marker
        position={[userMarker.latitude, userMarker.longitude]}
        icon={defaultIcon}
      >
        <Popup>{userMarker.popupText}</Popup>
      </Marker>
      {markers.map((marker, idx) => (
        <Marker
          key={idx}
          position={[marker.latitude, marker.longitude]}
          icon={parkIcon}
        >
          <Popup>{marker.popupText}</Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};
