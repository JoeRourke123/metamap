# API docs

## Login

`curl -v -X POST --data '{"username": "snake", "password": "plane"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/login`

## Signup

`curl -v -X POST --data '{"username": "snake", "password": "plane"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/signup`

## Create post

`curl -v -X POST --data '{"operation": "add", "type": "text", "data": "tums festival", "coordinates": [LAT, LONG], "timestamp" : "UTC_TIME"' --header "Content-Type:application/json" https://metamapp.herokuapp.com/post`
