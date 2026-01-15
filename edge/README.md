after you install the service every time the raspberrypi boots:
    start.sh is called:
        start.sh checks if the wireless interface is on monitor mode. If not it activates monitor mode
        before exiting a python script is called
    
    Python script:
        wifi.py:
            each time a probe request is sniffed a function from log.py is called. This function logs the data into a json file (plain text for now)
        After the json is created another function from log.py is called. Each mac is now hashed and salted 
        Continuing a channelHopper.py function is called which changes the channel of the wirelles interface
        Finnaly a POST is sent to the API with the collected data
        Repeats after that