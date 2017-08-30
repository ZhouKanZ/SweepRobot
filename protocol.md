| Service Name(服务名称)       |Service content(服务内容)        |
| ------------- |:-------------:|
| gpsbot_navigation/execute_nav_task.srv      
| int32 map_id</br> 
string map_name </br>
int32 task_id</br> 
int32 rate 
---
float64[] data </br>  
string errormsg</br>
bool successed |
| gpsbot_navigation/edit_nav_task.srv     | int32 map_id</br>string map_name</br>int32 type  1 删除 2 添加 3更新</br>int32[] nav_id</br>int32 task_id</br>---float64[] data</br>string errormsg</br>bool successed      |
| operation/edit_wall_set | basic_msgs/edit_wall edit_wall</br>int32 mapid</br>string mapname</br>int32[] wall_id</br>---float64[] data</br>string errormsg</br>bool successed    |
|gpsbot_navigation/nav_flag.srv|int32 map_id</br>string map_name</br>int32 nav_flag</br>---</br>float64[] data</br>string errormsg</br>bool successed|
