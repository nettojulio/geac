export type EventCategory =
  | "palestra"
  | "seminario"
  | "cultural"
  | "feira"
  | "workshop"
  | "livre"
  | "conferencia"
  | "festival"
  | "outro";

export type Campus =
  | "reitoria"
  | "ondina"
  | "sao lazaro"
  | "canela"
  | "graca"
  | "federacao";

export interface Event {
  id: string;
  title: string;
  description: string;
  category: EventCategory;
  date: string;
  startTime: string;
  endTime: string;
  location: string;
  campus: Campus;
  speakers: string[];
  capacity: number;
  registered: number;
  requirements: string[];
  organizer: string;
  organizerType: string;
  image?: string;
  tags: string[];
  isRegistered?: boolean;
}
