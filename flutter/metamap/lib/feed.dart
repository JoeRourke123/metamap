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
  void initState() {
    super.initState();
    Consumer<MetamapState>(builder: (context, state, x) {
      int temp = 0;
      if(state.index != null) {
        temp = state.postList.indexOf(state.index, 0);
      }
      state.scrollController.animateTo(MediaQuery.of(context).size.height*temp, duration: Duration(seconds: 1), curve: Curves.fastOutSlowIn);
    });
  }
  @override
  Widget build(BuildContext context) {
    return Expanded(child: Consumer<MetamapState>(builder: (context, state, x) {
      return FutureBuilder(
        future: state.populatePosts(),
        builder: (context, snapshot) {
          if(snapshot.hasData) {
            List<Widget> widgetList = state.postList.map((post) => post.generateWidget(state.position.latitude, state.position.longitude)).toList();

            return ListView.builder(
              itemCount: widgetList.length,
              itemBuilder: (context, index) => Padding(padding: EdgeInsets.all(20), child: widgetList[index]),
              controller: state.scrollController,
            );
          } else {
            print(snapshot.error);
            return Text("Something went wrong");
          }
        }
      );
    })
    );
  }
}
