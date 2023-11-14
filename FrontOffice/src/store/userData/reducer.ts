import { PayloadAction, createSlice } from "@reduxjs/toolkit";

export interface UserDataState {
  email: string;
  password: string;
}

export const initialState: UserDataState = {
  email: "",
  password: "",
};

export const userSlice = createSlice({
  name: "userData",
  initialState,
  reducers: {
    setUserData(state, action: PayloadAction<UserDataState>) {
      state.email = action.payload.email;
      state.password = action.payload.password;
    },
  },
});

export const { setUserData } = userSlice.actions;
export const userDataReducer = userSlice.reducer;
