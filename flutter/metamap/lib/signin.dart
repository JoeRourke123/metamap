import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:metamap/main.dart';
import 'package:provider/provider.dart';

class SignIn extends StatefulWidget {
  SignIn({Key key}) : super(key: key);

  @override
  _SignInState createState() => _SignInState();
}

class _SignInState extends State<SignIn> {
  final _formKey = GlobalKey<FormState>();
  Map<String, String> data = {
    "username": "",
    "password": ""
  };

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
            child: Column(children: [
              Text("metamap"),
              Form(
                  key: _formKey,
                  child: Column(children: <Widget>[
                    TextFormField(
                      validator: (value) {
                        if (value.isEmpty) {
                          return 'Please enter some text';
                        }
                        return null;
                      },
                      onChanged: (val) {
                        data["username"] = val;
                      },
                    ),
                    TextFormField(
                      validator: (value) {
                        if (value.isEmpty) {
                          return 'Please enter some text';
                        }
                        return null;
                      },
                      onChanged: (val) {
                        data["password"] = val;
                      },
                      obscureText: true,
                    ),
                    Consumer<MetamapState>(
                        builder: (context, state, x) {
                          return RaisedButton(
                            onPressed: () {
                              // Validate returns true if the form is valid, otherwise false.
                              if (_formKey.currentState.validate()) {
                                // If the form is valid, display a snackbar. In the real world,
                                // you'd often call a server or save the information in a database.
                                _formKey.currentState.save();
                                state.signIn(
                                  data["username"],
                                  data["password"]
                                );
                              }
                            },
                            child: Text('Submit'),
                          );
                        }
                    )
                  ]))
            ])));
  }
}
