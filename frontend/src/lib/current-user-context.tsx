"use client";

import { createContext, useContext } from "react";
import type { UserResponseDto } from "./api-types";

const CurrentUserContext = createContext<UserResponseDto | null>(null);

export function CurrentUserProvider({
  user,
  children,
}: {
  user: UserResponseDto | null;
  children: React.ReactNode;
}) {
  return (
    <CurrentUserContext.Provider value={user}>
      {children}
    </CurrentUserContext.Provider>
  );
}

export function useCurrentUser() {
  return useContext(CurrentUserContext);
}
