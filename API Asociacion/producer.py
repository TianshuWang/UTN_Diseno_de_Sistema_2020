import requests
import json
from resources import LinkEvents


with open(r'events.json') as fp:
    data = json.load(fp)

rp = requests.post('http://127.0.0.1:5000/link/', json=data, params={"vincularPor":'egresos', "criterioOrden" : "montoTotal"})
print(rp.text)