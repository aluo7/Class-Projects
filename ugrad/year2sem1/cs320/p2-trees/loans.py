import pandas as pd
import numpy as np
import json
from zipfile import ZipFile
from io import TextIOWrapper
import csv

race_lookup = {
    "1": "American Indian or Alaska Native",
    "2": "Asian",
    "21": "Asian Indian",
    "22": "Chinese",
    "23": "Filipino",
    "24": "Japanese",
    "25": "Korean",
    "26": "Vietnamese",
    "27": "Other Asian",
    "3": "Black or African American",
    "4": "Native Hawaiian or Other Pacific Islander",
    "41": "Native Hawaiian",
    "42": "Guamanian or Chamorro",
    "43": "Samoan",
    "44": "Other Pacific Islander",
    "5": "White",
}

class Applicant:
    def __init__(self, age, race):
        self.age = age
        self.race = set()
        for r in race:
            if r in race_lookup.keys():
                self.race.add(race_lookup.get(r))
                
    def __repr__(self):
        return f"Applicant('{self.age}', {sorted(x for x in self.race)})"
    
    def lower_age(self):
        if ">" in self.age:
            self.age = self.age.replace(">", "")
        elif "<" in self.age:
            self.age = self.age.replace("<", "")
        return int(self.age.split("-")[0])
    
    def __lt__(self, other):
        return self.lower_age() < other.lower_age()
    
class Loan:
    def __init__(self, values):
        attr = ["loan_amount", "property_value", "interest_rate"]
        for x in attr:
            if values[x] == "NA" or values[x] == "Exempt":
                values[x] = -1
                
        self.loan_amount = float(values["loan_amount"])
        self.property_value = float(values["property_value"])
        self.interest_rate = float(values["interest_rate"])
    
        applicant_race = []
        
        for i in range(5):
            if values[f"applicant_race-{i+1}"]:
                applicant_race.append(values[f"applicant_race-{i+1}"])
                            
        self.applicants = [Applicant(values["applicant_age"], applicant_race)]
                            
        if not values["co-applicant_age"] == "9999":   
            coapplicant_race = []
            
            for i in range(5):
                if values[f"co-applicant_race-{i+1}"]:
                    coapplicant_race.append(values[f"co-applicant_race-{i+1}"])
            
            self.applicants.append(Applicant(values["co-applicant_age"], coapplicant_race))
            
    def __str__(self):
        return f"<Loan: {self.interest_rate}% on ${self.property_value} with {len(self.applicants)} applicant(s)>"
    
    def __repr__(self):
        return f"<Loan: {self.interest_rate}% on ${self.property_value} with {len(self.applicants)} applicant(s)>"
    
    def yearly_amounts(self, yearly_payment):
        if self.interest_rate and self.loan_amount > 0:
            amt = self.loan_amount

            while amt > 0:
                yield amt
                amt += (0.01*self.interest_rate)*amt
                amt -= yearly_payment
                
class Bank:
    def __init__(self, name):
        with open("banks.json") as f:
            banks = json.load(f)
            
            for x in banks:
                if x["name"] == name:
                    self.name = name
                    self.lei = x["lei"]
                    
        self.loans = []
        with ZipFile("wi.zip") as zf:
            with zf.open("wi.csv", "r") as f:
                tio = TextIOWrapper(f)
                reader = csv.DictReader(tio)
                for row in reader:
                    if row["lei"] == self.lei:
                        self.loans.append(Loan(row))
    
    def __getitem__(self, index):
        return self.loans[index]
    
    def __len__(self):
        return len(self.loans)