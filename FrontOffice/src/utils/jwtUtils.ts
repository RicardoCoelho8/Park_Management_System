export function decodeJwt(token: string): any {
  const base64Url: string = token.split(".")[1];
  const base64: string = base64Url.replace(/-/g, "+").replace(/_/g, "/");
  const jsonPayload: string = decodeURIComponent(
    atob(base64)
      .split("")
      .map((c) => {
        return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join("")
  );
  console.log("jsonPayload", jsonPayload);
  return JSON.parse(jsonPayload);
}

export function storeUserInLocalStorage(
  token: string,
  userId: string,
  userRole: string
): void {
  localStorage.setItem("userToken", token);
  localStorage.setItem("userId", userId);
  localStorage.setItem("userRole", userRole);
}

export function getTokenFromLocalStorage(): string | null {
  return localStorage.getItem("userToken");
}
export function getUserIdFromLocalStorage(): string | null {
  return localStorage.getItem("userId");
}
export function getUserRoleFromLocalStorage(): string | null {
  return localStorage.getItem("userRole");
}

export function removeUserFromLocalStorage(): void {
  localStorage.removeItem("userToken");
  localStorage.removeItem("userId");
  localStorage.removeItem("userRole");
}
