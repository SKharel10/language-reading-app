"use client";

import { useState, useTransition } from "react";
import { Quicksand, Nunito } from "next/font/google";
import { completeOnboarding } from "@/app/onboarding/actions";
import type { Language, CEFRLevel } from "@/src/lib/api-types";

const quicksand = Quicksand({ subsets: ["latin"], weight: ["600", "700"] });
const nunito = Nunito({ subsets: ["latin"], weight: ["400", "500", "600"] });

const LANGS: { code: Language; label: string; native: string; books: string }[] = [
  { code: "FRENCH", label: "French", native: "français", books: "128 books" },
  { code: "SPANISH", label: "Spanish", native: "español", books: "96 books" },
  { code: "NEPALI", label: "Nepali", native: "नेपाली", books: "24 books" },
];

const LEVELS: { code: CEFRLevel; name: string; desc: string }[] = [
  { code: "A1", name: "Beginner", desc: "Single sentences, present tense, familiar words." },
  { code: "A2", name: "Elementary", desc: "Short stories about everyday life." },
  { code: "B1", name: "Intermediate", desc: "Longer narratives, past tenses, some idiom." },
  { code: "B2", name: "Upper intermediate", desc: "Full novels, lightly adapted." },
  { code: "C1", name: "Advanced", desc: "Unabridged prose and journalism." },
  { code: "C2", name: "Mastery", desc: "Literary style, wordplay, dialect." },
];

type Step = 1 | 2 | 3;

