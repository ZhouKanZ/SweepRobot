# SweepRobot
gps project

### 技术点：

#### RxJava2操作符:

#### 1.rxjava2  轮询

      Displosable dis =  Observable
                .interval(2, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(@NonNull Long aLong) throws Exception {
                        return Http.getHttpService().downImage();
                    }
                })
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception {
                        Bitmap bitmap=BitmapFactory.decodeStream(responseBody.byteStream());
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);

      interval 表示请求周期
      flatMap  将网络请求转换成ObservableSource<ResponseBody>
      map      从ResponseBody中读取bitmap
      将结果回掉到consumer中

      dis.dispose() // 用来取消请求

#### 2.Activity 横屏生命周期

    配置如下参数 在下面三种情况下 将不改变activity的生命周期
    android:configChanges="orientation|keyboardHidden|screenSize"

#### 3.当发起一个advertise的时候，如何监听websocket中返回的信息

    由rxbus在onMessage中post，并在presenter中监听

#### 4.ros service 的作用用与模拟http方式的网络请求

    依旧是通过websocket和rxbus的方式
    
### 二.模块功能介绍 ：

* 创建地图

   发送指令<br>
   接收消息<br>
   保存数据<br>





