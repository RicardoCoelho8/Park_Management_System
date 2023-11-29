import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import {
  UserDataAddNewVehicleInput,
  UserDataLoginInput,
  UserDataRegisterInput,
  UserNearbyParksType,
  UserParkingHistoryOutput,
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
    getUserVehicles: build.query<any, string>({
      query: (userId) => ({ url: `/users/${userId}/vehicles` }),
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
  useGetUserVehiclesQuery,
  useGetParksNearbyQuery,
  useGetUserParkingHistoryQuery,
} = userDataApi;
