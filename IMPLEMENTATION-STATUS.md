# Periodic v2.2 implementation and validation notes

This rebuild treats the locked roadmap as one release rather than a chain of partial APKs.

## Implemented in this package

- Hybrid presentation: Filament renders a real three-dimensional laboratory exhibit environment; the periodic table is rendered as a crisp interactive overlay so textbook information stays readable.
- Table begins large and centered, supports horizontal movement and pinch zoom, and cannot roll, flip, or expose its underside.
- Modern textbook tile layout: atomic number, symbol, element name, and atomic mass.
- Category-specific matte metallic materials, beveled depth, moving highlights, pressed lift, haptics, and restrained dynamic lighting.
- Illuminated PERIODIC branding with a nearby slogan and no renderer/developer label.
- Correct routing for all 118 elements. Invalid IDs no longer silently open Hydrogen.
- Continuous electron animation using complete trigonometric loops, with electrons locked to visible educational shell paths.
- Interactive electron shells: tap a shell to highlight it and show its electron total.
- Representative-isotope nuclei for every element, with exact displayed proton and neutron counts in the magnified nucleus.
- Distinct proton, neutron, electron, up-quark, down-quark, and gluon presentations.
- Element-specific visual signatures, including bespoke effects for Carbon, Oxygen, Neon, Gold, Mercury, and Uranium plus deterministic unique styling for every other element.
- Explore interface with common isotopes where curated data is bundled, common ions, representative compounds, scientific cautions, and “How we know” explanations.
- Animated Scale Journey.
- Cinematic zoom/fade transitions.
- Build corrections incorporated: published Filament dependency, AndroidX, Jetifier, Java 17, Kotlin JVM 17, Android 15 target, and automated GitHub validation.

## Accuracy safeguards

- The nucleus is always labelled with the representative isotope being displayed.
- Curated common-isotope lists are included only where the data was explicitly entered and reviewed. Other elements show a clear fallback instead of invented abundance or half-life values.
- Ion and compound sections describe representative chemistry and explicitly warn that charge state depends on chemical environment.
- The app states that orbit rings are an educational shell model and that real electrons are described by quantum orbitals and probability distributions.
- Unit tests verify 118 unique elements, valid routing data, shell totals that sum to atomic number, and representative nucleus counts for key examples.

## Validation performed before packaging

- All Java source parsed successfully.
- Core element and nuclear-data classes compiled and ran in a standalone Java validation harness.
- All 118 atomic numbers were checked for uniqueness.
- Electron-shell totals were checked to equal each element’s atomic number.
- Hydrogen, Carbon, Oxygen, Iron, Gold, Uranium, and Oganesson representative nuclei were checked.
- Android resource XML and the manifest were parsed successfully.
- The scientific laboratory GLB was loaded and inspected as a valid 47-geometry scene.
- GitHub Actions performs unit tests, routing-key checks, a clean Android build, APK existence verification, and artifact upload.

## Remaining real-device responsibility

A complete Android/Filament runtime test requires the GitHub Actions build and installation on the target phones. The workflow is intentionally configured to fail before producing an artifact if configuration, tests, compilation, or APK generation fails.