export default function OnboardingWizard() {
  const [step, setStep] = useState<Step>(1);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [language, setLanguage] = useState<Language | null>(null);
  const [level, setLevel] = useState<CEFRLevel | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [isPending, startTransition] = useTransition();

  const canGoNext =
    step === 1
      ? firstName.trim().length > 0 && lastName.trim().length > 0
      : step === 2
        ? language !== null
        : level !== null;

  function handleNext() {
    if (step < 3) {
      setStep((s) => (s + 1) as Step);
      return;
    }

    if (!language || !level) return;

    setError(null);
    startTransition(async () => {
      try {
        await completeOnboarding(
          `${firstName.trim()} ${lastName.trim()}`,
          language,
          level,
        );
      } catch {
        setError("Something went wrong saving your details. Try again.");
      }
    });
  }

  function handleBack() {
    setStep((s) => (s > 1 ? ((s - 1) as Step) : s));
  }

  return (
    <div className={`${nunito.className} min-h-screen bg-[#f7fafc] flex flex-col`}>
      <div className="flex items-center justify-between px-14 py-[26px]">
        <div className={`${quicksand.className} font-semibold text-[21px] text-[#1c3247]`}>
          Bookdrift
        </div>
        <div className="flex items-center gap-2.5 text-[11.5px] text-[#4f6c83]">
          <span>Step {step} of 3</span>
          <span className="flex gap-[5px]">
            {[1, 2, 3].map((n) => (
              <i
                key={n}
                className={`w-[18px] h-[2px] block ${
                  n <= step ? "bg-[#1c3247]" : "bg-[rgba(28,50,71,0.16)]"
                }`}
              />
            ))}
          </span>
        </div>
      </div>

      <div className="flex-1 flex items-start justify-center px-14 pb-14 pt-11">
        <div className="w-[620px]">
          {step === 1 && (
            <div className="flex flex-col gap-2">
              <h2
                className={`${quicksand.className} font-bold text-[40px] leading-[1.1] text-[#1c3247]`}>
                What should we call you?
              </h2>
              <p className="text-[14.5px] text-[#4f6c83] mb-[14px]">
                Two fields, then two choices. You can change all of it later.
              </p>
              <div className="grid grid-cols-2 gap-4 mt-[10px]">
                <label className="flex flex-col gap-[7px]">
                  <span className="text-[10px] tracking-[0.14em] uppercase text-[#4f6c83]">
                    First name
                  </span>
                  <input
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    placeholder="Amélie"
                    className="text-[15px] px-[14px] py-[13px] border border-[rgba(28,50,71,0.16)] rounded-[7px] bg-white text-[#1c3247] outline-none focus:border-[#2f86cf]"
                  />
                </label>
                <label className="flex flex-col gap-[7px]">
                  <span className="text-[10px] tracking-[0.14em] uppercase text-[#4f6c83]">
                    Last name
                  </span>
                  <input
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    placeholder="Descamps"
                    className="text-[15px] px-[14px] py-[13px] border border-[rgba(28,50,71,0.16)] rounded-[7px] bg-white text-[#1c3247] outline-none focus:border-[#2f86cf]"
                  />
                </label>
              </div>
            </div>
          )}

          {step === 2 && (
            <div className="flex flex-col gap-2">
              <h2
                className={`${quicksand.className} font-bold text-[40px] leading-[1.1] text-[#1c3247]`}>
                Which language are you learning?
              </h2>
              <p className="text-[14.5px] text-[#4f6c83] mb-[18px]">
                Translation goes into English for now.
              </p>
              <div className="flex flex-col gap-[10px]">
                {LANGS.map((l) => (
                  <button
                    key={l.code}
                    type="button"
                    onClick={() => setLanguage(l.code)}
                    className={`flex items-center justify-between text-left px-5 py-[18px] rounded-[7px] bg-white cursor-pointer border ${
                      language === l.code
                        ? "border-[#2f86cf] shadow-[0_0_0_3px_rgba(47,134,207,0.14)]"
                        : "border-[rgba(28,50,71,0.16)] hover:border-[rgba(28,50,71,0.3)]"
                    }`}>
                    <span className="flex flex-col gap-[3px]">
                      <span className="text-[16px] text-[#1c3247]">{l.label}</span>
                      <span
                        className={`${quicksand.className} font-semibold text-[15px] text-[#4f6c83]`}>
                        {l.native}
                      </span>
                    </span>
                    <span className="text-[12px] text-[#4f6c83]">{l.books}</span>
                  </button>
                ))}
              </div>
            </div>
          )}

          {step === 3 && (
            <div className="flex flex-col gap-2">
              <h2
                className={`${quicksand.className} font-bold text-[40px] leading-[1.1] text-[#1c3247]`}>
                Where are you starting?
              </h2>
              <p className="text-[14.5px] text-[#4f6c83] mb-[18px]">
                CEFR level. Pick low if you&apos;re unsure — the library shows your level and
                everything below it.
              </p>
              <div className="grid grid-cols-2 gap-[10px]">
                {LEVELS.map((v) => (
                  <button
                    key={v.code}
                    type="button"
                    onClick={() => setLevel(v.code)}
                    className={`flex flex-col gap-[5px] text-left px-[17px] py-[15px] rounded-[7px] bg-white cursor-pointer border ${
                      level === v.code
                        ? "border-[#2f86cf] shadow-[0_0_0_3px_rgba(47,134,207,0.14)]"
                        : "border-[rgba(28,50,71,0.16)] hover:border-[rgba(28,50,71,0.3)]"
                    }`}>
                    <span className="flex items-baseline gap-[9px]">
                      <span className="text-[15px] font-semibold tracking-[0.04em] text-[#1c3247]">
                        {v.code}
                      </span>
                      <span className="text-[12px] text-[#4f6c83]">{v.name}</span>
                    </span>
                    <span className="text-[12.5px] leading-[1.45] text-[#4f6c83]">{v.desc}</span>
                  </button>
                ))}
              </div>
            </div>
          )}

          {error && <p className="text-[13px] text-red-600 mt-4">{error}</p>}

          <div className="flex items-center justify-between mt-[34px] pt-[22px] border-t border-[rgba(28,50,71,0.09)]">
            <button
              type="button"
              onClick={handleBack}
              disabled={step === 1}
              className={`bg-transparent border-none text-[13px] cursor-pointer py-[6px] ${
                step === 1 ? "invisible" : "text-[#4f6c83]"
              }`}>
              ← Back
            </button>
            <button
              type="button"
              onClick={handleNext}
              disabled={!canGoNext || isPending}
              className="bg-[#2f86cf] disabled:opacity-50 text-white border-none rounded-[7px] px-[26px] py-[14px] text-[13.5px] font-medium cursor-pointer hover:bg-[#1e6aa8]">
              {isPending ? "Saving..." : step === 3 ? "Get started" : "Continue"}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
