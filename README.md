# task
activity任务栈

很早就像做一个这样的博客了

其实activity任务栈的使用在Android中的使用还是很频繁的，想象一个场景，比如一个登陆的场景，你登录了以后想要安全退出

或者要新密码登陆，你是不是要重MainActivity跳转到LoginActivity，这样一来，当你从新从LoginActivity又重新跳转到了

MainActivity,就会产生两个MainActivity了，可以这样理解

l >> m

m >> l >>m  产生了两个m(MainActivity)

又或者有这样一个场景，在商城中支付的时候先是在商品的详情页，然后购买支付到了支付页面，还可能要跳转到支付成功的页面

当你支付成功了以后再跳转到你的购买页面，但是你之前的商品详情页，支付页面，等等都要finish()

所以以上是两个常见的需要使用activity任务栈来管理activity的场景，总结来说有四种方法可以用来处理这种事件

#1  用个list装起来全部的activity

用个list装起来全部的activity，每次打开一个activity的时候传个context进去，finish的时候移除就好，可以用BaseActivity封装好也不是特别麻烦，要干掉全部的时候，一个循环就干掉了

#2 用广播(本地广播)

用广播，就是之前写的RxBus或者eventBus，或者自己写，要退出了发送个退出的广播，要关掉的activity注册个监听接收广播后finish掉就好
1，先发送广播
 Intent  intent1 = new Intent("finish");
    sendBroadcast(intent1);

2，然后在要finish的Activity中注册广播

 private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals("finish")) {
                Toast.makeText(ThreeActivity.this, "好的", Toast.LENGTH_SHORT).show();
                ThreeActivity.this.finish();//接受到消息就会结束自身
            }

        }
    };

    //注册，不要忘记了在destroy（）方法中取消注册,不然可能会包oom哦

    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("finish");
   registerReceiver(mBroadcastReceiver, intentFilter);
#3 设置启动方式

standard 这个是默认的，正常都用这种,
singleInstance这个是坑爹的，一般不用，也最好不要乱用，它会新建多一个栈
手机查看所有启动应用的窗口再一键清除的嘛，具体表现就是用了以后，这里会有2个窗口。反正不用就是了

singleTop这个很不错
比如默认就是
A-B-C-D-D-D-D

用了这个后就是
A-B-C-D
D这个activity再打开自己，就不会new一个了，就会一直用自己这个

但是··············
不会重新调用
onCreate
而是调用
onNewIntent
这个方法了，所有新的数据啊什么的要放这里面
比如我传值过来


 @Override
    protected void onNewIntent(Intent intent) {
        //data= getIntent().getStringExtra("数据");    //坑点
        String data = intent.getStringExtra("数据");  //注意点
        super.onNewIntent(intent);
    }


    getIntent()是之前的intent数据
    新打开的intent的数据是(Intent intent)这里面intent的数据


    singleTask就是你了

    它的功能就是
    如果要启动的Activity不在，就创建新的然后放到栈顶。
    如果要启动的Activity已经在栈顶，就跟singleTop模式一样。
    如果要启动的Activity已经在于栈中，就会干掉这个Activty上面的所有其他Activty。

    举个栗子：
    1.A-B-C-D------E
    A-B-C-D-E

    2.A-B-C-D------D
    A-B-C-D------D

    3.A-B-C-D------B
    就变成
    A-B

    就是这么炫酷

    好回到上面的那个难题

    开始是
    M,S
    退出登录是
    M,L
    再登录用了这个以后就是
    M,L-----M就只剩下了M

    虽然退出登录并不能结束全部activity然后跳到LoginActivity，
    但是这样的逻辑才是最正确的，再登录以后就一切正常了。


    <activity
                android:name=".******.****.*******"
                android:launchMode="singleTop"   //这就行了
                android:screenOrientation="portrait" //这个是竖屏显示，表示不能横屏
                android:theme="@style/AppTheme"/>


#其实一般的MainActivity的启动模式都会设置成singleTask，这样才会保证任务栈中只有一个MainActivity实例



#4 当然也可以在intent跳转的时候给intent加个Flag，你喜欢就好，反正我不想那样加clearTaskOnLaunch