from fastapi import FastAPI, HTTPException, Request
import httpx

app = FastAPI()

EXCHANGE_API_URL = 'https://economia.awesomeapi.com.br/json/last/'

@app.get("/exchange/{from_currency}/{to_currency}")
def get_exchange_rate(from_currency: str, to_currency: str, request: Request): 
    user_id = request.headers.get("id-account") 
    if not user_id:
        raise HTTPException(status_code=400, detail="User ID is required in headers")

    url = f"{EXCHANGE_API_URL}{from_currency.upper()}-{to_currency.upper()}"
    
    try:
        response = httpx.get(url)
        response.raise_for_status()
        data = response.json()
        
        pair_key = f"{from_currency.upper()}{to_currency.upper()}"
        if pair_key not in data:
            raise HTTPException(status_code=400, detail="Invalid currency pair")
        
        return {
            "sell": data[pair_key]['ask'],  
            "buy": data[pair_key]['bid'],
            "date": data[pair_key]['create_date'],
            "id-account": user_id
        }
    except httpx.HTTPError as e:
        raise HTTPException(status_code=500, detail=f"Error fetching exchange rate: {str(e)}")
