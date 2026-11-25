import type { Workout, ApiError } from './types'

export async function generateWorkout(): Promise<Workout> {
  const response = await fetch('/api/workouts/generate', {
    method: 'POST',
  })

  if (!response.ok) {
    try {
      const body = (await response.json()) as ApiError
      throw new Error(body.error)
    } catch (jsonError) {
      if (jsonError instanceof SyntaxError) {
        throw new Error(`Request failed (${response.status})`)
      }
      throw jsonError
    }
  }

  return response.json() as Promise<Workout>
}