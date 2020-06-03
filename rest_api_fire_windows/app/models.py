# -*- coding: utf-8 -*-
"""
Created on Thu Apr 16 10:30:38 2020

@author: cypri
"""


from app import db

class Incendies(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    position_x = db.Column(db.Float, index=True)
    position_y = db.Column(db.Float, index=True)
    intensite = db.Column(db.Integer, index=True)
    categorie = db.Column(db.String(10), index=True)
    detecte = db.Column(db.Integer, index=True)
    prise_en_charge = db.Column(db.Integer, index=True)
    

    def __repr__(self):
        return '<Incendies {}>'.format(self.position_x)  


#class Incendies_detectes(db.Model):
 #   id = db.Column(db.Integer, primary_key=True)
  #  position_x = db.Column(db.Float, index=True)
   # position_y = db.Column(db.Float, index=True)
    #intensite = db.Column(db.Integer, index=True)
#    categorie = db.Column(db.String(10), index=True)
 #   sonde_qui_a_detecte_en_dernier = db.Column(db.Integer, index=True)
  #  prise_en_charge = db.Column(db.Integer, index=True)

   # def __repr__(self):
    #    return '<Incendies {}>'.format(self.position_x)  