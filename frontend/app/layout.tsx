import { Inter } from "next/font/google";
import type { Metadata } from "next";
import { Auth0Provider } from "@auth0/nextjs-auth0/client";
import { auth0 } from "@/src/lib/auth0";
import { apiFetch } from "@/src/lib/api-client";
import { CurrentUserProvider } from "@/src/lib/current-user-context";
import type { UserResponseDto } from "@/src/lib/api-types";
import "./globals.css";

const inter = Inter({
  subsets: ["latin"],
  weight: ["300", "400", "500", "600", "700"],
});

export const metadata: Metadata = {
  title: "Auth0 Next.js App",
  description: "Next.js app with Auth0 authentication",
};

export default async function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const session = await auth0.getSession();
  const user = session?.user
    ? await apiFetch<UserResponseDto>("/api/users/me", "GET")
    : null;

  return (
    <html lang="en">
      <body className={inter.className}>
        <Auth0Provider>
          <CurrentUserProvider user={user}>{children}</CurrentUserProvider>
        </Auth0Provider>
      </body>
    </html>
  );
}
