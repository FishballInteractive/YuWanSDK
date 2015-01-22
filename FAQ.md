#常见FAQ问题（整理中……）：
---
##1.关于APPID和APPKEY
Q：初始化需要的AppId ，AppKey ，论坛ID如何获取？<br>
A：联系鱼丸互动运营人员分配。
##2.配置问题
Q：已经配置
```
<meta-data  android:name="GAME_CHANNEL" android:value="YUWAN_CHANNEL" />
```
但后台没有渠道统计数据？<br>
A:检查配置文件，**meta-data**节点应配置在**application**节点内。
##3.ANE等需要拆分资源的打包方式需要注意的问题
Q：打包后程序调用银联支付时提示找不到相关资源文件？<br>
A：把银联架包**UPPayPluginEx.jar**里面的资源文件拿出来放到资源文件夹下再按正常打包流程走。
##4.如何获得支付结果通知
Q：如何获得支付结果通知？<br>
A：使用异步购买，支付结果将以消息的方式通知到您的业务服务器或者服务端虚拟商店中。 应用接入方需要在**RgBuyInfo**中回传通知地址(或把回传地址交由服务器配置)。
您的业务服务器需要处理用户的支付结果通知。
接收由鱼丸互动移动开发平台服务器发送给各个应用服务的支付购买结果。
服务端对接方式和相应的数据格式具体参见[服务端对接说明文档][1]。

##5.注销账号时点重启游戏没有反应
Q：SDK界面点注销账号时点重启游戏没有反应<br>
A：需要在初始化SDK时回调里添加CP自己的处理逻辑，如：
```java
RgCommplatform.rgInit(this, appInfo, new OnInitCompleteListener() {
    		@Override
			public void onInitComplete(int initCode) {
				if (initCode == RgConstant.INIT_SUCCESS) {
					RgCommplatform.addOnLogoutListener(new OnLogoutListener() {
						@Override
						public void onLogout() {
// 切换帐号处理逻辑，以下代码仅为示例代码，cp可根据自身需要进行操作，如重新弹出登录界面等
							Intent i = getPackageManager()
									.getLaunchIntentForPackage(getPackageName());
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
							startActivity(i);
						}
					});
					RgCommplatform.rgLogin(mLoginCallback);
					// TODO 初始化游戏
					initGame();
				}
			}
		});

```
[1]:server.md "服务端对接说明文档"