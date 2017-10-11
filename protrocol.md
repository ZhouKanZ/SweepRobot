| Service Name(服务名称)       |Service content(服务内容)        |
| ------------- |:-------------|
| gpsbot_navigation/execute_nav_task      | int32 map_id </br>string map_name </br>int32 task_id</br>int32 rate </br></br>int32 type </br>---float64[] data </br>string errormsg</br>bool successed </br>|
| gpsbot_navigation/edit_nav_task     | int32 map_id</br>string map_name</br>int32 type  1 删除 2 添加 3更新</br>int32[] nav_id</br>int32 task_id</br>---float64[] data</br>string errormsg</br>bool successed      |
| operation/edit_wall_set | basic_msgs/edit_wall edit_wall</br>int32 mapid</br>string mapname</br>int32[] wall_id</br>---float64[] data</br>string errormsg</br>bool successed    |
| gpsbot_navigation/nav_flag | int32 map_id</br>string map_name</br>int32 nav_flag</br>basic_msgs/gridpose gridpose</br> &ensp;&ensp;float64 x</br> &ensp;&ensp;float64 y</br> &ensp;&ensp;float64 angle</br>---</br>float64[] data</br>string errormsg</br>bool successed |


| topic(话题名称)  | topic(话题type) |topic内容(服务内容)        |
| ------------- |:-------------|:-------------|
|/map_status_feedback | operation/status     | int32 sta |
|/cmd_vel | geometry_msgs/Twist    | geometry_msgs/Vector3 linear</br>&ensp;&ensp;float64 x</br> &ensp;&ensp;float64 y</br> &ensp;&ensp;float64 z </br> geometry_msgs/Vector3 angular</br> &ensp;&ensp;float64 x</br> &ensp;&ensp;float64 y</br> &ensp;&ensp;float64 z|
|/status | std_msgs/Int32     | int32 data |
