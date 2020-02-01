from flask_restful import Resource, Api

class Login(Resource):
    # corresponds to the GET request. 
    # this function is called whenever there 
    # is a GET request for this resource 
    def get(self): 
  
        return {'message': 'hello world'}
  
    # Corresponds to POST request 
    def post(self): 
          
        data = request.get_json()     # status code 
        return {'data': data}, 201

# class Signup:
#     pass