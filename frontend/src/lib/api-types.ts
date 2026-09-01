export type Language = "NEPALI" | "FRENCH" | "SPANISH" | "ENGLISH";
export type CEFRLevel = "A1" | "A2" | "B1" | "B2" | "C1" | "C2";

export interface UserResponseDto {
  id: string;
  name: string;
}

export interface UserRequestDto {
  name: string;
}

export interface PageResponseDto {
  id: string;
  number: number;
  content: string;
}

export interface ChapterResponseDto {
  pages: PageResponseDto[];
  name: string;
  number: number;
}

export interface BookResponseDto {
  id: string;
  title: string;
  description: string;
  language: Language;
  level: CEFRLevel;
  coverImageUrl: string;
  chapters: ChapterResponseDto[];
}

export interface ReadingProgressResponseDto {
  id: string;
  bookId: string;
  pageId: string;
}

export interface ReadingProgressRequestDto {
  pageId: string;
}

export interface TranslationResponseDto {
  translation: string;
}

export interface TranslationRequestDto {
  sourceLanguage: Language;
  text: string;
  context?: string;
}
