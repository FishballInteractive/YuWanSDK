#����FAQ���⣨�����С�������
---
##1.����APPID��APPKEY
Q����ʼ����Ҫ��AppId ��AppKey ����̳ID��λ�ȡ��<br>
A����ϵ���軥����Ӫ��Ա���䡣
##2.��������
Q���Ѿ�����
```
<meta-data  android:name="GAME_CHANNEL" android:value="YUWAN_CHANNEL" />
```
����̨û������ͳ�����ݣ�<br>
A:��������ļ���**meta-data**�ڵ�Ӧ������**application**�ڵ��ڡ�
##3.ANE����Ҫ�����Դ�Ĵ����ʽ��Ҫע�������
Q�����������������֧��ʱ��ʾ�Ҳ��������Դ�ļ���<br>
A���������ܰ�**UPPayPluginEx.jar**�������Դ�ļ��ó����ŵ���Դ�ļ������ٰ�������������ߡ�
##4.��λ��֧�����֪ͨ
Q����λ��֧�����֪ͨ��<br>
A��ʹ���첽����֧�����������Ϣ�ķ�ʽ֪ͨ������ҵ����������߷���������̵��С� Ӧ�ý��뷽��Ҫ��**RgBuyInfo**�лش�֪ͨ��ַ(��ѻش���ַ���ɷ���������)��
����ҵ���������Ҫ�����û���֧�����֪ͨ��
���������軥���ƶ�����ƽ̨���������͸�����Ӧ�÷����֧����������
����˶Խӷ�ʽ����Ӧ�����ݸ�ʽ����μ�[����˶Խ�˵���ĵ�][1]��

##5.ע���˺�ʱ��������Ϸû�з�Ӧ
Q��SDK�����ע���˺�ʱ��������Ϸû�з�Ӧ<br>
A����Ҫ�ڳ�ʼ��SDKʱ�ص������CP�Լ��Ĵ����߼����磺
```java
RgCommplatform.rgInit(this, appInfo, new OnInitCompleteListener() {
    		@Override
			public void onInitComplete(int initCode) {
				if (initCode == RgConstant.INIT_SUCCESS) {
					RgCommplatform.addOnLogoutListener(new OnLogoutListener() {
						@Override
						public void onLogout() {
// �л��ʺŴ����߼������´����Ϊʾ�����룬cp�ɸ���������Ҫ���в����������µ�����¼�����
							Intent i = getPackageManager()
									.getLaunchIntentForPackage(getPackageName());
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
							startActivity(i);
						}
					});
					RgCommplatform.rgLogin(mLoginCallback);
					// TODO ��ʼ����Ϸ
					initGame();
				}
			}
		});

```
[1]:server.md "����˶Խ�˵���ĵ�"