import serial
from datetime import datetime
import requests
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
from winotify import Notification, audio
import time
BASE_URL = "https://api.thingspeak.com/update.json"
WRITE_API_KEY = "*****************"
from twilio.rest import Client

# Your Account Sid and Auth Token from twilio.com/console
account_sid = '**********************'
auth_token = '******************'
client = Client(account_sid, auth_token)





try:
    ser = serial.Serial('COM3', 9600, timeout=1)  
    
    while True:
        # Read data from the serial port
        data = ser.readline().decode('latin-1')        
        
        if data:
            # Convert each character to decimal
            decimal_data = [ord(char) for char in data]
            tempreture = decimal_data[-1]
            real_one = (tempreture)/ 10
             # Display the decimal values
            print("Received data:",decimal_data)
            print("Received data (decimal):",tempreture )
            print("Temp: ", real_one)
            if(real_one < 18):
               toaster = Notification(app_id="Xbee Monotoring", title="Tempreture LOW", msg="Tempreture lower than 18", duration="long", icon="C:\\Users\\lenovo\\Downloads\\xbee.png")
               toaster.set_audio(audio.LoopingAlarm6, loop=False)
               toaster.show()
               message = client.messages \
                .create(
                     body="LOW Tempreture",
                     from_='+12705155049',
                     to='+21651086688'
                 )
               print(message.sid)

            elif (real_one > 30):
                toaster = Notification(app_id="Xbee Monotoring", title="Tempreture HIGH", msg="Tempreture higher than 30", duration="long", icon="C:\\Users\\lenovo\\Downloads\\xbee.png")
                toaster.set_audio(audio.LoopingAlarm10, loop=True)
                toaster.add_actions(label="View", launch="https://thingspeak.com/channels/2136774")
                toaster.show()
                message = client.messages \
                .create(
                     body="HIGH Tempreture",
                     from_='+12705155049',
                     to='+21651086688'
                 )
                print(message.sid)

           
            data = {
                "api_key": WRITE_API_KEY,
                "field1": real_one,                
                "created_at": datetime.now().strftime("%Y-%m-%dT%H:%M:%SZ")
            }
            
            response = requests.post(BASE_URL, json=data)
            
            if response.status_code == 200:
                print("Data sent to ThingSpeak successfully.")
            else:
                print("Error sending data to ThingSpeak.")
         
           
         
except serial.SerialException as e:
    print("Error opening the serial port:", str(e))
