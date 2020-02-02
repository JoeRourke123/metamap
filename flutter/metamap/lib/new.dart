import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:metamap/main.dart';
import 'package:provider/provider.dart';

class MyImagePicker extends StatefulWidget {
  @override
  MyImagePickerState createState() => MyImagePickerState();
}

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
    return Row(mainAxisAlignment: MainAxisAlignment.center, children: <Widget>[
      Container(
          margin: EdgeInsets.fromLTRB(0, 30, 0, 20),
          child: RaisedButton(
            onPressed: () => getImageFromCamera(),
            child: Text('From Camera'),
            textColor: Colors.white,
            color: Colors.green,
            padding: EdgeInsets.fromLTRB(12, 12, 12, 12),
          )),
      Container(
          margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
          child: RaisedButton(
            onPressed: () => getImageFromGallery(),
            child: Text('From Gallery'),
            textColor: Colors.white,
            color: Colors.green,
            padding: EdgeInsets.fromLTRB(12, 12, 12, 12),
          ))
    ]);
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
    Map<String, String> currentFormField = {
      "text": "What would you like to say?",
      "spotify": "Embed a Spotify URL",
      "youtube": "Embed a Youtube URL"
    };

    // TODO: implement build
    return Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: <Widget>[
              Text("Create a New Post", style: TextStyle(fontSize: 24)),
              Text(
                  "Your current coordinates...\n(${Provider.of<MetamapState>(context).position.latitude}, ${Provider.of<MetamapState>(context).position.longitude})"),
              Form(
                  key: _newKey,
                  child: Column(children: [
                    Padding(
                        child: DropdownButtonFormField(
                            items: ["Text", "YouTube", "Spotify"]
                                .map((type) => DropdownMenuItem(
                                    value: type.toLowerCase(),
                                    child: Text(type)))
                                .toList(),
                            hint: Text(
                                '${data["type"][0].toUpperCase()}${data["type"].substring(1)}'),
                            onChanged: (val) {
                              setState(() {
                                data["type"] = val;
                              });
                            }),
                        padding:
                            EdgeInsets.symmetric(vertical: 10, horizontal: 30)),
                    Padding(
                      padding: EdgeInsets.symmetric(vertical: 10, horizontal: 30),
                      child: TextFormField(
                        validator: (value) {
                          if (value.isEmpty) {
                            return 'Please enter some text';
                          }
                          return null;
                        },
                        onChanged: (val) {
                          data["content"] = val;
                        },
                        decoration: InputDecoration(
                          labelText: currentFormField[data["type"]],
                        ),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.symmetric(vertical: 10),
                      child: Consumer<MetamapState>(
                        builder: (context, state, x) {
                          return RaisedButton(
                            child: Text("Add"),
                            onPressed: () {
                              state.addPost(
                                data
                              );
                            },
                          );
                        }
                      )
                    )
                  ]))
            ]);
  }
}
