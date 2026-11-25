export type Exercise = {
  id: number
  name: string
  muscleGroup: string
  movementPattern: string
  equipment: string
  compound: boolean
  instructions: string
}

export type WorkoutSlot = {
  id: number
  pattern: string
  skipped: boolean
  exercise: Exercise | null
  sets: number
  minReps: number
  maxReps: number
  targetWeightKg: number | null
  why: string | null
}

export type Workout = {
  id: number
  createdAt: string
  slots: WorkoutSlot[]
}

export type ApiError = {
  status: number
  error: string
  fieldErrors: Record<string, string>
}