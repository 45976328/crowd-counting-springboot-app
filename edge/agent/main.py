#!/usr/bin/env python3
import os, requests

from collectors.wifi import *
from util.log import *
from util.channelHopper import *
from util.send import *
from util.getToken import *

from dotenv import load_dotenv

load_dotenv("../.env")
load_dotenv("../.secrets")


def main():
    print("Capturing Probe Requests ...")
    ProbReqSniffer()
    print("Changing Channel ...")
    hopChannel()
    #processMac(os.getenv("WIFI_DUMP_FILENAME"))
    handleToken()
    response = sendData(os.getenv("WIFI_DUMP_FILENAME"))
    try:
        if response.status_code == 200:
            os.remove(os.getenv("WIFI_DUMP_FILENAME"))
    except:
        print("wifi.json won't be deleted until post is successful")


if __name__ == "__main__":
    while True:
        main()
