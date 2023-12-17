import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import {
  UserDataAddNewVehicleInput,
  UserDataLoginInput,
  UserDataRegisterInput,
  UserNearbyParksType,
  UserParkingHistoryOutput,
  UserPaymentMethodInput,
  ParkOutput,
  SpotOutput,
  ThresholdsOutput,
  SpotOperationalInput,
  PriceTableEntryOutput,
} from "./types";
import { getTokenFromLocalStorage } from "../../utils/jwtUtils";
import { RootState } from "../store";

export const userDataApi = createApi({
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8080",
    prepareHeaders: (headers, { getState }) => {
      const { userId, userRole } = (getState() as RootState).userData;

      if (userId) {
        headers.set("Authorization", `Bearer ${getTokenFromLocalStorage()}`);
        headers.set("X-User-Id", userId);
        headers.set("X-User-Role", userRole);
      }

      return headers;
    },
  }),
  endpoints: (build) => ({
    postUserData: build.mutation<any, UserDataRegisterInput>({
      query: (newData) => ({
        url: "/users",
        method: "POST",
        body: newData,
      }),
    }),
    login: build.mutation<any, UserDataLoginInput>({
      query: (loginData) => ({
        url: "/users/login",
        method: "POST",
        body: loginData,
      }),
    }),
    changePayment: build.mutation<
      any,
      { paymentData: UserPaymentMethodInput; userId: string }
    >({
      query: ({ paymentData, userId }) => ({
        url: `/users/${userId}/payment-method`,
        method: "PUT",
        body: paymentData,
      }),
    }),
    changeOperational: build.mutation<
      any,
      { operationalData: SpotOperationalInput }
    >({
      query: ({ operationalData }) => ({
        url: `/parks/enableDisableSpot`,
        method: "PUT",
        body: operationalData,
      }),
    }),
    changeThresholds: build.mutation<
      any,
      { thresholdsData: ThresholdsOutput; parkNumber: string }
    >({
      query: ({ thresholdsData, parkNumber }) => ({
        url: `/parks/changeParkyThresholds/${parkNumber}`,
        method: "PUT",
        body: thresholdsData,
      }),
    }),
    addNewVehicle: build.mutation<
      any,
      { newVehicle: UserDataAddNewVehicleInput; userId: string }
    >({
      query: ({ newVehicle, userId }) => ({
        url: `/users/${userId}/vehicle`,
        method: "PUT",
        body: newVehicle,
      }),
    }),
    getUserVehicles: build.query<UserDataAddNewVehicleInput[], string>({
      query: (userId) => ({ url: `/users/${userId}/vehicles` }),
    }),
    getUserPaymentMethod: build.query<any, string>({
      query: (userId) => ({ url: `/users/${userId}/paymentMethod` }),
    }),
    getSpotsByParkNumber: build.query<SpotOutput[], string>({
      query: (parkNumber) => ({ url: `/parks/getSpotsByParkNumber/${parkNumber}` }),
    }),
    getPriceTableEntriesByParkNumber: build.query<PriceTableEntryOutput[], string>({
      query: (parkNumber) => ({ url: `/parks/getAllPriceTableEntries/${parkNumber}` }),
    }),
    getThresholdsByParkNumber: build.query<ThresholdsOutput, string>({
      query: (parkNumber) => ({ url: `/parks/getParkyThresholds/${parkNumber}` }),
    }),
    getAllParks: build.query<ParkOutput[], void>({
      query: () => ({ url: `/parks/getAllParks` }),
    }),
    getParksNearby: build.query<UserNearbyParksType[], void>({
      query: () => ({
        url: "/parks/real-time/occupancy/41.178496516445534/-8.607637458224238/1500",
      }),
    }),
    getUserParkingHistory: build.query<UserParkingHistoryOutput[], string>({
      query: (userId) => ({ url: `/parks/parkingHistory/${userId}` }),
    }),
  }),
});

export const {
  usePostUserDataMutation,
  useLoginMutation,
  useAddNewVehicleMutation,
  useChangePaymentMutation,
  useChangeOperationalMutation,
  useChangeThresholdsMutation,
  useGetUserVehiclesQuery,
  useGetUserPaymentMethodQuery,
  useGetParksNearbyQuery,
  useGetUserParkingHistoryQuery,
  useGetAllParksQuery,
  useGetSpotsByParkNumberQuery,
  useGetThresholdsByParkNumberQuery,
  useGetPriceTableEntriesByParkNumberQuery
} = userDataApi;
