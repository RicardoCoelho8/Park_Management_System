import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { UserDataInput } from "./types";
import { AxiosError } from "axios";
import { POST_USER_DATA } from "./types";

const API_URL = "http://localhost:8080/users";

export const postUserData = createAsyncThunk(
  POST_USER_DATA,
  async (newData: UserDataInput, { rejectWithValue }) => {
    try {
      const response = await axios.post(API_URL, newData);
      return response.data;
    } catch (error) {
      return rejectWithValue("An error occurred");
    }
  }
);
