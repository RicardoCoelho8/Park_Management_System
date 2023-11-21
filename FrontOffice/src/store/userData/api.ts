import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { UserDataLoginInput, UserDataRegisterInput } from "./types";

export const userDataApi = createApi({
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8080/users" }),
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
  }),
});

export const { usePostUserDataMutation, useLoginMutation } = userDataApi;
