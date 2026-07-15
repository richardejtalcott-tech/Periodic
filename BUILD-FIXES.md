# Periodic 2.3 clean replacement build fixes

This package incorporates the compile blockers observed during the prior Codespace attempt:

- restores `Element.shells` compatibility used by the UI and validation code;
- renames the Scale Journey helper so it does not collide with Android `View.draw(Canvas)`;
- initializes and guards the Filament render lifecycle more safely;
- updates activity transition handling for Android 14/15 with legacy fallback;
- applies window-inset handling to the element-detail screen;
- builds the debug APK before optional lint/test diagnostics.

Static checks are included, but a real Codespaces/Android SDK build is still the final proof.
