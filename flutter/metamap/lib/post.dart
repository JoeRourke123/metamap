import 'dart:math';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:url_launcher/url_launcher.dart';

import 'main.dart';

class PostWidget extends StatefulWidget {
  PostWidget({Key key, this.post, this.userCoords}) : super(key: key);

  List<double> userCoords;
  Post post;

  _PostState createState() => _PostState();
}
class _PostState extends State<PostWidget> {
  List<Color> randomColour = [[Colors.deepOrange, Colors.orange], [Colors.green, Colors.greenAccent], [Colors.purple, Colors.deepPurple],
    [Colors.red, Colors.deepOrange], [Colors.blue, Colors.lightBlueAccent]][new Random().nextInt(5)];

  Widget body;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    switch (widget.post.type) {
      case "text":
        body = Text(
            widget.post.content,
            style: TextStyle(
                fontSize: 22,
                color: Colors.white
            )
        );
        break;
      case "youtube":
        body = RaisedButton(
          child: Text("Open Youtube Link"),
          color: Colors.white,
          textColor: randomColour[0],
          onPressed: () async {
            if (await canLaunch(widget.post.content)) {
              await launch(widget.post.content);
            } else {
              throw 'Could not launch';
            }
          },
        );
        break;
      case "spotify":
        body = RaisedButton(
            child: Text("Open Spotify Link"),
            color: Colors.white,
            textColor: randomColour[0],
            onPressed: () async {
              if (await canLaunch(widget.post.content)) {
                await launch(widget.post.content);
              } else {
                throw 'Could not launch';
              }
            }
        );
        break;
    }
  }


  @override
  Widget build(BuildContext context) {
    return GestureDetector(child: Container(
      padding: EdgeInsets.all(32.0),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.all(Radius.circular(20.0)),
          gradient: new LinearGradient(
            colors: randomColour,
          ),
          boxShadow: [
            BoxShadow(
              color: Color.fromARGB(50, 100, 100, 100),
              blurRadius: 10.0, // has the effect of softening the shadow
              spreadRadius: 1.0, // has the effect of extending the shadow
              offset: Offset(0.0, 5.0),
            )
          ]),
      child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
                "from @" + widget.post.username,
                style: TextStyle(
                  color: Colors.white70,
                )
            ),
            Padding(
                padding: EdgeInsets.symmetric(vertical: 5)
            ),
            body,
            Padding(
                padding: EdgeInsets.symmetric(vertical: 5)
            ),
            Text(
                "Posted " + widget.post.getDistance(widget.userCoords[0], widget.userCoords[1]).toString() + "km from you, " + ((DateTime.now().difference(widget.post.date).inHours < 1) ? "< 1" : DateTime.now().difference(widget.post.date).inHours.toString()) + " hours ago",
                style: TextStyle(
                    color: Colors.white70
                )
            ),
            Row(
                children: [
                  Text((widget.post.liked) ? "You and " + (widget.post.likes.length + 1).toString() + " others have liked this" : (widget.post.likes.length.toString() + " people have liked this")
                  ,style: TextStyle(color: Colors.black54)),
                ]
            )
          ]
      ),
    ),
        onDoubleTap: () async {
          await Provider.of<MetamapState>(context).likePost(widget.post);

          setState(() {
            widget.post.liked = true;
          });
        });
  }
}

class Post {
  String type;
  String username;
  List coordinates;
  String content;
  DateTime date;

  bool liked;
  List likes;

  String postID;

  Post(this.type, this.username, this.coordinates, this.content, this.date, this.liked, this.likes, this.postID);

  String getDistance(double lat, double lon) {
    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat-coordinates[0]);  // deg2rad below
    var dLon = deg2rad(lon-coordinates[1]);
    var a =
        sin(dLat/2) * sin(dLat/2) +
            cos(deg2rad(coordinates[0])) * cos(deg2rad(lat)) *
                sin(dLon/2) * sin(dLon/2)
    ;
    var c = 2 * atan2(sqrt(a), sqrt(1-a));
    var d = R * c; // Distance in km
    return d.toStringAsFixed(1);
  }

  double deg2rad(deg) {
    return deg * (pi/180);
  }

  String getType() => type;
  String getUser() => username;
  String getContent() => content;

  Widget generateWidget(double latitude, double longitude) {
    return PostWidget(post: this, userCoords:[latitude, longitude]);
  }
}