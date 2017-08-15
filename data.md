### 数据格式统计


#### 1.机器人位置 

    {
        "msg": {
            "orientation": {
                "y": 0.9222127509445808,
                "z": 2.4710595290954823e-8,
                "x": -0.3866828700566241,
                "w": 1.0361127514369314e-8
            },
            "position": {
                "y": 993.0124389157891,
                "z": 0.000053215331216791324,
                "x": 1066.8206502206713
            }
        },
        "op": "publish",
        "topic": "picture_pose"
    }
    
#### 2.激光点位置

    {
        "msg": {
            "data": [
                {
                    "y": 47.345045897670836,
                    "x": 51.07507311781742
                }
         
            ]
        },
        "op": "publish",
        "topic": "laser_pose_picture"
    }
    
####  service_response
    
    服务不存在的情况
    {
        "op": "service_response",
        "service": "/mapping_status",
        "result": false,
        "values": "Service /mapping_status does not exist"
    }
