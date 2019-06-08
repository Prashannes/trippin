from flask import Flask, request, Response, Blueprint
from model import User


class EndpointAction(object):

    def __init__(self, action, name):
        self.action = action
        self.response = Response(status=200, headers={})

    def __call__(self, *args):
        self.action()
        return self.response


class FlaskAppWrapper(object):
    app = None

    def __init__(self, name):
        self.app = Flask(name)

    def run(self, host, port, debug):
        self.app.run(host=host, port=port, debug=debug)

    def add_endpoint(self, endpoint=None, handler=None, method='GET'):
        self.app.add_url_rule(rule=endpoint, view_func=EndpointAction(handler), methods=[method]) 


class V0Api:

    def __init__(self, accountManager):
        self.app = FlaskAppWrapper(__name__)
        self.app.add_endpoint(endpoint='/accounts', handler=self.create_account, method='POST')
        #self.app.add_endpoint(endpoint='/accounts', handler=self.get_account, method='GET')
        self.accountManager = accountManager

    def create_account(self):
        user = User.fromDict(request.values)
        self.accountManager.create_account(user=user)
        savedUser = self.accountManager.create_account(user=user)
        if savedUser is not None:
            return Response("CREATED", status=201, mimetype='application/json')
        return Response("Error", status=500)

        # add_entry({'email':request.values['email'], 'password':request.values['password'], 'nickname':request.values['nickname']})

    def get_account(self):
        return Response("OKAY :D::D", status=200)
   

    def run(self, host='0.0.0.0', port=5000, debug=False):
        self.app.run(host=host, port=port, debug=debug)


