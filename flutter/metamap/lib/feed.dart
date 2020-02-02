import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:metamap/signin.dart';
import 'package:provider/provider.dart';

import 'main.dart';

class FeedPage extends StatefulWidget {
  FeedPage({Key key}) : super(key: key);

  @override
  _FeedPageState createState() => _FeedPageState();
}

class _FeedPageState extends State<FeedPage> {
  GlobalKey feedKey;

  @override
  Widget build(BuildContext context) {
    return Expanded(child: Consumer<MetamapState>(builder: (context, state, x) {
      return FutureBuilder(
        future: state.populatePosts(),
        builder: (context, snapshot) {
          if(snapshot.hasData) {
            Map data = snapshot.data as Map;


            return Text("yo");
          }
        }
      );
    })
    );
  }
}
