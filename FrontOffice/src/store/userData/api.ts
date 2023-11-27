import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import {
  UserDataAddNewVehicleInput,
  UserDataLoginInput,
  UserDataRegisterInput,
} from "./types";
import { getTokenFromLocalStorage } from "../../utils/jwtUtils";
import { RootState } from "../store";

export const userDataApi = createApi({
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8080/users",
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
        url: "",
        method: "POST",
        body: newData,
      }),
    }),
    login: build.mutation<any, UserDataLoginInput>({
      query: (loginData) => ({
        url: "/login",
        method: "POST",
        body: loginData,
      }),
    }),
    addNewVehicle: build.mutation<
      any,
      { newVehicle: UserDataAddNewVehicleInput; userId: string }
    >({
      query: ({ newVehicle, userId }) => ({
        url: `/${userId}/vehicle`,
        method: "PUT",
        body: newVehicle,
      }),
    }),
    getUserVehicles: build.query<any, string>({
      query: (userId) => ({ url: `/${userId}/vehicles` }),
    }),
  }),
});

export const {
  usePostUserDataMutation,
  useLoginMutation,
  useAddNewVehicleMutation,
  useGetUserVehiclesQuery,
} = userDataApi;
