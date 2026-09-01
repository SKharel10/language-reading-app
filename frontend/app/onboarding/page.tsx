import { redirect } from "next/navigation";
import { auth0 } from "@/src/lib/auth0";
import OnboardingWizard from "@/src/components/OnboardingWizard";

export default async function OnboardingPage() {
  const session = await auth0.getSession();

  if (!session?.user) {
    redirect("/auth/login");
  }

  return <OnboardingWizard />;
}
