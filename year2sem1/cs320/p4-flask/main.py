# project: p4
# submitter: aluo7
# partner: none
# hours: 4

# https://www.kaggle.com/datasets/prashant808/global-internet-speed-2022?resource=download

import pandas as pd
import matplotlib.pyplot as plt
import re, time, json, io
from flask import Flask, request, Response, jsonify

app = Flask(__name__)

ab_counter = 0
@app.route('/')
def home():
    global ab_counter
    global count_orange
    global count_red
    
    if ab_counter >= 10:
        if count_orange > count_red:
            font_color = "orange"
            a_or_b = "A"
        else:
            font_color = "red"
            a_or_b = "B"
    else:
        if ab_counter % 2 == 0:
            font_color = "orange"
            a_or_b = "A"
        else:
            font_color = "red"
            a_or_b = "B"
        ab_counter += 1
            
    with open("index.html") as f:
        html = f.read()
        
    html = html.replace("chartreuse", font_color)
    html = html.replace("to_replace", a_or_b)
    return html

@app.route('/browse.html')
def browse():
    a = pd.read_csv("main.csv")
    html_file = a.to_html()
    
    return "<html><body><h1>Browse here!</h1><p>{}</p></body></html>".format(html_file)

count_orange = 0
count_red = 0
@app.route('/donate.html')
def donate():
    global count_orange
    global count_red
    
    ab_test = request.args.get('from')
    if ab_test == "A":
        count_orange += 1
    if ab_test == "B":
        count_red += 1
        
    return "<html><body><h1>Donate here!</h1><p>Pleasepleasepleaseeeee donate?</p></body></html>"

num_subscribed = 0
@app.route('/email', methods=["POST"])
def email():
    global num_subscribed
    email = str(request.data, "utf-8")
    if len(re.findall(r"^[a-z0-9]+[\._]?[a-z0-9]+[@]\w+[.]\w{2,3}$", email)) > 0: # 1
        num_subscribed += 1
        with open("emails.txt", "a") as f: # open file in append mode
            f.write(email + "\n") # 2
        return jsonify(f"thanks, you're subscriber number {num_subscribed}!")
    return jsonify(f"are you kidding?? at least put a valid email in you loser!") # 3

visitor_tracker = {}
@app.route('/browse.json')
def browse_json():
    global last_visit
    global visitor_tracker
    
    visitor = request.remote_addr
    
    if visitor not in visitor_tracker or time.time() - visitor_tracker[visitor] >= 60:
        visitor_tracker[visitor] = time.time()
        a = pd.read_csv("main.csv")
        a = a.to_dict(orient = "index")
        return jsonify(a)
    else:
        return Response("wait at least one minute!", status=429, headers={"Retry-After": 60})
    
@app.route('/visitors.json')
def visitors_json():
    global visitor_tracker
    return Response(visitor_tracker.keys())

@app.route('/plot1.svg')
def plot1():
    query = request.args.get('type')
    if query == "bar":
        df = pd.read_csv("main.csv")

        fig, ax = plt.subplots(figsize=(3,2))
        pd.Series(df["mobile"]).hist()
        ax.set_ylabel("Mobile")
        ax.set_ylabel("Country #")
        plt.tight_layout()

        f = io.StringIO() # fake "file" object
        fig.savefig(f, format="svg")
        plt.close() # closes most recent fig
        return Response(f.getvalue(),
                              headers={"Content-Type": "image/svg+xml"})
    else:
        df = pd.read_csv("main.csv")

        fig, ax = plt.subplots(figsize=(3,2))
        pd.Series(df["mobile"]).plot()
        ax.set_ylabel("Mobile")
        ax.set_xlabel("Country #")
        plt.tight_layout()

        f = io.StringIO() # fake "file" object
        fig.savefig(f, format="svg")
        plt.close() # closes most recent fig
        return Response(f.getvalue(),
                              headers={"Content-Type": "image/svg+xml"})

@app.route('/plot2.svg')
def plot2():
    df = pd.read_csv("main.csv")
    
    fig, ax = plt.subplots(figsize=(3,2))
    pd.Series(df["broadband"]).plot()
    ax.set_ylabel("broadband")
    ax.set_xlabel("Country #")
    plt.tight_layout()
    
    f = io.StringIO() # fake "file" object
    fig.savefig(f, format="svg")
    plt.close() # closes most recent fig
    return Response(f.getvalue(),
                          headers={"Content-Type": "image/svg+xml"})

if __name__ == '__main__':
    app.run(host="0.0.0.0", debug=True, threaded=False) # don't change this line!

# NOTE: app.run never returns (it runs for ever, unless you kill the process)
# Thus, don't define any functions after the app.run call, because it will
# never get that far.