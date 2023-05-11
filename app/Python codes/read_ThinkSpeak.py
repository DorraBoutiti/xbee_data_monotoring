import requests
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# Remplacez <channel_id> et <read_api_key> par les valeurs de votre canal ThingSpeak
channel_id = "****************"
read_api_key = "*************"
# URL de l'API ThingSpeak pour récupérer les données
url = f"https://api.thingspeak.com/channels/{channel_id}/feeds.json?api_key={read_api_key}"
# Récupération des données
response = requests.get(url)
data = response.json()["feeds"]
# Convertir les données en DataFrame Pandas
df = pd.DataFrame(data)
# Convertir les colonnes de temps en format datetime
df["created_at"] = pd.to_datetime(df["created_at"])
print(type(df["created_at"][2]))
# Convertir les valeurs en float
df["field1"] = df["field1"].astype(float)
print(type(df["field1"][2]))
# afficher valeurs
#for i in range(len(df["field1"])):
#    print(df["field1"][i], df["created_at"][i])
plt.plot(df["created_at"], df["field1"])
# Add labels to the chart
plt.title('Xbee Values')
plt.xlabel('Date')
plt.ylabel('Value')
# Show the chart
plt.show()   
plt.close()
    
# Calcul des statistiques
print("Moyennes : ")
print(df.mean())
print("\n")
print("Médianes : ")
print(df.median())
print("\n")
