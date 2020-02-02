# API docs

Need to make posts have post_ID.

## Login

`curl -v -X POST --data '{"username": "snake", "password": "plane"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/login`

## Signup

`curl -v -X POST --data '{"username": "snake", "password": "plane"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/signup`

## Create post

`curl -v -X POST -b 'flask_sess=cdb382ac-48ac-43c9-8ba2-b1007308bd14' --data '{"operation": "add", "type": "text", "data": "tums festival", "coordinates": [51.424316, -0.560857]}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/post`

## Get posts

`curl -v -X POST --data '{"operation": "get", "coordinates": [51.424217, -0.560667]}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/post`

## Like & Unlike posts

`curl -v -X POST -b 'flask_sess=cdb382ac-48ac-43c9-8ba2-b1007308bd14' --data '{"post_id": "Ff6wU7L8"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/like`

`curl -v -X POST -b 'flask_sess=cdb382ac-48ac-43c9-8ba2-b1007308bd14' --data '{"post_id": "Ff6wU7L8"}' --header "Content-Type:application/json" https://metamapp.herokuapp.com/unlike`

## Upload image

`curl -v -b 'flask_sess=cdb382ac-48ac-43c9-8ba2-b1007308bd14' -F "data=@pic.jpg" http://127.0.0.1:5000/upload`
