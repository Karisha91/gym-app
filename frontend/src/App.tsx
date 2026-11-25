import { ExerciseCard } from './components/ExerciseCard'

function App() {
  return (
    <main className="mx-auto min-h-dvh max-w-md px-4 pb-32 pt-10">
      <header className="mb-8">
        <p className="text-sm font-medium uppercase tracking-widest text-accent">Today</p>
        <h1 className="mt-1 text-3xl font-bold tracking-tight">Full-body workout</h1>
        <p className="mt-2 text-muted">Muscle goal · 45 min</p>
      </header>

      <div className="flex flex-col gap-4">
        <ExerciseCard
          position={1}
          name="Goblet Squat"
          muscleGroup="QUADS"
          equipment="DUMBBELL"
          sets={3}
          minReps={8}
          maxReps={12}
          targetWeightKg={25}
          why="Goblet Squats build leg and core strength using just a dumbbell, a simple foundation for lower body power."
        />
        <ExerciseCard
          position={2}
          name="Barbell Bench Press"
          muscleGroup="CHEST"
          equipment="BARBELL"
          sets={3}
          minReps={8}
          maxReps={12}
          targetWeightKg={null}
          why="Pressing a barbell lets you load the chest, shoulders and triceps heavily, which drives upper body strength."
        />
        <ExerciseCard
          position={3}
          name="Dumbbell Romanian Deadlift"
          muscleGroup="HAMSTRINGS"
          equipment="DUMBBELL"
          sets={3}
          minReps={8}
          maxReps={12}
          targetWeightKg={22.5}
          why="This hinge trains the hamstrings and glutes under a load you control, adding strength to the back of the body."
        />
      </div>

      <div className="fixed inset-x-0 bottom-0 border-t border-border bg-bg/80 px-4 pb-6 pt-4 backdrop-blur">
        <button
          type="button"
          className="mx-auto block w-full max-w-md rounded-2xl bg-accent py-4 text-lg font-semibold text-accent-ink shadow-lg shadow-accent/20 transition active:scale-[0.98]"
        >
          Start workout
        </button>
      </div>
    </main>
  )
}

export default App