
import feedparser
from flask import Flask, render_template

app = Flask(__name__)

RSS_FEED_URL = "https://static.cricinfo.com/rss/livescores.xml"

@app.route("/")
def get_scores():
    feed = feedparser.parse(RSS_FEED_URL)
    return render_template("index.html", entries=feed.entries)

if __name__ == "__main__":
    app.run(debug=True)
