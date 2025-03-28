from flask import Flask, render_template, request, redirect, url_for, session

from dotenv import load_dotenv


app = Flask('__name__', template_folder='index')
app.secret_key = 'aiowruiob#BO(!@&B(;ljhuoiadsh'

load_dotenv()

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/login', methods=['GET', 'POST'])
def login():
    return render_template('login.html')


@app.route('/help')
def help():
    return render_template('help.html')

@app.route('/store')
def store():
    return render_template('store.html')

@app.route('/chess')
def chess():
    return render_template('chess.html')

@app.route('/checkers')
def checkers():
    return render_template('checkers.html')

@app.route('/connect4')
def connect4():
    return render_template('connect4.html')

@app.route('/tictactoe')
def tictactoe():
    return render_template('tictactoe.html')

if __name__ == '__main__':
    app.run(debug=True)