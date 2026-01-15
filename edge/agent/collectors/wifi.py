#!/usr/bin/env python3
from util.log import *

import codecs, time, os
from datetime import datetime, timezone
from scapy.all import *



def handler(p): #https://blog.podkalicki.pl/capturing-wifi-beacon-frames-probe-requests-and-probe-responses-in-python-with-scapy/
    if not (p.haslayer(Dot11ProbeResp) or p.haslayer(Dot11ProbeReq) or p.haslayer(Dot11Beacon)):
        return

    rssi = p[RadioTap].dBm_AntSignal
    dst_mac = p[Dot11].addr1
    src_mac = p[Dot11].addr2
    ap_mac = p[Dot11].addr2
    info = f"rssi={rssi:2}dBm, dst={dst_mac}, src={src_mac}, ap={ap_mac}"

    if p.haslayer(Dot11ProbeReq):
        #print(f"[ProbReq ] {info}")
        dump(os.getenv("WIFI_DUMP_FILENAME") , [src_mac, rssi, datetime.now(timezone.utc).isoformat(timespec='milliseconds') ])



def ProbReqSniffer():
    
    sniff(iface=os.getenv("MON_IFACE"), prn=handler, store=0, timeout=int(os.getenv("SNIFF_TIMEOUT"))) #https://scapy.readthedocs.io/en/stable/api/scapy.sendrecv.html

