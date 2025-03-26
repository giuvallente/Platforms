
FROM python:3.12

COPY ./app /app

RUN pip install --no-cache-dir --upgrade -r /app/requirements.txt

CMD ["fastapi", "run", "/app/main.py", "--port", "80"]