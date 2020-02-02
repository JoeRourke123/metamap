import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:metamap/main.dart';
import 'package:provider/provider.dart';

class MyImagePickerState extends State {

  File imageURI;

  Future getImageFromCamera() async {
    imageURI = await ImagePicker.pickImage(source: ImageSource.camera);
  }

  Future getImageFromGallery() async {
    imageURI = await ImagePicker.pickImage(source: ImageSource.gallery);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
            child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Container(
                      margin: EdgeInsets.fromLTRB(0, 30, 0, 20),
                      child: RaisedButton(
                        onPressed: () => getImageFromCamera(),
                        child: Text('Click Here To Select Image From Camera'),
                        textColor: Colors.white,
                        color: Colors.green,
                        padding: EdgeInsets.fromLTRB(12, 12, 12, 12),
                      )),

                  Container(
                      margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
                      child: RaisedButton(
                        onPressed: () => getImageFromGallery(),
                        child: Text('Click Here To Select Image From Gallery'),
                        textColor: Colors.white,
                        color: Colors.green,
                        padding: EdgeInsets.fromLTRB(12, 12, 12, 12),
                      ))
                ]))
    );
  }
}

class NewPage extends StatefulWidget {
  NewPage({Key key}) : super(key: key);

  @override
  _NewState createState() => _NewState();
}

class _NewState extends State<NewPage> {
  final _newKey = GlobalKey<FormState>();
  Map<String, String> data = {
    "type": "text",
    "content": "",
  };

  @override
  Widget build(BuildContext context) {
    Map<String, Widget> currentFormField = {
      "text": TextFormField(
        validator: (value) {
          if (value.isEmpty) {
            return 'Please enter some text';
          }
          return null;
        },
        onChanged: (val) {
          data["content"] = val;
        },
      ),
      "image":
    }

    // TODO: implement build
    return Center(
      child: Column(
        children: <Widget>[
          Text("Create a Post!"),
          Text("Your current coordinates are...(${Provider.of<MetamapState>(context).position.latitude}, ${Provider.of<MetamapState>(context).position.latitude})"),
          Form(
            key: _newKey,
            child: Column(
              children: <Widget>[
                DropdownButtonFormField(
                  items: ["Text", "Image", "Spotify", "YouTube"].map((str) => DropdownMenuItem(child: Text(str), value: str.toLowerCase())).toList(),
                  onChanged: (val) {
                    setState(() {
                      data["type"] = val;
                    });
                  }
                )
              ]
            )
          )
        ]
      )
    );
  }
}