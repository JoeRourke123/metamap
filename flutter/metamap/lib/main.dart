import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:geolocator/geolocator.dart';

import 'package:shared_preferences/shared_preferences.dart';

import 'post.dart';
import 'map.dart';
import 'feed.dart';
import 'signin.dart';
import 'new.dart';

class MetamapState extends ChangeNotifier {
  List<Post> postList = [];
  Position position;
  String cookie;
  BuildContext rootContext;

  MetamapState(BuildContext context) {
    rootContext = context;

    _getPosition().then((pos) async {
      position = pos;
    });

    _getCookie().then((auth) async {
      cookie = auth;
    });
  }

  Future<Position> _getPosition() async {
    final Geolocator geolocator = Geolocator()..forceAndroidLocationManager;
    return await geolocator.getCurrentPosition(desiredAccuracy: LocationAccuracy.best);

  }

  Future<String> _getCookie() async {
    return (await SharedPreferences.getInstance()).get("session");
  }

  Future<bool> hasCookie() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();

    return prefs.containsKey("session");
  }

  void _setCookie(val) async {
    val = val.split("=")[1].split(";")[0];

    (await SharedPreferences.getInstance()).setString("session", val);
  }

  Future<Map<String, String>> signIn(String username, String password) async {
    var apiUrl = Uri.parse('https://metamapp.herokuapp.com/login');
    var client = HttpClient(); // `new` keyword optional

    // 1. Create request
    HttpClientRequest request = await client.postUrl(apiUrl);
    request.headers.add('Content-type','application/json');
    request.headers.add('Accept','application/json');
    // 2. Add payload to request
    var payload = {
      "username": username,
      "password": password
    };
    request.write(json.encode(payload));

    // 3. Send the request
    HttpClientResponse response = await request.close();

    if(response.statusCode == 200) {
      _setCookie(response.headers.value("Set-Cookie"));
      print(cookie);
    }

    // 4. Handle the response
    var resStream = response.transform(Utf8Decoder());
    await for (var data in resStream) {
      print('Received data: $data');
    }
  }

  Future<Map> populatePosts() async {
    var apiUrl = Uri.parse('https://metamapp.herokuapp.com/post');
    var client = HttpClient(); // `new` keyword optional

    // 1. Create request
    HttpClientRequest request = await client.postUrl(apiUrl);
    request.headers.add('Content-type','application/json');
    request.headers.add('Accept','application/json');
    print(cookie);
    request.headers.add('Cookie', 'flask_sess=${cookie}');
    // 2. Add payload to request
    _getPosition();

    var payload = {
      "operation": "get",
      "coordinates": [position.latitude, position.longitude]
    };
    request.write(json.encode(payload));

    // 3. Send the request
    HttpClientResponse response = await request.close();

    Map reply = json.decode(await response.transform(utf8.decoder).join());
    client.close();

    List<Post> posts = (reply["posts"] as List).map((post) {
      return Post(post["type"], post["username"], post["location"]["coordinates"], post["data"], DateTime.parse(post["timestamp"]), post["liked"], post["likes"]);
    }).toList();

    postList = posts;

    return reply;
  }

}

void main() => runApp(
    ChangeNotifierProvider(
        create: (_) => MetamapState(_),
        child: MyApp()
    )
);

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'metamap',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: FutureBuilder(
        future: Provider.of<MetamapState>(context, listen: false).hasCookie(),
        builder: (context, snapshot) {
          if(snapshot.hasData) {
            if(snapshot.data) {
              return MyHomePage();
            } else {
              return SignIn();
            }
          } else {
            return Container();
          }
        }
      ),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  @override
  _MyHomePageState createState() => _MyHomePageState();
}


class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;
  List<Widget> widgetPages = <Widget>[
    FeedPage(),
    MapPage(),
    NewPage(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {

    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text("metamap"),
      ),
      body: Flex(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        children: [widgetPages.elementAt(_selectedIndex)],
        direction: Axis.vertical
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.dashboard),
            title: Text('Feed'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.map),
            title: Text('Map'),
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.add_circle),
            title: Text('New Post'),
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
