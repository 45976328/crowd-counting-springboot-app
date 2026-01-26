import requests, json
from pathlib import Path

from util.getToken import *

API_GATEWAY="http://192.168.1.241:8080/api/v1/gateway"

def sendData(filename):
    if Path(filename).exists(): #if file exists
            with open(filename, mode="r", encoding="utf-8") as f:
                data = json.load(f)

    if Path(".token").exists(): #if file exists
            with open(".token", mode="r", encoding="utf-8") as f:
                token = f.read()

    headers = {
        "Authorization": f"Bearer {token}",
        "Content-Type" : "application/json"
    }
    print("Sending to api gateway ...")
    try:
        r = requests.post(API_GATEWAY, json = data, headers=headers , timeout=10)
    except:
        print("Could not post ...")
    else:
        print("Response :", r)
        return r

      
# https://stackoverflow.com/questions/36719540/how-can-i-get-an-oauth2-access-token-using-python
