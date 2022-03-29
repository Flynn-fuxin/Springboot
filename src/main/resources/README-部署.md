                                dvb-server系统文档
#环境部署
###1、系统环境
 * Java EE 8
 * Gradle 7.1.1
 * dvb-server 2.8.0
###2、主框架
 * mumblesdk_version=3.1.7.4
 * mysql
###3、准备工作

* JDK >= 1.8 (推荐1.8版本)
* Mysql >= 5.7.0 (推荐5.7版本)


# 运行系统
###1、前往下载
```
git@git.weoa.com:calveinchen/dvb-server.git
```
###2、导入 并配置Java环境  gradle 环境 初次加载会比较慢（根据自身网络情况而定）
###3、创建数据库 dvb_server 并导入数据脚本 init_dvb_server.sql，所在目录：
```
https://wes.test.webank.com:8443/gitea/nlu/dvb-sever/src/branch/poc/src/main/resources/sql
```
###4、打开项目运行 cn.webank.dvbserver.system.server.DvbServer.java，出现如下图表示启动成功。
```
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//            佛祖保佑       永不宕机      永无BUG                   //
////////////////////////////////////////////////////////////////////
  _______      ______        _____ ______ _______      ________ _____
|  __ \ \    / /  _ \      / ____|  ____|  __ \ \    / /  ____|  __ \
| |  | \ \  / /| |_) |____| (___ | |__  | |__) \ \  / /| |__  | |__) |
| |  | |\ \/ / |  _ <______\___ \|  __| |  _  / \ \/ / |  __| |  _  /
| |__| | \  /  | |_) |     ____) | |____| | \ \  \  /  | |____| | \ \
|_____/   \/   |____/     |_____/|______|_|  \_\  \/   |______|_|  \_\
```


#必要配置
###修改数据库连接，编辑  resources 目录下的 dvb-server.properties
###数据源配置
```
jdbc.user=数据库账号
jdbc.password=数据库密码
jdbc.url=数据库地址
```
###redis 服务器配置
```
redis.database=1
redis.host=redis 地址
redis.port=6379
redis.password=
```

###TTS配置
```
dvb.tts.server= tts地址
#TTS是否启动代理
dvb.tts.proxy.enable=false
#TTS代理地址
dvb.tts.proxy.ip=172.16.153.103
#TTS代理端口
dvb.tts.proxy.port=3128
```

###nlu配置
```
#请求NLU推荐
dvb.nlu.sentence= nlu地址/dvb-nlu/api/v1/search_similar_sentence
#获取推荐
dvb.nlu.recommend= nlu地址/dvb-nlu/api/v1/recommend
#nlu是否启动代理
dvb.nlu.proxy.enable=false
#nlu代理地址
dvb.nlu.proxy.ip=172.16.153.103
#nlu代理端口
dvb.nlu.proxy.port=3128
```
###dm配置
```
#测试DM机器人地址
dvb.dm.test-robot-url= dm地址/ivb-dm/api/v1/chat
#传输机器人信息
dvb.dm.post-robot-info= dm地址/ivb-dm/api/v1/config
#dm是否启动代理
dvb.dm.proxy.enable=false
#dm代理地址
dvb.dm.proxy.ip=172.16.153.103
#dm代理端口
dvb.dm.proxy.port=3128
```

###job配置
```
#华云拨号地址
dvb.job.hua-cloud-call= job地址/dvb-job/outbound/huaCloud/makeCall
#线路地址
dvb.job.line.addr= job地址/dvb-job/gateway/
#验签加密
dvb.job.salt=darling_huaCloud2020!#%&
#job是否启动代理
dvb.job.proxy.enable=false
#job代理地址
dvb.job.proxy.ip=172.16.153.103
#job代理端口
dvb.job.proxy.port=3128
```

###Fes配置
```
#前置会话登录
dvb.fes.login=http://fes地址/fes一级目录/fes/login
#前置音频地址
#dvb.fes.ws-url=http://fes地址/fes一级目录
#dvb.fes.ws-url=wss://fes地址/fes一级目录
dvb.fes.ws-url-huacloud=wss://fes地址/fes一级目录
dvb.fes.ws-url-xiangclound=ws://{}:5590/d-dvb-fes
#前置腾讯云加密key
dvb.fes.key=3052c609ff0fc420042adc8e26d2ef7b3e452576231f86c001d818993f36fc42
#前置是否启动代理
dvb.fes.proxy.enable=false
#前置代理地址
dvb.fes.proxy.ip=172.16.153.103
#前置代理端口
dvb.fes.proxy.port=3128
```


###Fps信息配置
```
dvb.server.fps.user=dvb-fes
dvb.server.fps.password= fps密码
dvb.server.fps.proxyHost= fps ip
dvb.server.fps.proxyPort= fps 端口
dvb.server.fps.enable=true
dvb.server.fps.host= fps代理地址
dvb.server.fps.port= fps代理端口
```


##部署系统
###1.打包
在 dvb_server 项目的 gradle插件下 生成tar/jar包文件:
```
gradle tar
gradle jar
```
然后会生成文件夹包含tar或jar:
```
dist/tar
build/lib/jar
```
###2. jar包方式发布

```
上传路径:
cd /data/appsystems/dvb-server/apps/

上传jar:
rz -be 

停止服务:
sh dvb-server/bin/stop.sh

启动服务:
sh dvb-server/bin/start.sh
```


###3. war部署方式一
```
上传目录
cd /data/appsystems/d/server/

上传tar：
rz -be

解压：
tar -zxvf dvb-server.tar.gz

编译并启动：
./install.sh -c sit -b false 
或者
./install.sh -c sit -b false -p /data/appsystems/dvb-server/
```

###4. war部署方式二：
通过APOM 方式部署

##常见问题






