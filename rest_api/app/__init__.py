# -*- coding: utf-8 -*-
"""
Created on Thu Apr 16 10:03:58 2020

@author: cypri
"""


from flask import Flask
from config import Config
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate

app = Flask(__name__)
app.config.from_object(Config)
db = SQLAlchemy(app)
migrate = Migrate(app, db)

from app import routes, models