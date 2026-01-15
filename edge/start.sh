#!/bin/bash
set -euo pipefail

# Ensure we're root
if [[ $EUID -ne 0 ]]; then
  echo "Run as root"; exit 1
fi

# configurable via .env (IFACE), default to wlan0
IFACE="wlan0"

# Stop services that fight monitor mode
# (NetworkManager/wpa_supplicant often reconfigure the card)
systemctl stop NetworkManager.service || true
systemctl stop wpa_supplicant.service || true

# Bring link down, switch to monitor, bring up
airmon-ng check kill
ip link set "$IFACE" down || true
airmon-ng start "$IFACE"  || { echo "airmon-ng failed"; exit 1; }
ip link set wlan0mon up || true