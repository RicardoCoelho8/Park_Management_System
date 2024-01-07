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
  PriceTableEntry,
  OvernightConfig,
  OvernightEnableInput,
  OvernightFeeInput,
  GetAllUsersOutputItem,
  AssignParkyCoinsInput,
  UserParkyWalletOutput,
  UserParkyCoinsWalletFlag,
  ParkReportOutput,
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
    changeOvernightEnable: build.mutation<
      any,
      { overnightEnable: OvernightEnableInput }
    >({
      query: ({ overnightEnable }) => ({
        url: `/parks/enableDisableOvernightFee`,
        method: "PUT",
        body: overnightEnable,
      }),
    }),
    changeOvernightFee: build.mutation<
      any,
      { overnightPrice: OvernightFeeInput }
    >({
      query: ({ overnightPrice }) => ({
        url: `/parks/changeOvernightFeePrice`,
        method: "PUT",
        body: overnightPrice,
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
    changePriceTable: build.mutation<
      any,
      { priceTableData: PriceTableEntry[]; parkNumber: string }
    >({
      query: ({ priceTableData, parkNumber }) => ({
        url: `/parks/defineTimePeriods/${parkNumber}`,
        method: "PUT",
        body: priceTableData,
      }),
    }),
    assignParkyCoins: build.mutation<any, AssignParkyCoinsInput>({
      query: (assignParkyCoinsInputBody) => ({
        url: `/users/parkies`,
        method: "POST",
        body: assignParkyCoinsInputBody,
      }),
    }),
    changeUserParkyCoinsWalletFlag: build.mutation<
      any,
      UserParkyCoinsWalletFlag
    >({
      query: (assignParkyCoinsInputBody) => ({
        url: `/parks/changeUserParkyFlag`,
        method: "PUT",
        body: assignParkyCoinsInputBody,
      }),
    }),
    getUserVehicles: build.query<UserDataAddNewVehicleInput[], string>({
      query: (userId) => ({ url: `/users/${userId}/vehicles` }),
    }),
    getUserPaymentMethod: build.query<any, string>({
      query: (userId) => ({ url: `/users/${userId}/paymentMethod` }),
    }),
    getSpotsByParkNumber: build.query<SpotOutput[], string>({
      query: (parkNumber) => ({
        url: `/parks/getSpotsByParkNumber/${parkNumber}`,
      }),
    }),
    getOvernightConfigByParkNumber: build.query<OvernightConfig, string>({
      query: (parkNumber) => ({
        url: `/parks/getOvernightConfig/${parkNumber}`,
      }),
    }),
    getPriceTableEntriesByParkNumber: build.query<
      PriceTableEntryOutput[],
      string
    >({
      query: (parkNumber) => ({
        url: `/parks/getAllPriceTableEntries/${parkNumber}`,
      }),
    }),
    getThresholdsByParkNumber: build.query<ThresholdsOutput, string>({
      query: (parkNumber) => ({
        url: `/parks/getParkyThresholds/${parkNumber}`,
      }),
    }),
    getAllParks: build.query<ParkOutput[], void>({
      query: () => ({ url: `/parks/getAllParks` }),
    }),
    getParksNearby: build.query<
      UserNearbyParksType[],
      { latitude: number; longitude: number }
    >({
      query: ({ latitude, longitude }) => ({
        url: `/parks/real-time/occupancy/${latitude}/${longitude}/1500`,
      }),
    }),
    getUserParkingHistory: build.query<UserParkingHistoryOutput[], string>({
      query: (userId) => ({ url: `/parks/parkingHistory/${userId}` }),
    }),
    getAllUsers: build.query<GetAllUsersOutputItem[], void>({
      query: () => ({ url: `/users` }),
    }),
    getUserNumberOfVisits: build.query<number, string>({
      query: (userId) => ({
        url: `/parks/getQuantityOfHistoryByCustomerID/${userId}`,
      }),
    }),
    getUserParkyWallet: build.query<UserParkyWalletOutput, string>({
      query: (userId) => ({
        url: `/users/parkies/${userId}`,
      }),
    }),
    getUserParkyCoinsFlag: build.query<boolean, string>({
      query: (userId) => ({
        url: `/parks/getUserParkyFlag/${userId}`,
      }),
    }),
    getParkUsageReport: build.query<ParkReportOutput, number>({
      query: (parkId) => ({
        url: `/parkReport/percentageOf/${parkId}`,
      }),
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
  useChangeOvernightEnableMutation,
  useChangeOvernightFeeMutation,
  useAssignParkyCoinsMutation,
  useChangeUserParkyCoinsWalletFlagMutation,
  useGetUserVehiclesQuery,
  useGetUserPaymentMethodQuery,
  useGetParksNearbyQuery,
  useGetUserParkingHistoryQuery,
  useGetAllParksQuery,
  useGetSpotsByParkNumberQuery,
  useGetThresholdsByParkNumberQuery,
  useGetPriceTableEntriesByParkNumberQuery,
  useGetOvernightConfigByParkNumberQuery,
  useChangePriceTableMutation,
  useGetAllUsersQuery,
  useGetUserNumberOfVisitsQuery,
  useGetUserParkyWalletQuery,
  useGetUserParkyCoinsFlagQuery,
  useGetParkUsageReportQuery,
} = userDataApi;
