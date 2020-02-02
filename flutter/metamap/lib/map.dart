import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:metamap/post.dart';
import 'package:provider/provider.dart';

import 'main.dart';

class MapPage extends StatefulWidget {
  MapPage({Key key, this.nav}) : super(key: key);
  final State<MyHomePage> nav;

  @override
  _MapState createState() => _MapState();
}

class _MapState extends State<MapPage> {
  GoogleMapController mapController;

  Set<Marker> markers;

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<MetamapState>(
      builder: (context, state, x) {
        return SizedBox(width: MediaQuery.of(context).size.width, height: MediaQuery.of(context).size.height-136,child: GoogleMap(
            mapType: MapType.normal,
            onMapCreated: _onMapCreated,
            initialCameraPosition: CameraPosition(
              target: LatLng(state.position.latitude, state.position.longitude),
              zoom: 11.0,
            ),
            markers: state.postList.map((post) {return new Marker(
                markerId: MarkerId(post.date.toString()),
                infoWindow: InfoWindow (
            title: "Post from ${post.username}",
                  snippet: "${post.content}"
            ),
                position: LatLng(post.coordinates[0],
                    post.coordinates[1]),
              onTap: () {
                  state.index = post;
              widget.nav.setState(() {
                state.selectedIndex = 0;
              });
            });
            }).toSet()
        )
        );
      });
  }
}