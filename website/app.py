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
if __name__ == '__main__':
    app.run(debug=True)