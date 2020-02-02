import 'dart:math';
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Post {
  String type;
  String username;
  List<double> coordinates;
  String content;
  DateTime date;

  bool liked;
  List<String> likes;

  Post(this.type, this.username, this.coordinates, this.content, this.date, this.liked, this.likes);

  int getDistance(double lat, double lon) {
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
    return d.round();
  }

  double deg2rad(deg) {
    return deg * (pi/180);
  }

  String getType() => type;
  String getUser() => username;
  String getContent() => content;

  Widget generateWidget() {
    List<Color> randomColour = [[Colors.deepOrange, Colors.orange], [Colors.green, Colors.greenAccent], [Colors.purple, Colors.deepPurple],
      [Colors.red, Colors.deepOrange], [Colors.blue, Colors.lightBlueAccent]][new Random().nextInt(5)];

    Widget body;

    if(type != "text") {
      randomColour = [Colors.white60, Colors.white70];
    }

    switch(type) {
      case "text":
        body = Text(
          content,
          style: TextStyle(
            fontSize: 18,
            color: Colors.white
          )
        );
        break;
    }

    return Container(
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
              "from @" + username
            ),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 5)
            ),
          ]
      ),
    );
  }
}