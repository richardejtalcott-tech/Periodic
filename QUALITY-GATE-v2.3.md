# Periodic 2.3 quality gate

This package is a hardened source update. It implements architecture, persistence, lifecycle, build, validation, accessibility, rendering-safety, data-validation, and release-hardening changes from the 250-item review.

## Mandatory before release

1. Run `python3 tools/validate_source.py`.
2. Run `./gradlew clean lint testDebugUnitTest assembleDebug`.
3. Install the debug APK on at least one Android 15 phone.
4. Confirm the real `scientific_lab.glb` environment is visible behind the preserved v2.2 table.
5. Exercise every Explore tab, Journey stage, back action, process recreation, and low-memory recovery.
6. Run a release build and inspect the R8 mapping/output.

## Honest scope

The 250-item review mixes code hardening with large content projects. This source implements the engineering framework and a substantial hardening pass, but it does not pretend that all isotope records, compound databases, narration, HDR assets, true runtime 3D text meshes, or every advanced simulation have been fully authored. Those remain content/asset work and are tracked in `ROADMAP-250.md`.
