from flask import Flask, request, jsonify

from main import chatWithBot

app = Flask(__name__)


@app.route('/chat', methods=['GET', 'POST'])
def chatBot():
    chatInput = request.form['chatInput']
    return (chatWithBot(chatInput))


if __name__ == '__main__':
    app.run(host='192.168.1.4', debug=True)