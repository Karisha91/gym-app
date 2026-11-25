import { useMutation } from '@tanstack/react-query'
import { generateWorkout } from './api/client'
import { ExerciseCard } from './components/ExerciseCard'

function App() {
  const generate = useMutation({ mutationFn: generateWorkout })

  const exercises = (generate.data?.slots ?? []).flatMap((slot) =>
    slot.exercise ? [{ slot, exercise: slot.exercise }] : [],
  )

  return (
    <main className="mx-auto min-h-dvh max-w-md px-4 pb-32 pt-10">
      <header className="mb-8">
        <p className="text-sm font-medium uppercase tracking-widest text-accent">Today</p>
        <h1 className="mt-1 text-3xl font-bold tracking-tight">Full-body workout</h1>
        <p className="mt-2 text-muted">
          {generate.data
            ? `${exercises.length} exercises`
            : 'Built from your profile'}
        </p>
      </header>

      {!generate.data && !generate.isPending && (
        <div className="rounded-3xl border border-dashed border-border p-8 text-center text-muted">
          No workout yet. Tap the button below to generate one.
        </div>
      )}

      <div className="flex flex-col gap-4">
        {exercises.map(({ slot, exercise }, index) => (
          <ExerciseCard
            key={slot.id}
            position={index + 1}
            name={exercise.name}
            muscleGroup={exercise.muscleGroup}
            equipment={exercise.equipment}
            sets={slot.sets}
            minReps={slot.minReps}
            maxReps={slot.maxReps}
            targetWeightKg={slot.targetWeightKg}
            why={slot.why}
          />
        ))}
      </div>

      <div className="fixed inset-x-0 bottom-0 border-t border-border bg-bg/80 px-4 pb-6 pt-4 backdrop-blur">
        {generate.error && (
          <p className="mx-auto mb-3 max-w-md text-center text-sm text-danger">
            {generate.error.message}
          </p>
        )}
        <button
          type="button"
          onClick={() => generate.mutate()}
          disabled={generate.isPending}
          className="mx-auto block w-full max-w-md rounded-2xl bg-accent py-4 text-lg font-semibold text-accent-ink shadow-lg shadow-accent/20 transition active:scale-[0.98] disabled:opacity-60"
        >
          {generate.isPending ? 'Generating…' : 'Generate workout'}
        </button>
      </div>
    </main>
  )
}

export default App