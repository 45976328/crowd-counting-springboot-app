#!/bin/bash

set -euo pipefail

sudo airmon-ng stop wlan0mon || true

sudo iw dev wlan0 set type managed || true
sudo ip link set wlan0 up || true

# Re-enable NetworkManager
sudo systemctl start wpa_supplicant.service || true
sudo systemctl start NetworkManager.service || true