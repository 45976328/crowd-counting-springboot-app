#!/bin/bash

#set -euo pipefail

sudo apt update
sudo apt install python3-scapy aircrack-ng -y

sudo cp start.sh /usr/local/bin/start.sh
sudo chmod +x /usr/local/bin/start.sh

sudo cp "crowd.service" /etc/systemd/system/crowd.service
sudo systemctl daemon-reload
sudo systemctl enable crowd
sudo systemctl start crowd
sudo systemctl status crowd --no-pager