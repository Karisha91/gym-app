type ExerciseCardProps = {
  position: number
  name: string
  muscleGroup: string
  equipment: string
  sets: number
  minReps: number
  maxReps: number
  targetWeightKg: number | null
  why: string | null
}

function formatLabel(value: string) {
  return value.charAt(0) + value.slice(1).toLowerCase()
}

function formatReps(min: number, max: number) {
  return min === max ? `${min}` : `${min}–${max}`
}

export function ExerciseCard({
  position,
  name,
  muscleGroup,
  equipment,
  sets,
  minReps,
  maxReps,
  targetWeightKg,
  why,
}: ExerciseCardProps) {
  return (
    <article className="rounded-3xl border border-border bg-surface p-5 shadow-lg shadow-black/30">
      <header className="flex items-center gap-3">
        <span className="flex size-8 shrink-0 items-center justify-center rounded-full bg-accent/15 text-sm font-bold text-accent">
          {position}
        </span>
        <h2 className="text-lg font-semibold leading-tight">{name}</h2>
      </header>

      <div className="mt-3 flex flex-wrap gap-2">
        <span className="rounded-full border border-border px-3 py-1 text-xs text-muted">
          {formatLabel(muscleGroup)}
        </span>
        <span className="rounded-full border border-border px-3 py-1 text-xs text-muted">
          {formatLabel(equipment)}
        </span>
      </div>

      <div className="mt-5 grid grid-cols-2 gap-3">
        <div className="rounded-2xl bg-bg p-4">
          <p className="text-xs uppercase tracking-wider text-muted">Target</p>
          {targetWeightKg === null ? (
            <p className="mt-2 text-base font-medium text-muted">Pick your weight</p>
          ) : (
            <p className="mt-1 flex items-baseline gap-1">
              <span className="text-4xl font-bold tabular-nums text-accent">
                {targetWeightKg}
              </span>
              <span className="text-muted">kg</span>
            </p>
          )}
        </div>

        <div className="rounded-2xl bg-bg p-4">
          <p className="text-xs uppercase tracking-wider text-muted">Sets × reps</p>
          <p className="mt-1 flex items-baseline gap-1">
            <span className="text-4xl font-bold tabular-nums">{sets}</span>
            <span className="text-xl text-muted tabular-nums">
              × {formatReps(minReps, maxReps)}
            </span>
          </p>
        </div>
      </div>

      {why && (
        <p className="mt-5 border-l-2 border-accent/60 pl-3 text-sm leading-relaxed text-text/80">
          {why}
        </p>
      )}
    </article>
  )
}