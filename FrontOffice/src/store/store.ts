import { configureStore } from "@reduxjs/toolkit";
import { userDataReducer } from "./userData";
import { userDataApi } from "./userData/api";
import { navigationReducer } from "./navigation";

export const store = configureStore({
  reducer: {
    userData: userDataReducer,
    navigation: navigationReducer,
    [userDataApi.reducerPath]: userDataApi.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(userDataApi.middleware),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
