| Service Name(服务名称)       |Service content(服务内容)        |
| ------------- |:-------------:|
| gpsbot_navigation/execute_nav_task.srv      | int32 map_id</br> string map_name </br>int32 task_id</br> int32 rate ---float64[] data </br>  string errormsg</br>bool successed |
| gpsbot_navigation/edit_nav_task.srv     | int32 map_id
string map_name
int32 type  1 删除 2 添加 3更新
int32[] nav_id
int32 task_id

---
float64[] data
string errormsg
bool successed      |
| zebra stripes | are neat      |
