# project: p7
# submitter: aluo7
# partner: none
# hours: 1.5

import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression, LogisticRegression
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler, PolynomialFeatures
from sklearn.cluster import KMeans

class UserPredictor():
    def __init__(self):
        self.model = Pipeline([
            ("pf", PolynomialFeatures(degree=2, include_bias=False)),
            ("std", StandardScaler()),
            ("lr", LogisticRegression(fit_intercept=False, max_iter=100)),
        ])
        self.xcols = ["user_id", "seconds", "age", "past_purchase_amt"]
        
    def fit(self, users_df, logs_df, y_df):
        train = pd.merge(logs_df, users_df, on = "user_id", how = "outer").groupby(by = "user_id").sum("seconds").reset_index()
        train = pd.merge(train, y_df, on = "user_id")
        self.model.fit(train[self.xcols], train["y"])
        
    def predict(self, users_df, logs_df):
        test = pd.merge(logs_df, users_df, on = "user_id", how = "outer").groupby(by = "user_id").sum("seconds").reset_index()
        test["predictions"] = self.model.predict(test[self.xcols])
        return test["predictions"].to_numpy()