from fastapi import FastAPI, HTTPException, Request
import httpx

app = FastAPI()

EXCHANGE_API_URL = 'https://economia.awesomeapi.com.br/json/last/'

@app.get("/exchange/{from_currency}/{to_currency}")
def get_exchange_rate(from_currency, to_currency, request):

    user_id = request.headers.get("id-account") 
    if not user_id:
        raise HTTPException(status_code=400, detail="User ID is required in headers")

    url = f"{EXCHANGE_API_URL}{from_currency.upper()}-{to_currency.upper()}"
    
    try:
        response = httpx.get(url)
        response.raise_for_status()
        data = response.json()
        
        if to_currency.upper() not in data[f"{from_currency.upper()}{to_currency.upper()}"]["codein"]:
            raise HTTPException(status_code=400, detail="Invalid target currency")
        
        return {
            "sell": data[f"{from_currency.upper()}{to_currency.upper()}"]['ask'],  
            "buy": data[f"{from_currency.upper()}{to_currency.upper()}"]['bid'],
            "date": data[f"{from_currency.upper()}{to_currency.upper()}"]['create_date'],
            "id-account": user_id
        }
    except httpx.HTTPError as e:
        raise HTTPException(status_code=500, detail=f"Error fetching exchange rate: {str(e)}")
