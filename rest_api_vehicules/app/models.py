# -*- coding: utf-8 -*-
"""
Created on Thu Apr 16 10:30:38 2020

@author: cypri
"""


from app import db

class Vehicules(db.Model): 

    id = db.Column(db.Integer, primary_key=True)
    position_x = db.Column(db.Float, index=True)
    position_y = db.Column(db.Float, index=True)
    type_vehicule = db.Column(db.String(20), index=True)
    type_produit = db.Column(db.String(20), index=True)
    produit = db.Column(db.Float, index=True)
    carburant = db.Column(db.Float, index=True)
    caserne = db.Column(db.Integer, index=True)
    
    def __repr__(self):
        return '<Vehicules {}>'.format(self.position_x)  

class Personnel(db.Model):

    id = db.Column(db.Integer, primary_key=True)
    categorie_personnel = db.Column(db.String(10), index = True)
    vehicule = db.Column(db.Integer, index = True)
    experience = db.Column(db.Float, index=True)
    caserne = db.Column(db.Integer, index=True)

    def __repr__(self):
        return '<Personnel {}>'.format(self.categorie_personnel)

class Caserne(db.Model):

    id = db.Column(db.Integer, primary_key=True)
    position_x = db.Column(db.Float, index = True)
    position_y = db.Column(db.Float, index = True)

    def __repr__(self):
        return '<Caserne {}>'.format(self.position_x)

class Sonde(db.Model):

    id = db.Column(db.Integer, primary_key=True)
    position_x = db.Column(db.Float, index = True)
    position_y = db.Column(db.Float, index = True)
    etat = db.Column(db.Float, index = True)
    alarme = db.Column(db.Integer, index = True)

    def __repr__(self):
        return '<Sonde {}>'.format(self.position_x)


