# Periodic 2.4 — hardened source edition

# Periodic v2.4 Clean Replacement

Android 15 project using a hybrid renderer: Filament supplies the real 3D scientific-laboratory environment while custom Android rendering keeps all 118 table tiles crisp and readable.

Implemented foundation:
- corrected Filament dependency, AndroidX and Java/Kotlin 17 configuration
- readable textbook-style tiles with category materials
- locked front-facing camera: horizontal movement and zoom only
- correct routing for all 118 elements
- continuous orbital animation
- isotope-labelled nuclei with exact proton/neutron particle counts
- redesigned particle exhibits
- Explore pages with curated isotope, ion, compound and evidence sections
- Scale Journey

Scientific notes are included directly in the interface where a model is educational rather than literal.


## Third-pass 3D environment
The main table now overlays a real Filament GLB laboratory rather than a painted black canvas. See `V2.3-THIRD-PASS.md`.


See `QUALITY-GATE-v2.3.md` and `ROADMAP-250.md` before building.

## v2.3 visual-match pass

The main exhibit now preserves the v2.2 periodic-table geometry while adding the approved
left navigation rail, laboratory title bar, quick search/filter surface, favorites,
settings, progress panel, bottom information cards, selection glow, camera lock and reset.
These controls render over the real Filament science-laboratory scene rather than replacing it.
