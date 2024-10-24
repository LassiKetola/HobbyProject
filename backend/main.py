from typing import Union
from fastapi import FastAPI
from motor.motor_asyncio import AsyncIOMotorClient
from pydantic import BaseModel
from bson import ObjectId

MONGODB_URL = "mongodb://127.0.0.1:27017"

client = AsyncIOMotorClient(MONGODB_URL)
db = client["recipes"]
collection = db["posts"]

app = FastAPI()

class Recipe(BaseModel):
    name: str
    cookingTime: str

def recipe_helper(recipe):
    return {
        "name": recipe["name"],
        "cookingTime": recipe["cookingTime"]
    }

@app.on_event("shutdown")
async def shutdown_event():
    client.close()

@app.get("/get")
async def read_root():
    try:
        cursor = collection.find()
        recipe_list = await cursor.to_list(length = 20)
        return [Recipe(**recipe) for recipe in recipe_list]
    except Exception as e:
        return {"error": str(e)}

@app.post("/recipes")
async def create_item(recipe: Recipe):
    recipe_dict = recipe.dict()
    collection.insert_one(recipe_dict)
