import { auth0 } from "./auth0";

export async function apiFetch<T>(
  path: string,
  option: "POST" | "GET" | "PUT" | "DELETE",
  body?: unknown,
): Promise<T> {
  const token = (await auth0.getAccessToken()).token;

  if (token === null) {
    throw new Error("No access token found");
  }

  const API_URL = process.env.API_URL;
  if (!API_URL) {
    throw new Error("API URL is not set");
  }

  try {
    const response = await fetch(API_URL + path, {
      method: option,
      headers: {
        Authorization: `Bearer ${token}`,
        ...(body !== undefined ? { "Content-Type": "application/json" } : {}),
      },
      ...(body !== undefined ? { body: JSON.stringify(body) } : {}),
    });

    if (!response.ok) {
      throw new Error("API request failed...");
    }

    if (response.status === 204) {
      return undefined as T;
    }

    return (await response.json()) as T;
  } catch {
    throw new Error("API request failed");
  }
}
