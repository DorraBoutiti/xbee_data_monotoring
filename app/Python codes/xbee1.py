import serial
from datetime import datetime
import requests
import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

BASE_URL = "https://api.thingspeak.com/update.json"
WRITE_API_KEY = "****************"

try:
    ser = serial.Serial('COM3', 9600, timeout=1)
    # Replace 'COM6' with the appropriate COM port number and 9600 with the baud rate used
    
    while True:
        # Read data from the serial port
        data = ser.readline().decode('latin-1').strip()        
        
        if data:
            # Convert each character to decimal
            decimal_data = [ord(char) for char in data]
            tempreture = decimal_data[6]
            real_one = (tempreture)/ 10
             # Display the decimal values
            print("Received data:",decimal_data)
            print("Received data (decimal):",tempreture )
            print("Temp: ", real_one)
            
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
