# Flyway migration workflow

## Version allocation

- Runtime migrations live in `src/main/resources/db/migration`. SQL files in this documentation directory are not loaded by Flyway.
- Update the feature branch from the latest `develop` before assigning a version and again before merging.
- Use the next unused version across the entire application, not one sequence per domain.
- Rename only migrations that have not been applied to a shared database. Never rename or edit a migration already applied to stag or prod; add a new migration instead.
- Run `./gradlew validateMigrationVersions` before committing. CI, `test`, and `bootJar` run this check even when there are no test sources. Equivalent versions such as `V5`, `V05`, and `V5_0` are treated as duplicates.
- Require the CI build status and an up-to-date branch (or a merge queue) in GitHub branch protection. The repository check alone cannot prevent merging an outdated PR without those settings.
- This check validates filenames and version uniqueness, not SQL correctness or compatibility with deployed databases. Validate migrations on an isolated MySQL database before deployment.

## V5 collision on 2026-09-28

The team-membership and employee-account branches independently selected V5.
The team migration entered `develop` through PR #183 on September 27;
the employee migration entered through PR #192 on September 28.
Different filenames avoided a Git conflict, but Flyway requires unique versions.
The old CI only ran `test`, which reported `NO-SOURCE` and never started Flyway.

The stag schema history was checked before the fix: V5 was
`V5__join_team_allow_multiple_teams.sql` with checksum `1654028166` and success `1`.
The employee migration had not run. The fix preserves that V5 and renames only
the employee migration to `V6__add_employee_delete_status_and_create_at.sql`,
without changing either SQL body.

## Deployment and recovery

1. Check `flyway_schema_history` on each target environment before rollout. Do not assume prod has the same history as stag.
2. If V5 is the team migration, a normal deployment applies employee V6. Do not delete history, run `clean`, or use `repair` to hide the collision.
3. If another environment already recorded the employee migration as V5, stop and plan a backed-up, environment-specific reconciliation. Renaming the file alone is not safe for that history.
4. Build a fresh artifact with `./gradlew clean bootJar` so an old copied V5 resource cannot remain in the JAR after the rename.
5. Confirm the application is healthy and V6 is recorded as successful. A successful image build or deployment command does not by itself confirm successful application startup.

References:
- https://documentation.red-gate.com/flyway/reference/exit-codes-and-error-codes/general-error-codes
- https://documentation.red-gate.com/flyway/reference/commands/validate
