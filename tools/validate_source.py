#!/usr/bin/env python3
from pathlib import Path
import re, sys, xml.etree.ElementTree as ET, zipfile
root=Path(__file__).resolve().parents[1]
errors=[]
for p in (root/'app/src/main/res').rglob('*.xml'):
    try: ET.parse(p)
    except Exception as e: errors.append(f'XML {p}: {e}')
try: ET.parse(root/'app/src/main/AndroidManifest.xml')
except Exception as e: errors.append(f'Manifest: {e}')
for p in (root/'app/src/main/java').rglob('*'):
    if p.suffix not in {'.java','.kt'}: continue
    text=p.read_text(errors='ignore'); balance=0
    text=re.sub(r'//.*?$|/\*.*?\*/|"(?:\\.|[^"\\])*"','',text,flags=re.M|re.S)
    for ch in text:
        if ch=='{': balance+=1
        elif ch=='}': balance-=1
        if balance<0: errors.append(f'Brace underflow: {p}'); break
    if balance: errors.append(f'Brace imbalance {balance}: {p}')
for asset in ['periodic_table.glb','scientific_lab.glb']:
    p=root/'app/src/main/assets'/asset
    if not p.exists() or p.stat().st_size<100: errors.append(f'Missing/empty asset: {asset}')
    elif p.read_bytes()[:4] != b'glTF': errors.append(f'Invalid GLB header: {asset}')
if errors:
    print('\n'.join(errors));sys.exit(1)
print('Static validation passed: XML, source braces, required GLB assets.')
