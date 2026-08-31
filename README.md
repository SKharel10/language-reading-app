# Bookdrift

Learn a new language by reading books written for your level, with contextual translation
built right into the reading experience.

## How it works

Pick a language you're learning and a reading level, browse a library of AI-generated books at
that level, and read them in the app. Select any word or phrase while reading and get a
translation that accounts for the surrounding context — not just a dictionary lookup — so
ambiguous words get the right meaning. Your reading position saves automatically, so you can
pick up where you left off.

## Status

- **Backend**: MVP complete. Auth, book browsing, reading progress, and contextual translation
  are all implemented.
- **Frontend**: Not started yet. Currently just the default Next.js scaffold — the actual app
  (auth, library, reader, translation UI) is next.

## Stack

- **Backend**: Spring Boot (Java 21), PostgreSQL, Auth0 (JWT resource server), Google Gemini for
  translation
- **Frontend**: Next.js (App Router), Auth0's Next.js SDK, Tailwind

## Getting started

```bash
docker compose up -d             # local Postgres

cd backend
./gradlew bootRun                # needs DB_URL, DB_USERNAME, DB_PASSWORD, AUTH_DOMAIN,
                                  # AUTH_AUDIENCE, GEMINI_API_KEY set

cd frontend
pnpm dev
```

## Features

- Account creation and login (Auth0)
- Select a language to learn and a reading level
- Browse and read pre-generated books
- Select a word or phrase for a context-aware translation
- Reading progress saves automatically and resumes where you left off

## Roadmap

- Finish the frontend — auth, library, reader, translation UI
- Reading levels tied to user progress, rather than just a fixed CEFR level on each book
- An experience/progression system, so reading builds toward something over time
- Translate into languages other than English — right now translation only goes *into* English;
  there's no concept of a learner's own native/interface language yet

## Known limitations

- **Translation always targets English.** There's no field for a user's native language yet, so
  translation is hardcoded English-only for now.
- **No translation caching.** Every word/phrase selection calls Gemini live — fine for now, but
  it means repeat lookups of the same word on the same page cost the same as the first.
- **Language/level selection isn't persisted server-side.** The backend has no field for it on
  the user yet, so the frontend tracks it client-side (a cookie) rather than syncing it to the
  account.

See `docs/MVP.md` and `docs/USER_STORIES.md` for the full feature scope, and
`docs/IMPLEMENTATION_GUIDE.md` for the frontend build plan.
