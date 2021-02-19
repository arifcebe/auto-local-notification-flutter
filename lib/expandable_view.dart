import 'package:flutter/material.dart';

class ExpandableListView extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _ExpandableListViewState();
  }
}

class _ExpandableListViewState extends State<ExpandableListView> {
  List<String> months = [
    "Januari",
    "Februari",
    "Maret",
    "April",
    "Mei",
    "Juni",
  ];

  List<String> days = [
    "Legi",
    "Pahing",
    "Pon",
    "Wage",
    "Kliwon",
  ];

  final bool isExpanded = true;
  final bool isNotExpanded = false;
  int selectedExpanded = 0;
  GlobalKey key = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Oke"),
      ),
      body: _buildBody(),
    );
  }

  Widget _buildBody() {
    return Container(
      child: ListView.builder(
          itemCount: months.length,
          itemBuilder: (context, index) {
            String month = months[index];

            return ExpansionTile(
              key: key,
              title: Text(month),
              onExpansionChanged: (changed) {

                if (changed) {
                  setState(() {
                    selectedExpanded = index;
                  });
                } else {

                }
                print("index $index $changed $selectedExpanded");
              },
              maintainState: true,
              initiallyExpanded: index == selectedExpanded ? true : false,
              children: days
                  .map((day) => Container(
                        child: Text(day),
                      ))
                  .toList(),
            );
          }),
    );
  }
}
