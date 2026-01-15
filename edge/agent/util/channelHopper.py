import subprocess
import re
import os

#sudo iw wlan0mon set channel 11
#iwlist wlan0mon channel  
#iwlist wlan0mon channel | grep '(Channel'.*')'
#https://docs.python.org/3/library/subprocess.html

def hopChannel():
    interface = os.getenv("MON_IFACE")

    cmd = ["iwlist", interface, "channel"] #command we want to run
    try:
        out = subprocess.check_output(cmd, text = True)
    except(subprocess.CalledProcessError):
        print("Error running iwlist")

    m = re.search(r"\(Channel\s+(\d+)\)", out, flags=re.IGNORECASE) #regex to extract channel
    channel = int(m.group(1))
    
    if( channel < 13): #2.4GHz has 13 channels
        channel+=1
    else:
        channel = 1

    cmd = ["iw", interface, "set", "channel", str(channel)]

    try:
        subprocess.run(cmd, check = True)
        print("Channel changed to : ", channel)
    except subprocess.CalledProcessError as e:
        print(e)