import requests, os, time
from pathlib import Path

KEYCLOAK_SERVER_URL=f"http://192.168.1.241:8082/realms/api-gateway/protocol/openid-connect/token"
CLIENT_SECRET = "Y8D4cosVmykLdfSAJQGcchgqG0TadXHm"
CLIENT_ID="edge-agent"

def getDataAccessToken():
    data = {
        "grant_type" : "client_credentials",
        "client_id" : CLIENT_ID,
        "client_secret" : CLIENT_SECRET
    }

    headers = {"Content-Type": "application/x-www-form-urlencoded"}
    r = requests.post( KEYCLOAK_SERVER_URL , data = data, headers = headers, timeout=10)
    r.raise_for_status()
    j = r.json()
    return j["access_token"], j["expires_in"], time.time()

def storeToken(token, ttl, token_acquired_at):
    with open(".token", mode="w", encoding="utf-8") as f:
        f.write(token)
    with open(".ttl", mode="w", encoding="utf-8") as t:
        t.write(str (ttl))
    with open(".token_acquired_at", mode="w", encoding="utf-8") as ta:
        ta.write(str (token_acquired_at))

def checkIfExpiredToken():
    with open(".ttl", mode="r", encoding="utf-8") as t:
        ttl = t.read()
    with open(".token_acquired_at", mode="r", encoding="utf-8") as ta:
        token_acquired_at = ta.read()
    return time.time() > float(token_acquired_at) + int(ttl) - 10 
    

def handleToken():
    if Path(".token").exists(): #if file exists
        if checkIfExpiredToken(): # if expired
            print("Token is expired getting a new one")
            token, ttl, token_acquired_at = getDataAccessToken()
            storeToken(token, ttl, token_acquired_at)
        else:
            print("Already have a valid token")
    else:
        print("Token does not exist getting a new one")
        token, ttl, token_acquired_at = getDataAccessToken()
        storeToken(token, ttl, token_acquired_at)


# ON WINDOWS FIREWALL ENABLE CREATE INBOUND RULE FOR THE PORT KEYCLOAK IS BOUND TO

