"use server";

import { cookies } from "next/headers";
import { redirect } from "next/navigation";
import { apiFetch } from "@/src/lib/api-client";
import type { UserResponseDto, Language, CEFRLevel } from "@/src/lib/api-types";

const ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

export async function completeOnboarding(
  name: string,
  language: Language,
  level: CEFRLevel,
) {
  await apiFetch<UserResponseDto>("/api/users/me", "PUT", { name });

  const cookieStore = await cookies();
  cookieStore.set("language", language, {
    path: "/",
    maxAge: ONE_YEAR_SECONDS,
  });
  cookieStore.set("level", level, { path: "/", maxAge: ONE_YEAR_SECONDS });

  redirect("/");
}
