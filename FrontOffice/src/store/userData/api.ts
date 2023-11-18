import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { UserDataInput } from "./types";

export const userDataApi = createApi({
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8080/users" }),
  endpoints: (build) => ({
    postUserData: build.mutation<any, any>({
      query: (newData) => ({
        url: "",
        method: "POST",
        body: newData,
      }),
    }),
  }),
});

export const { usePostUserDataMutation } = userDataApi;
