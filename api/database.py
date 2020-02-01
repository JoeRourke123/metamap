import os
from pymongo import MongoClient

class Database:
    def __init__(self):
        self.client = MongoClient(os.environ["MONGODB_URI"], retryWrites=False)
        self.db = self.client["heroku_9pm8qrb1"]
        self.posts = self.db.posts
        self.users = self.db.users

    def get_users(self):
        return self.users
    
    def get_posts(self):
        return self.posts

    def get_client(self):
        return self.client