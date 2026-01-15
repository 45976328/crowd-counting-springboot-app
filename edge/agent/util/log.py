import json
import os
from pathlib import Path
from util.hash import *

def read(filename):
    if Path(filename).exists(): #if file exists
        try:
            with open(filename, mode="r", encoding="utf-8") as f:
                data = json.load(f)
                # If old shape was a single dict, wrap it into a list
                if isinstance(data, dict):
                    return [data]
                if isinstance(data, list):
                    return data
                return []
        except (json.JSONDecodeError, OSError):
            return []
    else:
        return []

def write(filename, data):
    with open(filename+".tmp", mode = "w", encoding="utf-8") as write_file: # write data on tmp file first
        json.dump(data,write_file, indent=2)
    os.replace(filename+".tmp", filename) # replace tmp file 

def dump (filename, record): #https://realpython.com/python-json/
    mac, rssi, timestamp = record
    nodeId = int( os.getenv("NODE_ID"))
    zoneId = int( os.getenv("ZONE_ID"))

    data = read(filename)

    # Normalize data to always be a list
    if isinstance(data, dict):
        data = [data]
    elif not isinstance(data, list):
        data = []

    found = False
    for rec in data:
        if rec.get("mac") == mac:
                rec["nodeId"] = nodeId
                rec["zoneId"] = zoneId
                rec["mac"] = str(mac)
                rec["rssi"].append(rssi)
                rec["timestamp"] = str(timestamp)

                found = True
                break
    if not found:
        entry = {
            "nodeId": nodeId,          # int
            "zoneId": zoneId,          # int
            "mac": str(mac),            # string
            "rssi": [rssi],             # start a list of RSSI readings
            "timestamp": str(timestamp) # last-seen timestamp (string)
        }
        data.append(entry)
        
    write(filename, data)
    

def processMac(filename):
    data = read(filename)
    for rec in data: #https://discuss.python.org/t/change-key-name-in-json-python/19667/2
        if "mac" in rec:
            rec["mac"] = hashMac(rec["mac"])

    write(filename, data)