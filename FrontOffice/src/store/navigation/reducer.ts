import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface NavigationState {
  currentScreen: string;
}

export const initialState: NavigationState = {
  currentScreen: "Login",
};

export const navigationSlice = createSlice({
  name: "navigation",
  initialState,
  reducers: {
    setCurrentScreen(state, action: PayloadAction<NavigationState>) {
      state.currentScreen = action.payload.currentScreen;
    },
  },
});

export const { setCurrentScreen } = navigationSlice.actions;
export const navigationReducer = navigationSlice.reducer;
