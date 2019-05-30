from flask import Flask, request, Response


class V0Api:

    app = Flask(__name__)
    
    @app.route('/accounts', methods=['POST'])
    def get_account(self):
        # add_entry({'email':request.values['email'], 'password':request.values['password'], 'nickname':request.values['nickname']})
        return Response("CREATED :)", status=201, mimetype='application/json')

    @app.route('/accounts', methods=['GET'])
    def create_account(self):
        return Response("OKAY :D::D", status=200)

    def run(self, host='0.0.0.0', debug=False):
        self.app.run(host=host, debug=debug)

