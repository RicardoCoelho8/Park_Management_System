import { configureStore } from "@reduxjs/toolkit";
import { userDataReducer } from "./userData";
import { userDataApi } from "./userData/api";

export const store = configureStore({
  reducer: {
    userData: userDataReducer,
    [userDataApi.reducerPath]: userDataApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(userDataApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
