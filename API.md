# API docs

## Login

`curl -v -X POST --data '{"username": "snake", "password": "plane"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/login`

## Signup

`curl -v -X POST --data '{"username": "snake", "password": "plane"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/signup`

## Create post

`curl -v -X POST --data '{"type": "text", "data": "tums festival"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/post`